package com.android.cjzk.Bean;

/**
 * Created by XOne on 2016/8/8.
 */
public class CommentBean {


    /**
     * Message : 添加评论成功!
     * Succeed : true
     * Data : null
     */

    private String Message;
    private boolean Succeed;
    private Object Data;

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

    public Object getData() {
        return Data;
    }

    public void setData(Object Data) {
        this.Data = Data;
    }

    @Override
    public String toString() {
        return "CommentBean{" +
                "Message='" + Message + '\'' +
                ", Succeed=" + Succeed +
                ", Data=" + Data +
                '}';
    }
}
