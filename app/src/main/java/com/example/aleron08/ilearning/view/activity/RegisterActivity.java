package com.example.aleron08.ilearning.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.aleron08.ilearning.R;
import com.example.aleron08.ilearning.bean.UserBean;
import com.example.aleron08.ilearning.util.HttpConnection;
import com.example.aleron08.ilearning.util.ImmersiveStatusBar;
import com.example.aleron08.ilearning.util.InternetCheck;
import com.example.aleron08.ilearning.view.inter.IRegisterViw;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.Call;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity implements IRegisterViw,View.OnClickListener{

    private EditText etPhone,etVerification;
    private EditText etPwd,etPwd2;
    private Button btnRegister,btnVerification;

    private RegisterActivity.MyHandler myHandler = new RegisterActivity.MyHandler(this);

    private UserBean userBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        LinearLayout linearLayout = this.findViewById(R.id.ll_register_actionbar);
        ImmersiveStatusBar.initStateInActivity(this,linearLayout);
        initView();
    }

    void initView(){
        etPhone = findViewById(R.id.et_register_phone);
        etVerification = findViewById(R.id.et_register_verification);
        btnVerification = findViewById(R.id.btn_register_verification);
        etPwd = findViewById(R.id.et_register_pwd);
        etPwd2 = findViewById(R.id.et_register_pwd2);
        btnRegister = findViewById(R.id.btn_register_register);
        btnVerification.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    //弱引用，防止内存泄漏
    private static class MyHandler extends Handler {
        private final WeakReference<RegisterActivity> mRegisterActivity;

        public MyHandler(RegisterActivity activity){
            mRegisterActivity = new WeakReference<RegisterActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if(mRegisterActivity.get() == null){
                return;
            }
            mRegisterActivity.get().updateUIThread(msg);
            //super.handleMessage(msg);
        }
    }

    private void updateUIThread(Message msg){
//        Bundle bundle = msg.getData();
//        String result = bundle.getString("result");
        if (msg.what==1) {
            String responseData = (String)msg.obj;
            String result = responseData.substring(responseData.indexOf("#"),responseData.lastIndexOf("#"));
            if(result.equals("#succeed")){
                String JSONObj = responseData.substring(responseData.indexOf("{"));
                userBean = new Gson().fromJson(JSONObj,UserBean.class);
                Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                RegisterActivity.this.finish();
            }
            if(result.equals("#failed")){
                Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                etPwd.setText("");
                etPwd2.setText("");
            }
            if(result.equals("#verify_failed")){
                Toast.makeText(RegisterActivity.this,"验证码错误",Toast.LENGTH_SHORT).show();
                etVerification.setText("");
            }
            if(result.equals("#phone_existed")){
                Toast.makeText(RegisterActivity.this,"该手机号已被注册",Toast.LENGTH_SHORT).show();

            }
            if(result.equals("#verify_sent")){
                Toast.makeText(RegisterActivity.this,"验证码已发送",Toast.LENGTH_SHORT).show();

            }
            if(result.equals("#verify_sent_failed")){
                Toast.makeText(RegisterActivity.this,"验证码发送失败",Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.btn_register_register:
                doRegister();
                break;
            case R.id.btn_register_verification:
                doVerification();
        }
    }

    private void doRegister(){
        if(InternetCheck.isNetworkAvailable(this)){
            if(etPhone.getText().toString().isEmpty() || etPwd.getText().toString().isEmpty()
                    || etPwd2.getText().toString().isEmpty() || etVerification.getText().toString().isEmpty()){
                Toast.makeText(RegisterActivity.this,"手机号、验证码或密码不能为空"
                        ,Toast.LENGTH_SHORT).show();
            }else if (!etPwd.getText().toString().equals(etPwd2.getText().toString())){
                Toast.makeText(RegisterActivity.this,"密码不一致，请重新输入"
                        ,Toast.LENGTH_SHORT).show();
                etPwd.setText("");
                etPwd2.setText("");
            }else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final UserBean userBean = new UserBean();
                        userBean.setPhone(etPhone.getText().toString());
                        userBean.setPwd(etPwd.getText().toString());
                        String address = "http://40.121.148.51:8080/test07-message/RegisterServlet";
                        HttpConnection.sendRegisterRequest(address, userBean,etVerification.getText().toString(),
                                new okhttp3.Callback() {

                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.d("------------", "onFailure: 连接失败");
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String responseData = response.body().string();
                                Log.d("------------", "onResponse: " + responseData);
                                //userBean = new Gson().fromJson(responseData,UserBean.class);
                                //                                Looper.prepare();
                                //                                Looper.loop();
                                myHandler.obtainMessage(1, responseData).sendToTarget();
                            }
                        });
                    }
                }).start();
            }
        }
    }

    private void doVerification() {
        if(InternetCheck.isNetworkAvailable(this)) {
            if(etPhone.getText().toString().isEmpty()) {
                Toast.makeText(RegisterActivity.this, "手机号不能为空"
                        , Toast.LENGTH_SHORT).show();
            }else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final UserBean userBean = new UserBean();
                        userBean.setPhone(etPhone.getText().toString());
                        String address = "http://40.121.148.51:8080/test07-message/SMSServlet";
                        HttpConnection.sendUserRequest(address, userBean, new okhttp3.Callback() {

                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.d("------------", "onFailure: 连接失败");
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String responseData = response.body().string();
                                Log.d("------------", "onResponse: " + responseData);
                                //userBean = new Gson().fromJson(responseData,UserBean.class);
                                //                                Looper.prepare();
                                //                                Looper.loop();
                                myHandler.obtainMessage(1, responseData).sendToTarget();
                            }
                        });
                    }
                }).start();
            }
        }
    }

}
