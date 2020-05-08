package com.example.youthhealth.activity;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.youthhealth.R;
import com.example.youthhealth.fragment.FoodFragment;
import com.example.youthhealth.fragment.UserFragment;
import com.example.youthhealth.fragment.VideoFragment;
import com.example.youthhealth.utils.ConstantValues;
import com.example.youthhealth.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayer;

public class MainActivity extends AppCompatActivity {
    private ViewPager vp_main;
    private RadioGroup rg_menu;
    private RadioButton rb_food,rb_ask,rb_user;
    private List<Fragment> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        initDate();

        vp_main.setAdapter(new MenuViewPageAdpter(getSupportFragmentManager()));
        vp_main.setOffscreenPageLimit(2);
        vp_main.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                rb_food.setChecked(false);
                rb_ask.setChecked(false);
                rb_user.setChecked(false);
                    switch (i){
                        case 0:
                            rb_food.setChecked(true);
                            break;
                        case 1:
                            rb_ask.setChecked(true);
                            break;
                        case 2:
                            rb_user.setChecked(true);
                            break;
                        default:
                            break;

                    }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        rg_menu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_food:
                        vp_main.setCurrentItem(0);
                        break;
                    case R.id.rb_ask:
                        vp_main.setCurrentItem(1);
                        break;
                    case R.id.rb_user:
                        vp_main.setCurrentItem(2);
                        break;
                    default:
                        break;
                }
            }
        });
    }


    private void initUI() {
        boolean mode_follow_system = PrefUtils.getBoolean(MainActivity.this, ConstantValues.MODE_FOLLOW_SYSTEM, false);
        if(mode_follow_system){

        }
        vp_main = findViewById(R.id.vp_main);
        rg_menu = findViewById(R.id.rg_menu);
        rb_food = findViewById(R.id.rb_food);
        rb_ask = findViewById(R.id.rb_ask);
        rb_user = findViewById(R.id.rb_user);

    }

    private void initDate() {
        list = new ArrayList<>();
        list.add(new FoodFragment());
        list.add(new VideoFragment());
        list.add(new UserFragment());
    }

    class MenuViewPageAdpter extends FragmentPagerAdapter {


        public MenuViewPageAdpter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return list.get(i);
        }

        @Override
        public int getCount() {
            return list.size();
        }

    }

    @Override
    public void onBackPressed() {
        if(JZVideoPlayer.backPress()){
            return;
        }
        super.onBackPressed();
    }
}
