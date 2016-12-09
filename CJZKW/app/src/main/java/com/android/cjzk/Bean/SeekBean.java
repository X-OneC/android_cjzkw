package com.android.cjzk.Bean;

import java.util.List;

/**
 * Created by XOne on 2016/8/8.
 */
public class SeekBean {
    /**
     * Message :
     * Succeed : true
     * Data : {"TotalCount":72,"ArticleList":[{"Title":"[财经早餐]楚天科技拟定增募资6.78亿元 投向智能装备制造","Intro":"楚天科技拟定增4000万股，募资6.78亿元，投向年产100台套后包工业机器人建设项目、年产50套智能仓储物流系统建设项目，预计两项目达产后合计可实现年均利润总额1.69亿元。具体实施上述两项目的楚天机器人(楚天科技现持股67.74%)此前曾获得国开基金6000万元注资。","Content":"align: left; line-height: 1.5em; margin-bottom: 25px;\"><strong><span style=\"font-family: 微软雅黑, &#39;Microsoft YaHei&#39;;\">【产业<font color=\"Red\">情报<\/font>】<\/span><\/strong><\/p><p style=\"text-indent: 2em; text-align: left; line-height: 1.5em; margin-bottom: 25px;\"><strong><span sty","Source":"财经智库网","ArticleCurrentID":"1195cb4a-5fc0-4822-911c-a65c0077daa8","ReleaseDate":"636062373824861038"}]}
     */

    private String Message;
    private boolean Succeed;
    /**
     * TotalCount : 72
     * ArticleList : [{"Title":"[财经早餐]楚天科技拟定增募资6.78亿元 投向智能装备制造","Intro":"楚天科技拟定增4000万股，募资6.78亿元，投向年产100台套后包工业机器人建设项目、年产50套智能仓储物流系统建设项目，预计两项目达产后合计可实现年均利润总额1.69亿元。具体实施上述两项目的楚天机器人(楚天科技现持股67.74%)此前曾获得国开基金6000万元注资。","Content":"align: left; line-height: 1.5em; margin-bottom: 25px;\"><strong><span style=\"font-family: 微软雅黑, &#39;Microsoft YaHei&#39;;\">【产业<font color=\"Red\">情报<\/font>】<\/span><\/strong><\/p><p style=\"text-indent: 2em; text-align: left; line-height: 1.5em; margin-bottom: 25px;\"><strong><span sty","Source":"财经智库网","ArticleCurrentID":"1195cb4a-5fc0-4822-911c-a65c0077daa8","ReleaseDate":"636062373824861038"}]
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

    public  class DataBean {
        private int TotalCount;
        /**
         * Title : [财经早餐]楚天科技拟定增募资6.78亿元 投向智能装备制造
         * Intro : 楚天科技拟定增4000万股，募资6.78亿元，投向年产100台套后包工业机器人建设项目、年产50套智能仓储物流系统建设项目，预计两项目达产后合计可实现年均利润总额1.69亿元。具体实施上述两项目的楚天机器人(楚天科技现持股67.74%)此前曾获得国开基金6000万元注资。
         * Content : align: left; line-height: 1.5em; margin-bottom: 25px;"><strong><span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;;">【产业<font color="Red">情报</font>】</span></strong></p><p style="text-indent: 2em; text-align: left; line-height: 1.5em; margin-bottom: 25px;"><strong><span sty
         * Source : 财经智库网
         * ArticleCurrentID : 1195cb4a-5fc0-4822-911c-a65c0077daa8
         * ReleaseDate : 636062373824861038
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

        public  class ArticleListBean {
            private String Title;
            private String Intro;
            private String Content;
            private String Source;
            private String ArticleCurrentID;
            private String ReleaseDate;

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

            public String getContent() {
                return Content;
            }

            public void setContent(String Content) {
                this.Content = Content;
            }

            public String getSource() {
                return Source;
            }

            public void setSource(String Source) {
                this.Source = Source;
            }

            public String getArticleCurrentID() {
                return ArticleCurrentID;
            }

            public void setArticleCurrentID(String ArticleCurrentID) {
                this.ArticleCurrentID = ArticleCurrentID;
            }

            public String getReleaseDate() {
                return ReleaseDate;
            }

            public void setReleaseDate(String ReleaseDate) {
                this.ReleaseDate = ReleaseDate;
            }
        }
    }

//    private SeekData Data;
//
//    public class SeekData{}
//
//    public class SeekArticleList{
//
//    }
}
