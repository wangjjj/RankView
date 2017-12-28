package com.cocoon.jay.rankview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cocoon.jay.R;
import com.cocoon.jay.TagBean;

import java.util.ArrayList;


public class RankAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<TagBean> ranks;
    private float spaceHeight;
    private float rankHeight;
    private Orientation orientation;

    public RankAdapter(Context mContext, ArrayList<TagBean> ranks, float spaceHeight, float rankHeight, Orientation orientation) {
        this.mContext = mContext;
        this.ranks = ranks;
        this.spaceHeight = spaceHeight;
        this.rankHeight = rankHeight;
        this.orientation = orientation;
    }

    @Override
    public int getCount() {
        return ranks.size();
    }

    @Override
    public Object getItem(int position) {
        return ranks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.adapter_rank, null);
            holder = new ViewHolder();
            holder.spaceView = convertView.findViewById(R.id.rank_sv);
            holder.rankPView = (ProgressView) convertView.findViewById(R.id.rank_pv);
            holder.spaceView.setMinimumHeight((int) spaceHeight);
            holder.rankPView.setHeight(rankHeight);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final int RID = position;
        if (RID == 0) {
            holder.spaceView.setVisibility(View.GONE);
        } else {
            holder.spaceView.setVisibility(View.VISIBLE);
        }

        TagBean rank = ranks.get(RID);
        holder.rankPView.update(rank, orientation, RID % 2 == 0);
        return convertView;
    }

    private class ViewHolder {
        public View spaceView;

        public ProgressView rankPView;
    }
}
