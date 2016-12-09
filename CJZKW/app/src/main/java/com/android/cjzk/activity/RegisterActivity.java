package com.android.cjzk.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.cjzk.Api.ApiManager;
import com.android.cjzk.Bean.LoginBean;
import com.android.cjzk.R;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends MyBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_userName)
    EditText etUserName;
    @BindView(R.id.et_yx)
    EditText etYx;
    @BindView(R.id.et_userPass)
    EditText etUserPass;
    @BindView(R.id.et_AuserPass)
    EditText etAuserPass;
    @BindView(R.id.bt_ok)
    Button btOk;

    @Override
    protected void onCreateT(Bundle savedInstanceState) {
        toolbar.setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.text_color));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_register;
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

    @OnClick(R.id.bt_ok)
    public void onClick() {
        String name = etUserName.getText().toString();
        String yx = etYx.getText().toString();
        String pass = etUserPass.getText().toString();
        String aPass = etAuserPass.getText().toString();
        if (name.equals("")) {
            Toast.makeText(RegisterActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isEmailValid(yx)) {
            Toast.makeText(RegisterActivity.this, "请输入正确的邮箱", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pass.equals("")) {
            Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!aPass.equals(pass)) {
            Toast.makeText(RegisterActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        getRegisterData(name, pass, yx);
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private void getRegisterData(String name, String password, String email) {
        ApiManager.getInstance().getRegisterData(name, password, email).enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                if (null == response.body()) {
                    Toast.makeText(RegisterActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.body().isSucceed()) {
                    Toast.makeText(RegisterActivity.this, "注册成功，请登录", Toast.LENGTH_SHORT).show();
                    RegisterActivity.this.finish();
                } else {
                    Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
