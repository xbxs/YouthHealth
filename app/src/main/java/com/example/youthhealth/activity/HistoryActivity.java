package com.example.youthhealth.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.youthhealth.R;
import com.example.youthhealth.adapter.CollectAdapter;
import com.example.youthhealth.dbUtils.DBUtils;
import com.example.youthhealth.entity.DBNews;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private ImageButton imgb_webview_back;
    private TextView tv_title;
    private Button bt_collect;
    private List<DBNews> mDBNews;
    private ListView lv_history;
    private CollectAdapter mMCollectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initUI();

        initData();
    }


    private void initUI() {
        imgb_webview_back = findViewById(R.id.imgb_webview_back);
        imgb_webview_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_title = findViewById(R.id.tv_title);
        bt_collect = findViewById(R.id.bt_collect);
        lv_history = findViewById(R.id.lv_history);

        tv_title.setText("最近浏览");
        bt_collect.setBackgroundColor(Color.WHITE);
        bt_collect.setText("清空");
        bt_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mDBNews.size() > 0) {
                    DBUtils.DeleteNewsCollect("2");
                    Toast.makeText(HistoryActivity.this, "清空成功！", Toast.LENGTH_SHORT).show();
                    mMCollectAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    //
    private void initData() {
        mDBNews = DBUtils.queryNewsCollet("2");
        mMCollectAdapter = new CollectAdapter(HistoryActivity.this,mDBNews);
        lv_history.setAdapter(mMCollectAdapter);
        for(DBNews dbNews : mDBNews){
            Log.i("TAG","history:"+dbNews.toString());
        }
    }

}
