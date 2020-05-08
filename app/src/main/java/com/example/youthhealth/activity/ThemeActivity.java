package com.example.youthhealth.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.youthhealth.R;
import com.example.youthhealth.utils.ConstantValues;
import com.example.youthhealth.utils.PrefUtils;

public class ThemeActivity extends AppCompatActivity {
    private ImageButton imgb_webview_back;
    private TextView tv_title;
    private Button bt_collect;
    private RadioGroup rg_mode;
    private RadioButton mRb_light;
    private RadioButton mRb_night;
    private Switch sw_mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        initUI();
    }

    public void initUI() {
        imgb_webview_back = findViewById(R.id.imgb_webview_back);
        imgb_webview_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ThemeActivity.this,MainActivity.class));
                finish();
            }
        });
        tv_title = findViewById(R.id.tv_title);
        bt_collect = findViewById(R.id.bt_collect);

        tv_title.setText("主题");
        bt_collect.setVisibility(View.INVISIBLE);
        boolean mode = PrefUtils.getBoolean(ThemeActivity.this,ConstantValues.APPMODE,false);

        rg_mode = findViewById(R.id.rg_mode);
        mRb_light = findViewById(R.id.rb_light);
        mRb_night = findViewById(R.id.rb_night);
        sw_mode = findViewById(R.id.sw_mode);
        //判断是否跟随系统深色模式
        boolean isFollw_System = PrefUtils.getBoolean(ThemeActivity.this,ConstantValues.MODE_FOLLOW_SYSTEM,false);
        sw_mode.setChecked(isFollw_System);
        sw_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    PrefUtils.setBoolean(ThemeActivity.this,ConstantValues.MODE_FOLLOW_SYSTEM,true);
                }else {
                    PrefUtils.setBoolean(ThemeActivity.this,ConstantValues.MODE_FOLLOW_SYSTEM,false);
                }
            }
        });
        if(mode){
            mRb_night.setChecked(true);
        }else{
            mRb_light.setChecked(true);
        }
        rg_mode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {


                    int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                    boolean isnight= false;
                    switch (i){
                        case R.id.rb_light:
                            isnight = false;
                            break;
                        case R.id.rb_night:
                            isnight = true;
                            break;
                    }
                    PrefUtils.setBoolean(ThemeActivity.this, ConstantValues.APPMODE, isnight);

                    getDelegate().setDefaultNightMode(currentNightMode == Configuration.UI_MODE_NIGHT_NO ?
                            AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
                    Log.i("TAG","nightmode:"+currentNightMode);
                    startActivity(new Intent(ThemeActivity.this,ThemeActivity.class));
                    finish();

            }
        });
    }
}
