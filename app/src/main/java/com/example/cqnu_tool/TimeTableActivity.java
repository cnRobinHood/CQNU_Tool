package com.example.cqnu_tool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.cqnu_tool.entity.CourseInfo;
import com.example.cqnu_tool.jsonutil.JsonsRootBean;
import com.example.cqnu_tool.jsonutil.Kblist;
import com.zhuangfei.timetable.TimetableView;
import com.zhuangfei.timetable.listener.ISchedule;
import com.zhuangfei.timetable.listener.IWeekView;
import com.zhuangfei.timetable.listener.OnItemBuildAdapter;
import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.view.WeekView;


import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.http2.Header;

public class TimeTableActivity extends AppCompatActivity {
    private static final String TAG = "bossliu";
    private List<CourseInfo> courseInfos = new ArrayList<>();
    private ConcurrentHashMap<String, List<Cookie>> cookieStore = new ConcurrentHashMap<>();
    private String final_session;
    TimetableView mTimetableView;
    WeekView mWeekView;
    private Handler mhandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //List<Integer> list= new ArrayList<>();
            switch (msg.what) {
                case 1:
                    Log.d(TAG, "handleMessage: "+final_session);
                    getCourseInfo(final_session);
                    break;
                case 2:
                    mWeekView.source(courseInfos).showView();
                    mTimetableView.source(courseInfos).showView();
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        initTimetableView();
        Log.d(TAG, "onCreate: "+getIntent().getStringExtra("session"));
        final_session = getNewCookie(getIntent().getStringExtra("session"));

    }

