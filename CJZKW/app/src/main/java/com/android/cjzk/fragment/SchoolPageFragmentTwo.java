package com.android.cjzk.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.cjzk.Adapter.SchoolTwoAdapter;
import com.android.cjzk.Api.ApiManager;
import com.android.cjzk.Bean.VideoBean;
import com.android.cjzk.R;
import com.android.cjzk.Utility.IRefresh;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SchoolPageFragmentTwo extends MyBaseFragment implements IRefresh {

    @BindView(android.R.id.empty)
    ProgressBar progressBar;
    @BindView(R.id.swipe_target)
    RecyclerView recyclerView;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    private int Index = 1;
    private SchoolTwoAdapter adapter;

    public SchoolPageFragmentTwo() {

    }

    public static SchoolPageFragmentTwo newInstance(String param1, String param2) {
        SchoolPageFragmentTwo fragment = new SchoolPageFragmentTwo();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_school_page_fragment_one;
    }

    @Override
    protected void onCreateViewT() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SchoolTwoAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                Index = 1;
                getVideoData(1);
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Index++;
                getVideoData(Index);
            }
        });
        getVideoData(1);
    }

    private void getVideoData(final int index) {
        ApiManager.getInstance().getVideoData(index).enqueue(new Callback<VideoBean>() {
            @Override
            public void onResponse(Call<VideoBean> call, Response<VideoBean> response) {
                progressBar.setVisibility(View.GONE);
                if (response.body() == null || null == response.body().getData() || null == response.body().getData().getArticleList()) {
                    Toast.makeText(getActivity(), "网络加载失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (index == 1) {
                    adapter.getListData(response.body().getData().getArticleList());
                    adapter.notifyDataSetChanged();
                    swipeToLoadLayout.setRefreshing(false);
                } else {
                    adapter.getAllListData(response.body().getData().getArticleList());
                    adapter.notifyDataSetChanged();
                    swipeToLoadLayout.setLoadingMore(false);
                }
            }

            @Override
            public void onFailure(Call<VideoBean> call, Throwable t) {
                if (null == progressBar || null == swipeToLoadLayout) {
                    return;
                }
                progressBar.setVisibility(View.GONE);
                if (index == 1) {
                    swipeToLoadLayout.setRefreshing(false);
                } else {
                    swipeToLoadLayout.setLoadingMore(false);
                }
                Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void refresh() {
        progressBar.setVisibility(View.VISIBLE);
        Index = 1;
        getVideoData(1);
    }
}
