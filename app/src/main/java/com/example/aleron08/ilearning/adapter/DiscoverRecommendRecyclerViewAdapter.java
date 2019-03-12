package com.example.aleron08.ilearning.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleron08.ilearning.R;
import com.example.aleron08.ilearning.view.activity.BlogActivity;
import com.example.aleron08.ilearning.view.activity.TeachActivity;

/**
 * Created by aleron08 on 2018/10/30.
 */

public class DiscoverRecommendRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    public DiscoverRecommendRecyclerViewAdapter(Context mContext){
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DiscoverRecommendViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_rv_discover_recommend,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    //自定义一个回调接口来实现Click事件
    public interface OnItemClickListener  {
        void onItemClick(View v, int position);
    }

    public OnItemClickListener mOnItemClickListener;//声明自定义的接口

    //定义方法并暴露给外面的调用者
    public void setOnItemClickListener(OnItemClickListener  listener) {
        this.mOnItemClickListener  = listener;
    }

    class DiscoverRecommendViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private RelativeLayout rlDiscoverRecommend;
        private ImageView ivHead;
        private TextView tvFollow;

        public DiscoverRecommendViewHolder(View itemView) {
            super(itemView);
            rlDiscoverRecommend = itemView.findViewById(R.id.rl_discover_recommend_item);
            ivHead = itemView.findViewById(R.id.iv_discover_recommend_head);
            tvFollow = itemView.findViewById(R.id.tv_discover_recommend_follow);
            rlDiscoverRecommend.setClickable(true);
            rlDiscoverRecommend.setOnClickListener(this);
            ivHead.setClickable(true);
            ivHead.setOnClickListener(this);
            tvFollow.setClickable(true);
            tvFollow.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mOnItemClickListener != null){
                mOnItemClickListener.onItemClick(view,getAdapterPosition());
            }
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
