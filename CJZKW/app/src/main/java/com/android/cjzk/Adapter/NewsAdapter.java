package com.android.cjzk.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.cjzk.Bean.NewsBean;
import com.android.cjzk.R;
import com.android.cjzk.Utility.DateUtil;
import com.android.cjzk.Utility.Utility;
import com.android.cjzk.activity.WebViewActivity;
import com.jakewharton.rxbinding.view.RxView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * Created by XOne on 2016/7/22.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private Context mContext;
    private LayoutInflater inflater;
    private List<NewsBean.NewsArticleList> newsArticleLists;
    private DisplayImageOptions options;

    public NewsAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
        newsArticleLists = new ArrayList<>();
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.background).showImageForEmptyUri(R.mipmap.text)
                .showImageOnFail(R.mipmap.text).cacheInMemory(true)
                .cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_news_one, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final NewsBean.NewsArticleList newsArticleList = newsArticleLists.get(position);
        final NewsBean.NewsArticle newsArticle = newsArticleList.getArticle();
        holder.tvTitle.setText(newsArticleList.getTitle());
//        holder.tvIntro.setText(newsArticle.getIntro());
        holder.tvTime.setText(Utility.getTimeDesc(DateUtil.getDateByFormat(format(newsArticleList.getRelDate()), "yyyy-MM-dd HH:mm:ss")));
        holder.tvAffStock.setText(newsArticleList.getAffStock());
        holder.DefPicUrl.getLayoutParams().width = (int) (Utility.screenWidth - Utility.setRatio(20));
        holder.DefPicUrl.getLayoutParams().height = (int) ((Utility.screenWidth - Utility.setRatio(20)) / 2);
        ImageLoader.getInstance().displayImage(Utility.DefPicUrl + newsArticleList.getDefPicUrl(), holder.DefPicUrl, options);
        RxView.clicks(holder.cardView).throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Intent intent = new Intent(mContext, WebViewActivity.class);
                        intent.putExtra("ArtCtId", newsArticleList.getArtCtId());
                        intent.putExtra("collection", newsArticleList.isCollection());
                        mContext.startActivity(intent);
                    }
                });
        if (newsArticleList.getMark().equals("")) {
            holder.hot.setVisibility(View.GONE);
        } else if (newsArticleList.getMark().equals("短线机会")) {
            holder.hot.setBackgroundResource(R.drawable.hot_gb_orange);
        } else if (newsArticleList.getMark().equals("中性")) {
            holder.hot.setBackgroundResource(R.drawable.hot_gb_blue);
        } else if (newsArticleList.getMark().equals("中线机会")) {
            holder.hot.setBackgroundResource(R.drawable.hot_gb_red);
        }
        holder.hot.setText(newsArticleList.getMark());
    }

    @Override
    public int getItemCount() {
        return newsArticleLists == null ? 0 : newsArticleLists.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;
//        @BindView(R.id.tv_Intro)
//        TextView tvIntro;
        @BindView(R.id.tv_AffStock)
        TextView tvAffStock;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.cardView)
        CardView cardView;
        @BindView(R.id.DefPicUrl)
        ImageView DefPicUrl;
        @BindView(R.id.hot)
        TextView hot;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void getListData(List<NewsBean.NewsArticleList> listbo) {
        if (listbo == null) {
            Toast.makeText(mContext, "网络加载失败", Toast.LENGTH_SHORT).show();
            return;
        }
        this.newsArticleLists = listbo;
        notifyDataSetChanged();
    }

    public void getAllListData(List<NewsBean.NewsArticleList> listbo) {
        if (listbo == null) {
            Toast.makeText(mContext, "网络加载失败", Toast.LENGTH_SHORT).show();
            return;
        }
        this.newsArticleLists.addAll(listbo);
        notifyDataSetChanged();
    }

    private String format(String str) {
        String str1 = str.replace("T", " ");
        String string = str1.substring(0, 18);
        return string;
    }
}
