package com.android.cjzk.Bean;

import java.util.List;

/**
 * Created by XOne on 2016/8/8.
 */
public class CommentListBean {


    /**
     * Message :
     * Succeed : true
     * Data : {"List":[{"Children":[],"Member":{"MbrId":0,"MbrName":"","RelName":"","Password":"","UId":0,"Email":"","TelNo":"","Icon":"","RegTime":"1900-01-01T00:00:00","User":null,"Roles":null},"Article":null,"ComId":"be083475-4fc1-4525-bff2-a65c01126329","ArtCurId":"ec4be068-99b9-4b6f-9bbb-a65c01116cd0","UId":0,"UIp":"1","UIpAdd":"","ParComId":"00000000-0000-0000-0000-000000000000","SubTime":"2016-08-08T16:39:00.687","IsDelete":false,"IsStick":false,"Content":"1","UpCount":0,"DownCount":0}],"TotalCount":1}
     */

    private String Message;
    private boolean Succeed;
    /**
     * List : [{"Children":[],"Member":{"MbrId":0,"MbrName":"","RelName":"","Password":"","UId":0,"Email":"","TelNo":"","Icon":"","RegTime":"1900-01-01T00:00:00","User":null,"Roles":null},"Article":null,"ComId":"be083475-4fc1-4525-bff2-a65c01126329","ArtCurId":"ec4be068-99b9-4b6f-9bbb-a65c01116cd0","UId":0,"UIp":"1","UIpAdd":"","ParComId":"00000000-0000-0000-0000-000000000000","SubTime":"2016-08-08T16:39:00.687","IsDelete":false,"IsStick":false,"Content":"1","UpCount":0,"DownCount":0}]
     * TotalCount : 1
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
         * Children : []
         * Member : {"MbrId":0,"MbrName":"","RelName":"","Password":"","UId":0,"Email":"","TelNo":"","Icon":"","RegTime":"1900-01-01T00:00:00","User":null,"Roles":null}
         * Article : null
         * ComId : be083475-4fc1-4525-bff2-a65c01126329
         * ArtCurId : ec4be068-99b9-4b6f-9bbb-a65c01116cd0
         * UId : 0
         * UIp : 1
         * UIpAdd :
         * ParComId : 00000000-0000-0000-0000-000000000000
         * SubTime : 2016-08-08T16:39:00.687
         * IsDelete : false
         * IsStick : false
         * Content : 1
         * UpCount : 0
         * DownCount : 0
         */

        private java.util.List<ListBean> List;

        public int getTotalCount() {
            return TotalCount;
        }

        public void setTotalCount(int TotalCount) {
            this.TotalCount = TotalCount;
        }

        public List<ListBean> getList() {
            return List;
        }

        public void setList(List<ListBean> List) {
            this.List = List;
        }

        public static class ListBean {
            /**
             * MbrId : 0
             * MbrName :
             * RelName :
             * Password :
             * UId : 0
             * Email :
             * TelNo :
             * Icon :
             * RegTime : 1900-01-01T00:00:00
             * User : null
             * Roles : null
             */

            private MemberBean Member;
            private Object Article;
            private String ComId;
            private String ArtCurId;
            private int UId;
            private String UIp;
            private String UIpAdd;
            private String ParComId;
            private String SubTime;
            private boolean IsDelete;
            private boolean IsStick;
            private String Content;
            private int UpCount;
            private int DownCount;
            private java.util.List<?> Children;

            public MemberBean getMember() {
                return Member;
            }

            public void setMember(MemberBean Member) {
                this.Member = Member;
            }

            public Object getArticle() {
                return Article;
            }

            public void setArticle(Object Article) {
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

            public List<?> getChildren() {
                return Children;
            }

            public void setChildren(List<?> Children) {
                this.Children = Children;
            }

            public static class MemberBean {
                private int MbrId;
                private String MbrName;
                private String RelName;
                private String Password;
                private int UId;
                private String Email;
                private String TelNo;
                private String Icon;
                private String RegTime;
                private Object User;
                private Object Roles;

                public int getMbrId() {
                    return MbrId;
                }

                public void setMbrId(int MbrId) {
                    this.MbrId = MbrId;
                }

                public String getMbrName() {
                    return MbrName;
                }

                public void setMbrName(String MbrName) {
                    this.MbrName = MbrName;
                }

                public String getRelName() {
                    return RelName;
                }

                public void setRelName(String RelName) {
                    this.RelName = RelName;
                }

                public String getPassword() {
                    return Password;
                }

                public void setPassword(String Password) {
                    this.Password = Password;
                }

                public int getUId() {
                    return UId;
                }

                public void setUId(int UId) {
                    this.UId = UId;
                }

                public String getEmail() {
                    return Email;
                }

                public void setEmail(String Email) {
                    this.Email = Email;
                }

                public String getTelNo() {
                    return TelNo;
                }

                public void setTelNo(String TelNo) {
                    this.TelNo = TelNo;
                }

                public String getIcon() {
                    return Icon;
                }

                public void setIcon(String Icon) {
                    this.Icon = Icon;
                }

                public String getRegTime() {
                    return RegTime;
                }

                public void setRegTime(String RegTime) {
                    this.RegTime = RegTime;
                }

                public Object getUser() {
                    return User;
                }

                public void setUser(Object User) {
                    this.User = User;
                }

                public Object getRoles() {
                    return Roles;
                }

                public void setRoles(Object Roles) {
                    this.Roles = Roles;
                }
            }
        }
    }
}
