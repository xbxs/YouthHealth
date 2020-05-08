package com.example.youthhealth.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.youthhealth.activity.NewsActivity;
import com.example.youthhealth.R;
import com.example.youthhealth.adapter.FoodsAdapter;
import com.example.youthhealth.entity.Foods;
import com.example.youthhealth.utils.ConstantValues;
import com.example.youthhealth.utils.HttpUtils;
import com.example.youthhealth.utils.PrefUtils;
import com.example.youthhealth.utils.Uiutils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 *   这个碎片主要是呈现出主页健康饮食
 */

public class FoodFragment extends Fragment {

    private  ViewPager vp_food_guide;
    private View mView;
    private ListView lv_dynamic;
    private int[] images = new int[]{R.drawable.food1,R.drawable.food2,R.drawable.food3,R.drawable.food4};
    //轮播的图
    private List<ImageView> mImageViewList;
//    动态数据
    private List<Foods.DataBean> mFoods;
//     推文适配器
    private FoodsAdapter mFoodsAdapter;
//    小圆点的容器
    private LinearLayout ll_food_carousel;
    private int beforepoint;
    //自动轮播
    private myHandler mMyHandler;
//    按顺序 oncreate在oncreateView之前

    private String [] mfoodNewsUrl = new String[]{"https://api.vslimit.com/news?cid=63&date=1575108551000&req=REFRESH",
            "https://api.vslimit.com/news?cid=77&req=REFRESH","https://api.vslimit.com/news?cid=83&req=REFRESH"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        beforepoint = 0;
        mView = inflater.inflate(R.layout.fragment_food, container, false);
        vp_food_guide = mView.findViewById(R.id.vp_food_guide);
        ll_food_carousel = mView.findViewById(R.id.ll_food_carousel);
        lv_dynamic = mView.findViewById(R.id.lv_dynamic);


        mFoods = new ArrayList<>();
        initDate();


        vp_food_guide.setAdapter(new FoodCarouselAdapter());
        vp_food_guide.setCurrentItem(5000000);
        mMyHandler = new myHandler();
        vp_food_guide.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
        vp_food_guide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
               int newPosition = i % mImageViewList.size();
               ll_food_carousel.getChildAt(beforepoint).setEnabled(false);
               ll_food_carousel.getChildAt(newPosition).setEnabled(true);
               beforepoint = newPosition;

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        lv_dynamic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), NewsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id",mFoods.get(i).getId());
                bundle.putString("title",mFoods.get(i).getTitle());
                bundle.putString("url",mFoods.get(i).getUrl());
                bundle.putString("praise",mFoods.get(i).getPraise());
                bundle.putString("miniimg",mFoods.get(i).getMiniimg().get(0).getSrc());
                intent.putExtra("newsbundle",bundle);
                String newcollect = PrefUtils.getString(getContext(),ConstantValues.COLLECT,"");
                if(!newcollect.contains(mFoods.get(i).getUrl()+",")) {
                    PrefUtils.setString(getContext(), ConstantValues.COLLECT, mFoods.get(i).getUrl()+",");
                }
                startActivity(intent);
            }
        });


        mMyHandler.sendEmptyMessageDelayed(0,2000);
        return mView;
    }

    @Override
    public void onResume() {
        Log.i("TAG","resume");
        super.onResume();
    }



    private void initDate() {
//        这个集合用于装轮播图（Imageview）
        mImageViewList = new ArrayList<>();
        ImageView point;
        //用于确定小圆点的格式 位置
        LinearLayout.LayoutParams layoutParams;
        // images是图片数组，图片数组的大小决定存储imageview集合的大小
        for(int i = 0;i < images.length; i++){
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(images[i]);
            mImageViewList.add(imageView);

            point = new ImageView(getContext());
            point.setBackgroundResource(R.drawable.selector_food_carousel);
            layoutParams = new LinearLayout.LayoutParams(35,35);
            //leftMargin是小圆点之间的间距
            if(i != 0){
                layoutParams.leftMargin = 15;
                //使小圆点不能接收触摸事件或点击
                point.setEnabled(false);
            }

            ll_food_carousel.addView(point,layoutParams);
        }
        //初始化动态数据

        Random random = new Random();
        final int fooduri = random.nextInt(mfoodNewsUrl.length);
        String cache = PrefUtils.getString(getContext(),ConstantValues.CACHE_STORAGE+mfoodNewsUrl[fooduri],"");
        if(!TextUtils.isEmpty(cache)){
            showResponse(cache,1);
        }
        HttpUtils.sendHttpRequest(mfoodNewsUrl[fooduri], new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Uiutils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Uiutils.getContext(),"请求数据失败，请检查网络!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String datareceiver = response.body().string();
                        if(!TextUtils.isEmpty(datareceiver)){

                            showResponse(datareceiver, 2);
                            PrefUtils.setString(Uiutils.getContext(), ConstantValues.CACHE_STORAGE + mfoodNewsUrl[fooduri], datareceiver);
                        }

            }
        });

    }

    private void showResponse(String jsonData, final int isCache) {
        Gson gson = new Gson();
        final Foods foods =  gson.fromJson(jsonData, new TypeToken<Foods>(){}.getType());
        mFoods.clear();
        mFoods.addAll(foods.getData());

        Uiutils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                    if(mFoodsAdapter == null) {
                        mFoodsAdapter = new FoodsAdapter(Uiutils.getContext(), mFoods);
                        lv_dynamic.setAdapter(mFoodsAdapter);
                    }else {
                        mFoodsAdapter.notifyDataSetChanged();
                    }
            }
        });

    }
    // 轮播适配器
    class FoodCarouselAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = mImageViewList.get(position%mImageViewList.size());
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    class myHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            vp_food_guide.setCurrentItem(vp_food_guide.getCurrentItem() + 1);
            mMyHandler.sendEmptyMessageDelayed(0,2000);
        }
    }



    @Override
    public void onDestroyView() {
        if(mMyHandler != null){
            mMyHandler.removeMessages(0);
            mMyHandler = null;
            // ll_food_carousel.getChildAt(beforepoint).setEnabled(false);
            Log.i("TAG","handler销毁");
        }
        super.onDestroyView();
    }

}
