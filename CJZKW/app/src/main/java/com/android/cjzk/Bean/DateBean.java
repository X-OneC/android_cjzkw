package com.android.cjzk.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XOne on 2016/8/4.
 */
public class DateBean implements Serializable{

    private DateData Data;

    public DateData getData() {
        return Data;
    }

    public void setData(DateData data) {
        Data = data;
    }

    @Override
    public String toString() {
        return "DateBean{" +
                "Data=" + Data +
                '}';
    }

    public class DateData implements Serializable{
        private List<DateArticleList> ArticleList;

        public List<DateArticleList> getArticleList() {
            return ArticleList;
        }

        public void setArticleList(List<DateArticleList> articleList) {
            ArticleList = articleList;
        }

        @Override
        public String toString() {
            return "DateData{" +
                    "ArticleList=" + ArticleList +
                    '}';
        }
    }

    public class DateArticleList implements Serializable {
        //        "CalId": "3710c084-1184-4f8b-9498-a6420098f112",
//                "OccurTime": "2016-07-01T00:00:00",
//                "CalIntro": "中明确规定，严禁",
//                "RelDate": "2016-07-13T09:16:50.617",
//                "AffPlate": "中明确规定，严禁",
//                "StkRecommend": "中明确规定，严禁",
//                "IsDelete": false
        private String CalId;
        private String OccurTime;
        private String CalIntro;
        private String RelDate;
        private String AffPlate;
        private String StkRecommend;
        private String IsDelete;

        public String getCalId() {
            return CalId;
        }

        public String getOccurTime() {
            return OccurTime;
        }

        public String getCalIntro() {
            return CalIntro;
        }

        public String getRelDate() {
            return RelDate;
        }

        public String getAffPlate() {
            return AffPlate;
        }

        public String getStkRecommend() {
            return StkRecommend;
        }

        public String getIsDelete() {
            return IsDelete;
        }

        public void setCalId(String calId) {
            CalId = calId;
        }

        public void setOccurTime(String occurTime) {
            OccurTime = occurTime;
        }

        public void setCalIntro(String calIntro) {
            CalIntro = calIntro;
        }

        public void setRelDate(String relDate) {
            RelDate = relDate;
        }

        public void setAffPlate(String affPlate) {
            AffPlate = affPlate;
        }

        public void setStkRecommend(String stkRecommend) {
            StkRecommend = stkRecommend;
        }

        public void setIsDelete(String isDelete) {
            IsDelete = isDelete;
        }

        @Override
        public String toString() {
            return "DateArticleList{" +
                    "CalId='" + CalId + '\'' +
                    ", OccurTime='" + OccurTime + '\'' +
                    ", CalIntro='" + CalIntro + '\'' +
                    ", RelDate='" + RelDate + '\'' +
                    ", AffPlate='" + AffPlate + '\'' +
                    ", StkRecommend='" + StkRecommend + '\'' +
                    ", IsDelete='" + IsDelete + '\'' +
                    '}';
        }
    }
}
