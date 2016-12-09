package com.android.cjzk.fragment;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.cjzk.Adapter.DatePagerAdapter;
import com.android.cjzk.Adapter.SecondDateAdaptre;
import com.android.cjzk.Api.ApiManager;
import com.android.cjzk.Bean.DateBean;
import com.android.cjzk.R;
import com.android.cjzk.Utility.DateClick;
import com.android.cjzk.weight.WrapContentHeightViewPager;

import java.util.Calendar;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondPageFragmentTwo extends MyBaseFragment implements DateClick {

    @BindView(R.id.im_bt1)
    ImageButton imBt1;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.im_bt2)
    ImageButton imBt2;
    @BindView(R.id.datepager)
    WrapContentHeightViewPager datepager;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private DatePagerAdapter datePagerAdapter;
    private Calendar calendar;
    private int month, week, today, year;
    private SecondDateAdaptre madapter;
    private String[] monthl = new String[]{"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
    private int pageIndex;

    public SecondPageFragmentTwo() {
    }

    public static SecondPageFragmentTwo newInstance(String param1, String param2) {
        SecondPageFragmentTwo fragment = new SecondPageFragmentTwo();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_second_page_fragment_two;
    }

    @Override
    protected void onCreateViewT() {
        calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH) + 1;
        week = calendar.get(Calendar.DAY_OF_WEEK);
        today = calendar.get(Calendar.DAY_OF_MONTH);
        year = calendar.get(Calendar.YEAR);
        tvDate.setText(monthl[month - 1] + "   月");
        datePagerAdapter = new DatePagerAdapter(getActivity().getSupportFragmentManager());
        datepager.setAdapter(datePagerAdapter);
        datepager.setCurrentItem(month + 5);
        pageIndex = month + 5;
        addListener();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        madapter = new SecondDateAdaptre(getActivity());
        recyclerView.setAdapter(madapter);
        String date = year + "-" + month + "-" + today;
        getDateData(date, 1);
    }

    private void getDateData(final String date, int index) {
        ApiManager.getInstance().getDateData(date, index).enqueue(new Callback<DateBean>() {
            @Override
            public void onResponse(Call<DateBean> call, Response<DateBean> response) {
                if (response.body() == null || null == response.body().getData() || null == response.body().getData().getArticleList()) {
                    Toast.makeText(getActivity(), "网络加载失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                madapter.getListData(response.body().getData().getArticleList());
                madapter.setDateData(date);
                madapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<DateBean> call, Throwable t) {
                Toast.makeText(getActivity(), "网络加载失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void dateClick(String date, int p) {
//        Toast.makeText(getActivity(), date, Toast.LENGTH_SHORT).show();
        getDateData(date, 1);
    }

    private void addListener() {
        datepager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Calendar cal_select = Calendar.getInstance();
                cal_select.set(Calendar.MONTH, position - 6);
                tvDate.setText(monthl[cal_select.get(Calendar.MONTH)] + "   月");
                cal_select = null;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        imBt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageIndex--;
                datepager.setCurrentItem(pageIndex);
            }
        });
        imBt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageIndex++;
                datepager.setCurrentItem(pageIndex);
            }
        });
    }
}
