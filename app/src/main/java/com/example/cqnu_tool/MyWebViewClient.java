package com.example.cqnu_tool;

import android.app.Activity;
import android.content.Intent;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class MyWebViewClient extends WebViewClient {
    String url;
    WebView view;
    Activity activity;
    TextView textView;
    WebView webView;
    int count=1;
    public MyWebViewClient(Activity activity,WebView webView)
    {
        //  this.textView =textView;
        this.activity =activity;
        this.webView = webView;
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    public void onPageFinished(WebView view, String url) {
        CookieManager cookieManager = CookieManager.getInstance();
        String CookieStr = cookieManager.getCookie(url);
        //Common.cookie = CookieStr;
        if (CookieStr.contains("route")) {

            if (++count==4) {
                webView.destroy();
                Intent intent = new Intent(activity, TimeTableActivity.class);
                intent.putExtra("session", CookieStr);
                activity.startActivity(intent);
                activity.finish();
            }

            //activity.finish();
        }
        //Toast.makeText(activity,CookieStr,Toast.LENGTH_SHORT).show();
        super.onPageFinished(view, url);
    }

}
