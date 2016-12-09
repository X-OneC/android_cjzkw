package com.android.cjzk.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.cjzk.Adapter.SecondLiveAdapter;
import com.android.cjzk.Api.ApiManager;
import com.android.cjzk.Bean.LiveBean;
import com.android.cjzk.R;
import com.android.cjzk.Utility.IRefresh;
import com.android.cjzk.Utility.Utility;
import com.android.cjzk.activity.MyBaseActivity;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SecondPageFragmentOne extends MyBaseFragment implements IRefresh {

    @BindView(android.R.id.empty)
    ProgressBar progressBar;
    @BindView(R.id.swipe_target)
    RecyclerView recyclerView;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    private SecondLiveAdapter madapter;
    private int index = 1;
    private String token;

    public SecondPageFragmentOne() {
    }

    public static SecondPageFragmentOne newInstance(String param1, String param2) {
        SecondPageFragmentOne fragment = new SecondPageFragmentOne();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_second_page_fragment_one;
    }

    @Override
    protected void onCreateViewT() {
        token = Utility.getSharedData(getActivity(), "token");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        madapter = new SecondLiveAdapter((MyBaseActivity) getActivity(), this);
        recyclerView.setAdapter(madapter);
        addListener();
        getShowLiveData(1);
    }


    private void getShowLiveData(final int Index) {
        ApiManager.getInstance().getShowLiveData(Index, token).enqueue(new Callback<LiveBean>() {
            @Override
            public void onResponse(Call<LiveBean> call, Response<LiveBean> response) {
                progressBar.setVisibility(View.GONE);
                LiveBean newsBean = response.body();
                if (null == newsBean || null == newsBean.getData() || null == newsBean.getData().getArticleList()) {
                    Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Index == 1) {
                    madapter.getListData(newsBean.getData().getArticleList());
                    swipeToLoadLayout.setRefreshing(false);
                } else {
                    madapter.getAllListData(newsBean.getData().getArticleList());
                    swipeToLoadLayout.setLoadingMore(false);
                }
                madapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<LiveBean> call, Throwable t) {
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
                index = 1;
                getShowLiveData(1);
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                index++;
                getShowLiveData(index);
            }
        });
    }

    @Override
    public void changText(String text) {
        super.changText(text);
        tvHeader.setText(setTime(text));
    }

    @Override
    public void refresh() {
        progressBar.setVisibility(View.VISIBLE);
        index = 1;
        getShowLiveData(1);
    }

    private CharSequence setTime(String time) {
        String[] str = null;
        str = time.split("-");
        return str[0] + "月" + str[1] + "日";
    }
}
