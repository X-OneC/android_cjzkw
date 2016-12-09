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

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by XOne on 2016/7/22.
 */
public interface ApiManagerService {

//    //news/article/list/{queryType}/{pageIndex}
//    @GET("news/article/list/{queryType}/{pageIndex}")
//    Observable<NewsBean> getNewsData(@Path("queryType") int queryType, @Path("pageIndex") int pageIndex);
//
//    // http://192.168.1.8:1997/news/showlive/list/1
//    @GET("news/showlive/list/{pageIndex}")
//    Observable<NewsBean> getShowLiveData(@Path("pageIndex") int index);

    @GET("news/article/list/{queryType}/{pageIndex}")
    Call<NewsBean> getNewsData(@Path("queryType") int queryType, @Path("pageIndex") int pageIndex, @Query("token") String token);

    @GET("/news/showlive/list/{pageIndex}")
    Call<LiveBean> getShowLiveData(@Path("pageIndex") int index, @Query("token") String token);

    ///news/calendar/list/{date}/{pageIndex}
    @GET("/news/calendar/list/{date}/{pageIndex}")
    Call<DateBean> getDateData(@Path("date") String date, @Path("pageIndex") int pageIndex);

    //news/calendar/list/2016-7
    @GET("news/calendar/list/{date}")
    Call<DataPositionBean> getPositionData(@Path("date") String date);

    //video/live/list/1'
    @GET("video/live/list/{pageIndex}")
    Call<SchoolBean> getSchoolData(@Path("pageIndex") int pageIndex);

    //news/search/情报/1/1'
    @GET("news/search/{text}/{pageIndex}/{pageSize}")
    Call<SeekBean> getSeekData(@Path("text") String text, @Path("pageIndex") int pageIndex, @Path("pageSize") int pageSize);

    //news/comments/submit?articleID=ec4be068-99b9-4b6f-9bbb-a65c01116cd0&content=1&userIP=1&parComId=1&token=1
    @GET("news/comments/submit")
    Call<CommentBean> getCommentData(@Query("articleID") String articleID,
                                     @Query("content") String content, @Query("userIP") String userIP
            , @Query("parComId") int parComId, @Query("token") String token);

    //news/comments/list/ec4be068-99b9-4b6f-9bbb-a65c01116cd0/1
    @GET("news/comments/list/{artId}/{pageIndex}")
    Call<CommentListBean> getCommentListData(@Path("artId") String artId, @Path("pageIndex") int pageIndex);

    //account/login?name=xone&password=123456
    @POST("account/login")
    Call<LoginBean> getLoginData(@Query("name") String name, @Query("password") String password);

    //account/register?name=xone&password=123456&email=1396649117%40qq.com
    @POST("account/register")
    Call<LoginBean> getRegisterData(@Query("name") String name, @Query("password") String password, @Query("email") String email);

    //news/collection/add/
    @GET("news/collection/add/{token}/{articleCurrentID}")
    Call<LoginBean> getCollectionData(@Path("articleCurrentID") String articleCurrentID, @Path("token") String token);

    //news/collection/Cancle/
    @GET("news/collection/Cancle/{token}/{articleCurrentID}")
    Call<LoginBean> getCollectionCancle(@Path("articleCurrentID") String articleCurrentID, @Path("token") String token);

    ///news/article/collectionList/91cb3dc8-bc7b-46c7-aa3f-b9054cd58744/1/10
    @GET("news/article/collectionList/{token}/{pageIndex}/15")
    Call<NewsBean> getCollectionListData(@Path("token") String token, @Path("pageIndex") int pageIndex);

    //news/comments/list/91cb3dc8-bc7b-46c7-aa3f-b9054cd58744/1/1
    @GET("news/comments/list/{token}/{pageIndex}/15")
    Call<CommentVipListBean> getShareData(@Path("token") String token, @Path("pageIndex") int pageIndex);

    ///video/record/list/1
    @GET("video/record/list/{index}")
    Call<VideoBean> getVideoData(@Path("index") int index);

    //news/article/info/aa2db87c-af3b-47fb-97bd-a65f009bb7d1?token=%22%22
    @GET("news/article/info/{articleCurrentID}")
    Call<LoginBean> getWebViewData(@Path("articleCurrentID") String articleCurrentID, @Query("token") String token);

    //account/logout?token=91cb3dc8-bc7b-46c7-aa3f-b9054cd58744
    @POST("account/logout")
    Call<LoginBean> getLogout(@Query("token") String token);

    //video/record/comment/submit?recId=90840b17-afc8-48dc-ade1-a65e00fe0ce1&token=87e12440-3f7d-41c4-9b7e-9c0434a51eb9&content=12344
    @POST("video/record/comment/submit")
    Call<LoginBean> sendVideoComment(@Query("recId") String recId, @Query("token") String token, @Query("content") String content);

    //video/record/comment/list/90840b17-afc8-48dc-ade1-a65e00fe0ce1/1
    @GET("video/record/comment/list/{recId}/{pageIndex}")
    Call<VideoCommentBean> getVideoCommentData(@Path("recId") String recId, @Path("pageIndex") int pageIndex);

    //news/banner/list?token=1
    @GET("news/banner/list")
    Call<NewsBean> getBanner(@Query("token") String token);

    //video/record/UpdatePlayCount/4587e838-7f37-4057-b651-a66400a977c6
    @POST("video/record/UpdatePlayCount")
    Call<LoginBean> getUpdatePlayCount(@Query("id") String id);

    //news/article/collectio/02ca7a95-5fc4-4e3f-8595-25d421f36c6e/07b44f24-fea7-459a-a8d3-a69e00a26b09
    @GET("news/article/collectio/{token}/{id}")
    Call<collectioBean> isCollectio(@Path("token") String token, @Path("id") String id);

    //http://api.cjzkw.cn/news/sifting/list
    @GET("news/sifting/list")
    Call<SiftingBean> getSiftingData();

    //news/hotsearch/list
    @GET("news/hotsearch/list")
    Call<SiftingBean> getHotsearchData();
}
