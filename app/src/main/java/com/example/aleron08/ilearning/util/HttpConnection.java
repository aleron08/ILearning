package com.example.aleron08.ilearning.util;

import com.example.aleron08.ilearning.bean.UserBean;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by aleron08 on 2019/1/22.
 */

public class HttpConnection {
    /*
    * Callback 是okhttp库中自带的回调接口，okhttp在equenue()方法内部开了子线程，
    * 在子线程中进行http请求，并将返回结果回调到Callback当中
    * */
    public static void sendUserRequest(String address, UserBean userBean, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        String params = new Gson().toJson(userBean);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON,params);
        Request request = new Request.Builder().url(address).post(requestBody).build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendRegisterRequest(String address, UserBean userBean, String smsMessage, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        String params = new Gson().toJson(userBean);
        params = "#"+smsMessage+"#"+params;
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON,params);
        Request request = new Request.Builder().url(address).post(requestBody).build();
        client.newCall(request).enqueue(callback);
    }
}
