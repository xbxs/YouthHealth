package com.example.youthhealth.fragment;

import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.youthhealth.R;
import com.example.youthhealth.adapter.VideoListViewAdapter;
import com.example.youthhealth.entity.Videos;
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

import cn.jzvd.JZVideoPlayer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class VideoFragment extends Fragment {

    private ListView list_video;
    private SwipeRefreshLayout rf_video;

    private String[] mVideosUri;
    private VideoListViewAdapter mAdapter;
    private List<Videos.DataBean> mVideos;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mVideosUri = new String[]{"https://api.vslimit.com/videos?category=%E5%A8%B1%E4%B9%90&req=REFRESH",
        "https://api.vslimit.com/videos?category=%E7%BE%8E%E9%A3%9F&req=REFRESH",
        "https://api.vslimit.com/videos?category=%E4%B8%96%E7%95%8C&req=REFRESH",
        "https://api.vslimit.com/videos?category=%E7%94%9F%E6%B4%BB&req=REFRESH",
        "https://api.vslimit.com/videos?category=%E6%B1%BD%E8%BD%A6&req=REFRESH"};

        mVideos = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_ask, container, false);
        rf_video = view.findViewById(R.id.rf_video);
        list_video = view.findViewById(R.id.list_video);
        initData(false);
        //为下拉刷新设置监听
        rf_video.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        SystemClock.sleep(2000);
                        Uiutils.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                rf_video.setRefreshing(false);
                            }
                        });
                        initData(true);

                    }
                }).start();

            }
        });
        return view;
    }

    private void initData(final boolean isfresh) {
        Random random = new Random();
        final int videoUri = random.nextInt(mVideosUri.length - 1);
        String cache = PrefUtils.getString(getContext(),ConstantValues.CACHE_STORAGE+mVideosUri[videoUri],"");
        if(!TextUtils.isEmpty(cache)){
            showResponse(cache,false);
        }
       HttpUtils.sendHttpRequest(mVideosUri[videoUri], new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Uiutils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Uiutils.getContext(),"请求视频数据失败，请检查网络!", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String datacache = response.body().string();
                if(!TextUtils.isEmpty(datacache)) {
                    PrefUtils.setString(Uiutils.getContext(), ConstantValues.CACHE_STORAGE+mVideosUri[videoUri],datacache);
                    showResponse(datacache, isfresh);
                }
            }
        });

    }

    private void showResponse(String jsonData, final boolean isFresh) {
        Gson gson = new Gson();
        Log.i("TAG","jsonData"+jsonData);
        final  Videos videos =  gson.fromJson(jsonData, new TypeToken<Videos>(){}.getType());
        mVideos.clear();
        mVideos.addAll(videos.getData());
        Uiutils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
               if(isFresh) {
                   mAdapter.notifyDataSetChanged();
               }else{

                    mAdapter = new VideoListViewAdapter(getContext(),mVideos);
                    list_video.setAdapter(mAdapter);
               }
            }
        });

    }


    @Override
    public void onPause() {
        JZVideoPlayer.releaseAllVideos();
        super.onPause();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){

        }else{
            JZVideoPlayer.releaseAllVideos();
        }
    }
}
