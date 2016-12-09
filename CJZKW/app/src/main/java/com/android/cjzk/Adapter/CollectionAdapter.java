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

import com.android.cjzk.Api.ApiManager;
import com.android.cjzk.Bean.LoginBean;
import com.android.cjzk.Bean.NewsBean;
import com.android.cjzk.R;
import com.android.cjzk.Utility.Utility;
import com.android.cjzk.activity.WebViewActivity;
import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.functions.Action1;

/**
 * Created by XOne on 2016/8/10.
 */
public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder> {

    private Context mContext;
    private LayoutInflater inflater;
    private List<NewsBean.NewsArticleList> newsArticleLists;

    public CollectionAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
        newsArticleLists = new ArrayList<>();
    }

    @Override
    public CollectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_collection, parent, false);
        CollectionViewHolder holder = new CollectionViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CollectionViewHolder holder, final int position) {
        final NewsBean.NewsArticleList newsArticleList = newsArticleLists.get(position);
        holder.tvContent.setText(newsArticleList.getTitle());
        RxView.clicks(holder.relMain).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("ArtCtId", newsArticleList.getArtCtId());
                intent.putExtra("collection",newsArticleList.isCollection());
                mContext.startActivity(intent);
            }
        });

        RxView.longClicks(holder.relMain).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                getCollectionCancle(newsArticleList.getArtCtId(), Utility.getSharedData(mContext, "token"), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsArticleLists.size();
    }

    class CollectionViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.rel_main)
        RelativeLayout relMain;

        public CollectionViewHolder(View itemView) {
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

    private void getCollectionCancle(String articleCurrentID, String token, final int position) {
        ApiManager.getInstance().getCollectionCancle(articleCurrentID, token).enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                if (null == response.body()) {
                    Toast.makeText(mContext, "网络连接失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.body().isSucceed()) {
                    Toast.makeText(mContext, "取消收藏成功", Toast.LENGTH_SHORT).show();
                    newsArticleLists.remove(position);
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                Toast.makeText(mContext, "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
