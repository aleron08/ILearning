package com.example.aleron08.ilearning.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.aleron08.ilearning.bean.UserBean;

/**
 * Created by aleron08 on 2019/1/24.
 */

public class LoggingStatus {
    public static void saveUser(Context context, UserBean userBean){//context:getApplicationContext()
        SharedPreferences sp = context.getSharedPreferences("user",context.MODE_PRIVATE);
        sp.edit()
                .putInt("id",userBean.getId())
                .putString("pwd",userBean.getPwd())
                .putString("phone",userBean.getPhone())
                .putString("hobby",userBean.getHobby())
                .putString("major",userBean.getMajor())
                .putString("name",userBean.getName())
                .putString("picture",userBean.getPicture())
                .putString("school",userBean.getSchool())
                .putString("sex",userBean.getSex())
                .putString("state",userBean.getState())
                .apply();
    }

    public static boolean isLogged(Context context){
        if(getUserId(context)==0){
            return false;
        }else{
            return true;
        }
    }

    public static int getUserId(Context context){
        SharedPreferences sp = context.getSharedPreferences("user",context.MODE_PRIVATE);
        int id = sp.getInt("id",0);
        return id;
    }

    public static UserBean getUser(Context context){
        SharedPreferences sp = context.getSharedPreferences("user",context.MODE_PRIVATE);
        UserBean userBean = new UserBean();
        userBean.setId(sp.getInt("id",0));
        userBean.setPwd(sp.getString("pwd",null));
        userBean.setPhone(sp.getString("phone",null));
        userBean.setHobby(sp.getString("hobby",null));
        userBean.setMajor(sp.getString("major",null));
        userBean.setName(sp.getString("name",null));
        userBean.setPicture(sp.getString("picture",null));
        userBean.setSchool(sp.getString("school",null));
        userBean.setSex(sp.getString("sex",null));
        userBean.setState(sp.getString("state",null));
        return userBean;
    }

    public static void clearUser(Context context){
        SharedPreferences sp = context.getSharedPreferences("user",context.MODE_PRIVATE);
        sp.edit()
                .putInt("id",0).apply();
    }
}
