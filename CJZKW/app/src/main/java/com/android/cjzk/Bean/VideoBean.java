package com.android.cjzk.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XOne on 2016/8/10.
 */
public class VideoBean implements Serializable {


    /**
     * Message :
     * Succeed : true
     * Data : {"TotalCount":2,"ArticleList":[{"RecId":"f87e9572-54d7-4c29-b049-a65e00feac16","Title":"sdfasdf","Intro":"safsadf","Teacher":"asdfsadf","CTime":"2016-08-10T15:27:13.987","VideoId":"123456","ImgUrl":"/Upload/image/201608/6360643962166021214285437.jpg","IsDelete":false},{"RecId":"90840b17-afc8-48dc-ade1-a65e00fe0ce1","Title":"test","Intro":"asdf","Teacher":"adsf","CTime":"2016-08-10T15:24:58.117","VideoId":"asdf","ImgUrl":"/Upload/image/201608/6360643948030355123903713.jpg","IsDelete":false}]}
     */

    private String Message;
    private boolean Succeed;
    /**
     * TotalCount : 2
     * ArticleList : [{"RecId":"f87e9572-54d7-4c29-b049-a65e00feac16","Title":"sdfasdf","Intro":"safsadf","Teacher":"asdfsadf","CTime":"2016-08-10T15:27:13.987","VideoId":"123456","ImgUrl":"/Upload/image/201608/6360643962166021214285437.jpg","IsDelete":false},{"RecId":"90840b17-afc8-48dc-ade1-a65e00fe0ce1","Title":"test","Intro":"asdf","Teacher":"adsf","CTime":"2016-08-10T15:24:58.117","VideoId":"asdf","ImgUrl":"/Upload/image/201608/6360643948030355123903713.jpg","IsDelete":false}]
     */

    private DataBean Data;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public boolean isSucceed() {
        return Succeed;
    }

    public void setSucceed(boolean Succeed) {
        this.Succeed = Succeed;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean implements Serializable {
        private int TotalCount;
        /**
         * RecId : f87e9572-54d7-4c29-b049-a65e00feac16
         * Title : sdfasdf
         * Intro : safsadf
         * Teacher : asdfsadf
         * CTime : 2016-08-10T15:27:13.987
         * VideoId : 123456
         * ImgUrl : /Upload/image/201608/6360643962166021214285437.jpg
         * IsDelete : false
         */

        private List<ArticleListBean> ArticleList;

        public int getTotalCount() {
            return TotalCount;
        }

        public void setTotalCount(int TotalCount) {
            this.TotalCount = TotalCount;
        }

        public List<ArticleListBean> getArticleList() {
            return ArticleList;
        }

        public void setArticleList(List<ArticleListBean> ArticleList) {
            this.ArticleList = ArticleList;
        }

        public static class ArticleListBean implements Serializable {
            private String RecId;
            private String Title;
            private String Intro;
            private String Teacher;
            private String CTime;
            private String VideoUrl;
            private String ImgUrl;
            private boolean IsDelete;
            private boolean IsElite;
            private int count;


            public String getRecId() {
                return RecId;
            }

            public void setRecId(String RecId) {
                this.RecId = RecId;
            }

            public String getTitle() {
                return Title;
            }

            public void setTitle(String Title) {
                this.Title = Title;
            }

            public String getIntro() {
                return Intro;
            }

            public void setIntro(String Intro) {
                this.Intro = Intro;
            }

            public String getTeacher() {
                return Teacher;
            }

            public void setTeacher(String Teacher) {
                this.Teacher = Teacher;
            }

            public String getCTime() {
                return CTime;
            }

            public void setCTime(String CTime) {
                this.CTime = CTime;
            }

            public String getVideoUrl() {
                return VideoUrl;
            }

            public void setVideoUrl(String VideoId) {
                this.VideoUrl = VideoId;
            }

            public String getImgUrl() {
                return ImgUrl;
            }

            public void setImgUrl(String ImgUrl) {
                this.ImgUrl = ImgUrl;
            }

            public boolean isIsDelete() {
                return IsDelete;
            }

            public void setIsDelete(boolean IsDelete) {
                this.IsDelete = IsDelete;
            }

            public int getCount() {
                return count;
            }

            public boolean isDelete() {
                return IsDelete;
            }

            public void setDelete(boolean delete) {
                IsDelete = delete;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public boolean isElite() {
                return IsElite;
            }

            public void setElite(boolean elite) {
                IsElite = elite;
            }
        }
    }
}
