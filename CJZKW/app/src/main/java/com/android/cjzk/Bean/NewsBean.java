package com.android.cjzk.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XOne on 2016/7/22.
 */
public class NewsBean implements Serializable {
    private NewsData Data;

    public NewsData getData() {
        return Data;
    }

    public void setData(NewsData data) {
        this.Data = data;
    }

    @Override
    public String toString() {
        return "NewsBean{" +
                "data=" + Data +
                '}';
    }

    public class NewsData implements Serializable {
        private List<NewsArticleList> ArticleList;

        public List<NewsArticleList> getArticleLists() {
            return ArticleList;
        }

        public void setArticleLists(List<NewsArticleList> articleLists) {
            this.ArticleList = articleLists;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "articleLists=" + ArticleList +
                    '}';
        }
    }

    public class NewsArticleList implements Serializable {
        private String Title;
        private String RelDate;
        private String DefPicUrl;
        private String AffStock;
        private NewsArticle Article;
        private String ArtCtId;
        private boolean IsCollection;
        private String Mark;

        public boolean isCollection() {
            return IsCollection;
        }

        public void setCollection(boolean collection) {
            IsCollection = collection;
        }

        public String getTitle() {
            return Title;
        }

        public String getRelDate() {
            return RelDate;
        }

        public String getDefPicUrl() {
            return DefPicUrl;
        }

        public String getAffStock() {
            return AffStock;
        }

        public String getArtCtId() {
            return ArtCtId;
        }

        public void setArtCtId(String artCtId) {
            ArtCtId = artCtId;
        }

        public NewsArticle getArticle() {
            return Article;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public void setRelDate(String relDate) {
            RelDate = relDate;
        }

        public void setDefPicUrl(String defPicUrl) {
            DefPicUrl = defPicUrl;
        }

        public void setAffStock(String affStock) {
            AffStock = affStock;
        }

        public void setArticle(NewsArticle article) {
            this.Article = article;
        }

        public String getMark() {
            return Mark;
        }

        public void setMark(String mark) {
            Mark = mark;
        }

        @Override
        public String toString() {
            return "ArticleList{" +
                    "Title='" + Title + '\'' +
                    ", RelDate='" + RelDate + '\'' +
                    ", DefPicUrl='" + DefPicUrl + '\'' +
                    ", AffStock='" + AffStock + '\'' +
                    ", article=" + Article +
                    '}';
        }
    }

    public class NewsArticle implements Serializable {
        private String ArtId;
        private String Author;
        private String Source;
        private String Intro;
        private String Content;

        public String getArtId() {
            return ArtId;
        }

        public String getAuthor() {
            return Author;
        }

        public String getSource() {
            return Source;
        }

        public String getIntro() {
            return Intro;
        }

        public String getContent() {
            return Content;
        }

        public void setArtId(String artId) {
            ArtId = artId;
        }

        public void setAuthor(String author) {
            Author = author;
        }

        public void setSource(String source) {
            Source = source;
        }

        public void setIntro(String intro) {
            Intro = intro;
        }

        public void setContent(String content) {
            Content = content;
        }

        @Override
        public String toString() {
            return "Article{" +
                    "ArtId='" + ArtId + '\'' +
                    ", Author='" + Author + '\'' +
                    ", Source='" + Source + '\'' +
                    ", Intro='" + Intro + '\'' +
                    ", Content='" + Content + '\'' +
                    '}';
        }
    }
}
