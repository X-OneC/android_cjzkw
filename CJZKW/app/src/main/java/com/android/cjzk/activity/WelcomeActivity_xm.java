package com.android.cjzk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.android.cjzk.R;
import com.android.cjzk.Utility.Utility;

public class WelcomeActivity_xm extends MyBaseActivity2 {

    @Override
    protected void onCreateT(Bundle savedInstanceState) {
        Utility.showImmerseStatusBar(android.R.color.transparent, this);
        Utility.StatusBarLightMode(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(WelcomeActivity_xm.this, HomeActivity.class);
                String ArtCtId = getIntent().getStringExtra("ArtCtId");
                if (null != ArtCtId) {
                    if (!ArtCtId.equals("")) {
                        i.putExtra("ArtCtId", ArtCtId);
                    }
                }
                startActivity(i);
                finish();
            }
        }, 2000);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_welcome_xm;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}
