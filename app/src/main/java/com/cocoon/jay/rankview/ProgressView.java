package com.cocoon.jay.rankview;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cocoon.jay.R;
import com.cocoon.jay.TagBean;


public class ProgressView extends LinearLayout {

	private TextView mTextViewRankNumLeft;
	private TextView mTextViewRankNumRight;
	private TextView mTextViewRankName;
	private ProgressBackgroundView mPBView;

	public ProgressView(Context context) {
		super(context);
		initViews();
	}

	public ProgressView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initViews();
	}

	@SuppressLint("NewApi") public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initViews();
	}

	private void initViews() {
		LayoutInflater.from(getContext()).inflate(R.layout.layout_progress_view, this);
		mTextViewRankNumLeft = findView(R.id.tv_rank_num_left);
		mTextViewRankNumRight = findView(R.id.tv_rank_num_right);
		mTextViewRankName = findView(R.id.tv_rank_name);
		mPBView = findView(R.id.pbv);
	}

	private <T extends View> T findView(int id) {
		return (T) findViewById(id);
	}

	public void setHeight(float h) {
		LinearLayout.LayoutParams layoutParams = (LayoutParams) getLayoutParams();
		layoutParams.height = (int) h;
		setLayoutParams(layoutParams);
	}

	public void update(TagBean rank, Orientation orientation, boolean isOddRow) {
		if (orientation == Orientation.LEFT) {
			mTextViewRankNumLeft.setVisibility(VISIBLE);
			mTextViewRankNumRight.setVisibility(GONE);
			mTextViewRankNumLeft.setText(rank.getRank() + "");
		} else {
			mTextViewRankNumRight.setVisibility(VISIBLE);
			mTextViewRankNumLeft.setVisibility(GONE);
			mTextViewRankNumRight.setText(rank.getRank() + "");
		}
		mTextViewRankName.setText(rank.getTag_name());

		mPBView.setOrientation(orientation);
		mPBView.setColor(isOddRow ? Config.COLOR_ROW_ODD: Config.COLOR_ROW_EVEN);
		mPBView.setRate((float) (rank.getRank() / Config.DEFAULT_RADIX));

//		if(Integer.parseInt(rank.getRank()) == 0){
//			mPBView.setRate(0.01f);
//		}else{
//			float itemRate = (float) Math.pow((float) Config.DEFAULT_RADIX / Integer.parseInt(rank.getRank()), (float) 1 / 4);
//			mPBView.setRate(itemRate);
//		}

	}

}
