package com.android.cjzk.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XOne on 2016/8/4.
 */
public class SchoolBean {
    private SchoolData Data;

    public SchoolData getData() {
        return Data;
    }

    public void setData(SchoolData data) {
        Data = data;
    }

    @Override
    public String toString() {
        return "SchoolBean{" +
                "Data=" + Data +
                '}';
    }

    public class SchoolData {
        private List<SchoolArticleList> ArticleList;

        public List<SchoolArticleList> getArticleList() {
            return ArticleList;
        }

        public void setArticleList(List<SchoolArticleList> articleList) {
            ArticleList = articleList;
        }

        @Override
        public String toString() {
            return "SchoolData{" +
                    "ArticleList=" + ArticleList +
                    '}';
        }
    }

    public class SchoolArticleList implements Serializable {
        /* "LiveId": "bdfc3341-31f5-4a80-9889-a652011f1505",
                "Title": "q123",
                "Intro": "123",
                "Teacher": "3213",
                "Btime": "2016-07-29T05:24:58",
                "Ctime": "2016-07-29T17:25:14.02",
                "RoomId": 615369354,
                "ImgUrl": "/Upload/image/201607/6360540991074552283565964.jpg",
                "IsDelete": false
        */
        private String LiveId;
        private String Title;
        private String Intro;
        private String Teacher;
        private String Btime;
        private String Ctime;
        private String RoomId;
        private String ImgUrl;
        private boolean IsDelete;
        private boolean IsCheck;
        private int State;

        public String getLiveId() {
            return LiveId;
        }

        public String getTitle() {
            return Title;
        }

        public String getIntro() {
            return Intro;
        }

        public String getTeacher() {
            return Teacher;
        }

        public String getBtime() {
            return Btime;
        }

        public String getCtime() {
            return Ctime;
        }

        public String getRoomId() {
            return RoomId;
        }

        public String getImgUrl() {
            return ImgUrl;
        }

        public boolean isDelete() {
            return IsDelete;
        }

        public void setLiveId(String liveId) {
            LiveId = liveId;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public void setIntro(String intro) {
            Intro = intro;
        }

        public void setTeacher(String teacher) {
            Teacher = teacher;
        }

        public void setBtime(String btime) {
            Btime = btime;
        }

        public void setCtime(String ctime) {
            Ctime = ctime;
        }

        public void setRoomId(String roomId) {
            RoomId = roomId;
        }

        public void setImgUrl(String imgUrl) {
            ImgUrl = imgUrl;
        }

        public void setDelete(boolean delete) {
            IsDelete = delete;
        }

        public boolean isCheck() {
            return IsCheck;
        }

        public void setCheck(boolean check) {
            IsCheck = check;
        }

        public int getState() {
            return State;
        }

        public void setState(int state) {
            State = state;
        }

        @Override
        public String toString() {
            return "SchoolArticleList{" +
                    "LiveId='" + LiveId + '\'' +
                    ", Title='" + Title + '\'' +
                    ", Intro='" + Intro + '\'' +
                    ", Teacher='" + Teacher + '\'' +
                    ", Btime='" + Btime + '\'' +
                    ", Ctime='" + Ctime + '\'' +
                    ", RoomId='" + RoomId + '\'' +
                    ", ImgUrl='" + ImgUrl + '\'' +
                    ", IsDelete=" + IsDelete +
                    '}';
        }
    }
}
