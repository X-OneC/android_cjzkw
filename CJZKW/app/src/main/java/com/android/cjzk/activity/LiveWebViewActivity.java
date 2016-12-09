package com.android.cjzk.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.android.cjzk.R;
import com.android.cjzk.Utility.Utility;

import butterknife.BindView;

public class LiveWebViewActivity extends MyBaseActivity2 {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(android.R.id.empty)
    ProgressBar empty;
    private String WebViewBaseUrl;

    @Override
    protected void onCreateT(Bundle savedInstanceState) {
        Utility.showImmerseStatusBar(R.color.text_color, this);
        Utility.StatusBarLightMode(this);
        WebViewBaseUrl = getIntent().getStringExtra("ArtCtId");
        toolBar.setTitle("");
        toolBar.setTitleTextColor(getResources().getColor(R.color.text_color));
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.mipmap.back);
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        webview.loadUrl(WebViewBaseUrl);
        addClient();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_live_web_view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addClient() {
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (null != empty) {
                    empty.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (null != empty) {
                    empty.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        webview.destroy();
        System.gc();
        super.onDestroy();
    }
}
