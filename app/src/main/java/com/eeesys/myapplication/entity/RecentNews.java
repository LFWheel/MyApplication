package com.eeesys.myapplication.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by ASUS on 2016/6/7.
 */
public class RecentNews extends BmobObject{
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    private String imgUrl;
    private String content;
}
