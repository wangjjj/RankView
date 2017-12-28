package com.cocoon.jay;

import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;


public class PbAdapter extends RecyclerView.Adapter<PbAdapter.ViewHolder>{

    private Context mContext;
    private LayoutInflater mInflater;
    private List<TagBean> mDatas;


    public PbAdapter(Context context, List<TagBean> datas){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this. mDatas = datas;

    }


    @Override
    public int getItemCount() {
        return (mDatas== null) ? 0 : mDatas.size();
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_progress_bar, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        setPropertyAnimation(holder.progressBar, (int)mDatas.get(position).getRank());
        holder.title.setText(mDatas.get(position).getTag_name());
        holder.value.setText(String.valueOf(mDatas.get(position).getRank()));
    }



    @SuppressLint("NewApi")
    private void setPropertyAnimation(final ProgressBar progressbar, int progress) {
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            ValueAnimator anim = ObjectAnimator.ofInt(0, progress);
            anim.setDuration(1000l);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @SuppressLint("NewApi")
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if(animation!=null && animation.getAnimatedValue()!= null)
                        progressbar.setProgress((Integer)animation.getAnimatedValue());
                }
            });
            anim.setEvaluator(new IntEvaluator());
            anim.start();
        } else {
            progressbar.setProgress(progress);
        }
    }



    static class ViewHolder extends RecyclerView.ViewHolder{

        private ProgressBar progressBar;
        private TextView title;
        private TextView value;

        public ViewHolder(View view){
            super(view);
            progressBar =  view.findViewById(R.id.progressBar);
            title =  view.findViewById(R.id.title);
            value =  view.findViewById(R.id.value);
        }
    }

}
