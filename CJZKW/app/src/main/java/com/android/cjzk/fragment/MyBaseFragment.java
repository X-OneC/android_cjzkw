package com.android.cjzk.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class MyBaseFragment extends Fragment {
    public View mainLayout;
    private Unbinder unbinder;

    protected abstract int getContentViewId();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null != mainLayout) {
            ViewGroup viewGroup = (ViewGroup) mainLayout.getParent();
            if (null != viewGroup) {
                viewGroup.removeView(mainLayout);
            }
            return mainLayout;
        }
        mainLayout = inflater.inflate(getContentViewId(), container, false);
        unbinder = ButterKnife.bind(this, mainLayout);
        onCreateViewT();
        return mainLayout;
    }

    protected abstract void onCreateViewT();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void changText(String text) {
    }
}
