package com.android.cjzk.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.cjzk.Adapter.SchoolOneAdapter;
import com.android.cjzk.Api.ApiManager;
import com.android.cjzk.Bean.SchoolBean;
import com.android.cjzk.R;
import com.android.cjzk.Utility.IRefresh;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SchoolPageFragmentOne extends MyBaseFragment implements IRefresh {

    @BindView(R.id.swipe_target)
    RecyclerView recyclerView;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    private SchoolOneAdapter madapter;
    private int Index = 1;
    @BindView(android.R.id.empty)
    ProgressBar progressBar;

    public SchoolPageFragmentOne() {

    }

    public static SchoolPageFragmentOne newInstance(String param1, String param2) {
        SchoolPageFragmentOne fragment = new SchoolPageFragmentOne();
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
        madapter = new SchoolOneAdapter(getActivity());
        recyclerView.setAdapter(madapter);
        addListener();
        getSchoolData(1);
    }

    private void getSchoolData(final int index) {
        ApiManager.getInstance().getSchoolData(index).enqueue(new Callback<SchoolBean>() {
            @Override
            public void onResponse(Call<SchoolBean> call, Response<SchoolBean> response) {
                progressBar.setVisibility(View.GONE);
                if (response.body() == null || null == response.body().getData() || null == response.body().getData().getArticleList()) {
                    Toast.makeText(getActivity(), "网络加载失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (index == 1) {
                    madapter.getListData(response.body().getData().getArticleList());
                    madapter.notifyDataSetChanged();
                    swipeToLoadLayout.setRefreshing(false);
                } else {
                    madapter.getAllListData(response.body().getData().getArticleList());
                    madapter.notifyDataSetChanged();
                    swipeToLoadLayout.setLoadingMore(false);
                }
            }

            @Override
            public void onFailure(Call<SchoolBean> call, Throwable t) {
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

    private void addListener() {
        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                Index = 1;
                getSchoolData(1);
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Index++;
                getSchoolData(Index);
            }
        });
    }

    @Override
    public void refresh() {
        progressBar.setVisibility(View.VISIBLE);
        Index = 1;
        getSchoolData(1);
    }
}
