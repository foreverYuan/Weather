package com.example.cyy.weather.utils;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by cyy on 2016/9/28.
 */
public class OkhttpUtils {
    private ResponseListener listener;
    public interface ResponseListener{
        void onSuccessAction(Response response);
    }

    public void setOnResponseListener(ResponseListener listener){
        this.listener = listener;
    }
    public void requestByGet(String url){
        OkHttpClient client = new OkHttpClient();
        //创建一个请求
        Request request = new Request.Builder().url(url).build();
        //将请求封装成任务
        Call call = client.newCall(request);
        //将call加入调度队列
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                if(listener != null){
                    listener.onSuccessAction(response);
                }
            }
        });
    }

    public void requestByPost(String url, FormEncodingBuilder builder){
        OkHttpClient client = new OkHttpClient();
        //创建一个请求
        Request request = new Request.Builder().url(url).post(builder.build()).build();
        //将请求封装成一个任务
        Call call = client.newCall(request);
        //任务加入调度序列
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                if(listener != null){
                    listener.onSuccessAction(response);
                }
            }
        });
    }
}
