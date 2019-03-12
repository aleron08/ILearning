package com.example.aleron08.ilearning.view.fragment;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleron08.ilearning.R;
import com.example.aleron08.ilearning.bean.UserBean;
import com.example.aleron08.ilearning.util.ImmersiveStatusBar;
import com.example.aleron08.ilearning.util.LoggingStatus;
import com.example.aleron08.ilearning.view.activity.LessonListActivity;
import com.example.aleron08.ilearning.view.activity.LoginActivity;
import com.example.aleron08.ilearning.view.activity.MineSetActivity;
import com.example.aleron08.ilearning.view.activity.MyInfoActivity;
import com.example.aleron08.ilearning.view.inter.IMineView;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MineFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MineFragment extends Fragment implements IMineView,View.OnClickListener{
    private RelativeLayout rlIntro;
    private TextView tvMineName,tvMineProfile;
    private LinearLayout llMineSet;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private UserBean userBean;
    private Context application;

    public MineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        LinearLayout linearLayout = getView().findViewById(R.id.ll_mine_actionbar);
        ImmersiveStatusBar.initStateInFragment(this,linearLayout);
//        SystemBarTintManager tintManager = new SystemBarTintManager(getActivity());
//        tintManager.setStatusBarTintEnabled(true);
//        tintManager.setStatusBarTintResource(R.color.colorBrown);//设置系统状态栏颜色
//        tintManager.setStatusBarTintDrawable(getResources().getDrawable(R.drawable.mybgcolor_02));//设置系统状态栏背景图
//        setScrollViewPaddingTop();
        initView();
        super.onResume();
    }

    private void initView(){
        application = getActivity().getApplicationContext();
        rlIntro = getView().findViewById(R.id.rl_mine_intro);
        tvMineName = getView().findViewById(R.id.tv_mine_name);
        tvMineProfile = getView().findViewById(R.id.tv_mine_profile);
        llMineSet = getView().findViewById(R.id.ll_mine_set);
        if(LoggingStatus.isLogged(application)){
            userBean = LoggingStatus.getUser(application);
            tvMineName.setText(userBean.getName());
            //tvMineProfile.setText(userBean.getProfile());
        }else {
            tvMineName.setText("未登录/点击登录");
        }
        llMineSet.setOnClickListener(this);
        rlIntro.setOnClickListener(this);
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
        return inflater.inflate(R.layout.fragment_mine, container, false);
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
        switch (view.getId()){
            case R.id.rl_mine_intro:
                Intent intent;
                if (LoggingStatus.isLogged(application)){
                    intent = new Intent(getActivity(), MyInfoActivity.class);
                    startActivity(intent);
                }else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ll_mine_set:
                Intent intent1 = new Intent(getActivity(),MineSetActivity.class);
                startActivity(intent1);
            default:
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

    public void setScrollViewPaddingTop(){
        int paddingTop = 0;
        ScrollView scrollView = getView().findViewById(R.id.sv_mine);
        paddingTop = ImmersiveStatusBar.getStatusBarHeightInFragment(this)+120;
        scrollView.setPadding(0,paddingTop,0,0);
    }
}
