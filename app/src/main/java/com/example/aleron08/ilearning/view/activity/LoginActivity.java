package com.example.aleron08.ilearning.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleron08.ilearning.R;
import com.example.aleron08.ilearning.bean.UserBean;
import com.example.aleron08.ilearning.config.ServerConfig;
import com.example.aleron08.ilearning.presenter.inter.ILoginPresenter;
import com.example.aleron08.ilearning.util.HttpConnection;
import com.example.aleron08.ilearning.util.ImmersiveStatusBar;
import com.example.aleron08.ilearning.util.InternetCheck;
import com.example.aleron08.ilearning.util.LoggingStatus;
import com.example.aleron08.ilearning.view.inter.ILoginView;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements ILoginView,View.OnClickListener {

    private EditText etPhone;
    private EditText etPwd;
    private Button btnLogin;
    private TextView tvForget;
    private TextView tvRegister;
    ILoginPresenter iLoginPresenter;
    private ProgressBar progressBar;

    private MyHandler myHandler = new MyHandler(this);
    private Thread loginThread;

    private UserBean userBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LinearLayout linearLayout = this.findViewById(R.id.ll_login_actionbar);
        ImmersiveStatusBar.initStateInActivity(this,linearLayout);
        initView();
    }

    void initView(){
        etPhone = findViewById(R.id.et_login_phone);
        etPwd = findViewById(R.id.et_login_pwd);
        btnLogin = findViewById(R.id.btn_login_login);
        tvForget = findViewById(R.id.tv_login_forget);
        tvRegister = findViewById(R.id.tv_login_register);
        //progressBar = findViewById(R.id.progress_login);
        btnLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
    }

    //弱引用，防止内存泄漏
    private static class MyHandler extends Handler{
        private final WeakReference<LoginActivity> mLoginActivity;

        public MyHandler(LoginActivity activity){
            mLoginActivity = new WeakReference<LoginActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if(mLoginActivity.get() == null){
                return;
            }
            mLoginActivity.get().updateUIThread(msg);
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
                LoggingStatus.saveUser(getApplicationContext(),userBean);
                Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                LoginActivity.this.finish();
            }else if(result.equals("#failed")){
                Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                etPwd.setText("");
            }
        }
    }

    @Override
    public void onClick(View view){
        Intent intent = null;
        switch (view.getId()){
            case R.id.btn_login_login:
                doLogin();
                break;
            case R.id.tv_login_forget:

                break;
            case R.id.tv_login_register:
                intent = new Intent(this,RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void doLogin(){
        if(InternetCheck.isNetworkAvailable(this)){
            if(etPhone.getText().toString().isEmpty() || etPwd.getText().toString().isEmpty()){
                Toast.makeText(LoginActivity.this,"手机号或密码不能为空",Toast.LENGTH_SHORT).show();
            }else {
                loginThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final UserBean userBean = new UserBean();
                        userBean.setPhone(etPhone.getText().toString());
                        userBean.setPwd(etPwd.getText().toString());
                        //String address = "http://40.121.148.51:8080/test07-message/LoginServlet";
                        String address = "http://" + ServerConfig.serverIP + ":" +ServerConfig.port + "/test07-message/LoginServlet";
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
                });
                loginThread.start();
            }
        }
    }

//        //添加建立表单，添加上传服务器参数
//        RequestBody formBody = new FormBody.Builder()
//                .add("phone",phone)
//                .add("pwd",pwd)
//                .build();
//        Request.Builder builder = new Request.Builder()
//                .url("http://40.121.148.51:8080/test02-android/LoginServlet")
//                .post(formBody);
//        //执行请求
//        Call call = client.newCall(builder.build());
//        //请求回调
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("@@@@@@@@@@@", "onFailure: 连接失败");
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                //从response获取服务器返回的数据，转成字符串处理
//                String str = new String(response.body().bytes(),"utf-8");
//                //通过handler更新ui
//                mHandler.obtainMessage(1,str).sendToTarget();
//            }
//        });
//        Log.i("!!!!!!!!!!!", "doLogin: over");
//    }

    @Override
    public void onClickText() {

    }

    @Override
    public void onLoginResult(boolean result, int code) {

    }

    @Override
    public void onSetProgressBarview(int visiblity) {

    }
}
