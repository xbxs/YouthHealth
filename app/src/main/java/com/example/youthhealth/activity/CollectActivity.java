package com.example.youthhealth.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.youthhealth.R;
import com.example.youthhealth.adapter.CollectAdapter;
import com.example.youthhealth.dbUtils.DBUtils;
import com.example.youthhealth.entity.DBNews;

import java.util.List;

public class CollectActivity extends AppCompatActivity {
    private ImageButton imgb_webview_back;
    private TextView tv_title;
    private Button bt_collect;
    private List<DBNews> mDBNews;
    private ListView lv_collect;
    private CollectAdapter mCollectAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);

        initUI();

        initData();
    }

    public void initUI(){
     imgb_webview_back = findViewById(R.id.imgb_webview_back);
     tv_title = findViewById(R.id.tv_title);
     bt_collect = findViewById(R.id.bt_collect);

     tv_title.setText("我的收藏");
     bt_collect.setVisibility(View.INVISIBLE);

     imgb_webview_back.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             finish();
         }
     });

     lv_collect = findViewById(R.id.lv_collect);

     lv_collect.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
         @Override
         public boolean onItemLongClick(AdapterView<?> adapterView, View view,final int i, long  l) {
             AlertDialog.Builder builder = new AlertDialog.Builder(CollectActivity.this)
                     .setTitle("移除收藏")
                     .setMessage("确定要移除这个收藏吗?")
                     .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialogInterface, int i1) {
                             Log.i("TAG","i: "+i+"  i1:"+i1);
                             DBUtils.deleteNewCollet(mDBNews.get(i).getNewsid(),"1");
                             mDBNews.remove(i);
                             mCollectAdapter.notifyDataSetChanged();
                         }
                     })
                     .setNegativeButton("取消",null);
                builder.show();
             return true;
         }
     });

    }

    private void initData() {
        mDBNews = DBUtils.queryNewsCollet("1");
        mCollectAdapter = new CollectAdapter(CollectActivity.this,mDBNews);
        lv_collect.setAdapter(mCollectAdapter);
    }
}
