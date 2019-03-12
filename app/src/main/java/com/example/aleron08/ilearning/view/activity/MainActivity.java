package com.example.aleron08.ilearning.view.activity;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.aleron08.ilearning.R;
import com.example.aleron08.ilearning.view.fragment.DiscoverFollowFragment;
import com.example.aleron08.ilearning.view.fragment.DiscoverFragment;
import com.example.aleron08.ilearning.view.fragment.HomeFragment;
import com.example.aleron08.ilearning.view.fragment.MineFragment;
import com.example.aleron08.ilearning.view.fragment.DiscoverRecommendFragment;
import com.example.aleron08.ilearning.view.fragment.TeachLearnFragment;
import com.example.aleron08.ilearning.view.fragment.WannaLearnFragment;
import com.example.aleron08.ilearning.view.fragment.WannaTeachFragment;
import com.example.aleron08.ilearning.view.inter.IMainView;


public class MainActivity extends AppCompatActivity implements IMainView
        , BottomNavigationBar.OnTabSelectedListener
        ,HomeFragment.OnFragmentInteractionListener
        ,DiscoverFragment.OnFragmentInteractionListener
        ,TeachLearnFragment.OnFragmentInteractionListener
        ,MineFragment.OnFragmentInteractionListener
        ,DiscoverRecommendFragment.OnFragmentInteractionListener
        ,DiscoverFollowFragment.OnFragmentInteractionListener
        ,WannaTeachFragment.OnFragmentInteractionListener
        ,WannaLearnFragment.OnFragmentInteractionListener {

    private BottomNavigationBar bottomNavigationBar;
    private HomeFragment homeFragment;
    private DiscoverFragment discoverFragment;
    private TeachLearnFragment teachLearnFragment;
    private MineFragment mineFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    int lastSelectedPosition = 0;
    private long lastBack = 0;//上次点击返回键时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBottomBar();
        setDefaultFragment();
    }

    @Override
    public void initBottomBar() {
        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        bottomNavigationBar.setTabSelectedListener(this);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.ic_main_home, "主页").setActiveColorResource(R.color.colorRed))
                .addItem(new BottomNavigationItem(R.mipmap.ic_main_discover, "发现").setActiveColorResource(R.color.colorOrange))
                .addItem(new BottomNavigationItem(R.mipmap.ic_main_teach_learn, "教与学").setActiveColorResource(R.color.colorGreen))
                .addItem(new BottomNavigationItem(R.mipmap.ic_main_mine, "我的").setActiveColorResource(R.color.colorBlue))
                .setFirstSelectedPosition(lastSelectedPosition)
                .initialise();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //不保存fragment state
        // super.onSaveInstanceState(outState);
    }

    //    @Override
//    public void onAttachFragment(android.support.v4.app.Fragment fragment) {
//        if (homeFragment == null && fragment instanceof HomeFragment)
//            homeFragment = fragment;
//        if (discoverFragment == null && fragment instanceof DiscoverFragment)
//            discoverFragment = fragment;
//        if (messageFragment == null && fragment instanceof MessageFragment)
//            messageFragment = fragment;
//        if (mineFragment == null && fragment instanceof MineFragment)
//            mineFragment = fragment;
//    }


    @Override
    public void setDefaultFragment() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        homeFragment = homeFragment.newInstance("主页", "home");
        fragmentTransaction.add(R.id.fl_main, homeFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void hideFragment(FragmentTransaction fragmentTransaction) {
        if (homeFragment != null) {
            fragmentTransaction.hide(homeFragment);
        }
        if (discoverFragment != null){
            fragmentTransaction.hide(discoverFragment);
        }
        if (teachLearnFragment != null){
            fragmentTransaction.hide(teachLearnFragment);
        }
        if (mineFragment != null){
            fragmentTransaction.hide(mineFragment);
        }
    }

    @Override
    public void onTabSelected(int position) {
        lastSelectedPosition = position;
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);
        switch (position){
            case 0:
                if(homeFragment==null){
                    homeFragment = homeFragment.newInstance("主页","home");
                    fragmentTransaction.add(R.id.fl_main,homeFragment);
                }else{
                    fragmentTransaction.show(homeFragment);
                }
                break;
            case 1:
                if(discoverFragment==null){
                    discoverFragment = discoverFragment.newInstance("发现","discover");
                    fragmentTransaction.add(R.id.fl_main,discoverFragment);
                }else{
                    fragmentTransaction.show(discoverFragment);
                }
                break;
            case 2:
                if(teachLearnFragment==null){
                    teachLearnFragment = teachLearnFragment.newInstance("教与学","message");
                    fragmentTransaction.add(R.id.fl_main,teachLearnFragment);
                }else {
                    fragmentTransaction.show(teachLearnFragment);
                }
                break;
            case 3:
                if(mineFragment==null){
                    mineFragment = mineFragment.newInstance("我的","mine");
                    fragmentTransaction.add(R.id.fl_main,mineFragment);
                }else {
                    fragmentTransaction.show(mineFragment);
                }
                break;
            default:
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        if (lastBack == 0 || System.currentTimeMillis() - lastBack > 2000) {
            Toast.makeText(MainActivity.this, "再按一次返回退出程序", Toast.LENGTH_SHORT).show();
            lastBack = System.currentTimeMillis();
            return;
        }
        super.onBackPressed();
    }
}
