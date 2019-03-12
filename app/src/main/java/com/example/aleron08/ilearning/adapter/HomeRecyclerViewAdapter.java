package com.example.aleron08.ilearning.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleron08.ilearning.R;

/**
 * Created by aleron08 on 2018/10/22.
 */

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    public HomeRecyclerViewAdapter(Context mContext){
        this.mContext = mContext;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomeViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_rv_main,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
//        holder.relativeLayout.setText("Hello World!");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
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

    class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private RelativeLayout rlMore;
        private LinearLayout ll1,ll2,ll3,ll4;
        private TextView tvTitle,tv1,tv2,tv3,tv4;
        private ImageView iv1,iv2,iv3,iv4;

        public HomeViewHolder(View itemView) {
            super(itemView);
            rlMore = itemView.findViewById(R.id.rl_home_item_more);
            ll1 = itemView.findViewById(R.id.ll_home_item_1);
            ll2 = itemView.findViewById(R.id.ll_home_item_2);
            ll3 = itemView.findViewById(R.id.ll_home_item_3);
            ll4 = itemView.findViewById(R.id.ll_home_item_4);
            tvTitle = itemView.findViewById(R.id.tv_home_item_title);
            tv1 = itemView.findViewById(R.id.tv_home_item_1);
            tv2 = itemView.findViewById(R.id.tv_home_item_2);
            tv3 = itemView.findViewById(R.id.tv_home_item_3);
            tv4 = itemView.findViewById(R.id.tv_home_item_4);
            iv1 = itemView.findViewById(R.id.iv_home_item_1);
            iv2 = itemView.findViewById(R.id.iv_home_item_2);
            iv3 = itemView.findViewById(R.id.iv_home_item_3);
            iv4 = itemView.findViewById(R.id.iv_home_item_4);
            ll1.setClickable(true);
            ll1.setOnClickListener(this);
            ll2.setClickable(true);
            ll2.setOnClickListener(this);
            ll3.setClickable(true);
            ll3.setOnClickListener(this);
            ll4.setClickable(true);
            ll4.setOnClickListener(this);
            rlMore.setOnClickListener(this);
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
