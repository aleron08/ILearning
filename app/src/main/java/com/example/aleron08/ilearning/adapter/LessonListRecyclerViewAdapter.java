package com.example.aleron08.ilearning.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aleron08.ilearning.R;
import com.example.aleron08.ilearning.view.activity.TeachActivity;

/**
 * Created by aleron08 on 2018/10/24.
 */

public class LessonListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    public LessonListRecyclerViewAdapter(Context mContext){
        this.mContext = mContext;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LessonListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_rv_lesson_list,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
//        holder.relativeLayout.setText("Hello World!");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TeachActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class LessonListViewHolder extends RecyclerView.ViewHolder{

        public LessonListViewHolder(View itemView) {
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
