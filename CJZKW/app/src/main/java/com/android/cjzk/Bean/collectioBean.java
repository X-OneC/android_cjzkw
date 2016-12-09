package com.android.cjzk.Bean;

/**
 * Created by XOne on 2016/10/13.
 */

public class collectioBean {


    /**
     * Message :
     * Succeed : true
     * Data : {"Collection":true}
     */

    private String Message;
    private boolean Succeed;
    /**
     * Collection : true
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
        private boolean Collection;

        public boolean isCollection() {
            return Collection;
        }

        public void setCollection(boolean Collection) {
            this.Collection = Collection;
        }
    }
}
