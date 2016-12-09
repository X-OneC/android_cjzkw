package com.android.cjzk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.cjzk.Api.ApiManager;
import com.android.cjzk.Bean.LoginBean;
import com.android.cjzk.R;
import com.android.cjzk.Utility.MyApplication;
import com.android.cjzk.Utility.Utility;
import com.jakewharton.rxbinding.view.RxView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.functions.Action1;

public class loginActivity extends MyBaseActivity1 {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_userName)
    EditText etUserName;
    @BindView(R.id.et_userPass)
    EditText etUserPass;
    @BindView(R.id.bt_ok)
    Button btOk;
    @BindView(R.id.bt)
    Button bt;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.bt_register)
    Button btRegister;
    @BindView(R.id.bt_pass)
    Button btPass;
//    private MeFragment instance;
//    private IChangeText iChangeText;

    @Override
    protected void onCreateT(Bundle savedInstanceState) {
        toolbar.setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.text_color));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back);

        RxView.clicks(bt).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                UMShareAPI.get(loginActivity.this).doOauthVerify(loginActivity.this, SHARE_MEDIA.WEIXIN, authListener);
            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
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

    @OnClick({R.id.bt_ok, R.id.bt_register, R.id.bt_pass})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_ok:
                String name = etUserName.getText().toString();
                String pass = etUserPass.getText().toString();
                if (name.equals("")) {
                    Toast.makeText(loginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pass.equals("")) {
                    Toast.makeText(loginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                getLoginData(name, pass);
                break;
            case R.id.bt_register:
                startActivity(new Intent(loginActivity.this, RegisterActivity.class));
                break;
            case R.id.bt_pass:
                break;
        }
    }

    private void getLoginData(String name, String password) {
        ApiManager.getInstance().getLoginData(name, password).enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                if (null == response.body()) {
                    Toast.makeText(loginActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.body().isSucceed()) {
                    Toast.makeText(loginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    Utility.putSharedData(loginActivity.this, "token", response.body().getData());
                    Utility.putSharedData(loginActivity.this, "userName", etUserName.getText().toString());
                    MyApplication.getInstance().finishAll();
                    Intent intent = new Intent(loginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    loginActivity.this.finish();
                } else {
                    Toast.makeText(loginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                Toast.makeText(loginActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    interface ChangeText {
//        void changeText(String text);
//    }

    private UMAuthListener authListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            String temp = "";
            for (String key : map.keySet()) {
                temp = temp + key + " : " + map.get(key) + "\n";
            }
            tv.setText(temp);
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            Toast.makeText(loginActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            Toast.makeText(loginActivity.this, "onCancel", Toast.LENGTH_SHORT).show();

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
