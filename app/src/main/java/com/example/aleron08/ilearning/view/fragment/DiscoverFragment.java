package com.example.aleron08.ilearning.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.aleron08.ilearning.R;
import com.example.aleron08.ilearning.adapter.MyViewPagerAdapter;
import com.example.aleron08.ilearning.util.ImmersiveStatusBar;
import com.example.aleron08.ilearning.view.activity.NewBlogActivity;
import com.example.aleron08.ilearning.view.inter.IDiscoverView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DiscoverFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DiscoverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiscoverFragment extends Fragment implements IDiscoverView, View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private List<Fragment> fragments;
    private List<String> tabs;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private DiscoverRecommendFragment discoverRecommendFragment;
    private DiscoverFollowFragment discoverFollowFragment;

    private ImageView ivAdd;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DiscoverFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiscoverFragment newInstance(String param1, String param2) {
        DiscoverFragment fragment = new DiscoverFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        LinearLayout linearLayout = getView().findViewById(R.id.ll_discover_actionbar);
        ImmersiveStatusBar.initStateInFragment(this,linearLayout);
//        SystemBarTintManager tintManager = new SystemBarTintManager(getActivity());
//        tintManager.setStatusBarTintEnabled(true);
//        tintManager.setStatusBarTintResource(R.color.colorTeal);//设置系统状态栏颜色
//        tintManager.setStatusBarTintDrawable(getResources().getDrawable(R.drawable.mybgcolor_02));//设置系统状态栏背景图
        initViewPager();
        initTabLayout();
        ivAdd = getView().findViewById(R.id.iv_discover_add);
        ivAdd.setOnClickListener(this);
        super.onResume();
    }

    public void initTabLayout(){
        mTabLayout = getView().findViewById(R.id.tl_discover_tab);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setTabTextColors(ContextCompat.getColor(getContext(), R.color.colorDarkGray)
                , ContextCompat.getColor(getContext(), R.color.colorWhite));
        mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
        ViewCompat.setElevation(mTabLayout, 10);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    public void initViewPager(){
        tabs = new ArrayList<String>();
        fragments = new ArrayList<Fragment>();
        mViewPager = getView().findViewById(R.id.vp_discover);
        tabs.add("推荐");
        tabs.add("关注");
        fragments.add(DiscoverRecommendFragment.newInstance("推荐","Recommend"));
        fragments.add(DiscoverFollowFragment.newInstance("关注","Follow"));
        myViewPagerAdapter = new MyViewPagerAdapter(getChildFragmentManager());
        myViewPagerAdapter.setData(fragments,tabs);
        mViewPager.setAdapter(myViewPagerAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discover, container, false);
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
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.iv_discover_add:
                intent = new Intent(getContext(), NewBlogActivity.class);
                break;
        }
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
