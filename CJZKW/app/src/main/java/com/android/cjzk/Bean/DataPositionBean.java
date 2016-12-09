package com.android.cjzk.Bean;

import java.util.List;

/**
 * Created by XOne on 2016/8/7.
 */
public class DataPositionBean {


    private List<Integer> Data;

    public List<Integer> getData() {
        return Data;
    }

    public void setData(List<Integer> data) {
        Data = data;
    }

    @Override
    public String toString() {
        return "DataPositionBean{" +
                "Data=" + Data +
                '}';
    }
}
