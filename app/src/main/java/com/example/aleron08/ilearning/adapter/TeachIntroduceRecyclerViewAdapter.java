package com.example.aleron08.ilearning.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aleron08.ilearning.R;

/**
 * Created by aleron08 on 2018/10/28.
 */

public class TeachIntroduceRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    public TeachIntroduceRecyclerViewAdapter(Context mContext){
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TeachIntroduceViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_rv_teach_introduce,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class TeachIntroduceViewHolder extends RecyclerView.ViewHolder{

        public TeachIntroduceViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void startLoad(){
        Log.i("LinearAdapter", "startLoad: 开始");
    }

    public void addItem(){
        Log.i("LinearAdapter", "addItem: 添加");
    }

    public void addMoreItem(){
        Log.i("LinearAdapter", "addMoreItem: 更多");
    }
}
