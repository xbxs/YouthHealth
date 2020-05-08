package com.example.youthhealth.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.youthhealth.R;
import com.example.youthhealth.dbUtils.DBUtils;
import com.example.youthhealth.entity.DBNews;

public class NewsActivity extends AppCompatActivity {
    private ImageButton imgb_webview_back;
    private WebView wb_news;
    private WebSettings mWebSettings;
    private String mUri;
    private Button bt_collect;

    private Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        initUi();

        iniData();


    }

    private void initUi() {
        imgb_webview_back = findViewById(R.id.imgb_webview_back);
        wb_news = findViewById(R.id.wb_news);
        bt_collect = findViewById(R.id.bt_collect);


        imgb_webview_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bt_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt_collect.setSelected(!bt_collect.isSelected());
            }
        });


        mBundle = getIntent().getBundleExtra("newsbundle");
        mUri = mBundle.getString("url");

        DBNews dbNews = DBUtils.queryNewCollet(mBundle.getInt("id")+"","1");
        if(dbNews != null){
            bt_collect.setSelected(true);
        }
    }

    private void iniData() {
        mWebSettings = wb_news.getSettings();
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setBlockNetworkImage(false);

        wb_news.loadUrl(mUri);

        wb_news.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String str_uri = request.getUrl().toString();
                if(str_uri == null){
                    return false;
                }
                try {
                    if (str_uri.startsWith("http:") || str_uri.startsWith("https:")){
                        view.loadUrl(str_uri);
                        return false;
                    }else{
                        Intent intent = new Intent(Intent.ACTION_VIEW,request.getUrl());
                        startActivity(intent);
                        return true;
                    }
                }catch (Exception e){
                    return false;
                }
            }
        });

        DBNews dbNews = new DBNews(mBundle.getInt("id")+"",mBundle.getString("title"),mBundle.getString("url"),mBundle.getString("praise"),mBundle.getString("miniimg"),"2");
        Log.i("TAG","news:"+dbNews.toString());
        DBUtils.addNewsCollect(dbNews);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String newsid = mBundle.getInt("id")+"";

        if(bt_collect.isSelected()){
            DBNews dbNews = new DBNews(mBundle.getInt("id")+"",mBundle.getString("title"),mBundle.getString("url"),mBundle.getString("praise"),mBundle.getString("miniimg"),"1");
            DBUtils.addNewsCollect(dbNews);
        }else if(DBUtils.queryNewCollet(newsid,"1") != null){
            DBUtils.deleteNewCollet(newsid,"1");
        }
    }
}
