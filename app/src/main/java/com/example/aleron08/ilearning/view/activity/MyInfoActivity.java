package com.example.aleron08.ilearning.view.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleron08.ilearning.R;
import com.example.aleron08.ilearning.bean.UserBean;
import com.example.aleron08.ilearning.config.ServerConfig;
import com.example.aleron08.ilearning.util.HttpConnection;
import com.example.aleron08.ilearning.util.ImmersiveStatusBar;
import com.example.aleron08.ilearning.util.InternetCheck;
import com.example.aleron08.ilearning.util.LoggingStatus;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MyInfoActivity extends AppCompatActivity implements View.OnClickListener,TextWatcher,DialogInterface.OnClickListener {
    private ImageView ivBack;
    private TextView tvSave,tvSex;
    private EditText etName,etProfile,etSchool,etMajor,etHobby;
    private RelativeLayout rlHead,rlName,rlSex,rlProfile,rlSchool,rlMajor,rlHobby;
    private AlertDialog alertDialog;

    private MyInfoActivity.MyHandler myHandler = new MyInfoActivity.MyHandler(this);
    private Thread saveThread;

    private UserBean userBean;
    private Context application;

    final  String[] sexItem = new String[]{"男","女"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        LinearLayout linearLayout = findViewById(R.id.ll_my_info_actionbar);
        ImmersiveStatusBar.initStateInActivity(this,linearLayout);
        initView();
    }

    private void initView(){
        application = this.getApplicationContext();
        ivBack = findViewById(R.id.iv_my_info_back);
        tvSave = findViewById(R.id.tv_my_info_save);
        etName = findViewById(R.id.et_my_info_name);
        etProfile = findViewById(R.id.et_my_info_profile);
        etSchool = findViewById(R.id.et_my_info_school);
        etMajor = findViewById(R.id.et_my_info_major);
        etHobby = findViewById(R.id.et_my_info_hobby);
        tvSex = findViewById(R.id.tv_my_info_sex);
        rlSex = findViewById(R.id.rl_my_info_sex);
        initData();
        etName.addTextChangedListener(this);
        tvSex.addTextChangedListener(this);
        etProfile.addTextChangedListener(this);
        etSchool.addTextChangedListener(this);
        etMajor.addTextChangedListener(this);
        etHobby.addTextChangedListener(this);
        ivBack.setOnClickListener(this);
        tvSave.setOnClickListener(this);
        rlSex.setOnClickListener(this);
        alertDialog = new AlertDialog.Builder(this)
                .setTitle("请选择您的性别")
                .setIcon(R.mipmap.ic_share)
                .setSingleChoiceItems(sexItem, 0, this)
                .create();
    }

    private void initData(){
        if(LoggingStatus.isLogged(application)){
            userBean = LoggingStatus.getUser(application);
            if (userBean.getName() != null) etName.setText(userBean.getName());
            if (userBean.getSex() != null) tvSex.setText(userBean.getSex());
            //if (userBean.getProfile() != null) etProfile.setText(userBean.getProfile());
            if (userBean.getSchool() != null) etSchool.setText(userBean.getSchool());
            if (userBean.getMajor() != null) etMajor.setText(userBean.getMajor());
            if (userBean.getHobby() != null) etHobby.setText(userBean.getHobby());
        }
    }

    //弱引用，防止内存泄漏
    private static class MyHandler extends Handler {
        private final WeakReference<MyInfoActivity> mMyInfoActivity;

        public MyHandler(MyInfoActivity activity){
            mMyInfoActivity = new WeakReference<MyInfoActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if(mMyInfoActivity.get() == null){
                return;
            }
            mMyInfoActivity.get().updateUIThread(msg);
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
                Toast.makeText(MyInfoActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                MyInfoActivity.this.finish();
            }else if(result.equals("#failed")){
                Toast.makeText(MyInfoActivity.this,"保存失败",Toast.LENGTH_SHORT).show();
                tvSave.setTextColor(getResources().getColor(R.color.colorWhite));
                tvSave.setClickable(true);
            }
        }
    }

    private void doSave(){
        if(InternetCheck.isNetworkAvailable(this)){
            saveThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    UserBean userBean ;
                    userBean = LoggingStatus.getUser(application);
                    userBean.setName(etName.getText().toString());
                    userBean.setSex(tvSex.getText().toString());
                    userBean.setSchool(etSchool.getText().toString());
                    userBean.setMajor(etMajor.getText().toString());
                    userBean.setHobby(etHobby.getText().toString());
                    String address = "http://" + ServerConfig.serverIP + ":" +ServerConfig.port + "/test07-message/MyInfoServlet";
                    HttpConnection.sendUserRequest(address, userBean, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("--MyInfoActivity--", "onFailure: 连接失败");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String responseData = response.body().string();
                            Log.d("--MyInfoActivity--", "onResponse: " + responseData);
                            //userBean = new Gson().fromJson(responseData,UserBean.class);
//                                Looper.prepare();
//                                Looper.loop();
                            myHandler.obtainMessage(1, responseData).sendToTarget();
                        }
                    });
                }
            });
            saveThread.start();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_my_info_back:
                Toast.makeText(this,"back",Toast.LENGTH_SHORT).show();
                this.finish();
                break;
            case R.id.tv_my_info_save:
                Toast.makeText(this,"save",Toast.LENGTH_SHORT).show();
                tvSave.setTextColor(getResources().getColor(R.color.colorDarkGray));
                tvSave.setClickable(false);
                doSave();
                break;
            case R.id.rl_my_info_sex:
                Toast.makeText(this,"sex",Toast.LENGTH_SHORT).show();
                alertDialog.show();
                break;
            default:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        tvSave.setTextColor(getResources().getColor(R.color.colorWhite));
        tvSave.setClickable(true);
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        tvSex.setText(sexItem[i]);
    }
}
