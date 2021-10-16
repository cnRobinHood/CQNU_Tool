package com.example.cqnu_tool;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.cqnu_tool.databinding.ActivityMainBinding;


import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "aaa";
    public WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mTimetableView=(TimetableView) findViewById(R.id.id_timetableView);
//        mTimetableView.setDataSource(subjectBeans)
//                .setCurTerm("大三上学期")
//                .setCurWeek(curWeek)
//                .setOnSubjectItemClickListener(this)
//                .showTimetableView();
//
//        //调用过showSubjectView后需要调用changWeek()
//        //第二个参数为true时在改变课表布局的同时也会将第一个参数设置为当前周
//        //第二个参数为false时只改变课表布局
//        mTimetableView.changeWeek(curWeek, true);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.wv_get_cookie);
        webView.getSettings().setJavaScriptEnabled(true);
        String ua = webView.getSettings().getUserAgentString();
        webView.getSettings().setUserAgentString("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.71 Safari/537.36");
        Log.d(TAG, "onCreate: " + webView.getSettings().getUserAgentString());
        webView.setWebViewClient(new MyWebViewClient(this,webView) {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

        });
        webView.loadUrl("https://csxrz.cqnu.edu.cn/cas/login?service=https%3A%2F%2Fcsxmh.cqnu.edu.cn%2FPersonalApplications%2FviewPage%3Factive_nav_num%3D1");


    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
            webView = null;//释放资源
        }
        super.onDestroy();
    }
}