package com.example.aleron08.ilearning.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.aleron08.ilearning.R;
import com.example.aleron08.ilearning.adapter.HomeRecyclerViewAdapter;
import com.example.aleron08.ilearning.util.GlideImageLoader;
import com.example.aleron08.ilearning.util.ImmersiveStatusBar;
import com.example.aleron08.ilearning.view.activity.LessonListActivity;
import com.example.aleron08.ilearning.view.activity.TeachActivity;
import com.example.aleron08.ilearning.view.inter.IHomeView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class HomeFragment extends Fragment implements IHomeView,AppBarLayout.OnOffsetChangedListener,SwipeRefreshLayout.OnRefreshListener,HomeRecyclerViewAdapter.OnItemClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Banner banner;
    private RecyclerView mRvHome;
    private LinearLayoutManager linearLayoutManager;
    private HomeRecyclerViewAdapter homeRecyclerViewAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AppBarLayout appBarLayout;

    private int lastVisibleItem;

    public HomeFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        LinearLayout linearLayout = getView().findViewById(R.id.ll_home_actionbar);
        ImmersiveStatusBar.initStateInFragment(this,linearLayout);
//        SystemBarTintManager tintManager = new SystemBarTintManager(getActivity());
//        tintManager.setStatusBarTintEnabled(true);
//        tintManager.setStatusBarTintResource(R.color.colorOrange);//设置系统状态栏颜色
//        tintManager.setStatusBarTintDrawable(getResources().getDrawable(R.drawable.mybgcolor_02));//设置系统状态栏背景图
        initBanner();//初始化轮播图
        initRecyclerView();//初始化RecyclerView

        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void initRecyclerView(){
        mRvHome = getView().findViewById(R.id.rv_main);
        appBarLayout = getView().findViewById(R.id.app_bar_layout_home);
        swipeRefreshLayout = getView().findViewById(R.id.swipe_layout_home);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(getActivity());
        mRvHome.setLayoutManager(linearLayoutManager);
        mRvHome.setAdapter(homeRecyclerViewAdapter);
        appBarLayout.addOnOffsetChangedListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        mRvHome.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(swipeRefreshLayout.isRefreshing())return;
                if(newState==recyclerView.SCROLL_STATE_IDLE&&lastVisibleItem== homeRecyclerViewAdapter.getItemCount()-1){
                    homeRecyclerViewAdapter.startLoad();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            homeRecyclerViewAdapter.addMoreItem();
                        }
                    },1000);
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
        homeRecyclerViewAdapter.setOnItemClickListener(this);
    }

    public void initBanner(){
        banner = (Banner) getView().findViewById(R.id.br_home_banner);
        List<Integer> images = new ArrayList<Integer>();
        List<String> titles = new ArrayList<String>();
        images.add(R.drawable.banner_01);
        images.add(R.drawable.banner_02);
        images.add(R.drawable.banner_03);
        titles.add("banner01");
        titles.add("banner02");
        titles.add("banner03");
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(2000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if(verticalOffset >= 0){
            swipeRefreshLayout.setEnabled(true);
        }else{
            swipeRefreshLayout.setEnabled(false);
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                homeRecyclerViewAdapter.addItem();
                swipeRefreshLayout.setRefreshing(false);
            }
        },2000);
    }

    /*
    * item子布局点击事件的方法重写
    * */
    @Override
    public void onItemClick(View v, int position) {
        //Toast.makeText(getActivity(),"position:"+position+"/id:"+v.getId(),Toast.LENGTH_SHORT).show();
        Intent intent = null;
        if(v.getId() == R.id.rl_home_item_more){
            intent = new Intent(getActivity(), LessonListActivity.class);
        }else{
            intent = new Intent(getActivity(), TeachActivity.class);
        }
        startActivity(intent);
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        View view = new View(this.getContext());
        if (view != null) {
            if (hidden) {
                view.setFitsSystemWindows(false);
            } else {
                view.setFitsSystemWindows(true);
            }
            view.requestApplyInsets();
        }
        super.onHiddenChanged(hidden);
    }

}
