package com.android.cjzk.Api;

import com.android.cjzk.Bean.CommentBean;
import com.android.cjzk.Bean.CommentListBean;
import com.android.cjzk.Bean.CommentVipListBean;
import com.android.cjzk.Bean.DataPositionBean;
import com.android.cjzk.Bean.DateBean;
import com.android.cjzk.Bean.LiveBean;
import com.android.cjzk.Bean.LoginBean;
import com.android.cjzk.Bean.NewsBean;
import com.android.cjzk.Bean.SchoolBean;
import com.android.cjzk.Bean.SeekBean;
import com.android.cjzk.Bean.SiftingBean;
import com.android.cjzk.Bean.VideoBean;
import com.android.cjzk.Bean.VideoCommentBean;
import com.android.cjzk.Bean.collectioBean;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by XOne on 2016/7/22.
 */
public class ApiManager {

    private static final String BASE_URL = "http://api.cjzkw.cn/";
    private static final int DEFAULT_TIMEOUT = 10;
    private Retrofit retrofit;
    private static ApiManagerService apiManagerService;
    private static ApiManager apiManager;

    private ApiManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        apiManagerService = retrofit.create(ApiManagerService.class);
    }

    //获取单例
    public static ApiManager getInstance() {
        if (null == apiManager) {
            synchronized (ApiManager.class) {
                apiManager = new ApiManager();
            }
        }
        return apiManager;
    }

    // 获取情报数据
    public Call<NewsBean> getNewsData(int index, int queryType, String token) {
        Call<NewsBean> call = apiManagerService.getNewsData(queryType, index, token);
        return call;
    }

    //获取直播数据
    public Call<LiveBean> getShowLiveData(int index, String token) {
        Call<LiveBean> call = apiManagerService.getShowLiveData(index, token);
        return call;
    }

    //日历的接口
    public Call<DateBean> getDateData(String date, int pageIndex) {
        Call<DateBean> call = apiManagerService.getDateData(date, pageIndex);
        return call;
    }

    //获取日历上那天有数据的接口
    public Call<DataPositionBean> getPositionData(String date) {
        return apiManagerService.getPositionData(date);
    }

    //学堂的接口
    public Call<SchoolBean> getSchoolData(int pageIndex) {
        Call<SchoolBean> call = apiManagerService.getSchoolData(pageIndex);
        return call;
    }

    //搜索接口
    public Call<SeekBean> getSeekData(String text, int pageIndex, int pageSize) {
        return apiManagerService.getSeekData(text, pageIndex, pageSize);
    }

    //用户评论接口
    public Call<CommentBean> getCommentData(String articleID, String content, String userIP, int parComId, String token) {
        return apiManagerService.getCommentData(articleID, content, userIP, parComId, token);
    }

    //获取文章评论列表接口
    public Call<CommentListBean> getCommentListData(String artId, int pageIndex) {
        return apiManagerService.getCommentListData(artId, pageIndex);
    }

    //登录接口
    public Call<LoginBean> getLoginData(String name, String password) {
        return apiManagerService.getLoginData(name, password);
    }

    //注册接口
    public Call<LoginBean> getRegisterData(String name, String password, String email) {
        return apiManagerService.getRegisterData(name, password, email);
    }

    //添加评论
    public Call<LoginBean> getCollectionData(String articleCurrentID, String token) {
        return apiManagerService.getCollectionData(articleCurrentID, token);
    }

    //删除评论
    public Call<LoginBean> getCollectionCancle(String articleCurrentID, String token) {
        return apiManagerService.getCollectionCancle(articleCurrentID, token);
    }

    //收藏列表
    public Call<NewsBean> getCollectionListData(String token, int pageIndex) {
        return apiManagerService.getCollectionListData(token, pageIndex);
    }

    //会员评论接口
    public Call<CommentVipListBean> getShareData(String token, int pageIndex) {
        return apiManagerService.getShareData(token, pageIndex);
    }

    //录播视频接口
    public Call<VideoBean> getVideoData(int index) {
        return apiManagerService.getVideoData(index);
    }

    //webview数据获取接口
    public Call<LoginBean> getWebViewData(String articleCurrentID, String token) {
        return apiManagerService.getWebViewData(articleCurrentID, token);
    }

    //退出接口
    public Call<LoginBean> getLogout(String token) {
        return apiManagerService.getLogout(token);
    }

    //视频评论接口
    public Call<LoginBean> sendVideoComment(String recId, String token, String content) {
        return apiManagerService.sendVideoComment(recId, token, content);
    }

    //视频评论列表接口
    public Call<VideoCommentBean> getVideoCommentData(String recId, int pageIndex) {
        return apiManagerService.getVideoCommentData(recId, pageIndex);
    }

    //轮播图接口
    public Call<NewsBean> getBanner(String token) {
        return apiManagerService.getBanner(token);
    }

    //播放次数的接口
    public Call<LoginBean> getUpdatePlayCount(String id) {
        return apiManagerService.getUpdatePlayCount(id);
    }

    //筛选接口
    public Call<SiftingBean> getSiftingData() {
        return apiManagerService.getSiftingData();
    }

    //热门搜索接口
    public Call<SiftingBean> getHotsearchData() {
        return apiManagerService.getHotsearchData();
    }

    //文章是否收藏
    public Call<collectioBean> isCollectio(String token, String id) {
        return apiManagerService.isCollectio(token, id);
    }
//    private void setObservable(Observable observable, Action1<NewsBean> subscriber, Action1<Throwable> throwableAction1) {
//        observable.subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber, throwableAction1);
//    }

//    private void setObservable1(Observable observable, Subscriber<NewsBean> subscriber) {
//        observable.subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
//    }
}
