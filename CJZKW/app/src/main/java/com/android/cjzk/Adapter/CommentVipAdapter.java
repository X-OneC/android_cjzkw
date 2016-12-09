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

import com.android.cjzk.Bean.CommentVipListBean;
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
 * Created by XOne on 2016/8/10.
 */
public class CommentVipAdapter extends RecyclerView.Adapter<CommentVipAdapter.CommentVipViewHolder> {


    private Context mContext;
    private LayoutInflater inflater;
    private List<CommentVipListBean.DataBean.CommentListBean> listBeen;

    public CommentVipAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
        listBeen = new ArrayList<>();
    }

    @Override
    public CommentVipViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.adapter_com_vip, parent, false);
        return new CommentVipViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CommentVipViewHolder holder, int position) {
        final CommentVipListBean.DataBean.CommentListBean bean = listBeen.get(position);
        holder.userName.setText(bean.getUIp());
        holder.tvComment.setText(bean.getContent());
        holder.tvIntro.setText(bean.getArticle().getTitle());
        holder.tvTime.setText(bean.getSubTime().substring(0, 10));
        RxView.clicks(holder.relInfor).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("ArtCtId", bean.getArtCurId());
                intent.putExtra("collection", bean.isComment());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBeen.size();
    }

    class CommentVipViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.user_name)
        TextView userName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_comment)
        TextView tvComment;
        @BindView(R.id.tv_Intro)
        TextView tvIntro;
        @BindView(R.id.rel_infor)
        RelativeLayout relInfor;

        public CommentVipViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void getListData(List<CommentVipListBean.DataBean.CommentListBean> listbo) {
        if (listbo == null) {
            Toast.makeText(mContext, "网络加载失败", Toast.LENGTH_SHORT).show();
            return;
        }
        this.listBeen = listbo;
        notifyDataSetChanged();
    }

    public void getAllListData(List<CommentVipListBean.DataBean.CommentListBean> listbo) {
        if (listbo == null) {
            Toast.makeText(mContext, "网络加载失败", Toast.LENGTH_SHORT).show();
            return;
        }
        this.listBeen.addAll(listbo);
        notifyDataSetChanged();
    }
}
