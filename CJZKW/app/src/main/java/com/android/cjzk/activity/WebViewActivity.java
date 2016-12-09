package com.android.cjzk.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.webkit.ValueCallback;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.cjzk.Api.ApiManager;
import com.android.cjzk.Bean.CommentBean;
import com.android.cjzk.Bean.LoginBean;
import com.android.cjzk.Bean.ShareBean;
import com.android.cjzk.Bean.collectioBean;
import com.android.cjzk.R;
import com.android.cjzk.Utility.Utility;
import com.jakewharton.rxbinding.view.RxView;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import imageShow.ImageShowActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.functions.Action1;
import utils.UmengOneKeyShare;

public class WebViewActivity extends MyBaseActivity2 {

    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.rel_share)
    RelativeLayout relShare;
    @BindView(R.id.rel_collect)
    RelativeLayout relCollect;
    @BindView(R.id.rel_comment)
    RelativeLayout relComment;
    @BindView(R.id.br_cancel)
    Button brCancel;
    @BindView(R.id.br_send)
    Button brSend;
    @BindView(R.id.et_comment)
    EditText etComment;
    @BindView(R.id.lin_comment)
    LinearLayout linComment;
    @BindView(android.R.id.empty)
    ProgressBar progressBar;
    @BindView(R.id.rel_back)
    RelativeLayout relBack;
    @BindView(R.id.rel_comment1)
    RelativeLayout relComment1;
    private String ArtCtId;
    private boolean isCollection;
    private String WebViewBaseUrl = "http://www.cjzkw.cn/Home/MobileArticle/";
    private UmengOneKeyShare oneKeyShare;

    @Override
    protected void onCreateT(Bundle savedInstanceState) {
        Utility.showImmerseStatusBar(R.color.text_color, this);
        Utility.StatusBarLightMode(this);
        ArtCtId = getIntent().getStringExtra("ArtCtId");
        if (null == ArtCtId) {
            return;
        }
        isCollection = getIntent().getBooleanExtra("collection", false);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.loadUrl(WebViewBaseUrl + ArtCtId);
//        isCollection(Utility.getSharedData(this, "token"), ArtCtId);
        addClient();
        addListener();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_web_view;
    }

    private void addClient() {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                getInterceptUrl(view, url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
                isCollection(Utility.getSharedData(WebViewActivity.this, "token"), ArtCtId);
            }
        });
    }

    private void addListener() {
        RxView.clicks(relComment).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (!linComment.isShown()) {
                    linComment.setVisibility(View.VISIBLE);
                    Animation animation = AnimationUtils.loadAnimation(WebViewActivity.this, R.anim.xiu_gift_slide_in_from_bottom);
                    linComment.startAnimation(animation);
                    animation = null;
                }
            }
        });
        RxView.clicks(brCancel).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                etComment.setText("");
                InputMethodManager imm = (InputMethodManager) getSystemService(WebViewActivity.this.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(brCancel.getWindowToken(), 0);
                linComment.setVisibility(View.GONE);
                Animation animation = AnimationUtils.loadAnimation(WebViewActivity.this, R.anim.xiu_gift_slide_out_to_bottom);
                linComment.startAnimation(animation);
                animation = null;
            }
        });

        RxView.clicks(brSend).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                String text = etComment.getText().toString();
                if (text.equals("")) {
                    Toast.makeText(WebViewActivity.this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                getCommentData(ArtCtId, text, 1, 1, Utility.getSharedData(WebViewActivity.this, "token"));
            }
        });

        RxView.clicks(relCollect).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (!Utility.getSharedData(WebViewActivity.this, "token").equals("")) {
                    if (isCollection) {
                        getCollectionCancle(ArtCtId, Utility.getSharedData(WebViewActivity.this, "token"));
                    } else {
                        getCollectionData(ArtCtId, Utility.getSharedData(WebViewActivity.this, "token"));
                    }
                } else {
                    Utility.showLoadingDialog(WebViewActivity.this, "您还没有登录...");
                }
            }
        });
        RxView.clicks(relShare).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                testEvaluateJavascript(webView);
            }
        });
        RxView.clicks(relBack).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                finish();
            }
        });
        RxView.clicks(relComment1).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Intent intent = new Intent(WebViewActivity.this, CommentActivity.class);
                intent.putExtra("artId", ArtCtId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        webView.destroy();
        if (null != oneKeyShare) {
            oneKeyShare = null;
        }
        System.gc();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (linComment.isShown()) {
            linComment.setVisibility(View.GONE);
            etComment.setText("");
            Animation animation = AnimationUtils.loadAnimation(WebViewActivity.this, R.anim.xiu_gift_slide_out_to_bottom);
            linComment.startAnimation(animation);
            animation = null;
            return;
        } else {
            this.finish();
        }
    }

    private String UesrName() {
        return Utility.getSharedData(WebViewActivity.this, "userName").equals("") ? "" : Utility.getSharedData(WebViewActivity.this, "userName");
    }

    private void getCommentData(final String articleID, String content, int userIP, int parComId, String token) {
        ApiManager.getInstance().getCommentData(articleID, content, UesrName(), parComId, token).enqueue(new Callback<CommentBean>() {
            @Override
            public void onResponse(Call<CommentBean> call, Response<CommentBean> response) {
                if (null == response.body()) {
                    Toast.makeText(WebViewActivity.this, "网络连接失败，请稍后评论", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.body().isSucceed()) {
                    if (linComment.isShown()) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(WebViewActivity.this.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(linComment.getWindowToken(), 0);
                        linComment.setVisibility(View.GONE);
                        Animation animation = AnimationUtils.loadAnimation(WebViewActivity.this, R.anim.xiu_gift_slide_out_to_bottom);
                        linComment.startAnimation(animation);
                        animation = null;
                    }
                    etComment.setText("");
                    Toast.makeText(WebViewActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(WebViewActivity.this, "网络连接失败，请稍后评论", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommentBean> call, Throwable t) {
                Toast.makeText(WebViewActivity.this, "网络连接失败，请稍后评论", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private void getCollectionData(String articleCurrentID, String token) {
        ApiManager.getInstance().getCollectionData(articleCurrentID, token).enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                if (null == response.body()) {
                    Toast.makeText(WebViewActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.body().isSucceed()) {
                    Toast.makeText(WebViewActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                    relCollect.setSelected(true);
                    isCollection = true;
                } else {
                    Toast.makeText(WebViewActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                Toast.makeText(WebViewActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCollectionCancle(String articleCurrentID, String token) {
        ApiManager.getInstance().getCollectionCancle(articleCurrentID, token).enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                if (null == response.body()) {
                    Toast.makeText(WebViewActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.body().isSucceed()) {
                    Toast.makeText(WebViewActivity.this, "取消收藏成功", Toast.LENGTH_SHORT).show();
                    relCollect.setSelected(false);
                    isCollection = false;
                }
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                Toast.makeText(WebViewActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getWebViewData(String articleCurrentID, String token) {
        ApiManager.getInstance().getWebViewData(articleCurrentID, token).enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                if (null == response.body()) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(WebViewActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.body().isSucceed()) {
                    webView.loadUrl(response.body().getData());
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(WebViewActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(WebViewActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void testEvaluateJavascript(WebView web) {
        web.evaluateJavascript("getInfo()", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                String json = value.replace("\\", "");
                String json1 = json.substring(1, json.lastIndexOf("}") + 1);
                try {
                    JSONObject jsonObject = new JSONObject(json1);
                    ShareBean bean = new ShareBean(jsonObject);
                    if (null == oneKeyShare) {
                        oneKeyShare = new UmengOneKeyShare(WebViewActivity.this);
                    }
                    oneKeyShare.setOneShareBean(bean);
                    oneKeyShare.share();
                    jsonObject = null;
                    bean = null;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    class MyAsynTask extends AsyncTask<String, Integer, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            String value = params[0];
            String json = value.replace("\\", "");
            String json1 = json.substring(1, json.lastIndexOf("]"));
            String[] str = json1.split(",");
            ArrayList<String> imgsUrl = new ArrayList<String>();
            for (int i = 0; i < str.length; i++) {
                String img = str[i].split(" ")[0];
                if (!img.isEmpty()) {
                    imgsUrl.add(img.substring(1, img.lastIndexOf("\"")));
                }
                img = null;
            }
            value = null;
            json = null;
            json1 = null;
            str = null;
            return imgsUrl;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            Intent intent = new Intent();
            intent.putStringArrayListExtra("infos", strings);
            intent.setClass(WebViewActivity.this, ImageShowActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            super.onPostExecute(strings);
        }
    }

    private void getImageUrl(WebView web) {
        web.evaluateJavascript("getimgsrc()", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                if (null == value) return;
                new MyAsynTask().execute(value);
            }
        });
    }

    private void getInterceptUrl(final WebView web, final String url) {
        if (url.equals("share://wetime")) {
            testEvaluateJavascript(web, url, SHARE_MEDIA.WEIXIN_CIRCLE);
        } else if (url.equals("share://qzone")) {
            testEvaluateJavascript(web, url, SHARE_MEDIA.QZONE);
        } else if (url.equals("share://qq")) {
            testEvaluateJavascript(web, url, SHARE_MEDIA.QQ);
        } else if (url.equals("share://wechat")) {
            testEvaluateJavascript(web, url, SHARE_MEDIA.WEIXIN);
        } else if (url.equals("cjzkw://imgsrc")) {
            getImageUrl(web);
        } else {
            web.loadUrl(url);
        }
    }

    private void testEvaluateJavascript(final WebView web, final String url, final SHARE_MEDIA platform) {
        web.evaluateJavascript("getInfo()", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                if (null == value) return;
                String json = value.replace("\\", "");
                String json1 = json.substring(1, json.lastIndexOf("}") + 1);
                try {
                    JSONObject jsonObject = new JSONObject(json1);
                    ShareBean bean = new ShareBean(jsonObject);
                    if (null == oneKeyShare) {
                        oneKeyShare = new UmengOneKeyShare(WebViewActivity.this);
                    }
                    oneKeyShare.setOneShareBean(bean);
                    oneKeyShare.addQQPlatforms(platform);
                    jsonObject = null;
                    bean = null;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //    relCollect.setSelected(isCollection);
    private void isCollection(String token, String artCtId) {
        if (TextUtils.isEmpty(token)) {
            return;
        }
        if (isCollection) {
            relCollect.setSelected(isCollection);
            return;
        }
        ApiManager.getInstance().isCollectio(token, artCtId).enqueue(new Callback<collectioBean>() {
            @Override
            public void onResponse(Call<collectioBean> call, Response<collectioBean> response) {
                if (null == response.body()) {
                    return;
                }
                isCollection = response.body().getData().isCollection();
                relCollect.setSelected(isCollection);
            }

            @Override
            public void onFailure(Call<collectioBean> call, Throwable t) {
            }
        });
    }
}
