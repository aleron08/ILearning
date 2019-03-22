package com.example.aleron08.ilearning.view.activity;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aleron08.ilearning.R;
import com.example.aleron08.ilearning.adapter.MyViewPagerAdapter;
import com.example.aleron08.ilearning.util.ImmersiveStatusBar;
import com.example.aleron08.ilearning.view.fragment.NewIntroduceFragment;
import com.example.aleron08.ilearning.view.fragment.NewTeacherFragment;

import java.util.ArrayList;
import java.util.List;

public class NewTeachActivity extends AppCompatActivity implements View.OnClickListener,NewIntroduceFragment.OnFragmentInteractionListener,NewTeacherFragment.OnFragmentInteractionListener{

    private ImageView ivBack;
    private TextView tvSend;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private NewIntroduceFragment newIntroduceFragment;
    private NewTeacherFragment newTeacherFragment;
    private List<Fragment> fragments;
    private List<String> tabs;
    private MyViewPagerAdapter myViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_teach);
        LinearLayout linearLayout = findViewById(R.id.ll_new_teach_actionbar);
        ImmersiveStatusBar.initStateInActivity(this,linearLayout);
        initToolBar();
        initViewPager();//ViewPager的初始化必须在TabLayout之前
        initTabLayout();
    }

    public void initToolBar(){
        ivBack = findViewById(R.id.iv_new_teach_back);
        tvSend = findViewById(R.id.tv_new_teach_send);
        ivBack.setOnClickListener(this);
        tvSend.setOnClickListener(this);
    }

    public void initTabLayout(){
        mTabLayout = findViewById(R.id.tl_new_teach_tab);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setTabTextColors(ContextCompat.getColor(this, R.color.colorDarkGray)
                , ContextCompat.getColor(this, R.color.colorWhite));
        mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.colorWhite));
        ViewCompat.setElevation(mTabLayout, 10);
        mTabLayout.setupWithViewPager(mViewPager);//ViewPager的初始化必须在TabLayout之前
    }

    public void initViewPager(){
        tabs = new ArrayList<String>();
        fragments = new ArrayList<Fragment>();
        mViewPager = findViewById(R.id.vp_new_teach);
        tabs.add("介绍");
        tabs.add("讲师");
        fragments.add(NewIntroduceFragment.newInstance("介绍","Introduce"));
        fragments.add(NewTeacherFragment.newInstance("讲师","Teacher"));
        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        myViewPagerAdapter.setData(fragments, tabs);
        mViewPager.setAdapter(myViewPagerAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_new_teach_back:
                this.finish();
                break;
            case R.id.tv_new_teach_send:

                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
