package com.example.aleron08.ilearning.view.activity;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.aleron08.ilearning.R;
import com.example.aleron08.ilearning.adapter.LessonListRecyclerViewAdapter;
import com.example.aleron08.ilearning.util.ImmersiveStatusBar;

public class LessonListActivity extends AppCompatActivity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{

    private ImageView ivBack;
    private RecyclerView rvLessonList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private LessonListRecyclerViewAdapter lessonListRecyclerViewAdapter;

    private int lastVisibleItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_list);
        LinearLayout linearLayout = findViewById(R.id.ll_lesson_actionbar);
        ImmersiveStatusBar.initStateInActivity(this,linearLayout);
        initToolBar();
        initRecyclerView();
    }

    public void initToolBar(){
        ivBack = findViewById(R.id.iv_lesson_back);
        ivBack.setOnClickListener(this);
    }

    public void initRecyclerView(){
        rvLessonList = findViewById(R.id.rv_lesson_list);
        mSwipeRefreshLayout = findViewById(R.id.swipe_layout_lesson_list);
        linearLayoutManager = new LinearLayoutManager(this);
        lessonListRecyclerViewAdapter = new LessonListRecyclerViewAdapter(this);
        rvLessonList.setAdapter(lessonListRecyclerViewAdapter);
        rvLessonList.setLayoutManager(linearLayoutManager);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        rvLessonList.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(mSwipeRefreshLayout.isRefreshing())return;
                if(newState==recyclerView.SCROLL_STATE_IDLE&&lastVisibleItem== lessonListRecyclerViewAdapter.getItemCount()-1){
                    lessonListRecyclerViewAdapter.startLoad();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            lessonListRecyclerViewAdapter.addMoreItem();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_lesson_back:
                Toast.makeText(this,"back",Toast.LENGTH_SHORT).show();
                this.finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                lessonListRecyclerViewAdapter.addItem();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        },2000);
    }
}
