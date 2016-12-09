package com.android.cjzk.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.cjzk.Adapter.NewsAdapter;
import com.android.cjzk.Api.ApiManager;
import com.android.cjzk.Bean.NewsBean;
import com.android.cjzk.R;
import com.android.cjzk.Utility.IRefresh;
import com.android.cjzk.Utility.Utility;
import com.android.cjzk.activity.NewsMoreActivity;
import com.android.cjzk.activity.WebViewActivity;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.jakewharton.rxbinding.view.RxView;
import com.kevin.loopview.AdLoopView;
import com.kevin.loopview.internal.BaseLoopAdapter;
import com.kevin.wraprecyclerview.WrapAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.functions.Action1;

public class FirstPageFragmentOne extends MyBaseFragment implements IRefresh {

    @BindView(android.R.id.empty)
    ProgressBar progressBar;
    @BindView(R.id.swipe_target)
    RecyclerView recyclerView;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    private RelativeLayout headTab01, headTab02, headTab03, headTab04;

    private NewsAdapter madapter;
    private WrapAdapter<NewsAdapter> wrapAdapter;
    private AdLoopView mAdLoopView;
    private int Index = 1;
    private int queryType = 1;
    private String token;


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_first_page_fragment_one;
    }

    @Override
    protected void onCreateViewT() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        madapter = new NewsAdapter(getActivity());
        wrapAdapter = new WrapAdapter<>(madapter);
        wrapAdapter.adjustSpanSize(recyclerView);
        recyclerView.setAdapter(wrapAdapter);
        addHeaderView();
        addListener();
        token = Utility.getSharedData(getActivity(), "token");
        getNewsData(1);
        getBanner(token);
    }

    private void getNewsData(final int index) {
        ApiManager.getInstance().getNewsData(index, queryType, token).enqueue(new Callback<NewsBean>() {
            @Override
            public void onResponse(Call<NewsBean> call, Response<NewsBean> response) {
                if (null != progressBar) {
                    progressBar.setVisibility(View.GONE);
                }

                NewsBean newsBean = response.body();
                if (null == newsBean || null == newsBean.getData() || null == newsBean.getData().getArticleLists()) {
                    Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (index == 1) {
                    wrapAdapter.getWrappedAdapter().getListData(newsBean.getData().getArticleLists());
                    swipeToLoadLayout.setRefreshing(false);
                } else {
                    wrapAdapter.getWrappedAdapter().getAllListData(newsBean.getData().getArticleLists());
                    swipeToLoadLayout.setLoadingMore(false);
                }
                wrapAdapter.notifyDataSetChanged();
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

    private void getBanner(String token) {
        ApiManager.getInstance().getBanner(token).enqueue(new Callback<NewsBean>() {
            @Override
            public void onResponse(Call<NewsBean> call, Response<NewsBean> response) {
                NewsBean newsBean = response.body();
                if (null == newsBean) {
                    Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                setHeaderData(newsBean.getData().getArticleLists());
            }

            @Override
            public void onFailure(Call<NewsBean> call, Throwable t) {

            }
        });
    }

    private void addListener() {
        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                Index = 1;
                getNewsData(1);
                getBanner(token);
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Index++;
                getNewsData(Index);
            }
        });
        RxView.clicks(headTab01).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                setIntent(NewsMoreActivity.class, "独家原创", 11);
            }
        });
        RxView.clicks(headTab02).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                setIntent(NewsMoreActivity.class, "股市早报", 12);
            }
        });
        RxView.clicks(headTab03).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                setIntent(NewsMoreActivity.class, "涨停揭秘", 13);
            }
        });
        RxView.clicks(headTab04).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                setIntent(NewsMoreActivity.class, "公告精选", 14);
            }
        });
    }

    private void setIntent(Class cla, String name, int Type) {
        Intent intent = new Intent(getActivity(), cla);
        intent.putExtra("name", name);
        intent.putExtra("Type", Type);
        startActivity(intent);
    }

//    private void typeNetWork(int type) {
//        progressBar.setVisibility(View.VISIBLE);
//        Index = 1;
//        queryType = type;
//        getNewsData(1);
//    }

    private void addHeaderView() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.head_view, recyclerView, false);
        mAdLoopView = (AdLoopView) layout.findViewById(R.id.home_frag_rotate_vp);
        LinearLayout lin = (LinearLayout) layout.findViewById(R.id.lin);
        lin.getLayoutParams().height = (int) (Utility.screenWidth * 150 / 750);
        mAdLoopView.getLayoutParams().width = (int) Utility.screenWidth;
        mAdLoopView.getLayoutParams().height = (int) (Utility.screenWidth / 2);
        headTab01 = (RelativeLayout) layout.findViewById(R.id.headTab01);
        headTab02 = (RelativeLayout) layout.findViewById(R.id.headTab02);
        headTab03 = (RelativeLayout) layout.findViewById(R.id.headTab03);
        headTab04 = (RelativeLayout) layout.findViewById(R.id.headTab04);
        wrapAdapter.addHeaderView(layout);
    }

    private void setHeaderData(final List<NewsBean.NewsArticleList> newsArticleLists) {
        mAdLoopView.setOnClickListener(new BaseLoopAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PagerAdapter parent, View view, int position, int realPosition) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("ArtCtId", newsArticleLists.get(position).getArtCtId());
                intent.putExtra("collection", newsArticleLists.get(position).isCollection());
                getActivity().startActivity(intent);
            }
        });
        new MyAsynTask().execute(newsArticleLists);
    }

    class MyAsynTask extends AsyncTask<List<NewsBean.NewsArticleList>, Integer, List<Map<String, String>>> {

        @Override
        protected List<Map<String, String>> doInBackground(List<NewsBean.NewsArticleList>... params) {
            List<Map<String, String>> data = null;
            data = new ArrayList<>();
            for (int i = 0; i < params[0].size(); i++) {
                Map<String, String> map = new HashMap<>();
                map.put("imageURL", Utility.DefPicUrl + params[0].get(i).getDefPicUrl());
                map.put("link", "");
                map.put("descText", params[0].get(i).getTitle());
                map.put("type", String.valueOf(i));
                map.put("id", String.valueOf(i));
                data.add(map);
                map = null;
            }
            return data;
        }

        @Override
        protected void onPostExecute(List<Map<String, String>> maps) {
            super.onPostExecute(maps);
            mAdLoopView.refreshData(maps);
            mAdLoopView.startAutoLoop();
            maps.clear();
            maps = null;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            mAdLoopView.stopAutoLoop();
        } else {
            mAdLoopView.startAutoLoop();
        }
    }

    @Override
    public void refresh() {
        progressBar.setVisibility(View.VISIBLE);
        Index = 1;
        getNewsData(1);
        getBanner(token);
    }
}
