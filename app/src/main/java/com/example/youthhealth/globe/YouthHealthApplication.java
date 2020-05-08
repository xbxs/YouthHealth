package com.example.youthhealth.globe;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;

import androidx.appcompat.app.AppCompatDelegate;

import com.example.youthhealth.utils.ConstantValues;
import com.example.youthhealth.utils.PrefUtils;

import org.litepal.LitePal;


public class YouthHealthApplication extends Application {
    private static Context context;

    private static Handler handler;

    private static int Uithreadid;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        handler = new Handler();
        Uithreadid = android.os.Process.myTid();

        LitePal.initialize(context);

        setNightMode();

    }

    private void setNightMode() {
        boolean mode_follow_system = PrefUtils.getBoolean(context,ConstantValues.MODE_FOLLOW_SYSTEM,false);
        if(mode_follow_system){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            boolean mode = currentNightMode == Configuration.UI_MODE_NIGHT_NO ? false : true;
            PrefUtils.setBoolean(context,ConstantValues.APPMODE,mode);
        }else {
            boolean nightMode = PrefUtils.getBoolean(context, ConstantValues.APPMODE, false);
            AppCompatDelegate.setDefaultNightMode(nightMode ?
                    AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        }
    }


    public static Context getContext(){
            return context;
    }

    public static Handler getHandler(){
        return handler;
    }

    public static int getUithreadid(){
        return  Uithreadid;
    }
}
