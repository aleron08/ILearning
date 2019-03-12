package com.example.aleron08.ilearning.view.activity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.aleron08.ilearning.R;
import com.example.aleron08.ilearning.util.ImmersiveStatusBar;
import com.example.aleron08.ilearning.util.LoggingStatus;

public class MineSetActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnLogout;

    private Context application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_set);
        application = getApplicationContext();
        LinearLayout linearLayout = this.findViewById(R.id.ll_mine_set_actionbar);
        ImmersiveStatusBar.initStateInActivity(this,linearLayout);
        btnLogout = findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (LoggingStatus.isLogged(application)){
            LoggingStatus.clearUser(application);
            finish();
        }else {
            Toast.makeText(this,"未登录",Toast.LENGTH_SHORT);
        }
    }
}
