package com.jhtwl.googlestore.Activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jhtwl.googlestore.Bean.JavaScriptObject;
import com.jhtwl.googlestore.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class WebviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        WebView webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl("file:///android_asset/javaScriptCallNative.html");
        // String dataString = getFromAssets("javaScriptCallNative.html");
        // webView.loadDataWithBaseURL(null, dataString, "text/html", "utf-8", null);

        /**
         *  webView调用Android中的方法步骤
         *  1。WebSettings的setJavaScriptEnabled 设置为true
         *  2. addJavascriptInterface(Object obj, String name) 将对象暴露给JavaScrit
         *  3、在JavaScript脚本中通过name对象调用Android方法
         */
        WebSettings settings = webView.getSettings();
        // 设置webView可以使用JavaScript
        settings.setJavaScriptEnabled(true);
        // 将对象暴露给JavaScript脚本
        webView.addJavascriptInterface(new JavaScriptObject(), "objectTest");

        // 设置监听（webView中HTML加载的进度、标题等）
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Log.e("onProgressChanged", "进度改变 = " +  newProgress);
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                Log.e("onReceivedTitle", "收到标题 = " +  title);
                super.onReceivedTitle(view, title);
            }
        });

        // 设置监听（页面加载情况）
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.e("Loading", "shouldOverrideUrlLoading");
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.e("onPageFinished", "加载完成");
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.e("onPageStarted", "开始加载");
                super.onPageStarted(view, url, favicon);
            }
        });
    }

    private String getFromAssets(String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(
                    getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
