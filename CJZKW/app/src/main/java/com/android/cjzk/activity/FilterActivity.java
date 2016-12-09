package com.android.cjzk.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.cjzk.Adapter.FilterAdapter;
import com.android.cjzk.Api.ApiManager;
import com.android.cjzk.Bean.SiftingBean;
import com.android.cjzk.R;
import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.functions.Action1;

public class FilterActivity extends MyBaseActivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolBar;
    @BindView(R.id.rel_tab01)
    RelativeLayout relTab01;
    @BindView(R.id.rel_tab02)
    RelativeLayout relTab02;
    @BindView(R.id.rel_tab03)
    RelativeLayout relTab03;
    @BindView(R.id.rel_tab04)
    RelativeLayout relTab04;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private FilterAdapter adapter;

    private List<SiftingBean.DataBean> list;
    private List<SiftingBean.DataBean> list1 = new ArrayList<>();
    private List<SiftingBean.DataBean> list2 = new ArrayList<>();
    private List<SiftingBean.DataBean> list3 = new ArrayList<>();
    private List<SiftingBean.DataBean> list4 = new ArrayList<>();

    @Override
    protected void onCreateT(Bundle savedInstanceState) {
        toolBar.setTitle("");
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.mipmap.back);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        adapter = new FilterAdapter(this);
        recyclerView.setAdapter(adapter);


        getSiftingData();

        RxView.clicks(relTab01).throttleFirst(1, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                clearSelection();
                relTab01.setSelected(true);
                showType(1);
            }
        });
        RxView.clicks(relTab02).throttleFirst(1, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                clearSelection();
                relTab02.setSelected(true);
                showType(2);
            }
        });
        RxView.clicks(relTab03).throttleFirst(1, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                clearSelection();
                relTab03.setSelected(true);
                showType(3);
            }
        });
        RxView.clicks(relTab04).throttleFirst(1, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                clearSelection();
                relTab04.setSelected(true);
                showType(4);
            }
        });

        relTab01.setSelected(true);
    }

    private void clearSelection() {
        relTab01.setSelected(false);
        relTab02.setSelected(false);
        relTab03.setSelected(false);
        relTab04.setSelected(false);
    }


    @Override
    protected int getContentViewId() {
        return R.layout.activity_filter;
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

    private void getSiftingData() {
        call = ApiManager.getInstance().getSiftingData();
        call.enqueue(new Callback<SiftingBean>() {
            @Override
            public void onResponse(Call<SiftingBean> call, Response<SiftingBean> response) {
                if (null == response.body()) {
                } else {
                    list = response.body().getData();
                    setData();
                }
            }

            @Override
            public void onFailure(Call<SiftingBean> call, Throwable t) {
//                Toast.makeText(FilterActivity.this, "eorro", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showType(int type) {
        switch (type) {
            case 1:
                adapter.getListData(list1);
                break;
            case 2:
                adapter.getListData(list2);
                break;
            case 3:
                adapter.getListData(list3);
                break;
            case 4:
                adapter.getListData(list4);
                break;
        }
    }

    private void setData() {
        if (null != list) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getGroupName().equals("新闻数据")) {
                    list1.add(list.get(i));
                } else if (list.get(i).getGroupName().equals("市场观点")) {
                    list2.add(list.get(i));
                } else if (list.get(i).getGroupName().equals("机构动向")) {
                    list3.add(list.get(i));
                } else if (list.get(i).getGroupName().equals("参考研究")) {
                    list4.add(list.get(i));
                }
            }
        }
        adapter.getListData(list1);
    }

    @Override
    public void onClick(View v) {

    }


//    LinearLayout layout = new LinearLayout(this);
//    layout.setOrientation(LinearLayout.HORIZONTAL);
//    for (int i = 0; i < list1.size(); i++) {
//        Button bt = new Button(this);
//        bt.setText(list1.get(i).getName());
//        bt.setId(i + 100);
//        bt.setTextSize(10);
//        bt.setBackgroundResource(R.drawable.filter_bg);
//        bt.setTextColor(getResources().getColor(R.color.text_time));
//        bt.setOnClickListener(this);
//        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        llp.weight = 1;
//        llp.width = 0;
//        llp.height = 20;
//        layout.addView(bt, llp);
//    }
////                lin.addView(layout);
}
