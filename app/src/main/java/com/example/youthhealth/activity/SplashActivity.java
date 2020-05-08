package com.example.youthhealth.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.appcompat.app.AppCompatActivity;

import com.example.youthhealth.R;
import com.example.youthhealth.utils.ConstantValues;
import com.example.youthhealth.utils.PrefUtils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);

                boolean is_FirstOpen = PrefUtils.getBoolean(SplashActivity.this, ConstantValues.IS_FIRSTOPEN,true);
                Intent intent = null;
                if(is_FirstOpen){
                    intent = new Intent(SplashActivity.this, GuideActivity.class);
                }else{
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }

                startActivity(intent);
                finish();
            }
        }).start();


    }
}
