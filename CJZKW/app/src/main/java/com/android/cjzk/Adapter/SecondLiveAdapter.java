package com.android.cjzk.Adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.cjzk.Bean.LiveBean;
import com.android.cjzk.Bean.ShareBean;
import com.android.cjzk.R;
import com.android.cjzk.Utility.Utility;
import com.android.cjzk.activity.LiveWebViewActivity;
import com.android.cjzk.activity.MyBaseActivity;
import com.android.cjzk.fragment.MyBaseFragment;
import com.jakewharton.rxbinding.view.RxView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import imageShow.ImageShowActivity;
import rx.functions.Action1;

/**
 * Created by XOne on 2016/7/25.
 */
public class SecondLiveAdapter extends RecyclerView.Adapter<SecondLiveAdapter.SecondLiveHolder> {

    private static final String TAG = "SecondLiveAdapter";
    private MyBaseActivity mContext;
    private LayoutInflater inflater;
    private List<LiveBean.LiveArticleList> newsArticleLists;
    private DisplayImageOptions options;
    private final int MAX_LINE_COUNT = 3;
    private final int STATE_UNKNOW = -1;
    private final int STATE_NOT_OVERFLOW = 1; //文本行数不超过限定行数
    private final int STATE_COLLAPSED = 2; //文本行数超过限定行数,处于折叠状态
    private final int STATE_EXPANDED = 3; //文本行数超过限定行数,被点击全文展开
    private SparseArray<Integer> mTextStateList;
    private MyBaseFragment myBaseFragment;

