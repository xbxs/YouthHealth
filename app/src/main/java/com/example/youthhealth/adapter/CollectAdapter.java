package com.example.youthhealth.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.youthhealth.R;
import com.example.youthhealth.entity.DBNews;
import com.example.youthhealth.utils.Uiutils;

import java.util.List;

/**
 * 李维: TZZ on 2019-12-04 18:54
 * 邮箱: 3182430026@qq.com
 */
public class CollectAdapter extends BaseAdapter {
    private Context mContext;
    private List<DBNews> mDBNews;
    public CollectAdapter(Context context,List<DBNews> list){
        this.mContext = context;
        this.mDBNews = list;
    }
    @Override
    public int getCount() {
        return mDBNews.size();
    }

    @Override
    public Object getItem(int i) {
        return mDBNews.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       ViewHolder holder;
        if(view == null){
            view = View.inflate(Uiutils.getContext(), R.layout.layout_food_dynamic,null);

            holder = new ViewHolder();
            holder.layout_picture = view.findViewById(R.id.layout_picture);
            holder.layout_title = view.findViewById(R.id.layout_title);
            holder.layout_readnumber = view.findViewById(R.id.layout_readnumber);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }


        DBNews dbNews = mDBNews.get(i);
        Glide.with(mContext).load(dbNews.getMiniimg()).into(holder.layout_picture);
        holder.layout_title.setText(dbNews.getTitle());

        holder.layout_readnumber.setText("阅读："+dbNews.getPraise()+"次");

        return view;
    }


    static class ViewHolder{
        ImageView layout_picture;
        TextView layout_title;
        TextView layout_readnumber;
    }
}
