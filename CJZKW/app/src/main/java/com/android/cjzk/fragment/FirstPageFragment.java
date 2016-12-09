package com.android.cjzk.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.android.cjzk.R;
import com.android.cjzk.activity.MyBaseActivity;
import com.android.cjzk.activity.SeekActivity;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

public class FirstPageFragment extends MyBaseFragment {

    private static final String TAG = "FirstPageFragment";
    @BindView(R.id.openDrawer)
    RelativeLayout openDrawer;
    @BindView(R.id.newsTab01)
    Button newsTab01;
    @BindView(R.id.newsTab02)
    Button newsTab02;
    @BindView(R.id.relRefresh)
    RelativeLayout relRefresh;
    @BindView(R.id.relSeek)
    RelativeLayout relSeek;
    private FirstPageFragmentOne firstPageFragmentOne;
    private FirstPageFragmentTwo firstPageFragmentTwo;
    private boolean isRefresh = true;

    public FirstPageFragment() {
    }

    public static FirstPageFragment newInstance() {
        FirstPageFragment fragment = new FirstPageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != savedInstanceState) {
            FragmentManager manager = getActivity().getSupportFragmentManager();
            firstPageFragmentOne = (FirstPageFragmentOne) manager.findFragmentByTag("firstPageFragmentOne");
            firstPageFragmentTwo = (FirstPageFragmentTwo) manager.findFragmentByTag("firstPageFragmentTwo");
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_first_page;
    }

    @Override
    protected void onCreateViewT() {
        RxView.clicks(newsTab01).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        setTabSelection(1);
                    }
                });
        RxView.clicks(newsTab02).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        setTabSelection(2);
                    }
                });
        RxView.clicks(relRefresh).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (isRefresh) {
                    firstPageFragmentOne.refresh();
                } else {
                    firstPageFragmentTwo.refresh();
                }
            }
        });
        RxView.clicks(relSeek).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startActivity(new Intent(getActivity(), SeekActivity.class));
            }
        });
        setTabSelection(1);
    }

    @OnClick(R.id.openDrawer)
    public void openDrawer() {
        MyBaseActivity activity = (MyBaseActivity) getActivity();
        activity.openDrawer();
    }

    private void setTabSelection(int index) {
        clearSelection();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 1:
                newsTab01.setSelected(true);
                isRefresh = true;
                if (null == firstPageFragmentOne) {
                    firstPageFragmentOne = new FirstPageFragmentOne();
                    transaction.add(R.id.FrameLayout_main_news, firstPageFragmentOne, "firstPageFragmentOne");
                } else {
                    transaction.show(firstPageFragmentOne);
                }
                break;
            case 2:
                newsTab02.setSelected(true);
                isRefresh = false;
                if (null == firstPageFragmentTwo) {
                    firstPageFragmentTwo = new FirstPageFragmentTwo();
                    transaction.add(R.id.FrameLayout_main_news, firstPageFragmentTwo, "firstPageFragmentTwo");
                } else {
                    transaction.show(firstPageFragmentTwo);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (null != firstPageFragmentOne) {
            transaction.hide(firstPageFragmentOne);
        }
        if (null != firstPageFragmentTwo) {
            transaction.hide(firstPageFragmentTwo);
        }

    }

    private void clearSelection() {
        newsTab01.setSelected(false);
        newsTab02.setSelected(false);
    }

}
