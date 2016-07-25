package com.eeesys.myapplication.entity;

/**
 * Created by ASUS on 2016/7/19.
 */
public class Recomend {
    private String imgUrl;
    private String mark;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Recomend(String imgUrl,String mark){
        this.imgUrl = imgUrl;
        this.mark = mark;
    }
}
