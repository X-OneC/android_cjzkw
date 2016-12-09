package com.android.cjzk.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.cjzk.Bean.DateBean;
import com.android.cjzk.R;
import com.android.cjzk.Utility.Utility;

import butterknife.BindView;

public class DateDetailsActivity extends MyBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_about)
    TextView tvAbout;
    @BindView(R.id.lin_about)
    LinearLayout linAbout;
    @BindView(R.id.view002)
    View view002;
    @BindView(R.id.tv_sort)
    TextView tvSort;
    @BindView(R.id.lin_sort)
    LinearLayout linSort;
    @BindView(R.id.view001)
    View view001;
    @BindView(R.id.im_cjzk)
    ImageView imageView;
    private DateBean.DateArticleList bo;

    @Override
    protected void onCreateT(Bundle savedInstanceState) {
        bo = (DateBean.DateArticleList) getIntent().getSerializableExtra("data");
        toolbar.setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.text_color));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back);
        tvTitle.setText(bo.getCalIntro());
        tvTime.setText(bo.getOccurTime().subSequence(0, bo.getOccurTime().lastIndexOf("T")));
        tvAbout.setText(bo.getStkRecommend());
        tvSort.setText(bo.getAffPlate());
        imageView.setBackgroundDrawable(Utility.getBitMapDrawableFromId(R.mipmap.im_buttom_log, this));
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_date_details;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        System.gc();
        super.onDestroy();
    }

}
