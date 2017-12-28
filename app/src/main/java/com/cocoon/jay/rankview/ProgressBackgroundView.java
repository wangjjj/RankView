package com.cocoon.jay.rankview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class ProgressBackgroundView extends View {

    private Paint paint = new Paint();
    private int color = Color.BLACK;
    private float rate = 0.75f;
    private float animRate = 0;
    private Orientation orientation = Orientation.RIGHT;

    private float startX, stopX, width, height;

    public ProgressBackgroundView(Context context) {
        super(context);
    }

    public ProgressBackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProgressBackgroundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(color);
        paint.setAntiAlias(true);

        if (orientation == Orientation.LEFT) {
            startX = getMeasuredWidth();
            stopX = startX - getMeasuredWidth() * animRate;
        } else {
            startX = 0;
            stopX = getMeasuredWidth() * animRate;
        }

        width = height;
        if (width <= 0) {
            width = getMeasuredHeight();
        }
        paint.setStrokeWidth((int) width);
        canvas.drawLine(startX, width / 2, stopX, width / 2, paint);
    }

    public void setRate(float rate) {
        if (rate >= 1.0f) {
            rate = 1.0f;
        }
        this.rate = rate;
        if (Config.ANIMABLE){
            PBVAnimation animation = new PBVAnimation();
            animation.setDuration(1000l);
            this.startAnimation(animation);
        }else {
            setAnimRate(rate);
        }
    }

    private void setAnimRate(float animRate) {
        this.animRate = animRate;
    }

    private class PBVAnimation extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            setAnimRate(interpolatedTime * rate);
            postInvalidate();
        }
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
