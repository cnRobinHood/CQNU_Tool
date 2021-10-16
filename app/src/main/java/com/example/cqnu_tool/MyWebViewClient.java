package com.example.cqnu_tool;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class MyWebViewClient extends WebViewClient {
    Activity activity;
    WebView webView;
    int count = 1;
    private static final String TAG = "liulaoban";

    public MyWebViewClient(Activity activity, WebView webView) {
        this.activity = activity;
        this.webView = webView;
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    public void onPageFinished(WebView view, String url) {
        CookieManager cookieManager = CookieManager.getInstance();
        String CookieStr = cookieManager.getCookie(url);
        if (CookieStr.contains("CASTGC")) {
                webView.destroy();
                Intent intent = new Intent(activity, TimeTableActivity.class);
                intent.putExtra("session", CookieStr);
                Log.d(TAG, "onPageFinished: "+CookieStr);
                activity.startActivity(intent);
                activity.finish();

            //activity.finish();
        }
        //Toast.makeText(activity,CookieStr,Toast.LENGTH_SHORT).show();
        super.onPageFinished(view, url);
    }

}
