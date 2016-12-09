package com.android.cjzk.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.cjzk.Bean.ShareBean;
import com.android.cjzk.R;
import com.android.cjzk.Utility.MyApplication;
import com.android.cjzk.Utility.Utility;
import com.umeng.message.PushAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;

public abstract class MyBaseActivity extends AppCompatActivity {

    private Unbinder unbinder;
    protected Call call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        unbinder = ButterKnife.bind(this);
        Utility.showImmerseStatusBar(R.color.colorPrimary, this);
        MyApplication.getInstance().addActivity(this);
        PushAgent.getInstance(this).onAppStart();
        onCreateT(savedInstanceState);
    }

    protected abstract void onCreateT(Bundle savedInstanceState);

    protected abstract int getContentViewId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        MyApplication.getInstance().removeActivity(this);
        if (null != call) {
            call.cancel();
        }
        System.gc();
    }

    public void openDrawer() {
    }

    public void openShare(ShareBean bean) {

    }
}
