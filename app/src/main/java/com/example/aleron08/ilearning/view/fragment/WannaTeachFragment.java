package com.example.aleron08.ilearning.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aleron08.ilearning.R;
import com.example.aleron08.ilearning.adapter.WannaLearnRecyclerViewAdapter;
import com.example.aleron08.ilearning.adapter.WannaTeachRecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WannaTeachFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WannaTeachFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WannaTeachFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView recyclerView;
    private WannaTeachRecyclerViewAdapter wannaTeachRecyclerViewAdapter;
    private LinearLayoutManager linearLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;

    private int lastVisibleItem;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public WannaTeachFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WannaTeachFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WannaTeachFragment newInstance(String param1, String param2) {
        WannaTeachFragment fragment = new WannaTeachFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        return inflater.inflate(R.layout.fragment_wanna_teach, container, false);
    }

    @Override
    public void onResume() {
        initRecyclerView();
        super.onResume();
    }

    public void initRecyclerView(){
        recyclerView = getView().findViewById(R.id.rv_wanna_teach);
        swipeRefreshLayout = getView().findViewById(R.id.swipe_layout_wanna_teach);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        wannaTeachRecyclerViewAdapter = new WannaTeachRecyclerViewAdapter(getActivity());
        recyclerView.setAdapter(wannaTeachRecyclerViewAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(swipeRefreshLayout.isRefreshing())return;
                if(newState==recyclerView.SCROLL_STATE_IDLE&&lastVisibleItem== wannaTeachRecyclerViewAdapter.getItemCount()-1){
                    wannaTeachRecyclerViewAdapter.startLoad();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            wannaTeachRecyclerViewAdapter.addMoreItem();
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
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                wannaTeachRecyclerViewAdapter.addItem();
                swipeRefreshLayout.setRefreshing(false);
            }
        },2000);
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
}
