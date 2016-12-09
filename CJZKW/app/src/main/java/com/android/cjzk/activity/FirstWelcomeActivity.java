package com.android.cjzk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.cjzk.R;
import com.android.cjzk.Utility.Utility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FirstWelcomeActivity extends MyBaseActivity {

    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.firstPager)
    RelativeLayout firstPager;
    private List<ImageView> viewList;
    int[] id = {R.mipmap.one, R.mipmap.two, R.mipmap.three};

    @Override
    protected void onCreateT(Bundle savedInstanceState) {
        viewList = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            ImageView view = new ImageView(this);
            view.setBackgroundResource(id[i]);
            viewList.add(view);
            view = null;
        }
        PagerAdapter pagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return viewList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView(viewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewList.get(position));
                return viewList.get(position);
            }
        };
        pager.setAdapter(pagerAdapter);
        viewList.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstWelcomeActivity.this, HomeActivity.class));
                Utility.putSharedData(FirstWelcomeActivity.this, "one", "one");
                finish();
            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_first_welcome;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}
