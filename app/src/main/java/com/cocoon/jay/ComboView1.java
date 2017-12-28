package com.cocoon.jay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * 图表 1
 */
public class ComboView1 extends View {

	private static final String TAG = ComboView1.class.getSimpleName();
	/** view 背景 */
	private static final int VIEW_COLOR = Color.TRANSPARENT;
	/** 柱形条颜色 */
	private static final int COLUMN_COLOR = Color.BLACK;
	/** 柱形条背景颜色 */
	private static final int COLUMN_BG_COLOR = Color.TRANSPARENT;
	/** 数值颜色 */
	private static final int VALUE_COLOR = Color.BLACK;
	/** 每一项宽 */
	private static final int ITEM_WIDTH = 25;
	/** 每一项高 */
	private static final int ITEM_HIGHT = 300;
	/** 左右边距 */
	private static final int PARENT_WIDTH_PADDING = 165;
	/** 上边距 */
	private static final int PARENT_TOP_PADDING = 60;
	/** 下边距 */
	private static final int PARENT_BOTTOM_PADDING = 60;
	/** 文字边距 */
	private static final int PARENT_TEXT = 50;
	/** 最大数值 */
	private static final int DEF_RANK = 1;
	/** view 宽度 */
	private int mWidth;
	/** view 高度 */
	private int mHeight;
	/** 项目条数 */
	private int mItemCount;
	/** 项目间距 */
	private int mPaddingWitdh;
	/** 图片*/
	private Bitmap mBitmap;
	/** 图片矩阵集合 */
	private ArrayList<Rect> mIconList;
	/** 图表数据 */
	private ArrayList<TagBean> mBeanList;
	/** 矩阵集合 */
	private ArrayList<Rect> mRectList;
	/** 画笔 */
	private Paint mPaint;
	/** 文字矩阵 */
	private Rect mTextBound;

	public ComboView1(Context context) {
		this(context, null);
	}