    private void getCourseInfo(String session) {
        RequestBody requestBody = new FormBody.Builder().add("xnm", "2021").add("xqm", "3").build();
        Log.d(TAG, "getCourseInfo: "+session);
        Request request = new Request.Builder()
                .url("https://yjsxt.cqnu.edu.cn/yjsxt/kbcx/xskbcx_cxXsKb.html").addHeader("cookie", "JSESSIONID=281F46E1361FF65AA78EA2E4AA77E3CB; route=b4242e6dd44e76b338743664f27d8bdf").addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.71 Safari/537.36")
                .post(requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                Log.d(TAG, "onResponse: "+data);
                JsonsRootBean jsonsRootBean = JSON.parseObject(data, JsonsRootBean.class);
                for (Kblist kblist : jsonsRootBean.getKblist()
                ) {
                    CourseInfo courseInfo = new CourseInfo();
                    courseInfo.setCourseAddress(kblist.getCdmc());
                    courseInfo.setCourseLength(kblist.getJcs());
                    courseInfo.setTeacherName(kblist.getXm());
                    courseInfo.setCourseName(kblist.getKcmc());
                    courseInfo.setCourseWeek(kblist.getXqj());
                    courseInfos.add(courseInfo);
                }
                LitePal.deleteAll(CourseInfo.class);
                LitePal.markAsDeleted(courseInfos);
                LitePal.saveAll(courseInfos);
                mhandler.sendEmptyMessage(2);
            }
        });
    }
    //处理重定向的拦截器
    public class RedirectInterceptor implements Interceptor {

        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            okhttp3.Request request = chain.request();
            Response response = chain.proceed(request);
            int code = response.code();
            Log.d(TAG, "intercept: "+code);
            if (code == 302) {
                //获取重定向的地址

                String location = response.headers().get("Location");
                Log.d(TAG, "intercept: "+location);
                //重新构建请求
                Request newRequest = request.newBuilder().url(location).build();
                response = chain.proceed(newRequest);
            }
            return response;
        }
    }


    private String getNewCookie(String oldCookie) {
        //OkHttp3CookieHelper cookieHelper = new OkHttp3CookieHelper();
        //cookieHelper.setCookie("http://csxrz.cqnu.edu.cn/cas/login?service=https%3a%2f%2fyjsxt.cqnu.edu.cn%2fsso%2fzllogin", "cookie", oldCookie);
        final String[] session = new String[1];
        Request request = new Request.Builder()
                .url("http://csxrz.cqnu.edu.cn/cas/login?service=https%3a%2f%2fyjsxt.cqnu.edu.cn%2fsso%2fzllogin").addHeader("cookie", oldCookie).addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.71 Safari/537.36")
                .get()
                .build();
        final String[] cookie = new String[1];
        OkHttpClient okHttpClient = new OkHttpClient.Builder().followRedirects(false).followSslRedirects(false).addInterceptor(new RedirectInterceptor())
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //response.
                Log.d(TAG, "onResponse: size"+response.headers("Set-Cookie").size());
                for (String s:response.headers("Set-Cookie")
                     ) {
                    Log.d(TAG, "onResponse: "+s);
                }

                if (response.headers("Set-Cookie").size() == 2) {
                    final_session = response.headers("Set-Cookie").get(0)+" ;"+response.headers("Set-Cookie").get(1);
                    final_session = final_session.replace(" ","");
                    final_session = final_session.replace(";Path=/sso;HttpOnly","");
                    final_session = final_session.replace(";Path=/","");
                    Log.d(TAG, "onResponse: "+final_session);
                    mhandler.sendEmptyMessage(1);

                    Log.d(TAG, "onResponse: "+final_session);
                }
            }
        });
        return session[0];
    }

    private void initTimetableView() {
        //获取控件
        mWeekView = findViewById(R.id.id_weekview);
        mTimetableView = findViewById(R.id.id_timetableView);

        //设置周次选择属性
        mWeekView.curWeek(1)
                .callback(new IWeekView.OnWeekItemClickedListener() {
                    @Override
                    public void onWeekClicked(int week) {
                        int cur = mTimetableView.curWeek();
                        //更新切换后的日期，从当前周cur->切换的周week
                        mTimetableView.onDateBuildListener()
                                .onUpdateDate(cur, week);
                        mTimetableView.changeWeekOnly(week);
                    }
                })
                .callback(new IWeekView.OnWeekLeftClickedListener() {
                    @Override
                    public void onWeekLeftClicked() {
                        //onWeekLeftLayoutClicked();
                    }
                })
                .isShow(false)//设置隐藏，默认显示
                .showView();

        mTimetableView.curWeek(1)
                .curTerm("大三下学期")
                .callback(new ISchedule.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, List<Schedule> scheduleList) {
                        //display(scheduleList);
                    }
                })
                .callback(new ISchedule.OnItemLongClickListener() {
                    @Override
                    public void onLongClick(View v, int day, int start) {
//                        Toast.makeText(SimpleActivity.this,
//                                "长按:周" + day  + ",第" + start + "节",
//                                Toast.LENGTH_SHORT).show();
                    }
                })
                .callback(new ISchedule.OnWeekChangedListener() {
                    @Override
                    public void onWeekChanged(int curWeek) {
                        //titleTextView.setText("第" + curWeek + "周");
                    }
                })
                .callback(new OnItemBuildAdapter() {
                    @Override
                    public void onItemUpdate(FrameLayout layout, TextView textView, TextView countTextView, Schedule schedule, GradientDrawable gd) {
                        super.onItemUpdate(layout, textView, countTextView, schedule, gd);
//                        if(schedule.getName().equals("【广告】")){
//                            layout.removeAllViews();
//                            ImageView imageView=new ImageView(SimpleActivity.this);
//                            imageView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
//                            layout.addView(imageView);
//                            String url= (String) schedule.getExtras().get(MySubject.EXTRAS_AD_URL);
//
//                            Glide.with(SimpleActivity.this)
//                                    .load(url)
//                                    .into(imageView);
//
//                            imageView.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    Toast.makeText(SimpleActivity.this,"进入广告网页链接",Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
                    }
                })
                .showView();
    }
}