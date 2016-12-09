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

import com.android.cjzk.Adapter.CollectionAdapter;
import com.android.cjzk.Api.ApiManager;
import com.android.cjzk.Bean.NewsBean;
import com.android.cjzk.R;
import com.android.cjzk.Utility.Utility;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionActivity extends MyBaseActivity {

    @BindView(android.R.id.empty)
    ProgressBar progressBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.swipe_target)
    RecyclerView recyclerView;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    private int Index = 1;
    private CollectionAdapter adapter;

    @Override
    protected void onCreateT(Bundle savedInstanceState) {
        toolbar.setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.text_color));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CollectionAdapter(this);
        recyclerView.setAdapter(adapter);
        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                Index = 1;
                getCollectionListData(1);
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Index++;
                getCollectionListData(Index);
            }
        });

        getCollectionListData(1);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_collection;
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
                getCollectionListData(1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    private void getCollectionListData(final int pageIndex) {
        call = ApiManager.getInstance().getCollectionListData(Utility.getSharedData(CollectionActivity.this, "token"), pageIndex);
        call.enqueue(new Callback<NewsBean>() {
            @Override
            public void onResponse(Call<NewsBean> call, Response<NewsBean> response) {
                if (null != progressBar) {
                    progressBar.setVisibility(View.GONE);
                }
                NewsBean newsBean = response.body();
                if (null == newsBean || null == newsBean.getData() || null == newsBean.getData().getArticleLists()) {
                    Toast.makeText(CollectionActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pageIndex == 1) {
                    adapter.getListData(newsBean.getData().getArticleLists());
                    adapter.notifyDataSetChanged();
                    swipeToLoadLayout.setRefreshing(false);
                } else {
                    adapter.getAllListData(newsBean.getData().getArticleLists());
                    adapter.notifyDataSetChanged();
                    swipeToLoadLayout.setLoadingMore(false);
                }
            }

            @Override
            public void onFailure(Call<NewsBean> call, Throwable t) {
                if (null == progressBar || null == swipeToLoadLayout) {
                    return;
                }
                progressBar.setVisibility(View.GONE);
                if (pageIndex == 1) {
                    swipeToLoadLayout.setRefreshing(false);
                } else {
                    swipeToLoadLayout.setLoadingMore(false);
                }
                Toast.makeText(CollectionActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
