package com.android.cjzk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.android.cjzk.R;
import com.android.cjzk.Utility.Utility;

public class WelcomeActivity extends MyBaseActivity {

    @Override
    protected void onCreateT(Bundle savedInstanceState) {
        if (Utility.getSharedData(this, "one").equals("")) {
            startActivity(new Intent(WelcomeActivity.this, FirstWelcomeActivity.class));
            finish();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(WelcomeActivity.this, HomeActivity.class);
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


    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}
