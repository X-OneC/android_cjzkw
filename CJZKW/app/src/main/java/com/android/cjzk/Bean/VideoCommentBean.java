package com.android.cjzk.Bean;

import java.util.List;

/**
 * Created by XOne on 2016/8/12.
 */
public class VideoCommentBean {

    /**
     * Message :
     * Succeed : true
     * Data : {"TotalCount":6,"CommentList":[{"ComId":"990052de-6910-4a0e-b0c0-a65f018b7d57","RecId":"90840b17-afc8-48dc-ade1-a65e00fe0ce1","SubTime":"2016-08-11T23:59:55.983","Content":"12344","IsDelete":false,"UId":23,"Member":{"MbrId":0,"MbrName":"xone","RelName":"","Password":"","UId":0,"Email":"","TelNo":"","Icon":"","RegTime":"1900-01-01T00:00:00","User":null,"Roles":null}},{"ComId":"79d67f08-1ba3-4933-bee0-a65f0121c2b7","RecId":"90840b17-afc8-48dc-ade1-a65e00fe0ce1","SubTime":"2016-08-11T17:34:59.167","Content":"11111","IsDelete":false,"UId":29,"Member":{"MbrId":0,"MbrName":"123456","RelName":"","Password":"","UId":0,"Email":"","TelNo":"","Icon":"","RegTime":"1900-01-01T00:00:00","User":null,"Roles":null}},{"ComId":"5d2aee91-0d3f-4307-a951-a65f0121b665","RecId":"90840b17-afc8-48dc-ade1-a65e00fe0ce1","SubTime":"2016-08-11T17:34:48.65","Content":"11111","IsDelete":false,"UId":29,"Member":{"MbrId":0,"MbrName":"123456","RelName":"","Password":"","UId":0,"Email":"","TelNo":"","Icon":"","RegTime":"1900-01-01T00:00:00","User":null,"Roles":null}},{"ComId":"a5b0e35f-571a-4f96-9ce1-a65f012073ad","RecId":"90840b17-afc8-48dc-ade1-a65e00fe0ce1","SubTime":"2016-08-11T17:30:13.263","Content":"11111","IsDelete":false,"UId":29,"Member":{"MbrId":0,"MbrName":"123456","RelName":"","Password":"","UId":0,"Email":"","TelNo":"","Icon":"","RegTime":"1900-01-01T00:00:00","User":null,"Roles":null}},{"ComId":"f89cabb4-e800-4993-9c92-a65f011f1760","RecId":"90840b17-afc8-48dc-ade1-a65e00fe0ce1","SubTime":"2016-08-11T17:25:16.05","Content":"11111","IsDelete":false,"UId":29,"Member":{"MbrId":0,"MbrName":"123456","RelName":"","Password":"","UId":0,"Email":"","TelNo":"","Icon":"","RegTime":"1900-01-01T00:00:00","User":null,"Roles":null}},{"ComId":"4c46bdbb-377e-4cfd-a05c-a65f011d67ac","RecId":"90840b17-afc8-48dc-ade1-a65e00fe0ce1","SubTime":"2016-08-11T17:19:07.663","Content":"11111","IsDelete":false,"UId":29,"Member":{"MbrId":0,"MbrName":"123456","RelName":"","Password":"","UId":0,"Email":"","TelNo":"","Icon":"","RegTime":"1900-01-01T00:00:00","User":null,"Roles":null}}]}
     */

