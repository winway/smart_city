package com.example.smartcity.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smartcity.R;

public class RecommendThemeWebViewActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private WebView mWebView;
    private String mTitle;
    private TextView mServiceNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_theme_web_view);
        mToolbar = findViewById(R.id.tool_bar);
        mWebView = findViewById(R.id.web_view);
        mServiceNameTextView = findViewById(R.id.service_name_text_view);
        initData();
    }

    private void initData() {
        mToolbar.setNavigationIcon(R.mipmap.top_bar_left_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        String url = bundle.getString("url");
        mTitle = bundle.getString("title");
        mServiceNameTextView.setText(mTitle);
        mWebView.loadUrl(url);
        mWebView.requestFocusFromTouch();
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
    }
}