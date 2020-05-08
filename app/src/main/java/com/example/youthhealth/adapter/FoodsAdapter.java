package com.example.youthhealth.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.youthhealth.R;
import com.example.youthhealth.entity.Foods;
import com.example.youthhealth.utils.ConstantValues;
import com.example.youthhealth.utils.PrefUtils;
import com.example.youthhealth.utils.Uiutils;

import java.util.List;

/**
 * 李维: TZZ on 2019-12-02 19:36
 * 邮箱: 3182430026@qq.com
 */
public class FoodsAdapter extends BaseAdapter {
    private Context mContext;
    private List<Foods.DataBean> mfoods;

    public FoodsAdapter(Context mcontext,List<Foods.DataBean> foods){
        this.mContext = mcontext;
        this.mfoods = foods;
    }
    @Override
    public int getCount() {
        return mfoods.size();

    }

    @Override
    public Object getItem(int i) {
        return mfoods.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    //得到item视图
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            view = View.inflate(Uiutils.getContext(),R.layout.layout_food_dynamic,null);

            holder = new ViewHolder();
            holder.layout_picture = view.findViewById(R.id.layout_picture);
            holder.layout_title = view.findViewById(R.id.layout_title);
            holder.layout_readnumber = view.findViewById(R.id.layout_readnumber);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }


        //得到数据得javabean
        Foods.DataBean dataBean = mfoods.get(i);
        Glide.with(mContext).load(dataBean.getMiniimg().get(0).getSrc()).into(holder.layout_picture);
        holder.layout_title.setText(dataBean.getTitle());
        String news = PrefUtils.getString(Uiutils.getContext(), ConstantValues.COLLECT,"");
        if(news.contains(dataBean.getUrl()+",")){
            holder.layout_title.setTextColor(Color.GRAY);
        }
        holder.layout_readnumber.setText("阅读："+dataBean.getPraise()+"次");
        return view;
    }




static class ViewHolder{
    RelativeLayout layout_food;
    ImageView layout_picture;
    TextView layout_title;
    TextView layout_readnumber;
}
}
