package com.eeesys.myapplication.entity;

/**
 * Created by ASUS on 2016/6/7.
 */
public class RecentNews {
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNews_img() {
        return news_img;
    }

    public void setNews_img(int news_img) {
        this.news_img = news_img;
    }

    private int news_img;
    private String content;
}
