package com.example.aleron08.ilearning.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by aleron08 on 2019/1/22.
 */

public class InternetCheck {
    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivity != null){
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if(info != null && info.isConnected()){
                //网络已连接
                if (info.getState() == NetworkInfo.State.CONNECTED){
                    //当前网络可用
                    return true;
                }
            }
        }
        Toast.makeText(context,"网络未连接",Toast.LENGTH_SHORT).show();
        return false;
    }
}