    private String Message;
    private boolean Succeed;
    /**
     * TotalCount : 6
     * CommentList : [{"ComId":"990052de-6910-4a0e-b0c0-a65f018b7d57","RecId":"90840b17-afc8-48dc-ade1-a65e00fe0ce1","SubTime":"2016-08-11T23:59:55.983","Content":"12344","IsDelete":false,"UId":23,"Member":{"MbrId":0,"MbrName":"xone","RelName":"","Password":"","UId":0,"Email":"","TelNo":"","Icon":"","RegTime":"1900-01-01T00:00:00","User":null,"Roles":null}},{"ComId":"79d67f08-1ba3-4933-bee0-a65f0121c2b7","RecId":"90840b17-afc8-48dc-ade1-a65e00fe0ce1","SubTime":"2016-08-11T17:34:59.167","Content":"11111","IsDelete":false,"UId":29,"Member":{"MbrId":0,"MbrName":"123456","RelName":"","Password":"","UId":0,"Email":"","TelNo":"","Icon":"","RegTime":"1900-01-01T00:00:00","User":null,"Roles":null}},{"ComId":"5d2aee91-0d3f-4307-a951-a65f0121b665","RecId":"90840b17-afc8-48dc-ade1-a65e00fe0ce1","SubTime":"2016-08-11T17:34:48.65","Content":"11111","IsDelete":false,"UId":29,"Member":{"MbrId":0,"MbrName":"123456","RelName":"","Password":"","UId":0,"Email":"","TelNo":"","Icon":"","RegTime":"1900-01-01T00:00:00","User":null,"Roles":null}},{"ComId":"a5b0e35f-571a-4f96-9ce1-a65f012073ad","RecId":"90840b17-afc8-48dc-ade1-a65e00fe0ce1","SubTime":"2016-08-11T17:30:13.263","Content":"11111","IsDelete":false,"UId":29,"Member":{"MbrId":0,"MbrName":"123456","RelName":"","Password":"","UId":0,"Email":"","TelNo":"","Icon":"","RegTime":"1900-01-01T00:00:00","User":null,"Roles":null}},{"ComId":"f89cabb4-e800-4993-9c92-a65f011f1760","RecId":"90840b17-afc8-48dc-ade1-a65e00fe0ce1","SubTime":"2016-08-11T17:25:16.05","Content":"11111","IsDelete":false,"UId":29,"Member":{"MbrId":0,"MbrName":"123456","RelName":"","Password":"","UId":0,"Email":"","TelNo":"","Icon":"","RegTime":"1900-01-01T00:00:00","User":null,"Roles":null}},{"ComId":"4c46bdbb-377e-4cfd-a05c-a65f011d67ac","RecId":"90840b17-afc8-48dc-ade1-a65e00fe0ce1","SubTime":"2016-08-11T17:19:07.663","Content":"11111","IsDelete":false,"UId":29,"Member":{"MbrId":0,"MbrName":"123456","RelName":"","Password":"","UId":0,"Email":"","TelNo":"","Icon":"","RegTime":"1900-01-01T00:00:00","User":null,"Roles":null}}]
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
         * ComId : 990052de-6910-4a0e-b0c0-a65f018b7d57
         * RecId : 90840b17-afc8-48dc-ade1-a65e00fe0ce1
         * SubTime : 2016-08-11T23:59:55.983
         * Content : 12344
         * IsDelete : false
         * UId : 23
         * Member : {"MbrId":0,"MbrName":"xone","RelName":"","Password":"","UId":0,"Email":"","TelNo":"","Icon":"","RegTime":"1900-01-01T00:00:00","User":null,"Roles":null}
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

        public static class CommentListBean {
            private String ComId;
            private String RecId;
            private String SubTime;
            private String Content;
            private boolean IsDelete;
            private int UId;
            /**
             * MbrId : 0
             * MbrName : xone
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

            public String getComId() {
                return ComId;
            }

            public void setComId(String ComId) {
                this.ComId = ComId;
            }

            public String getRecId() {
                return RecId;
            }

            public void setRecId(String RecId) {
                this.RecId = RecId;
            }

            public String getSubTime() {
                return SubTime;
            }

            public void setSubTime(String SubTime) {
                this.SubTime = SubTime;
            }

            public String getContent() {
                return Content;
            }

            public void setContent(String Content) {
                this.Content = Content;
            }

            public boolean isIsDelete() {
                return IsDelete;
            }

            public void setIsDelete(boolean IsDelete) {
                this.IsDelete = IsDelete;
            }

            public int getUId() {
                return UId;
            }

            public void setUId(int UId) {
                this.UId = UId;
            }

            public MemberBean getMember() {
                return Member;
            }

            public void setMember(MemberBean Member) {
                this.Member = Member;
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
