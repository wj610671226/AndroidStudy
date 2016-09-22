package com.example.jhtwl.zhcity.Activity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.jhtwl.zhcity.R;

public class NewsDetailWebViewActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton back;
    private ImageButton share;
    private ImageButton text_font;
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail_web_view);
        initView();
    }

    private void initView() {
        webView = (WebView) findViewById(R.id.webview);
        back = (ImageButton) findViewById(R.id.back);
        share = (ImageButton) findViewById(R.id.share);
        text_font = (ImageButton) findViewById(R.id.text_font);
        back.setOnClickListener(this);
        share.setOnClickListener(this);
        text_font.setOnClickListener(this);

        // 加载webView信息
        String url = getIntent().getStringExtra("url");
        webView.loadUrl(url);

        // webView设置信息
        WebSettings settings = webView.getSettings();
        // webView是否可以加载js
        settings.setJavaScriptEnabled(true);


        webView.setWebChromeClient(new WebChromeClient() {
            // 进度改变
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.e("进度改变", "newProgress = " + newProgress);
            }

            // 标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.e("标题", "title = " + title);
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            // 网页是否打开呢
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                Log.e("网页是否打开呢", "request = " + request);
                return super.shouldOverrideUrlLoading(view, request);
            }

            // 网页开始加载
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.e("网页开始加载", "url = " + url);
            }

            // 网页加载完毕
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.e("网页加载完毕", "url = " + url);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.share:
                Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.text_font:
                showChooseFontSizeDiagLog();
                break;
        }
    }


    private int currentItem = 2;
    /**
     * 显示选择字体的弹框
     */
    private void showChooseFontSizeDiagLog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String item[] = new String[]{"超大号字体", "大号字体", "正常字体", "小号字体",
                "超小号字体"};
        builder.setTitle("字体设置");
        builder.setSingleChoiceItems(item, currentItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                currentItem = i;
            }
        });

        builder.setNeutralButton("取消", null);
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int defultSize = 100;
                int dx = 30;
                switch (currentItem) {
                    case 0:
                        defultSize += 2 * dx;
                        break;
                    case 1:
                        defultSize += dx;
                        break;
                    case 2:
                        break;
                    case 3:
                        defultSize -= dx;
                        break;
                    case 4:
                        defultSize -= 2 * dx;
                        break;
                }
                WebSettings settings = webView.getSettings();
                settings.setTextZoom(defultSize);
            }
        });
        builder.show();
    }
}
