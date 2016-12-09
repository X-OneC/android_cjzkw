package com.android.cjzk.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.cjzk.Bean.ShareBean;
import com.android.cjzk.R;
import com.android.cjzk.Utility.MyApplication;
import com.android.cjzk.Utility.Utility;
import com.android.cjzk.fragment.FirstPageFragment;
import com.android.cjzk.fragment.MeFragment;
import com.android.cjzk.fragment.SchoolFragment;
import com.android.cjzk.fragment.SecondPageFragment;
import com.umeng.message.PushAgent;
import com.umeng.socialize.UMShareAPI;

import butterknife.BindView;
import utils.UmengOneKeyShare;

public class HomeActivity extends MyBaseActivity implements View.OnClickListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.tab01)
    RelativeLayout tab01;
    @BindView(R.id.tab02)
    RelativeLayout tab02;
    @BindView(R.id.tab03)
    RelativeLayout tab03;
    @BindView(R.id.tab04)
    RelativeLayout tab04;
    private FirstPageFragment firstPageFragment;
    private SecondPageFragment secondPageFragment;
    private SchoolFragment schoolFragment;
    private MeFragment meFragment;
    private long touchTime = 0;
    private long waitTime = 2000;
    private UmengOneKeyShare oneKeyShare;
//    private UmengShareUtils umengShareUtils;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_home;
    }

    @Override
    protected void onCreateT(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS,
                    Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.GET_ACCOUNTS};
            ActivityCompat.requestPermissions(this, mPermissionList, 100);
        }
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.enable();
        if (null != savedInstanceState) {
            FragmentManager manager = getSupportFragmentManager();
            firstPageFragment = (FirstPageFragment) manager.findFragmentByTag("firstPageFragment");
            secondPageFragment = (SecondPageFragment) manager.findFragmentByTag("secondPageFragment");
            schoolFragment = (SchoolFragment) manager.findFragmentByTag("schoolFragment");
            meFragment = (MeFragment) manager.findFragmentByTag("meFragment");
        }
        getScreenWidth(this);
        addListener();
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.LEFT);
        setTabSelection(1);


        String ArtCtId = getIntent().getStringExtra("ArtCtId");
        if (null != ArtCtId) {
            if (!ArtCtId.equals("")) {
                Intent i = new Intent(this, WebViewActivity.class);
                i.putExtra("ArtCtId", ArtCtId);
                i.putExtra("isCollection", false);
                startActivity(i);
            }
        }
    }

    private void addListener() {
        tab01.setOnClickListener(this);
        tab02.setOnClickListener(this);
        tab03.setOnClickListener(this);
        tab04.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab01:
                setTabSelection(1);
                break;
            case R.id.tab02:
                setTabSelection(2);
                break;
            case R.id.tab03:
                setTabSelection(3);
                break;
            case R.id.tab04:
                setTabSelection(4);
                break;
        }
    }

    private void setTabSelection(int index) {
        clearSelection();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 1:
                tab01.setSelected(true);
                if (null == firstPageFragment) {
                    firstPageFragment = new FirstPageFragment();
                    transaction.add(R.id.FrameLayout_main, firstPageFragment, "firstPageFragment");
                } else {
                    transaction.show(firstPageFragment);
                }
                break;
            case 2:
                tab02.setSelected(true);
                if (null == secondPageFragment) {
                    secondPageFragment = new SecondPageFragment();
                    transaction.add(R.id.FrameLayout_main, secondPageFragment, "secondPageFragment");
                } else {
                    transaction.show(secondPageFragment);
                }
                break;
            case 3:
                tab03.setSelected(true);
                if (null == schoolFragment) {
                    schoolFragment = new SchoolFragment();
                    transaction.add(R.id.FrameLayout_main, schoolFragment, "schoolFragment");
                } else {
                    transaction.show(schoolFragment);
                }
                break;
            case 4:
                tab04.setSelected(true);
                if (null == meFragment) {
                    meFragment = new MeFragment();
                    transaction.add(R.id.FrameLayout_main, meFragment, "meFragment");
                } else {
                    transaction.show(meFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (null != firstPageFragment) {
            transaction.hide(firstPageFragment);
        }
        if (null != secondPageFragment) {
            transaction.hide(secondPageFragment);
        }
        if (null != schoolFragment) {
            transaction.hide(schoolFragment);
        }
        if (null != meFragment) {
            transaction.hide(meFragment);
        }
    }

    private void clearSelection() {
        tab01.setSelected(false);
        tab02.setSelected(false);
        tab03.setSelected(false);
        tab04.setSelected(false);
    }

    @Override
    public void openDrawer() {
        super.openDrawer();
        if (!drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.openDrawer(Gravity.LEFT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void openShare(ShareBean bean) {
        super.openShare(bean);
        if (null == oneKeyShare) {
            oneKeyShare = new UmengOneKeyShare(this);
        }
        oneKeyShare.setOneShareBean(bean);
        oneKeyShare.share();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
            return;
        }
        long curentTime = System.currentTimeMillis();
        if ((curentTime - touchTime) >= waitTime) {
            Toast.makeText(HomeActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            touchTime = curentTime;
        } else {
            MyApplication.getInstance().exit();
        }
    }

    public static void getScreenWidth(Activity context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Utility.screenWidth = displayMetrics.widthPixels;
        Utility.screenHeight = displayMetrics.heightPixels;
    }

    @Override
    protected void onDestroy() {
        if (null != oneKeyShare) {
            oneKeyShare = null;
        }
        System.gc();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //    @Override
//    protected void onResume() {
//        super.onResume();
//        Bundle bun = getIntent().getExtras();
//        if (bun != null) {
//            Set<String> keySet = bun.keySet();
//            for (String key : keySet) {
//                String value = bun.getString(key);
//                Toast.makeText(HomeActivity.this, value, Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            Toast.makeText(HomeActivity.this, "null", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        setIntent(intent);
//    }
}
