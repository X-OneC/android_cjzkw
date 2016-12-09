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

import com.android.cjzk.Bean.SchoolBean;
import com.android.cjzk.R;
import com.android.cjzk.Utility.Utility;
import com.android.cjzk.activity.SchoolLiveActivity;
import com.android.cjzk.activity.loginActivity;
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
 * Created by XOne on 2016/8/4.
 */
public class SchoolOneAdapter extends RecyclerView.Adapter<SchoolOneAdapter.SchoolOneViewHolder> {

    private Context mContext;
    private LayoutInflater inflater;
    private List<SchoolBean.SchoolArticleList> schoolArticleLists;
    private DisplayImageOptions options;

    public SchoolOneAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
        schoolArticleLists = new ArrayList<>();
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.background).showImageForEmptyUri(R.mipmap.text)
                .showImageOnFail(R.mipmap.text).cacheInMemory(true)
                .cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    @Override
    public SchoolOneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_school_one, parent, false);
        SchoolOneViewHolder holder = new SchoolOneViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(SchoolOneViewHolder holder, int position) {
        final SchoolBean.SchoolArticleList schoolArticleList = schoolArticleLists.get(position);
        holder.tvTitle.setText(schoolArticleList.getTitle());
        holder.tvName.setText(schoolArticleList.getTeacher());
        holder.DefPicUrl.getLayoutParams().width = (int) (Utility.screenWidth - Utility.setRatio(20));
        holder.DefPicUrl.getLayoutParams().height = (int) ((Utility.screenWidth - Utility.setRatio(20)) / 2);
        ImageLoader.getInstance().displayImage(Utility.DefPicUrl + schoolArticleList.getImgUrl(), holder.DefPicUrl, options);
        if (schoolArticleList.getState() == 1) {
            holder.imIsLive.setBackgroundDrawable(Utility.getBitMapDrawableFromId(R.mipmap.school_liveing, mContext));
            holder.tvTime.setText(setTime(schoolArticleList.getBtime(), "正播"));
        } else {
            holder.imIsLive.setBackgroundDrawable(Utility.getBitMapDrawableFromId(R.mipmap.school_yugao, mContext));
            holder.tvTime.setText(setTime(schoolArticleList.getBtime(), "开播"));
        }
        RxView.clicks(holder.cardView).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (Utility.getSharedData(mContext, "token").equals("")) {
                    if (schoolArticleList.isCheck()) {
                        mContext.startActivity(new Intent(mContext, loginActivity.class));
                    } else {
                        Intent intent = new Intent(mContext, SchoolLiveActivity.class);
                        intent.putExtra("bean", schoolArticleList);
                        mContext.startActivity(intent);
                    }
                } else {
                    Intent intent = new Intent(mContext, SchoolLiveActivity.class);
                    intent.putExtra("bean", schoolArticleList);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return schoolArticleLists == null ? 0 : schoolArticleLists.size();
    }

    class SchoolOneViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.cardView)
        CardView cardView;
        @BindView(R.id.im_isLive)
        ImageView imIsLive;
        @BindView(R.id.DefPicUrl)
        ImageView DefPicUrl;

        public SchoolOneViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void getListData(List<SchoolBean.SchoolArticleList> listbo) {
        if (listbo == null) {
            Toast.makeText(mContext, "网络加载失败", Toast.LENGTH_SHORT).show();
            return;
        }
        this.schoolArticleLists = listbo;
        notifyDataSetChanged();
    }

    public void getAllListData(List<SchoolBean.SchoolArticleList> listbo) {
        if (listbo == null) {
            Toast.makeText(mContext, "网络加载失败", Toast.LENGTH_SHORT).show();
            return;
        }
        this.schoolArticleLists.addAll(listbo);
        notifyDataSetChanged();
    }

    private String setTime(String str, String text) {
        StringBuffer bufferTime = null;
        bufferTime = new StringBuffer();
        bufferTime.append(str.substring(5, str.lastIndexOf("T")));
        bufferTime.append(" ");
        bufferTime.append(str.substring(str.lastIndexOf("T") + 1, str.lastIndexOf(":")));
        bufferTime.append(" ");
        bufferTime.append(text);
        return bufferTime.toString();
    }
}
