package com.android.cjzk.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XOne on 2016/8/5.
 */
public class LiveBean {
    private LiveData Data;

    public LiveData getData() {
        return Data;
    }

    public void setData(LiveData data) {
        Data = data;
    }

    @Override
    public String toString() {
        return "LiveBean{" +
                "Data=" + Data +
                '}';
    }

    public class LiveData {
        private List<LiveArticleList> ArticleList;

        public List<LiveArticleList> getArticleList() {
            return ArticleList;
        }

        public void setArticleList(List<LiveArticleList> articleList) {
            ArticleList = articleList;
        }

        @Override
        public String toString() {
            return "LiveData{" +
                    "ArticleList=" + ArticleList +
                    '}';
        }
    }

    public class LiveArticleList {

        //        "ArticleCurrentID": "91cf98ed-48e7-4ea0-8c5d-a65900ffd5b5",
//                "CategoryID": 3,
//                "ReleaseDate": "2016-08-05T00:00:00",
//                "Contents": [
//        "Images": [],
//                "Links": []
        private String ArticleCurrentID;
        private int CategoryID;
        private String ReleaseDate;
        private List<LiveContents> Contents;
        private ArrayList<String> Images;
        private List<LiveLinks> Links;

        public String getArticleCurrentID() {
            return ArticleCurrentID;
        }

        public int getCategoryID() {
            return CategoryID;
        }

        public String getReleaseDate() {
            return ReleaseDate;
        }

        public List<LiveContents> getContents() {
            return Contents;
        }

        public ArrayList<String> getImages() {
            return Images;
        }

        public List<LiveLinks> getLinks() {
            return Links;
        }

        public void setArticleCurrentID(String articleCurrentID) {
            ArticleCurrentID = articleCurrentID;
        }

        public void setCategoryID(int categoryID) {
            CategoryID = categoryID;
        }

        public void setReleaseDate(String releaseDate) {
            ReleaseDate = releaseDate;
        }

        public void setContents(List<LiveContents> contents) {
            Contents = contents;
        }

        public void setImages(ArrayList<String> images) {
            Images = images;
        }

        public void setLinks(List<LiveLinks> links) {
            Links = links;
        }

        @Override
        public String toString() {
            return "LiveArticleList{" +
                    "ArticleCurrentID='" + ArticleCurrentID + '\'' +
                    ", CategoryID=" + CategoryID +
                    ", ReleaseDate='" + ReleaseDate + '\'' +
                    ", Contents=" + Contents +
                    ", Images=" + Images +
                    ", Links=" + Links +
                    '}';
        }
    }

    public class LiveContents {

        /*"Content": "英国央行行长卡尼：不希望英国央行的行动对市场造成负面影响，希望其能对家庭以及商业起效只有在适宜的时候才会进一步降息",
                    "IsMark": false,
                    "IsBlod": false,
                    "ContentA": "<font color*/
        private String Content;
        private String ContentA;
        private boolean IsBlod;
        private boolean IsMark;

        public String getContent() {
            return Content;
        }

        public String getContentA() {
            return ContentA;
        }

        public boolean isBlod() {
            return IsBlod;
        }

        public boolean isMark() {
            return IsMark;
        }

        public void setContent(String content) {
            Content = content;
        }

        public void setContentA(String contentA) {
            ContentA = contentA;
        }

        public void setBlod(boolean blod) {
            IsBlod = blod;
        }

        public void setMark(boolean mark) {
            IsMark = mark;
        }

        @Override
        public String toString() {
            return "LiveContents{" +
                    "Content='" + Content + '\'' +
                    ", ContentA='" + ContentA + '\'' +
                    ", IsBlod=" + IsBlod +
                    ", IsMark=" + IsMark +
                    '}';
        }
    }

    public class LiveLinks {
        //        "Url": "http://www.caijingzk.com/Home/Index",
//                "Text": "6.2%"
        private String Url;
        private String Text;

        public String getUrl() {
            return Url;
        }

        public String getText() {
            return Text;
        }

        public void setText(String text) {
            Text = text;
        }

        public void setUrl(String url) {
            Url = url;
        }

        @Override
        public String toString() {
            return "LiveLinks{" +
                    "Url='" + Url + '\'' +
                    ", Text='" + Text + '\'' +
                    '}';
        }
    }
}
