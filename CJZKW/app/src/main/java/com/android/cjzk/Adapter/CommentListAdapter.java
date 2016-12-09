package com.android.cjzk.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.cjzk.Bean.CommentListBean;
import com.android.cjzk.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by XOne on 2016/8/8.
 */
public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.CommentListViewHolder> {

    private Context mContext;
    private LayoutInflater inflater;
    private List<CommentListBean.DataBean.ListBean> listBeen;

    public CommentListAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
        listBeen = new ArrayList<>();
    }

    @Override
    public CommentListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_video_comment, parent, false);
        CommentListViewHolder holder = new CommentListViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return listBeen == null ? 0 : listBeen.size();
    }

    @Override
    public void onBindViewHolder(CommentListViewHolder holder, int position) {
        CommentListBean.DataBean.ListBean bean = listBeen.get(position);
        holder.userName.setText(bean.getUIp().equals("")?"匿名用户":bean.getUIp());
        holder.tvContent.setText(bean.getContent());
        holder.tvTime.setText(bean.getSubTime().substring(0,10));
    }

    class CommentListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.user_name)
        TextView userName;
        @BindView(R.id.tv_comment)
        TextView tvContent;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public CommentListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void getListData(List<CommentListBean.DataBean.ListBean> listbo) {
        if (null == listbo) {
            Toast.makeText(mContext, "该篇文章暂时没有评论", Toast.LENGTH_SHORT).show();
            return;
        }
        this.listBeen = listbo;
        notifyDataSetChanged();
    }

    public void getAllListData(List<CommentListBean.DataBean.ListBean> listbo) {
        if (null == listbo) {
            Toast.makeText(mContext, "没有更多评论", Toast.LENGTH_SHORT).show();
            return;
        }
        this.listBeen.addAll(listbo);
        notifyDataSetChanged();
    }
}
