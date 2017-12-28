package com.cocoon.jay.rankview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cocoon.jay.R;
import com.cocoon.jay.TagBean;

import java.util.ArrayList;


public class RankView extends LinearLayout {

    private LinearLayout rankLayout;
    private ListView rankLVLeft;
    private ListView rankLVRight;

    public RankView(Context context) {
        super(context);
        initViews();
    }

    public RankView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    @SuppressLint("NewApi") public RankView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_rank_view, this);
        rankLayout = findView(R.id.ll_rank_left);
        rankLVLeft = findView(R.id.lv_rank_left);
        rankLVRight = findView(R.id.lv_tank_right);
    }

    private <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }

    public void update(ArrayList<TagBean> ranks) {
        if (isEmptyRanks(ranks)) return;

        ArrayList<TagBean> hasRanks = findHasRanks(ranks);
        if (isEmptyRanks(hasRanks)) return;

        notifyLayout(hasRanks);
    }

    /**
     * 通知开始布局
     */
    private void notifyLayout(ArrayList<TagBean> hasRanks) {
        int maxRankRows = (hasRanks.size() + 1) / 2;
        int maxSpaceRows = (maxRankRows - 1);
        float totalHeight = getResources().getDimension(R.dimen.rank_height);
        float avargeHeight = totalHeight / (maxRankRows + maxSpaceRows);

        ArrayList<TagBean> leftRanks = new ArrayList<TagBean>();
        ArrayList<TagBean> rightRanks = new ArrayList<TagBean>();
        for (int i = 0; i < hasRanks.size(); i++) {
            if (i % 2 == 0) {
                leftRanks.add(hasRanks.get(i));
            } else {
                rightRanks.add(hasRanks.get(i));
            }
        }

        rankLVLeft.setAdapter(new RankAdapter(getContext(), leftRanks, avargeHeight, avargeHeight, Orientation.LEFT));
        rankLVRight.setAdapter(new RankAdapter(getContext(), rightRanks, avargeHeight, avargeHeight, Orientation.RIGHT));
    }

    /**
     * 寻找有效的Rank
     */
    private ArrayList<TagBean> findHasRanks(ArrayList<TagBean> ranks) {
        ArrayList<TagBean> rankArrayList = new ArrayList<TagBean>();
        for (TagBean r : ranks) {
            if (Integer.parseInt(r.getHas()) == 1) {
                rankArrayList.add(r);
            }
        }
        return rankArrayList;
    }

    private boolean isEmptyRanks(ArrayList<TagBean> ranks) {
        if (ranks == null || ranks.size() == 0) {
            return true;
        }
        return false;
    }
}
