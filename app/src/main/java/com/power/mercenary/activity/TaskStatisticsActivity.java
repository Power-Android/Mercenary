package com.power.mercenary.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.power.mercenary.R;
import com.power.mercenary.adapter.MyFollowAdapter;
import com.power.mercenary.adapter.MyGridadapter;
import com.power.mercenary.adapter.RenWuAdapter;
import com.power.mercenary.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/28.
 */

public class TaskStatisticsActivity extends BaseActivity {

    @BindView(R.id.gv_rwtj)
    GridView gv_rwtj;


    @BindView(R.id.left_back)
    ImageView left_back;

    @BindView(R.id.title_text)
    TextView title_text;
    @BindView(R.id.mRecycler)
    RecyclerView mRecycler;

    @BindView(R.id.springView)
    SpringView springView;


    private List<Integer> mlist;

    private MyGridadapter myGridadapter;
    ArrayList<String> mList=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_statistics);
        ButterKnife.bind(this);

        String title=getIntent().getStringExtra("title");

        title_text.setText(title);
        mlist = new ArrayList<>();

        for(int i=0;i<6;i++){
            mlist.add(i);
        }
        myGridadapter = new MyGridadapter(TaskStatisticsActivity.this,mlist);
        gv_rwtj.setAdapter(myGridadapter);
        gv_rwtj.setOnItemClickListener(onItemClickListener);
        if (mList.size()<=0){
            mList.add("");
            mList.add("");
            mList.add("");
            mList.add("");
            mList.add("");
            mList.add("");
            mList.add("");
            mList.add("");
            mList.add("");
            mList.add("");
            mList.add("");
        }
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycler.setNestedScrollingEnabled(false);
        RenWuAdapter changegameAdapter = new RenWuAdapter(R.layout.renwu_item_view, mList);
        mRecycler.setAdapter(changegameAdapter);


        initRefresh();
        left_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            myGridadapter.setUpdata(i);

        }
    };

    //下拉刷新
    private void initRefresh() {
        //DefaultHeader/Footer是SpringView已经实现的默认头/尾之一
        //更多还有MeituanHeader、AliHeader、RotationHeader等如上图7种
        springView.setType(SpringView.Type.FOLLOW);
        springView.setHeader(new DefaultHeader(mContext));
        springView.setFooter(new DefaultFooter(mContext));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
//                Toast.makeText(mContext,"下拉刷新中",Toast.LENGTH_SHORT).show();
                // list.clear();
                // 网络请求;
                // mStarFragmentPresenter.queryData();
                //一分钟之后关闭刷新的方法
                finishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
//                Toast.makeText(mContext,"玩命加载中...",Toast.LENGTH_SHORT).show();
                finishFreshAndLoad();
            }
        });
    }

    /**
     * 关闭加载提示
     */
    private void finishFreshAndLoad() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                springView.onFinishFreshAndLoad();
            }
        }, 2000);
    }
}
