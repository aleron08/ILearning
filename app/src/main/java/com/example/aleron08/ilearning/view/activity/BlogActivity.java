package com.example.aleron08.ilearning.view.activity;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleron08.ilearning.R;
import com.example.aleron08.ilearning.util.ImmersiveStatusBar;

public class BlogActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView ivBack,ivShare,ivHead;
    private TextView tvToolBar,tvName,tvViewNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        LinearLayout linearLayout = findViewById(R.id.ll_blog_actionbar);
        ImmersiveStatusBar.initStateInActivity(this,linearLayout);
        initToolBar();
        initView();
    }

    private void initView() {
        ivHead = findViewById(R.id.iv_blog_head);
        tvName = findViewById(R.id.tv_blog_name);
        tvViewNum = findViewById(R.id.tv_blog_view_num);
    }

    private void initToolBar() {
        ivBack = findViewById(R.id.iv_blog_back);
        ivShare = findViewById(R.id.iv_blog_share);
        tvToolBar = findViewById(R.id.tv_blog_toolbar);
        ivBack.setOnClickListener(this);
        ivShare.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_blog_back:
                Toast.makeText(this,"back",Toast.LENGTH_SHORT).show();
                this.finish();
                break;
            case R.id.iv_blog_share:
                Toast.makeText(this,"share",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
