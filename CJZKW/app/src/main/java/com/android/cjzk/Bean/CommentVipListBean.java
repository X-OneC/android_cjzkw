package com.android.cjzk.Bean;

import java.util.List;

/**
 * Created by XOne on 2016/8/10.
 */
public class CommentVipListBean {

    @Override
    public String toString() {
        return "CommentVipListBean{" +
                "Message='" + Message + '\'' +
                ", Succeed=" + Succeed +
                ", Data=" + Data +
                '}';
    }

    /**
     * Message :
     * Succeed : true
     * Data : {"TotalCount":1,"CommentList":[{"Children":null,"Member":null,"Article":{"ArtCtId":"00000000-0000-0000-0000-000000000000","CatId":0,"Title":"[盘中异动]收购戛然而止 光一科技复牌跌停","TitleExtend":0,"Inputer":"","Hits":0,"RelDate":"1900-01-01T00:00:00","DefPicUrl":"/Upload/image/201608/6360642090913178269287271_s_l.jpg","ArtId":"00000000-0000-0000-0000-000000000000","Position":0,"AffStock":"","STitle":"","FakeHits":0,"IsComment":false,"IsDelete":false,"IsStick":false,"Mark":"","IsHot":false,"IsCollection":false,"IsRed":false,"TimeBase":"0001-01-01T00:00:00","RelCats":null,"Article":null,"Category":null},"ComId":"653a7372-877c-4cd3-b49d-a65e00aab35e","ArtCurId":"84777256-667b-46a7-9e03-a65e00a9074b","UId":0,"UIp":"1","UIpAdd":"","ParComId":"00000000-0000-0000-0000-000000000000","SubTime":"2016-08-10T10:21:30.017","IsDelete":false,"IsStick":false,"Content":"123456","UpCount":0,"DownCount":0}]}
     */

    private String Message;
    private boolean Succeed;
    /**
     * TotalCount : 1
     * CommentList : [{"Children":null,"Member":null,"Article":{"ArtCtId":"00000000-0000-0000-0000-000000000000","CatId":0,"Title":"[盘中异动]收购戛然而止 光一科技复牌跌停","TitleExtend":0,"Inputer":"","Hits":0,"RelDate":"1900-01-01T00:00:00","DefPicUrl":"/Upload/image/201608/6360642090913178269287271_s_l.jpg","ArtId":"00000000-0000-0000-0000-000000000000","Position":0,"AffStock":"","STitle":"","FakeHits":0,"IsComment":false,"IsDelete":false,"IsStick":false,"Mark":"","IsHot":false,"IsCollection":false,"IsRed":false,"TimeBase":"0001-01-01T00:00:00","RelCats":null,"Article":null,"Category":null},"ComId":"653a7372-877c-4cd3-b49d-a65e00aab35e","ArtCurId":"84777256-667b-46a7-9e03-a65e00a9074b","UId":0,"UIp":"1","UIpAdd":"","ParComId":"00000000-0000-0000-0000-000000000000","SubTime":"2016-08-10T10:21:30.017","IsDelete":false,"IsStick":false,"Content":"123456","UpCount":0,"DownCount":0}]
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

    public static class DataBean {
        private int TotalCount;
        /**
         * Children : null
         * Member : null
         * Article : {"ArtCtId":"00000000-0000-0000-0000-000000000000","CatId":0,"Title":"[盘中异动]收购戛然而止 光一科技复牌跌停","TitleExtend":0,"Inputer":"","Hits":0,"RelDate":"1900-01-01T00:00:00","DefPicUrl":"/Upload/image/201608/6360642090913178269287271_s_l.jpg","ArtId":"00000000-0000-0000-0000-000000000000","Position":0,"AffStock":"","STitle":"","FakeHits":0,"IsComment":false,"IsDelete":false,"IsStick":false,"Mark":"","IsHot":false,"IsCollection":false,"IsRed":false,"TimeBase":"0001-01-01T00:00:00","RelCats":null,"Article":null,"Category":null}
         * ComId : 653a7372-877c-4cd3-b49d-a65e00aab35e
         * ArtCurId : 84777256-667b-46a7-9e03-a65e00a9074b
         * UId : 0
         * UIp : 1
         * UIpAdd :
         * ParComId : 00000000-0000-0000-0000-000000000000
         * SubTime : 2016-08-10T10:21:30.017
         * IsDelete : false
         * IsStick : false
         * Content : 123456
         * UpCount : 0
         * DownCount : 0
         */

        private List<CommentListBean> CommentList;

        public int getTotalCount() {
            return TotalCount;
        }

        public void setTotalCount(int TotalCount) {
            this.TotalCount = TotalCount;
        }

        public List<CommentListBean> getCommentList() {
            return CommentList;
        }

