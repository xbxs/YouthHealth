package com.example.youthhealth.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.youthhealth.R;
import com.example.youthhealth.entity.Videos;

import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * 李维: TZZ on 2019-11-29 21:01
 * 邮箱: 3182430026@qq.com
 */
public class VideoListViewAdapter extends BaseAdapter {
    private List<Videos.DataBean> mVideos;
    private Context mContext;
    public VideoListViewAdapter(Context context,List<Videos.DataBean> lists){
        this.mVideos = lists;
        this.mContext = context;
    }
    @Override
    public int getCount() {
        return mVideos.size();
    }

    @Override
    public Object getItem(int i) {
        return mVideos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            view = View.inflate(mContext, R.layout.item_video,null);
            holder = new ViewHolder();
            holder.mJzvdStd = view.findViewById(R.id.player_lsit_video);
            holder.tv_video_playnumber = view.findViewById(R.id.tv_video_playnumber);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        Videos.DataBean dataBean  = mVideos.get(i);
        holder.mJzvdStd.setUp(dataBean.getVideo_url(),JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,dataBean.getVideo_title());

        Glide.with(mContext).load(dataBean.getVideo_image()).into(holder.mJzvdStd.thumbImageView);
        holder.tv_video_playnumber.setText(dataBean.getIntro());
        return view;
    }

    static class ViewHolder{
        JZVideoPlayerStandard mJzvdStd;
        TextView tv_video_playnumber;
    }
}
