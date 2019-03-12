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
import android.widget.Toast;

import com.example.aleron08.ilearning.R;
import com.example.aleron08.ilearning.adapter.MyViewPagerAdapter;
import com.example.aleron08.ilearning.util.ImmersiveStatusBar;
import com.example.aleron08.ilearning.view.fragment.CommentFragment;
import com.example.aleron08.ilearning.view.fragment.IntroduceFragment;
import com.example.aleron08.ilearning.view.fragment.TeacherFragment;

import java.util.ArrayList;
import java.util.List;

public class TeachActivity extends AppCompatActivity implements View.OnClickListener,IntroduceFragment.OnFragmentInteractionListener,TeacherFragment.OnFragmentInteractionListener,CommentFragment.OnFragmentInteractionListener {

    private ImageView ivBack,ivShare;
    private TextView tvPrice,tvCollect,tvOrder;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private IntroduceFragment introduceFragment;
    private TeacherFragment teacherFragment;
    private CommentFragment commentFragment;
    private List<Fragment> fragments;
    private List<String> tabs;
    private MyViewPagerAdapter myViewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teach);
        LinearLayout linearLayout = findViewById(R.id.ll_teach_actionbar);
        ImmersiveStatusBar.initStateInActivity(this,linearLayout);
        initToolBar();
        initViewPager();
        initTabLayout();
    }

    public void initToolBar(){
        ivBack = findViewById(R.id.iv_teach_back);
        ivBack.setOnClickListener(this);
    }

    public void initTabLayout(){
        mTabLayout = findViewById(R.id.tl_teach_tab);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setTabTextColors(ContextCompat.getColor(this, R.color.colorDarkGray)
                , ContextCompat.getColor(this, R.color.colorWhite));
        mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.colorWhite));
        ViewCompat.setElevation(mTabLayout, 10);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    public void initViewPager(){
        tabs = new ArrayList<String>();
        fragments = new ArrayList<Fragment>();
        mViewPager = findViewById(R.id.vp_teach);
        tabs.add("介绍");
        tabs.add("讲师");
        tabs.add("评论");
        fragments.add(IntroduceFragment.newInstance("介绍","Introduce"));
        fragments.add(TeacherFragment.newInstance("讲师","Teacher"));
        fragments.add(CommentFragment.newInstance("评论","Comment"));
        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        myViewPagerAdapter.setData(fragments,tabs);
        mViewPager.setAdapter(myViewPagerAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_teach_back:
                Toast.makeText(this,"back",Toast.LENGTH_SHORT).show();
                this.finish();
                break;
            case R.id.iv_teach_share:

                break;
            case R.id.tv_teach_collect:

                break;
            case R.id.tv_teach_order:

                break;
            default:
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
