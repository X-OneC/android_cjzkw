package com.android.cjzk.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.android.cjzk.R;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

public class SchoolFragment extends MyBaseFragment {
    @BindView(R.id.schoolTab01)
    Button schoolTab01;
    @BindView(R.id.schoolTab02)
    Button schoolTab02;
    @BindView(R.id.relRefresh)
    RelativeLayout relRefresh;
    private SchoolPageFragmentOne schoolPageFragmentOne;
    private SchoolPageFragmentTwo schoolPageFragmentTwo;
    private boolean isRefresh = true;

    public SchoolFragment() {

    }

    public static SchoolFragment newInstance() {
        SchoolFragment fragment = new SchoolFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != savedInstanceState) {
            FragmentManager manager = getActivity().getSupportFragmentManager();
            schoolPageFragmentOne = (SchoolPageFragmentOne) manager.findFragmentByTag
                    ("schoolPageFragmentOne");
            schoolPageFragmentTwo = (SchoolPageFragmentTwo) manager.findFragmentByTag
                    ("schoolPageFragmentTwo");
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_school;
    }

    @Override
    protected void onCreateViewT() {
        RxView.clicks(schoolTab01).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        setTabSelection(1);
                    }
                });
        RxView.clicks(schoolTab02).throttleFirst(1, TimeUnit.SECONDS)
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
                    schoolPageFragmentOne.refresh();
                } else {
                    schoolPageFragmentTwo.refresh();
                }
            }
        });
        setTabSelection(1);
    }

    private void setTabSelection(int index) {
        clearSelection();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 1:
                schoolTab01.setSelected(true);
                isRefresh = false;
                if (null == schoolPageFragmentTwo) {
                    schoolPageFragmentTwo = new SchoolPageFragmentTwo();
                    transaction.add(R.id.FrameLayout_main_school, schoolPageFragmentTwo, "schoolPageFragmentTwo");
                } else {
                    transaction.show(schoolPageFragmentTwo);
                }
                break;
            case 2:
                schoolTab02.setSelected(true);
                isRefresh = true;
                if (null == schoolPageFragmentOne) {
                    schoolPageFragmentOne = new SchoolPageFragmentOne();
                    transaction.add(R.id.FrameLayout_main_school, schoolPageFragmentOne, "schoolPageFragmentOne");
                } else {
                    transaction.show(schoolPageFragmentOne);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (null != schoolPageFragmentOne) {
            transaction.hide(schoolPageFragmentOne);
        }
        if (null != schoolPageFragmentTwo) {
            transaction.hide(schoolPageFragmentTwo);
        }

    }

    private void clearSelection() {
        schoolTab02.setSelected(false);
        schoolTab01.setSelected(false);
    }
}
