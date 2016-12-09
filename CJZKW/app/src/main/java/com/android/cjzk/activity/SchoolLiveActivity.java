package com.android.cjzk.activity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.android.cjzk.Bean.SchoolBean;
import com.android.cjzk.R;
import com.android.cjzk.Utility.Utility;

import butterknife.BindView;

import static android.R.id.empty;

public class SchoolLiveActivity extends MyBaseActivity2 {

    private static final String TAG = "SchoolLiveActivity";
    @BindView(empty)
    ProgressBar progressBar;
    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.framelayout)
    FrameLayout frameLayout;
    private WebChromeClient chromeClient = null;
    private View myView = null;
    private WebChromeClient.CustomViewCallback myCallBack = null;
    private String url = "http://e.vhall.com/webinar/inituser/";
    private SchoolBean.SchoolArticleList bean = null;
    private String userName;
    private String token;

    @Override
    protected void onCreateT(Bundle savedInstanceState) {
        bean = (SchoolBean.SchoolArticleList) getIntent().getSerializableExtra("bean");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebViewClient(new MyWebviewCient());
        chromeClient = new MyChromeClient();
        webView.setWebChromeClient(chromeClient);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setLoadWithOverviewMode(true);
        if (bean.isCheck()) {
            userName = Utility.getSharedData(this, "userName");
            token = Utility.getSharedData(this, "token");
            webView.loadUrl(url + bean.getRoomId() + "?email=" + userName + "@vhall.com&name=visitor&k=" + setToken(token));
//            webView.loadUrl("http://e.vhall.com/webinar/inituser/130389874?email=123456@vhall.com&k=10212a6101b94ea7906739dd3c527516");
        } else {
            webView.loadUrl(url + bean.getRoomId());
        }
        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState);
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_school_live;
    }

    @Override
    public void onBackPressed() {
        if (myView == null) {
            super.onBackPressed();
        } else {
            chromeClient.onHideCustomView();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        webView.saveState(outState);
    }

    @SuppressLint("JavascriptInterface")
    public void addJavaScriptMap(Object obj, String objName) {
        webView.addJavascriptInterface(obj, objName);
    }

    public class MyWebviewCient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            webView.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (null != progressBar) {
                progressBar.setVisibility(View.GONE);
            }
            super.onPageFinished(view, url);
        }
    }

    public class MyChromeClient extends WebChromeClient {

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            if (myView != null) {
                callback.onCustomViewHidden();
                return;
            }
            //设置横屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            frameLayout.removeView(webView);
            frameLayout.addView(view);
            myView = view;
            myCallBack = callback;
        }

        @Override
        public void onHideCustomView() {
            if (myView == null) {
                return;
            }
            //设置竖屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            frameLayout.removeView(myView);
            myView = null;
            frameLayout.addView(webView);
            myCallBack.onCustomViewHidden();
        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            return super.onConsoleMessage(consoleMessage);
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.clearFormData();
            webView.clearHistory();
            webView.clearSslPreferences();
            webView.destroy();
        }
        super.onDestroy();
    }

    private String setToken(String token) {
        return token.replace("-", "");
    }
}
