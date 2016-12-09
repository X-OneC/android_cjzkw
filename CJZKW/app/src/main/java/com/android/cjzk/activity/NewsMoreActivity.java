package com.android.cjzk.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.cjzk.Adapter.NewsAdapter;
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

public class NewsMoreActivity extends MyBaseActivity {

    @BindView(android.R.id.empty)
    ProgressBar progressBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.swipe_target)
    RecyclerView recyclerView;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.tv_title)
    TextView tv_title;
    private String Title;
    private int Type = 1;
    private NewsAdapter adapter;
    private int Index = 1;
    private String token;

    @Override
    protected void onCreateT(Bundle savedInstanceState) {
        Title = getIntent().getStringExtra("name");
        Type = (int) getIntent().getExtras().get("Type");
        toolbar.setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.text_color));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back);
        tv_title.setText(Title);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewsAdapter(this);
        recyclerView.setAdapter(adapter);
        token = Utility.getSharedData(this, "token");
        addListener();
        getNewsData(1);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_news_more;
    }

    private void getNewsData(final int index) {
        call = ApiManager.getInstance().getNewsData(index, Type, token);
        call.enqueue(new Callback<NewsBean>() {
            @Override
            public void onResponse(Call<NewsBean> call, Response<NewsBean> response) {
                if (null != progressBar) {
                    progressBar.setVisibility(View.GONE);
                }
                if (null == response.body() || null == response.body().getData() || null == response.body().getData().getArticleLists()) {
                    Toast.makeText(NewsMoreActivity.this, "网络加载失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (index == 1) {
                    adapter.getListData(response.body().getData().getArticleLists());
                    adapter.notifyDataSetChanged();
                    swipeToLoadLayout.setRefreshing(false);
                } else {
                    adapter.getAllListData(response.body().getData().getArticleLists());
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
                if (index == 1) {
                    swipeToLoadLayout.setRefreshing(false);
                } else {
                    swipeToLoadLayout.setLoadingMore(false);
                }
                Toast.makeText(NewsMoreActivity.this, "网络加载失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addListener() {
        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                Index = 1;
                getNewsData(1);
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Index++;
                getNewsData(Index);
            }
        });
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
                getNewsData(1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
