package com.example.youthhealth.entity;

import org.litepal.crud.LitePalSupport;

/**
 * 李维: TZZ on 2019-12-03 19:14
 * 邮箱: 3182430026@qq.com
 */
public class DBNews extends LitePalSupport {
    private String newsid;
    private String title;

    private String url;

    private String praise;

    private String miniimg;
    //通过type来判断是收藏还是浏览 1为收藏 2.为浏览
    private String type;



    public DBNews() {
    }

    public DBNews(String newsid, String title, String url, String praise, String miniimg, String type) {
        this.newsid = newsid;
        this.title = title;
        this.url = url;
        this.praise = praise;
        this.miniimg = miniimg;
        this.type = type;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPraise() {
        return praise;
    }

    public void setPraise(String praise) {
        this.praise = praise;
    }

    public String getMiniimg() {
        return miniimg;
    }

    public void setMiniimg(String miniimg) {
        this.miniimg = miniimg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "DBNews{" +
                "newsid='" + newsid + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", praise='" + praise + '\'' +
                ", miniimg='" + miniimg + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getNewsid() {
        return newsid;
    }

    public void setNewsid(String newsid) {
        this.newsid = newsid;
    }
}
