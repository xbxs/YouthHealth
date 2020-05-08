package com.example.youthhealth.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 李维: TZZ on 2019-12-01 19:27
 * 邮箱: 3182430026@qq.com
 */
public class HttpUtils {
    public static void sendHttpRequest(final  String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
