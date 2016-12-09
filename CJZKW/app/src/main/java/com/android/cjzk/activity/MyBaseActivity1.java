package com.android.cjzk.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.cjzk.R;
import com.android.cjzk.Utility.Utility;
import com.umeng.message.PushAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class MyBaseActivity1 extends AppCompatActivity {

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        unbinder = ButterKnife.bind(this);
        Utility.showImmerseStatusBar(R.color.colorPrimary, this);
//        MyApplication.getInstance().addActivity(this);
        PushAgent.getInstance(this).onAppStart();
        onCreateT(savedInstanceState);
    }

    protected abstract void onCreateT(Bundle savedInstanceState);

    protected abstract int getContentViewId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
//        MyApplication.getInstance().removeActivity(this);
        System.gc();
    }
}
