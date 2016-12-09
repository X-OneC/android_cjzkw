package com.android.cjzk.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.cjzk.Adapter.CommentListAdapter;
import com.android.cjzk.Api.ApiManager;
import com.android.cjzk.Bean.CommentListBean;
import com.android.cjzk.R;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentActivity extends MyBaseActivity {

    @BindView(android.R.id.empty)
    ProgressBar progressBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.swipe_target)
    RecyclerView recyclerView;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.tv_hint)
    TextView tv_hint;
    private int Index = 1;
    private String artId;
    private CommentListAdapter adapter;

    @Override
    protected void onCreateT(Bundle savedInstanceState) {
        artId = getIntent().getStringExtra("artId");
        init();
        addListener();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_news_more;
    }

    private void init() {
        toolbar.setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.text_color));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CommentListAdapter(this);
        recyclerView.setAdapter(adapter);
        getCommentListData(1);
    }

    private void addListener() {
        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                Index = 1;
                getCommentListData(1);
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Index++;
                getCommentListData(Index);
            }
        });
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

    private void getCommentListData(final int index) {
        call = ApiManager.getInstance().getCommentListData(artId, index);
        call.enqueue(new Callback<CommentListBean>() {
            @Override
            public void onResponse(Call<CommentListBean> call, Response<CommentListBean> response) {
                if (null != progressBar) {
                    progressBar.setVisibility(View.GONE);
                }
                if (null == response.body() || null == response.body() || null == response.body().getData().getList()) {
                    Toast.makeText(CommentActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (index == 1) {
                    if (response.body().getData().getList().size() == 0) {
                        tv_hint.setVisibility(View.VISIBLE);
                    } else {
                        tv_hint.setVisibility(View.GONE);
                    }
                    adapter.getListData(response.body().getData().getList());
                    swipeToLoadLayout.setRefreshing(false);
                } else {
                    adapter.getAllListData(response.body().getData().getList());
                    swipeToLoadLayout.setLoadingMore(false);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CommentListBean> call, Throwable t) {
                if (null == progressBar || null == swipeToLoadLayout) {
                    return;
                }
                progressBar.setVisibility(View.GONE);
                if (index == 1) {
                    swipeToLoadLayout.setRefreshing(false);
                } else {
                    swipeToLoadLayout.setLoadingMore(false);
                }
                Toast.makeText(CommentActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
