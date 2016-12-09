package com.android.cjzk.Bean;

/**
 * Created by XOne on 2016/8/9.
 */
public class LoginBean {


    /**
     * Message : 登录成功！
     * Succeed : true
     * Data : f5daae53-c808-4971-807e-a3d743f40a95
     */

    private String Message;
    private boolean Succeed;
    private String Data;

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

    public String getData() {
        return Data;
    }

    public void setData(String Data) {
        this.Data = Data;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "Message='" + Message + '\'' +
                ", Succeed=" + Succeed +
                ", Data='" + Data + '\'' +
                '}';
    }
}
