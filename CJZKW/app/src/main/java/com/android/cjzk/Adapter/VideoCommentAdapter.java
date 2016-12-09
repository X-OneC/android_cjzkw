package com.android.cjzk.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.cjzk.Bean.VideoCommentBean;
import com.android.cjzk.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by XOne on 2016/8/12.
 */
public class VideoCommentAdapter extends RecyclerView.Adapter<VideoCommentAdapter.VideoCommentViewHolder> {


    private Context mContext;
    private LayoutInflater inflater;
    private List<VideoCommentBean.DataBean.CommentListBean> listBeen;

    public VideoCommentAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
        listBeen = new ArrayList<>();
    }

    @Override
    public VideoCommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.adapter_video_comment, parent, false);
        return new VideoCommentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(VideoCommentViewHolder holder, int position) {
        final VideoCommentBean.DataBean.CommentListBean bean = listBeen.get(position);
        holder.userName.setText(bean.getMember().getMbrName().equals("") ? "匿名用户" : bean.getMember().getMbrName());
        holder.tvComment.setText(bean.getContent());
        holder.tvTime.setText(bean.getSubTime().substring(0, 10));
    }

    @Override
    public int getItemCount() {
        return listBeen.size();
    }

    class VideoCommentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.user_name)
        TextView userName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_comment)
        TextView tvComment;

        public VideoCommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void getListData(List<VideoCommentBean.DataBean.CommentListBean> listbo) {
        if (listbo == null) {
            Toast.makeText(mContext, "网络加载失败", Toast.LENGTH_SHORT).show();
            return;
        }
        this.listBeen = listbo;
        notifyDataSetChanged();
    }

    public void getAllListData(List<VideoCommentBean.DataBean.CommentListBean> listbo) {
        if (listbo == null) {
            Toast.makeText(mContext, "网络加载失败", Toast.LENGTH_SHORT).show();
            return;
        }
        this.listBeen.addAll(listbo);
        notifyDataSetChanged();
    }
}

