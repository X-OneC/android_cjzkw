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

public class SecondPageFragment extends MyBaseFragment {


    @BindView(R.id.secondTab01)
    Button secondTab01;
    @BindView(R.id.secondTab02)
    Button secondTab02;
    @BindView(R.id.relRefresh)
    RelativeLayout relRefresh;
    private SecondPageFragmentOne secondPageFragmentOne;
    private SecondPageFragmentTwo secondPageFragmentTwo;

    public SecondPageFragment() {
    }

    public static SecondPageFragment newInstance() {
        SecondPageFragment fragment = new SecondPageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != savedInstanceState) {
            FragmentManager manager = getActivity().getSupportFragmentManager();
            secondPageFragmentOne = (SecondPageFragmentOne) manager.findFragmentByTag
                    ("secondPageFragmentOne");
            secondPageFragmentTwo = (SecondPageFragmentTwo) manager.findFragmentByTag
                    ("secondPageFragmentTwo");
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_second_page;
    }

    @Override
    protected void onCreateViewT() {
        RxView.clicks(secondTab01).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        setTabSelection(1);
                    }
                });
        RxView.clicks(secondTab02).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        setTabSelection(2);
                    }
                });
        RxView.clicks(relRefresh).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                secondPageFragmentOne.refresh();
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
                secondTab01.setSelected(true);
                if (null == secondPageFragmentOne) {
                    secondPageFragmentOne = new SecondPageFragmentOne();
                    transaction.add(R.id.FrameLayout_main_second, secondPageFragmentOne, "secondPageFragmentOne");
                } else {
                    transaction.show(secondPageFragmentOne);
                }
                break;
            case 2:
                secondTab02.setSelected(true);
                if (null == secondPageFragmentTwo) {
                    secondPageFragmentTwo = new SecondPageFragmentTwo();
                    transaction.add(R.id.FrameLayout_main_second, secondPageFragmentTwo, "secondPageFragmentTwo");
                } else {
                    transaction.show(secondPageFragmentTwo);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (null != secondPageFragmentOne) {
            transaction.hide(secondPageFragmentOne);
        }
        if (null != secondPageFragmentTwo) {
            transaction.hide(secondPageFragmentTwo);
        }

    }

    private void clearSelection() {
        secondTab01.setSelected(false);
        secondTab02.setSelected(false);
    }
}