    public SecondLiveAdapter(MyBaseActivity context, MyBaseFragment BaseFragment) {
        this.mContext = context;
        this.myBaseFragment = BaseFragment;
        inflater = LayoutInflater.from(mContext);
        newsArticleLists = new ArrayList<>();
        mTextStateList = new SparseArray<>();
//        setHasStableIds(true);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.background).showImageForEmptyUri(R.mipmap.text)
                .showImageOnFail(R.mipmap.text).cacheInMemory(true)
                .cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    @Override
    public int getItemCount() {
        return newsArticleLists == null ? 0 : newsArticleLists.size();
    }

//    @Override
//    public long getItemId(int position) {
//        return newsArticleLists.get(position).getReleaseDate().hashCode();
//    }

    @Override
    public SecondLiveHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_live_one, parent, false);
        return new SecondLiveHolder(view);
    }

    @Override
    public void onBindViewHolder(final SecondLiveHolder holder, final int position) {
        final LiveBean.LiveArticleList newsArticleList = newsArticleLists.get(position);
        final LiveBean.LiveContents liveContents = newsArticleList.getContents().get(0);
        holder.tvTime.setText(setTime(newsArticleList.getReleaseDate()));
        if (newsArticleList.getImages() == null || newsArticleList.getImages().size() <= 0) {
            holder.image.setVisibility(View.GONE);
        } else {
            ImageLoader.getInstance().displayImage(Utility.DefPicUrl + newsArticleList.getImages().get(0), holder.image, options);
        }
        if (newsArticleList.getLinks() == null || newsArticleList.getLinks().size() <= 0) {
            holder.textview.setVisibility(View.GONE);
        } else {
            holder.textview.setVisibility(View.VISIBLE);
            RxView.clicks(holder.textview).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
                @Override
                public void call(Void aVoid) {
                    Intent intent = new Intent(mContext, LiveWebViewActivity.class);
                    intent.putExtra("ArtCtId", newsArticleList.getLinks().get(0).getUrl());
                    mContext.startActivity(intent);
                }
            });
        }
        addListener(holder, position);
        if (Utility.getTimeDate(newsArticleList.getReleaseDate()) <= 18 && Utility.getTimeDate(newsArticleList.getReleaseDate()) >= 6) {
            holder.imDate.setBackground(Utility.getBitMapDrawableFromId(R.mipmap.im_date_sun, mContext));
        } else {
            holder.imDate.setBackground(Utility.getBitMapDrawableFromId(R.mipmap.im_date_yue, mContext));
        }

        RxView.clicks(holder.ibShare).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                ShareBean bean = new ShareBean();
                bean.setID(newsArticleList.getArticleCurrentID());
                bean.setURL("http://www.cjzkw.cn/Home/ShowLiveShare/" + newsArticleList.getArticleCurrentID());
                bean.setImg("/favicon.ico");
                bean.setTitle(liveContents.getContent());
                mContext.openShare(bean);
                bean = null;
            }
        });
    }

    class SecondLiveHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_Intro)
        TextView tvIntro;
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.im_date)
        ImageView imDate;
        @BindView(R.id.textview)
        TextView textview;
        @BindView(R.id.textview1)
        TextView textview1;
        @BindView(R.id.ib_share)
        RelativeLayout ibShare;
        @BindView(R.id.ib_sc)
        ImageButton ibSc;
        @BindView(R.id.mianView)
        LinearLayout main_view;

        public SecondLiveHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void getListData(List<LiveBean.LiveArticleList> listbo) {
        if (listbo == null) {
            Toast.makeText(mContext, "网络加载失败", Toast.LENGTH_SHORT).show();
            return;
        }
        this.newsArticleLists = listbo;
        notifyDataSetChanged();
    }

    public void getAllListData(List<LiveBean.LiveArticleList> listbo) {
        if (listbo == null) {
            Toast.makeText(mContext, "网络加载失败", Toast.LENGTH_SHORT).show();
            return;
        }
        this.newsArticleLists.addAll(listbo);
        notifyDataSetChanged();
    }

    private String setTime(String time) {
        return time.substring(11, time.lastIndexOf(":"));
    }

    private void addListener(final SecondLiveHolder holder, final int position) {
        final LiveBean.LiveArticleList newsArticleList = newsArticleLists.get(position);
        final LiveBean.LiveContents liveContents = newsArticleList.getContents().get(0);
        String contentA = setTvIntroContent(newsArticleList.getContents());
        int state = mTextStateList.get(position, STATE_UNKNOW);
        //如果该item是第一次初始化，则去获取文本的行数
        if (state == STATE_UNKNOW) {
            holder.tvIntro.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    //这个回调会调用多次，获取完行数记得注销监听
                    holder.tvIntro.getViewTreeObserver().removeOnPreDrawListener(this);
                    if (holder.tvIntro.getLineCount() > MAX_LINE_COUNT) {
                        holder.tvIntro.setMaxLines(MAX_LINE_COUNT);
                        holder.tvIntro.setEllipsize(TextUtils.TruncateAt.END);
                        holder.textview1.setVisibility(View.VISIBLE);
                        holder.textview1.setText("展开");
                        mTextStateList.put(position, STATE_COLLAPSED);
                    } else {
                        holder.textview1.setVisibility(View.GONE);
                        mTextStateList.put(position, STATE_NOT_OVERFLOW);
                    }
                    return true;
                }
            });
            holder.tvIntro.setMaxLines(Integer.MAX_VALUE);
            holder.tvIntro.setText(Html.fromHtml(contentA));
        } else {
            //如果之前已经初始化过了，则使用保存的状态，无需再获取一次
            switch (state) {
                case STATE_NOT_OVERFLOW:
                    holder.textview1.setVisibility(View.GONE);
                    break;
                case STATE_COLLAPSED:
                    holder.tvIntro.setMaxLines(MAX_LINE_COUNT);
                    holder.tvIntro.setEllipsize(TextUtils.TruncateAt.END);
                    holder.textview1.setVisibility(View.VISIBLE);
                    holder.textview1.setText("展开");
                    break;
                case STATE_EXPANDED:
                    holder.tvIntro.setMaxLines(Integer.MAX_VALUE);
                    holder.tvIntro.setEllipsize(null);
                    holder.textview1.setVisibility(View.VISIBLE);
                    holder.textview1.setText("收起");
                    break;
            }
            holder.tvIntro.setText(Html.fromHtml(contentA));
        }

        holder.textview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int state = mTextStateList.get(position, STATE_UNKNOW);
                if (state == STATE_COLLAPSED) {
                    holder.tvIntro.setMaxLines(Integer.MAX_VALUE);
                    holder.tvIntro.setEllipsize(null);
                    holder.textview1.setText("收起");
                    mTextStateList.put(position, STATE_EXPANDED);
                } else if (state == STATE_EXPANDED) {
                    holder.tvIntro.setMaxLines(MAX_LINE_COUNT);
                    holder.tvIntro.setEllipsize(TextUtils.TruncateAt.END);
                    holder.textview1.setText("展开");
                    mTextStateList.put(position, STATE_COLLAPSED);
                }
            }
        });



        RxView.clicks(holder.image).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Intent intent = new Intent();
                intent.putStringArrayListExtra("infos", newsArticleList.getImages());
                intent.setClass(mContext, ImageShowActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        myBaseFragment.changText(newsArticleList.getReleaseDate().substring(5, 10));
    }

    private boolean setString(String s1, String s2) {
        return (!s1.substring(0, s1.lastIndexOf("T")).equals(s2.substring(0, s2.lastIndexOf("T"))));
    }

    private String setTvIntroContent(List<LiveBean.LiveContents> contents) {
        StringBuffer buffer = null;
        buffer = new StringBuffer();
        for (int i = 0; i < contents.size(); i++) {
            buffer.append(contents.get(i).getContentA());
        }
        return buffer.toString();
    }
}
