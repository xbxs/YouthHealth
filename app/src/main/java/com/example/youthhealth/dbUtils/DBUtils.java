package com.example.youthhealth.dbUtils;

import android.database.Cursor;

import com.example.youthhealth.entity.DBNews;

import org.litepal.LitePal;

import java.util.List;

/**
 * 李维: TZZ on 2019-12-03 19:38
 * 邮箱: 3182430026@qq.com
 */
public class DBUtils {
    public static boolean addNewsCollect(DBNews dbNews){
        DBNews dbNews1 = queryNewCollet(dbNews.getNewsid(),dbNews.getType());
        if(dbNews1 == null) {
            return dbNews.save();
        }else {
            return false;
        }
    }

    public static int deleteNewCollet(String newsid,String type){
        return LitePal.deleteAll(DBNews.class,"newsid = ? and type = ?",newsid,type);
    }
    public static int DeleteNewsCollect(String type){
        return LitePal.deleteAll(DBNews.class,"type = ?",type);
    }

    public static DBNews queryNewCollet(String code,String types){
        Cursor cursor =LitePal.findBySQL("select * from dbnews where newsid = ? and type = ?",code,types);
       
        DBNews dbNews = null;
        if(cursor != null){
            int count = cursor.getCount();
            if(count > 0 && cursor.moveToFirst()){

                String title = cursor.getString(cursor.getColumnIndex("title"));
                String url = cursor.getString(cursor.getColumnIndex("url"));
                String praise = cursor.getString(cursor.getColumnIndex("praise"));
                String miniimg = cursor.getString(cursor.getColumnIndex("miniimg"));
                String type = cursor.getString(cursor.getColumnIndex("type"));
                String newsid = cursor.getString(cursor.getColumnIndex("newsid"));

                dbNews = new DBNews(newsid,title,url,praise,miniimg,type);
            }
        }



        
        return dbNews;
    }

    public static List<DBNews> queryNewsCollet(String type){
        List<DBNews> list = LitePal.where("type = ?",type).order("id desc").find(DBNews.class);
        return list;
    }


}