        public void setCommentList(List<CommentListBean> CommentList) {
            this.CommentList = CommentList;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "TotalCount=" + TotalCount +
                    ", CommentList=" + CommentList +
                    '}';
        }

        public static class CommentListBean {
            /**
             * ArtCtId : 00000000-0000-0000-0000-000000000000
             * CatId : 0
             * Title : [盘中异动]收购戛然而止 光一科技复牌跌停
             * TitleExtend : 0
             * Inputer :
             * Hits : 0
             * RelDate : 1900-01-01T00:00:00
             * DefPicUrl : /Upload/image/201608/6360642090913178269287271_s_l.jpg
             * ArtId : 00000000-0000-0000-0000-000000000000
             * Position : 0
             * AffStock :
             * STitle :
             * FakeHits : 0
             * IsComment : false
             * IsDelete : false
             * IsStick : false
             * Mark :
             * IsHot : false
             * IsCollection : false
             * IsRed : false
             * TimeBase : 0001-01-01T00:00:00
             * RelCats : null
             * Article : null
             * Category : null
             */

            private ArticleBean Article;
            private String ComId;
            private String ArtCurId;
            private int UId;
            private String UIp;
            private String UIpAdd;
            private String ParComId;
            private String SubTime;
            private boolean IsDelete;
            private boolean IsStick;
            private boolean IsComment;
            private String Content;
            private int UpCount;
            private int DownCount;

            public ArticleBean getArticle() {
                return Article;
            }

            public void setArticle(ArticleBean Article) {
                this.Article = Article;
            }

            public String getComId() {
                return ComId;
            }

            public void setComId(String ComId) {
                this.ComId = ComId;
            }

            public String getArtCurId() {
                return ArtCurId;
            }

            public void setArtCurId(String ArtCurId) {
                this.ArtCurId = ArtCurId;
            }

            public int getUId() {
                return UId;
            }

            public void setUId(int UId) {
                this.UId = UId;
            }

            public String getUIp() {
                return UIp;
            }

            public void setUIp(String UIp) {
                this.UIp = UIp;
            }

            public String getUIpAdd() {
                return UIpAdd;
            }

            public void setUIpAdd(String UIpAdd) {
                this.UIpAdd = UIpAdd;
            }

            public String getParComId() {
                return ParComId;
            }

            public void setParComId(String ParComId) {
                this.ParComId = ParComId;
            }

            public String getSubTime() {
                return SubTime;
            }

            public void setSubTime(String SubTime) {
                this.SubTime = SubTime;
            }

            public boolean isComment() {
                return IsComment;
            }

            public void setComment(boolean comment) {
                IsComment = comment;
            }

            public boolean isIsDelete() {
                return IsDelete;
            }

            public void setIsDelete(boolean IsDelete) {
                this.IsDelete = IsDelete;
            }

            public boolean isIsStick() {
                return IsStick;
            }

            public void setIsStick(boolean IsStick) {
                this.IsStick = IsStick;
            }

            public String getContent() {
                return Content;
            }

            public void setContent(String Content) {
                this.Content = Content;
            }

            public int getUpCount() {
                return UpCount;
            }

            public void setUpCount(int UpCount) {
                this.UpCount = UpCount;
            }

            public int getDownCount() {
                return DownCount;
            }

            public void setDownCount(int DownCount) {
                this.DownCount = DownCount;
            }

            @Override
            public String toString() {
                return "CommentListBean{" +
                        "Article=" + Article +
                        ", ComId='" + ComId + '\'' +
                        ", ArtCurId='" + ArtCurId + '\'' +
                        ", UId=" + UId +
                        ", UIp='" + UIp + '\'' +
                        ", UIpAdd='" + UIpAdd + '\'' +
                        ", ParComId='" + ParComId + '\'' +
                        ", SubTime='" + SubTime + '\'' +
                        ", IsDelete=" + IsDelete +
                        ", IsStick=" + IsStick +
                        ", Content='" + Content + '\'' +
                        ", UpCount=" + UpCount +
                        ", DownCount=" + DownCount +
                        '}';
            }

            public static class ArticleBean {
                private String ArtCtId;
                private int CatId;
                private String Title;
                private int TitleExtend;
                private String Inputer;
                private int Hits;
                private String RelDate;
                private String DefPicUrl;
                private String ArtId;
                private int Position;
                private String AffStock;
                private String STitle;
                private int FakeHits;
                private boolean IsComment;
                private boolean IsDelete;
                private boolean IsStick;
                private String Mark;
                private boolean IsHot;
                private boolean IsCollection;
                private boolean IsRed;
                private String TimeBase;
                private Object RelCats;
                private Object Article;
                private Object Category;

