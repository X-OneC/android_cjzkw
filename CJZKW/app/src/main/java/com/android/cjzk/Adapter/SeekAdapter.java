package com.android.cjzk.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.cjzk.Bean.SeekBean;
import com.android.cjzk.R;
import com.android.cjzk.activity.WebViewActivity;
import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * Created by XOne on 2016/8/8.
 */
public class SeekAdapter extends RecyclerView.Adapter<SeekAdapter.SeekViewHolder> {
    private Context mContext;
    private LayoutInflater inflater;
    private List<SeekBean.DataBean.ArticleListBean> articleListBeen;

    public SeekAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
        articleListBeen = new ArrayList<>();
    }

    @Override
    public SeekViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_seek, parent, false);
        SeekViewHolder holder = new SeekViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(SeekViewHolder holder, int position) {
        final SeekBean.DataBean.ArticleListBean bean = articleListBeen.get(position);
        holder.tvTitle.setText(Html.fromHtml(bean.getTitle()));
        holder.tvIntro.setText(Html.fromHtml(bean.getIntro()));
        holder.tvSource.setText(bean.getSource());
        holder.tvTime.setText(bean.getReleaseDate().substring(0, 10));
        RxView.clicks(holder.mainView).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("ArtCtId", bean.getArticleCurrentID());
                intent.putExtra("collection", false);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleListBeen == null ? 0 : articleListBeen.size();
    }

    class SeekViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_Intro)
        TextView tvIntro;
        @BindView(R.id.tv_Source)
        TextView tvSource;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.mainView)
        LinearLayout mainView;

        public SeekViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void getListData(List<SeekBean.DataBean.ArticleListBean> listbo) {
        this.articleListBeen = listbo;
        notifyDataSetChanged();
    }

    public void getAllListData(List<SeekBean.DataBean.ArticleListBean> listbo) {
        this.articleListBeen.addAll(listbo);
        notifyDataSetChanged();
    }
}
