package com.android.cjzk.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.cjzk.Adapter.NewsAdapter;
import com.android.cjzk.Api.ApiManager;
import com.android.cjzk.Bean.NewsBean;
import com.android.cjzk.R;
import com.android.cjzk.Utility.IRefresh;
import com.android.cjzk.Utility.Utility;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstPageFragmentTwo extends MyBaseFragment implements IRefresh {

    @BindView(android.R.id.empty)
    ProgressBar progressBar;
    @BindView(R.id.swipe_target)
    RecyclerView recyclerView;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    private NewsAdapter madapter;
    private int Index = 1;
    private String token;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_first_page_fragment_two;
    }

    @Override
    protected void onCreateViewT() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        madapter = new NewsAdapter(getActivity());
        recyclerView.setAdapter(madapter);
        token = Utility.getSharedData(getActivity(), "token");
        addListener();
        getNewsData(1);
    }

    private void getNewsData(final int index) {
        ApiManager.getInstance().getNewsData(index, 2, token).enqueue(new Callback<NewsBean>() {
            @Override
            public void onResponse(Call<NewsBean> call, Response<NewsBean> response) {
                progressBar.setVisibility(View.GONE);
                NewsBean newsBean = response.body();
                if (null == newsBean || null == newsBean.getData() || null == newsBean.getData().getArticleLists()) {
                    Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (index == 1) {
                    madapter.getListData(newsBean.getData().getArticleLists());
                    swipeToLoadLayout.setRefreshing(false);
                } else {
                    madapter.getAllListData(newsBean.getData().getArticleLists());
                    swipeToLoadLayout.setLoadingMore(false);
                }
                madapter.notifyDataSetChanged();
                newsBean = null;
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
                Toast.makeText(getActivity(), "网络加载失败", Toast.LENGTH_SHORT).show();
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
    public void refresh() {
        progressBar.setVisibility(View.VISIBLE);
        Index = 1;
        getNewsData(1);
    }
}
