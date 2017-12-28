package com.cocoon.jay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.cocoon.jay.rankview.RankView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<TagBean> mRanks = new ArrayList<TagBean>();
        for (int i = 1; i < 10; i++) {
            TagBean tag = new TagBean();
            tag.setTag_name("数据"+ i );
            tag.setRank(i * 10f);
            tag.setHas("1");
            mRanks.add(tag);
        }


        RankView mRankView = findViewById(R.id.rank_main);
        mRankView.update(mRanks);


        RecyclerView mRecyclerView = findViewById(R.id.rv_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerView.setLayoutManager(linearLayoutManager);//禁止滑动
        PbAdapter adapter = new PbAdapter(this, mRanks);
        mRecyclerView.setAdapter(adapter);


        LinearLayout mChart1 =  findViewById(R.id.layout_chart1);
        mChart1.removeAllViews();
        ComboView1 view = new ComboView1(this);
        view.setList(mRanks);
        mChart1.addView(view);


        LinearLayout mChart2 =  findViewById(R.id.layout_chart2);
        mChart2.removeAllViews();
        ComboView2 view2 = new ComboView2(this);
        view2.setList(mRanks);
        mChart2.addView(view2);


    }
}