	public ComboView1(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mTextBound = new Rect();
		mRectList = new ArrayList<Rect>();
		mIconList = new ArrayList<Rect>();
		mWidth = context.getResources().getDisplayMetrics().widthPixels;
		mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_chart);
	}

	public void setList(ArrayList<TagBean> list) {
		if (list != null && !list.isEmpty()) {
			this.mBeanList = list;
			this.mItemCount = list.size();
			resetValidate();
		}
	}

	private void resetValidate() {
		mIconList.clear();
		mRectList.clear();

		if (mItemCount > 1)
			mPaddingWitdh = (mWidth - 2 * PARENT_WIDTH_PADDING - ITEM_WIDTH * mItemCount) / (mItemCount - 1);

		for (int i = 0; i < mItemCount; i ++) {
			int rank = (int)(mBeanList.get(i).getRank());
			float itemHeight ;
			if(rank == 0){
				itemHeight = 0.02f;
			}else{
//				itemHeight = (float) Math.pow((float) DEF_RANK / rank, (float) 1 / 4);
				itemHeight =  (float) mBeanList.get(i).getRank() / 100;
			}
			int left = PARENT_WIDTH_PADDING + i * (ITEM_WIDTH + mPaddingWitdh);
			int top = ITEM_HIGHT + PARENT_TOP_PADDING - (int) (ITEM_HIGHT * itemHeight);
			int right = left + ITEM_WIDTH;
			int bottom = ITEM_HIGHT + PARENT_TOP_PADDING;
			Rect rect = new Rect(left, top, right, bottom);
			mRectList.add(rect);

			Rect iconRect = new Rect(left - 20, top - 55, right + 20, top - 15);
			mIconList.add(iconRect);
		}

		invalidate();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mHeight = ITEM_HIGHT + PARENT_TOP_PADDING + PARENT_BOTTOM_PADDING + PARENT_TEXT / 2 ;
		setMeasuredDimension(mWidth, mHeight);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (mBeanList != null && !mBeanList.isEmpty()) {
			setChartBackground(canvas);
			setChartColumn(canvas);
			//            setChartLine(canvas);
			setChartText(canvas);
			//            setChartCircle(canvas);
			//            setChartBitmap(canvas);
			setChartValue(canvas);
		}
	}

	/**
	 * view 背景
	 * @param canvas
	 */
	private void setChartBackground(Canvas canvas) {
		mPaint.setColor(VIEW_COLOR);
		canvas.drawRect(0, 0, mWidth, mHeight, mPaint);
	}

	/**
	 * 矩形柱
	 */
	private void setChartColumn(Canvas canvas) {
		mPaint.setColor(COLUMN_BG_COLOR);
		for (int i = 0; i < mRectList.size(); i ++) {

			canvas.drawRect(
					mRectList.get(i).left,
					PARENT_TOP_PADDING,
					mRectList.get(i).right,
					ITEM_HIGHT + PARENT_TOP_PADDING,
					mPaint);


		}

		//				mPaint.setColor(COLUMN_COLOR);
		for (int i = 0; i < mRectList.size(); i ++) {
			if(i % 2 == 0){
				mPaint.setColor(COLUMN_COLOR);
			}else{
				mPaint.setColor(getResources().getColor(R.color.black));
			}
			canvas.drawRect(mRectList.get(i), mPaint);
		}
	}

	/**
	 * 连接线
	 * @param canvas
	 */
	private void setChartLine(Canvas canvas) {
		if (mRectList.size() > 1) {
			mPaint.setColor(COLUMN_COLOR);
			for (int i = 0; i < mRectList.size() - 1; i ++) {
				float startX = mRectList.get(i).left + (float) ITEM_WIDTH / 2;
				float startY = mRectList.get(i).top;
				float stopX = mRectList.get(i + 1).left + (float) ITEM_WIDTH / 2;
				float stopY = mRectList.get(i + 1).top;
				canvas.drawLine(startX, startY, stopX, stopY, mPaint);
			}
		}
	}

	/**
	 * 画小圆
	 * @param canvas
	 */
	private void setChartCircle(Canvas canvas) {
		mPaint.setColor(COLUMN_COLOR);
		for (int i = 0; i < mRectList.size(); i ++) {
			canvas.drawCircle(
					mRectList.get(i).left + (float) ITEM_WIDTH / 2,
					mRectList.get(i).top,
					15,
					mPaint);// 小圆
		}
	}

	/**
	 * 画图型
	 * @param canvas
	 */
	private void setChartBitmap(Canvas canvas) {
		mPaint.setColor(COLUMN_COLOR);
		for (int i = 0; i < mRectList.size(); i ++) {
			canvas.drawBitmap(mBitmap, null, mIconList.get(i), mPaint);
		}
	}

	/**
	 * 画数字
	 * @param canvas
	 */
	private void setChartValue(Canvas canvas) {
		mPaint.setColor(VALUE_COLOR);
		mPaint.setTextSize(18);
		for (int i = 0; i < mRectList.size(); i ++) {
			String value = mBeanList.get(i).getRank() + "";
			Rect iconRect = mIconList.get(i);
			mPaint.getTextBounds(value, 0, value.length(), mTextBound);
			canvas.drawText(
					value,
					iconRect.left + iconRect.width() / 2 - mTextBound.width() / 2 - 2,
					iconRect.bottom - mTextBound.height() + 5,
					mPaint);
		}
	}

	/**
	 * 文字
	 * @param canvas
	 */
	private void setChartText(Canvas canvas) {
		mPaint.setColor(COLUMN_COLOR);
		mPaint.setTextSize(20);
		for (int i = 0; i < mRectList.size(); i ++) {
			String value = mBeanList.get(i).getTag_name();
			Rect iconRect = mIconList.get(i);
			mPaint.getTextBounds(value, 0, value.length(), mTextBound);
			int x = iconRect.left + iconRect.width() / 2 - mTextBound.width() / 2 - 2;
			int y = ITEM_HIGHT + PARENT_TOP_PADDING + PARENT_TEXT;

			canvas.rotate(-45, x, y);//文字倾斜
			canvas.drawText(value, x, y, mPaint);
			canvas.rotate(45, x, y);
		}
	}
}
