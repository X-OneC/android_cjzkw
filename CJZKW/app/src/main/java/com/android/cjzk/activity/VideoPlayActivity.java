package com.android.cjzk.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.cjzk.Adapter.VideoCommentAdapter;
import com.android.cjzk.Api.ApiManager;
import com.android.cjzk.Bean.LoginBean;
import com.android.cjzk.Bean.ShareBean;
import com.android.cjzk.Bean.VideoBean;
import com.android.cjzk.Bean.VideoCommentBean;
import com.android.cjzk.R;
import com.android.cjzk.Utility.MyApplication;
import com.android.cjzk.Utility.Utility;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.kevin.wraprecyclerview.WrapAdapter;
import com.umeng.socialize.UMShareAPI;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.UmengOneKeyShare;

public class VideoPlayActivity extends AppCompatActivity implements UniversalVideoView.VideoViewCallback {

    private static final String TAG = "MainActivity";
    private static final String SEEK_POSITION_KEY = "SEEK_POSITION_KEY";
    private static final String VIDEO_URL = "http://v1.mukewang.com/3e35cbb0-c8e5-4827-9614-b5a355259010/L.mp4";
    private UniversalVideoView mVideoView;
    private UniversalMediaController mMediaController;
    private View mBottomLayout;
    private View mVideoLayout;
    private ImageView mStart;
    private int mSeekPosition;
    private int cachedHeight;
    private boolean isFullscreen;
    RecyclerView recyclerView;
    SwipeToLoadLayout swipeToLoadLayout;
    private RelativeLayout videoRel_back, videoRel_share, videoRel_comment, relBG;
    private LinearLayout videolin_comment;
    private Button videobr_cancel, videobr_send;
    private EditText videoet_comment;
    private VideoBean.DataBean.ArticleListBean listBean;
    private VideoCommentAdapter adapter;
    private WrapAdapter<VideoCommentAdapter> wrapAdapter;
    private int Index = 1;
    private UmengOneKeyShare oneKeyShare;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        listBean = (VideoBean.DataBean.ArticleListBean) getIntent().getSerializableExtra("listBean");
        MyApplication.getInstance().addActivity(this);
        mVideoLayout = findViewById(R.id.video_layout);
        mVideoView = (UniversalVideoView) findViewById(R.id.videoView);
        mMediaController = (UniversalMediaController) findViewById(R.id.media_controller);
        mVideoView.setMediaController(mMediaController);
        setVideoAreaSize();
        mVideoView.setVideoViewCallback(this);
        mStart = (ImageView) findViewById(R.id.isPlay);
        relBG = (RelativeLayout) findViewById(R.id.relBG);
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSeekPosition > 0) {
                    mVideoView.seekTo(mSeekPosition);
                }
                mVideoView.start();
                mMediaController.setTitle(listBean.getTitle());
                relBG.setVisibility(View.GONE);
            }
        });

        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                finish();
            }
        });
        mBottomLayout = findViewById(R.id.lin_bottom);

        recyclerView = (RecyclerView) findViewById(R.id.swipe_target);
        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VideoCommentAdapter(this);
        wrapAdapter = new WrapAdapter<>(adapter);
        wrapAdapter.adjustSpanSize(recyclerView);
        recyclerView.setAdapter(wrapAdapter);
        addHeaderView();
        videoRel_back = (RelativeLayout) findViewById(R.id.videoRel_back);
        videoRel_share = (RelativeLayout) findViewById(R.id.videoRel_share);
        videoRel_comment = (RelativeLayout) findViewById(R.id.videoRel_comment);
        videolin_comment = (LinearLayout) findViewById(R.id.videolin_comment);
        videobr_cancel = (Button) findViewById(R.id.videobr_cancel);
        videobr_send = (Button) findViewById(R.id.videobr_send);
        videoet_comment = (EditText) findViewById(R.id.videoet_comment);
        addListener();
        getVideoCommentData(listBean.getRecId(), 1);
        getUpdatePlayCount(listBean.getRecId());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause ");
        if (mVideoView != null && mVideoView.isPlaying()) {
            mSeekPosition = mVideoView.getCurrentPosition();
            Log.d(TAG, "onPause mSeekPosition=" + mSeekPosition);
            mVideoView.pause();
        }
    }

    /**
     * 置视频区域大小
     */
    private void setVideoAreaSize() {
        mVideoLayout.post(new Runnable() {
            @Override
            public void run() {
                int width = mVideoLayout.getWidth();
                cachedHeight = (int) (width * 9f / 16f);
                ViewGroup.LayoutParams videoLayoutParams = mVideoLayout.getLayoutParams();
                videoLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                videoLayoutParams.height = cachedHeight;
                mVideoLayout.setLayoutParams(videoLayoutParams);
                mVideoView.setVideoPath(listBean.getVideoUrl());
                mVideoView.requestFocus();
            }
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState Position=" + mVideoView.getCurrentPosition());
        outState.putInt(SEEK_POSITION_KEY, mSeekPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
        mSeekPosition = outState.getInt(SEEK_POSITION_KEY);
        Log.d(TAG, "onRestoreInstanceState Position=" + mSeekPosition);
    }


    @Override
    public void onScaleChange(boolean isFullscreen) {
        this.isFullscreen = isFullscreen;
        if (isFullscreen) {
            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            mVideoLayout.setLayoutParams(layoutParams);
            mBottomLayout.setVisibility(View.GONE);
        } else {
            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = this.cachedHeight;
            mVideoLayout.setLayoutParams(layoutParams);
            mBottomLayout.setVisibility(View.VISIBLE);
        }
//        switchTitleBar(!isFullscreen);
    }

    private void switchTitleBar(boolean show) {
        android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            if (show) {
                supportActionBar.show();
            } else {
                supportActionBar.hide();
            }
        }
    }

    @Override
    public void onPause(MediaPlayer mediaPlayer) {
    }

    @Override
    public void onStart(MediaPlayer mediaPlayer) {
    }

    @Override
    public void onBufferingStart(MediaPlayer mediaPlayer) {
    }

    @Override
    public void onBufferingEnd(MediaPlayer mediaPlayer) {
    }

    @Override
    public void onBackPressed() {
        if (this.isFullscreen) {
            mVideoView.setFullscreen(false);
            return;
        }
        if (videolin_comment.isShown()) {
            videolin_comment.setVisibility(View.GONE);
            videoet_comment.setText("");
            Animation animation = AnimationUtils.loadAnimation(VideoPlayActivity.this, R.anim.xiu_gift_slide_out_to_bottom);
            videolin_comment.startAnimation(animation);
            animation = null;
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoView.pause();
        mVideoView.closePlayer();
        mVideoView.destroyDrawingCache();
        mVideoView = null;
        if (null != oneKeyShare) {
            oneKeyShare = null;
        }
        MyApplication.getInstance().removeActivity(this);
        System.gc();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private void addListener() {
        videoRel_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!videolin_comment.isShown()) {
                    videolin_comment.setVisibility(View.VISIBLE);
                    Animation animation = AnimationUtils.loadAnimation(VideoPlayActivity.this, R.anim.xiu_gift_slide_in_from_bottom);
                    videolin_comment.startAnimation(animation);
                    animation = null;
                }
            }
        });
        videoRel_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareBean bean = new ShareBean();
                bean.setID(listBean.getRecId());
                bean.setURL("http://www.caijingzk.com/Home/RecordedVidoeShare/" + listBean.getRecId());
                bean.setImg(listBean.getImgUrl());
                bean.setTitle(listBean.getIntro());
                if (null == oneKeyShare) {
                    oneKeyShare = new UmengOneKeyShare(VideoPlayActivity.this);
                }
                oneKeyShare.setOneShareBean(bean);
                oneKeyShare.share();
                bean = null;
            }
        });
        videoRel_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        videobr_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoet_comment.setText("");
                InputMethodManager imm = (InputMethodManager) getSystemService(VideoPlayActivity.this.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(videobr_cancel.getWindowToken(), 0);
                videolin_comment.setVisibility(View.GONE);
                Animation animation = AnimationUtils.loadAnimation(VideoPlayActivity.this, R.anim.xiu_gift_slide_out_to_bottom);
                videolin_comment.startAnimation(animation);
                animation = null;
            }
        });
        videobr_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = videoet_comment.getText().toString();
                if (text.equals("")) {
                    Toast.makeText(VideoPlayActivity.this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                setSendComment(listBean.getRecId(), Utility.getSharedData(VideoPlayActivity.this, "token"), text);
            }
        });
        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                Index = 1;
                getVideoCommentData(listBean.getRecId(), 1);
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Index++;
                getVideoCommentData(listBean.getRecId(), Index);
            }
        });
    }

    private void setSendComment(String recId, String token, String content) {
        ApiManager.getInstance().sendVideoComment(recId, token, content).enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                if (null == response.body()) {
                    Toast.makeText(VideoPlayActivity.this, "网络连接失败，请稍后评论", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.body().isSucceed()) {
                    if (videolin_comment.isShown()) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(VideoPlayActivity.this.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(videolin_comment.getWindowToken(), 0);
                        videolin_comment.setVisibility(View.GONE);
                        Animation animation = AnimationUtils.loadAnimation(VideoPlayActivity.this, R.anim.xiu_gift_slide_out_to_bottom);
                        videolin_comment.startAnimation(animation);
                        animation = null;
                    }
                    videoet_comment.setText("");
                    Toast.makeText(VideoPlayActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Index = 1;
                    getVideoCommentData(listBean.getRecId(), 1);
                } else {
                    Toast.makeText(VideoPlayActivity.this, "网络连接失败，请稍后评论", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                Toast.makeText(VideoPlayActivity.this, "网络连接失败，请稍后评论", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getVideoCommentData(String recId, final int pageIndex) {
        ApiManager.getInstance().getVideoCommentData(recId, pageIndex).enqueue(new Callback<VideoCommentBean>() {
            @Override
            public void onResponse(Call<VideoCommentBean> call, Response<VideoCommentBean> response) {
                VideoCommentBean newsBean = response.body();
                if (null == newsBean || null == newsBean.getData() || null == newsBean.getData().getCommentList()) {
                    Toast.makeText(VideoPlayActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pageIndex == 1) {
                    wrapAdapter.getWrappedAdapter().getListData(newsBean.getData().getCommentList());
                    if (newsBean.getData().getCommentList().size() <= 0) {
                        tv.setVisibility(View.GONE);
                    } else {
                        tv.setVisibility(View.VISIBLE);
                    }
                    swipeToLoadLayout.setRefreshing(false);
                } else {
                    wrapAdapter.getWrappedAdapter().getAllListData(newsBean.getData().getCommentList());
                    swipeToLoadLayout.setLoadingMore(false);
                }
                wrapAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<VideoCommentBean> call, Throwable t) {
                if (pageIndex == 1) {
                    swipeToLoadLayout.setRefreshing(false);
                } else {
                    swipeToLoadLayout.setLoadingMore(false);
                }
                Toast.makeText(VideoPlayActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addHeaderView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.heade_video, recyclerView, false);
        TextView tv_title = (TextView) layout.findViewById(R.id.tv_title);
        TextView tv_content = (TextView) layout.findViewById(R.id.tv_content);
        TextView tv_time = (TextView) layout.findViewById(R.id.tv_time);
        TextView tv_mun = (TextView) layout.findViewById(R.id.tv_mun);
        tv = (TextView) layout.findViewById(R.id.tv);
        tv_mun.setText(String.valueOf(listBean.getCount()) + "次");
        tv_time.setText(listBean.getCTime().substring(0, 10));
        tv_title.setText(listBean.getTitle());
        tv_content.setText(listBean.getIntro());
        wrapAdapter.addHeaderView(layout);
    }

    private void getUpdatePlayCount(String id) {
        ApiManager.getInstance().getUpdatePlayCount(id).enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
            }
        });
    }
}