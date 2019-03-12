package com.example.aleron08.ilearning.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aleron08.ilearning.R;

public class NewBlogActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivBack, ivPicture;
    private TextView tvSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_blog);
        initView();
    }

    private void initView(){
        ivBack = findViewById(R.id.iv_new_blog_back);
        tvSend = findViewById(R.id.tv_new_blog_send);
        ivPicture = findViewById(R.id.iv_new_blog_picture);

    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.iv_new_blog_back:
                finish();
                break;
            case R.id.tv_new_blog_send:

                break;
            case R.id.iv_new_blog_picture:

                break;
        }
    }
}
