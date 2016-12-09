package com.android.cjzk.Bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by XOne on 2016/8/11.
 */
public class ShareBean {

    private String ID;
    private String title;
    private String img;
    private String URL;
    private String WebViewBaseUrl = "http://www.cjzkw.cn/Home/MobileArticleShare/";

    public ShareBean(){}

    public ShareBean(JSONObject obj) {
        try {
            title = obj.getString("title");
            img = obj.getString("img");
            ID = obj.getString("ID");
            URL = WebViewBaseUrl + ID;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }

    public String getImg() {
        return img;
    }

    public String getURL() {
        return URL;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "ShareBean{" +
                "ID='" + ID + '\'' +
                ", title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", URL='" + URL + '\'' +
                '}';
    }
}
