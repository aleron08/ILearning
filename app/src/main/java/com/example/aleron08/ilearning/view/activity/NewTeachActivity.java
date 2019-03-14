package com.example.aleron08.ilearning.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.aleron08.ilearning.R;
import com.example.aleron08.ilearning.util.ImmersiveStatusBar;

public class NewTeachActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_teach);
        LinearLayout linearLayout = findViewById(R.id.ll_new_teach_actionbar);
        ImmersiveStatusBar.initStateInActivity(this,linearLayout);
        initToolBar();
        initViewPager();
        initTabLayout();
    }

    public void initToolBar(){
        ivBack = findViewById(R.id.iv_new_teach_back);
        ivBack.setOnClickListener(this);
    }

    public void initViewPager(){

    }

    public void initTabLayout(){

    }

    @Override
    public void onClick(View view) {

    }
}
