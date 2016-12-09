package com.android.cjzk.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.cjzk.Bean.DateBean;
import com.android.cjzk.R;
import com.android.cjzk.activity.DateDetailsActivity;
import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * Created by XOne on 2016/8/4.
 */
public class SecondDateAdaptre extends RecyclerView.Adapter<SecondDateAdaptre.DateViewHolder> {
    private Context mContext;
    private LayoutInflater inflater;
    private List<DateBean.DateArticleList> dateArticleLists;
    private String day, month;

    public SecondDateAdaptre(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
        dateArticleLists = new ArrayList<>();
    }

    @Override
    public DateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_date_two, parent, false);
        DateViewHolder holder = new DateViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(DateViewHolder holder, final int position) {
        final DateBean.DateArticleList articleList = dateArticleLists.get(position);
        holder.dateTitle.setText(articleList.getCalIntro());
        holder.dateContext.setText(articleList.getStkRecommend().equals("") ? "" : "相关个股：" + articleList.getStkRecommend());
        holder.dateMonth.setText(month + "月");
        holder.dateToday.setText(day);
        RxView.clicks(holder.mainView).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Intent intent = new Intent(mContext, DateDetailsActivity.class);
                intent.putExtra("data", articleList);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dateArticleLists == null ? 0 : dateArticleLists.size();
    }

    class DateViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.date_today)
        TextView dateToday;
        @BindView(R.id.date_month)
        TextView dateMonth;
        @BindView(R.id.date_title)
        TextView dateTitle;
        @BindView(R.id.date_context)
        TextView dateContext;
        @BindView(R.id.main_view)
        RelativeLayout mainView;

        public DateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void getListData(List<DateBean.DateArticleList> listbo) {
        if (listbo == null) {
            Toast.makeText(mContext, "网络加载失败", Toast.LENGTH_SHORT).show();
            return;
        }
        this.dateArticleLists = listbo;
        notifyDataSetChanged();
    }

    public void getAllListData(List<DateBean.DateArticleList> listbo) {
        if (listbo == null) {
            Toast.makeText(mContext, "网络加载失败", Toast.LENGTH_SHORT).show();
            return;
        }
        this.dateArticleLists.addAll(listbo);
        notifyDataSetChanged();
    }

    public void setDateData(String date) {
        String[] str = date.split("-");
        this.day = str[2];
        this.month = str[1];
        str = null;
    }
}