                @Override
                public String toString() {
                    return "ArticleBean{" +
                            "ArtCtId='" + ArtCtId + '\'' +
                            ", CatId=" + CatId +
                            ", Title='" + Title + '\'' +
                            ", TitleExtend=" + TitleExtend +
                            ", Inputer='" + Inputer + '\'' +
                            ", Hits=" + Hits +
                            ", RelDate='" + RelDate + '\'' +
                            ", DefPicUrl='" + DefPicUrl + '\'' +
                            ", ArtId='" + ArtId + '\'' +
                            ", Position=" + Position +
                            ", AffStock='" + AffStock + '\'' +
                            ", STitle='" + STitle + '\'' +
                            ", FakeHits=" + FakeHits +
                            ", IsComment=" + IsComment +
                            ", IsDelete=" + IsDelete +
                            ", IsStick=" + IsStick +
                            ", Mark='" + Mark + '\'' +
                            ", IsHot=" + IsHot +
                            ", IsCollection=" + IsCollection +
                            ", IsRed=" + IsRed +
                            ", TimeBase='" + TimeBase + '\'' +
                            ", RelCats=" + RelCats +
                            ", Article=" + Article +
                            ", Category=" + Category +
                            '}';
                }

                public String getArtCtId() {
                    return ArtCtId;
                }

                public void setArtCtId(String ArtCtId) {
                    this.ArtCtId = ArtCtId;
                }

                public int getCatId() {
                    return CatId;
                }

                public void setCatId(int CatId) {
                    this.CatId = CatId;
                }

                public String getTitle() {
                    return Title;
                }

                public void setTitle(String Title) {
                    this.Title = Title;
                }

                public int getTitleExtend() {
                    return TitleExtend;
                }

                public void setTitleExtend(int TitleExtend) {
                    this.TitleExtend = TitleExtend;
                }

                public String getInputer() {
                    return Inputer;
                }

                public void setInputer(String Inputer) {
                    this.Inputer = Inputer;
                }

                public int getHits() {
                    return Hits;
                }

                public void setHits(int Hits) {
                    this.Hits = Hits;
                }

                public String getRelDate() {
                    return RelDate;
                }

                public void setRelDate(String RelDate) {
                    this.RelDate = RelDate;
                }

                public String getDefPicUrl() {
                    return DefPicUrl;
                }

                public void setDefPicUrl(String DefPicUrl) {
                    this.DefPicUrl = DefPicUrl;
                }

                public String getArtId() {
                    return ArtId;
                }

                public void setArtId(String ArtId) {
                    this.ArtId = ArtId;
                }

                public int getPosition() {
                    return Position;
                }

                public void setPosition(int Position) {
                    this.Position = Position;
                }

                public String getAffStock() {
                    return AffStock;
                }

                public void setAffStock(String AffStock) {
                    this.AffStock = AffStock;
                }

                public String getSTitle() {
                    return STitle;
                }

                public void setSTitle(String STitle) {
                    this.STitle = STitle;
                }

                public int getFakeHits() {
                    return FakeHits;
                }

                public void setFakeHits(int FakeHits) {
                    this.FakeHits = FakeHits;
                }

                public boolean isIsComment() {
                    return IsComment;
                }

                public void setIsComment(boolean IsComment) {
                    this.IsComment = IsComment;
                }

                public boolean isIsDelete() {
                    return IsDelete;
                }

                public void setIsDelete(boolean IsDelete) {
                    this.IsDelete = IsDelete;
                }

                public boolean isIsStick() {
                    return IsStick;
                }

                public void setIsStick(boolean IsStick) {
                    this.IsStick = IsStick;
                }

                public String getMark() {
                    return Mark;
                }

                public void setMark(String Mark) {
                    this.Mark = Mark;
                }

                public boolean isIsHot() {
                    return IsHot;
                }

                public void setIsHot(boolean IsHot) {
                    this.IsHot = IsHot;
                }

                public boolean isIsCollection() {
                    return IsCollection;
                }

                public void setIsCollection(boolean IsCollection) {
                    this.IsCollection = IsCollection;
                }

                public boolean isIsRed() {
                    return IsRed;
                }

                public void setIsRed(boolean IsRed) {
                    this.IsRed = IsRed;
                }

                public String getTimeBase() {
                    return TimeBase;
                }

                public void setTimeBase(String TimeBase) {
                    this.TimeBase = TimeBase;
                }

                public Object getRelCats() {
                    return RelCats;
                }

                public void setRelCats(Object RelCats) {
                    this.RelCats = RelCats;
                }

                public Object getArticle() {
                    return Article;
                }

                public void setArticle(Object Article) {
                    this.Article = Article;
                }

                public Object getCategory() {
                    return Category;
                }

                public void setCategory(Object Category) {
                    this.Category = Category;
                }
            }
        }
    }
}
