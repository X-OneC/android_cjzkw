package com.android.cjzk.fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.android.cjzk.R;
import com.android.cjzk.activity.FilterActivity;
import com.android.cjzk.activity.NewsMoreActivity;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuLeftFragment extends MyBaseFragment {

    @BindView(R.id.bt01)
    Button bt01;
    @BindView(R.id.bt02)
    Button bt02;
    @BindView(R.id.bt03)
    Button bt03;
    @BindView(R.id.bt04)
    Button bt04;
    @BindView(R.id.bt05)
    RelativeLayout bt05;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_menu_left;
    }

    @Override
    protected void onCreateViewT() {
        RxView.clicks(bt01).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                setIntent(NewsMoreActivity.class, "独家原创", 11);
            }
        });
        RxView.clicks(bt02).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                setIntent(NewsMoreActivity.class, "股市早报", 12);
            }
        });
        RxView.clicks(bt03).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                setIntent(NewsMoreActivity.class, "涨停揭秘", 13);
            }
        });
        RxView.clicks(bt04).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                setIntent(NewsMoreActivity.class, "公告精选", 14);
            }
        });
        RxView.clicks(bt05).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Intent intent = new Intent(getActivity(), FilterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setIntent(Class cla, String name, int Type) {
        Intent intent = new Intent(getActivity(), cla);
        intent.putExtra("name", name);
        intent.putExtra("Type", Type);
        startActivity(intent);
    }
}
