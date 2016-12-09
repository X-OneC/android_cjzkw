package com.android.cjzk.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.cjzk.Adapter.SeekAdapter;
import com.android.cjzk.Adapter.SeekAdapter2;
import com.android.cjzk.Api.ApiManager;
import com.android.cjzk.Bean.SeekBean;
import com.android.cjzk.Bean.SiftingBean;
import com.android.cjzk.R;
import com.android.cjzk.Utility.ISeekData;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.functions.Action1;

public class SeekActivity extends MyBaseActivity implements ISeekData {

    @BindView(R.id.toolbar)
    Toolbar toolBar;
    @BindView(R.id.seek_et)
    EditText seekEt;
    @BindView(R.id.bt_seek)
    Button btSeek;
    @BindView(android.R.id.empty)
    ProgressBar progressBar;
    //    @BindView(R.id.seek_tv01)
//    TextView seekTv01;
//    @BindView(R.id.seek_tvmun)
//    TextView seekTvmun;
    @BindView(R.id.tv_hint)
    TextView tv_hint;
    @BindView(R.id.swipe_target)
    RecyclerView recyclerView;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    private SeekAdapter adapter;
    private int Index = 1;
    @BindView(R.id.recyclerView)
    RecyclerView mrecyclerView;
    @BindView(R.id.relChange)
    RelativeLayout relChange;
    @BindView(R.id.rel01)
    RelativeLayout rel01;
    private SeekAdapter2 madapter;

    @Override
    protected void onCreateT(Bundle savedInstanceState) {
        init();
        addListener();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_seek;
    }

    private void init() {
        toolBar.setTitle("");
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.mipmap.back);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SeekAdapter(this);
        recyclerView.setAdapter(adapter);
//        seekTv01.setText(Html.fromHtml("搜索：<font color=\"#ed2626\"> 情报，股票，证券</font>"));
//        seekTvmun.setText(Html.fromHtml("共<font color=\"#ed2626\">" + 0 + "</font>条数据"));

        mrecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        madapter = new SeekAdapter2(this, this);
        mrecyclerView.setAdapter(madapter);
        getHotsearchData();
    }

    private void addListener() {

        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                String text = seekEt.getText().toString();
                if (text.equals("")) {
                    Toast.makeText(SeekActivity.this, "请输入关键字", Toast.LENGTH_SHORT).show();
                    swipeToLoadLayout.setRefreshing(false);
                    return;
                }
                Index = 1;
                getSeekData(text, 1);
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                String text = seekEt.getText().toString();
                if (text.equals("")) {
                    Toast.makeText(SeekActivity.this, "请输入关键字", Toast.LENGTH_SHORT).show();
                    swipeToLoadLayout.setLoadingMore(false);
                    return;
                }
                Index++;
                getSeekData(text, Index);
            }
        });

        btSeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = seekEt.getText().toString();
                if (text.equals("")) {
                    Toast.makeText(SeekActivity.this, "请输入关键字", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (btSeek.getText().equals("搜索")) {
//                    seekTv01.setText(Html.fromHtml("搜索：<font color=\"#ed2626\"> " + text + "</font>"));
                    InputMethodManager imm = (InputMethodManager) getSystemService(SeekActivity.this.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(btSeek.getWindowToken(), 0);
                    btSeek.setText("取消");
                    getSeekData(text, 1);
                    rel01.setVisibility(View.GONE);
                } else {
                    btSeek.setText("搜索");
                    seekEt.setText("");
//                    seekTv01.setText(Html.fromHtml("搜索：<font color=\"#ed2626\"> 情报，股票，证券</font>"));
//                    seekTvmun.setText(Html.fromHtml("共<font color=\"#ed2626\">" + 0 + "</font>条数据"));
                    adapter.getListData(null);
                    adapter.notifyDataSetChanged();
                    rel01.setVisibility(View.VISIBLE);
                    tv_hint.setVisibility(View.GONE);
                }
            }
        });

        RxView.clicks(relChange).throttleFirst(1, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                getHotsearchData();
            }
        });

        seekEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String text = s.toString().trim();
                if (text != null && (!text.equals(""))) {
                    btSeek.setText("搜索");
                    adapter.getListData(null);
                    adapter.notifyDataSetChanged();
                    rel01.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void getSeekData(String text, final int index) {
        if (null != progressBar) {
            progressBar.setVisibility(View.VISIBLE);
        }
        call = ApiManager.getInstance().getSeekData(text, index, 10);
//        ApiManager.getInstance().getSeekData(text, index, 10)
        call.enqueue(new Callback<SeekBean>() {
            @Override
            public void onResponse(Call<SeekBean> call, Response<SeekBean> response) {
                if (null != progressBar) {
                    progressBar.setVisibility(View.GONE);
                }
                if (null == response.body() || null == response.body().getData()) {
                    Toast.makeText(SeekActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (index == 1) {
//                    seekTvmun.setText(Html.fromHtml("共<font color=\"#ed2626\">" +
//                            response.body().getData().getTotalCount() + "</font>条数据"));
                    if (response.body().getData().getArticleList().size() == 0) {
                        tv_hint.setVisibility(View.VISIBLE);
                    } else {
                        tv_hint.setVisibility(View.GONE);
                    }
                    adapter.getListData(response.body().getData().getArticleList());
                    swipeToLoadLayout.setRefreshing(false);
                } else {
                    adapter.getAllListData(response.body().getData().getArticleList());
                    swipeToLoadLayout.setLoadingMore(false);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<SeekBean> call, Throwable t) {
                if (null == progressBar || null == swipeToLoadLayout) {
                    return;
                }
                progressBar.setVisibility(View.GONE);
                if (index == 1) {
                    swipeToLoadLayout.setRefreshing(false);
                } else {
                    swipeToLoadLayout.setLoadingMore(false);
                }
                Toast.makeText(SeekActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
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

    private void getHotsearchData() {
        ApiManager.getInstance().getHotsearchData().enqueue(new Callback<SiftingBean>() {
            @Override
            public void onResponse(Call<SiftingBean> call, Response<SiftingBean> response) {
                if (null == response.body()) {
                } else {
                    madapter.getListData(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<SiftingBean> call, Throwable t) {
            }
        });
    }

    @Override
    public void seekData(String data) {
        seekEt.setText(data);
        InputMethodManager imm = (InputMethodManager) getSystemService(SeekActivity.this.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(btSeek.getWindowToken(), 0);
        btSeek.setText("取消");
        getSeekData(data, 1);
        rel01.setVisibility(View.GONE);
    }
}
