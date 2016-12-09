package com.android.cjzk.Bean;

import java.util.List;

/**
 * Created by XOne on 2016/10/10.
 */

public class SiftingBean {


    /**
     * Message :
     * Succeed : true
     * Data : [{"Id":1,"GroupName":"新闻数据","Name":"财经早餐"},{"Id":2,"GroupName":"新闻数据","Name":"新股提示"},{"Id":3,"GroupName":"新闻数据","Name":"资金抢筹"},{"Id":4,"GroupName":"新闻数据","Name":"散户动向"},{"Id":5,"GroupName":"新闻数据","Name":"大资金动向"},{"Id":6,"GroupName":"新闻数据","Name":"解禁提示"},{"Id":7,"GroupName":"新闻数据","Name":"新闻联播"},{"Id":8,"GroupName":"新闻数据","Name":"公告精选"},{"Id":9,"GroupName":"市场观点","Name":"策略精选"},{"Id":10,"GroupName":"市场观点","Name":"独家早评"},{"Id":11,"GroupName":"市场观点","Name":"午评精选"},{"Id":12,"GroupName":"市场观点","Name":"复盘点睛"},{"Id":13,"GroupName":"市场观点","Name":"独家策略"},{"Id":14,"GroupName":"市场观点","Name":"收评精选"},{"Id":15,"GroupName":"机构动向","Name":"机构晨报"},{"Id":16,"GroupName":"机构动向","Name":"机构推荐"},{"Id":17,"GroupName":"机构动向","Name":"龙虎榜"},{"Id":18,"GroupName":"机构动向","Name":"游资风向"},{"Id":19,"GroupName":"机构动向","Name":"机构持仓"},{"Id":20,"GroupName":"机构动向","Name":"私募内参"},{"Id":21,"GroupName":"机构动向","Name":"个股调级"},{"Id":22,"GroupName":"参考研究","Name":"热点布局"},{"Id":23,"GroupName":"参考研究","Name":"热点直击"},{"Id":24,"GroupName":"参考研究","Name":"涨停预测"},{"Id":25,"GroupName":"参考研究","Name":"盘中异动"},{"Id":26,"GroupName":"参考研究","Name":"涨停揭秘"},{"Id":27,"GroupName":"参考研究","Name":"个股传闻"},{"Id":28,"GroupName":"参考研究","Name":"深度挖掘"},{"Id":29,"GroupName":"参考研究","Name":"机会前瞻"},{"Id":30,"GroupName":"参考研究","Name":"股市学堂"},{"Id":31,"GroupName":"参考研究","Name":"股吧精选"},{"Id":32,"GroupName":"参考研究","Name":"独家视频"}]
     */

    private String Message;
    private boolean Succeed;
    /**
     * Id : 1
     * GroupName : 新闻数据
     * Name : 财经早餐
     */

    private List<DataBean> Data;

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

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        private int Id;
        private String GroupName;
        private String Name;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getGroupName() {
            return GroupName;
        }

        public void setGroupName(String GroupName) {
            this.GroupName = GroupName;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "Id=" + Id +
                    ", GroupName='" + GroupName + '\'' +
                    ", Name='" + Name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SiftingBean{" +
                "Message='" + Message + '\'' +
                ", Succeed=" + Succeed +
                ", Data=" + Data +
                '}';
    }
}
