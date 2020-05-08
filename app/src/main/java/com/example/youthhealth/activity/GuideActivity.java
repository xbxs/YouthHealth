package com.example.youthhealth.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.youthhealth.R;
import com.example.youthhealth.utils.ConstantValues;
import com.example.youthhealth.utils.DensityUtils;
import com.example.youthhealth.utils.PrefUtils;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;
//引导页的ViewPager适配器
public class GuideActivity extends AppCompatActivity {
    private ViewPager vp_guide;
    private Button btn_experience;
    private LinearLayout ll_point;
    private ImageView iv_dot_blue;
    private int[] pictures = new int[]{R.drawable.first_guide,R.drawable.second_guide,R.drawable.third_guide};
    private List<ImageView> list = new ArrayList<>();
    private int mPointDis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);

        initUI();

        initData();

        vp_guide.setAdapter(new GuideAdpter());
    }
    //初始化布局
    private void initUI() {
        vp_guide = findViewById(R.id.vp_guide);
        btn_experience = findViewById(R.id.btn_experience);
        ll_point = findViewById(R.id.ll_point);
        iv_dot_blue = findViewById(R.id.iv_dot_blue);
        vp_guide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int i, float v, int i1) {
                int leftmargin = (int)(mPointDis * v) + i * mPointDis;
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv_dot_blue.getLayoutParams();
                params.leftMargin = leftmargin;
                iv_dot_blue.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int i) {
                if(i == list.size() -1){
                    btn_experience.setVisibility(View.VISIBLE);
                }else{
                    btn_experience.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        iv_dot_blue.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                iv_dot_blue.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mPointDis = ll_point.getChildAt(1).getLeft() - ll_point.getChildAt(0).getLeft();
            }
        });

        btn_experience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrefUtils.setBoolean(GuideActivity.this, ConstantValues.IS_FIRSTOPEN,false);
                startActivity(new Intent(GuideActivity.this,MainActivity.class));
            }
        });
    }
    //初始化图片
    private void initData() {
        LitePal.getDatabase();
        for(int i= 0;i < pictures.length;i++){
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(pictures[i]);
            list.add(imageView);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if(i > 0){
                params.leftMargin = DensityUtils.dip3px(15,this);
            }

            ImageView dotview = new ImageView(this);
            dotview.setImageResource(R.drawable.shape_point_gray);
            ll_point.addView(dotview,params);
        }
    }

    class GuideAdpter extends PagerAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            ImageView imageView = list.get(position);
            container.addView(imageView);

            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View)object);
        }
    }
}
