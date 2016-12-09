package com.android.cjzk.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.cjzk.Adapter.CommentVipAdapter;
import com.android.cjzk.Api.ApiManager;
import com.android.cjzk.Bean.CommentVipListBean;
import com.android.cjzk.R;
import com.android.cjzk.Utility.Utility;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShareActivity extends MyBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(android.R.id.empty)
    ProgressBar progressBar;
    @BindView(R.id.swipe_target)
    RecyclerView recyclerView;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    private int Index = 1;
    private CommentVipAdapter adapter;

    @Override
    protected void onCreateT(Bundle savedInstanceState) {
        toolbar.setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.text_color));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CommentVipAdapter(this);
        recyclerView.setAdapter(adapter);
        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                Index = 1;
                getShareData(1);
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Index++;
                getShareData(Index);
            }
        });
        getShareData(1);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_share;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.tb_ref:
                Index = 1;
                progressBar.setVisibility(View.VISIBLE);
                getShareData(1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getShareData(final int index) {
        call = ApiManager.getInstance().getShareData(Utility.getSharedData(ShareActivity.this, "token"), index);
        call.enqueue(new Callback<CommentVipListBean>() {
            @Override
            public void onResponse(Call<CommentVipListBean> call, Response<CommentVipListBean> response) {
                if (null != progressBar) {
                    progressBar.setVisibility(View.GONE);
                }
                CommentVipListBean newsBean = response.body();
                if (null == newsBean || null == newsBean.getData() || null == newsBean.getData().getCommentList()) {
                    Toast.makeText(ShareActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (index == 1) {
                    adapter.getListData(newsBean.getData().getCommentList());
                    adapter.notifyDataSetChanged();
                    swipeToLoadLayout.setRefreshing(false);
                } else {
                    adapter.getAllListData(newsBean.getData().getCommentList());
                    adapter.notifyDataSetChanged();
                    swipeToLoadLayout.setLoadingMore(false);
                }
            }

            @Override
            public void onFailure(Call<CommentVipListBean> call, Throwable t) {
                if (null == progressBar || null == swipeToLoadLayout) {
                    return;
                }
                progressBar.setVisibility(View.GONE);
                if (index == 1) {
                    swipeToLoadLayout.setRefreshing(false);
                } else {
                    swipeToLoadLayout.setLoadingMore(false);
                }
                Toast.makeText(ShareActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
