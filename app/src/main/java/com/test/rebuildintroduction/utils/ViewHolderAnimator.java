package com.test.rebuildintroduction.utils;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 大屏幕动画工具类2-item
 * @author sm2886
 */
public class ViewHolderAnimator {

    public static class ViewHolderAnimatorListener extends AnimatorListenerAdapter {
        private final RecyclerView.ViewHolder mHolder;

        /* 监听动画在开始、结束和取消状态下是否可以被回收 */
        public ViewHolderAnimatorListener(RecyclerView.ViewHolder holder) {
            mHolder = holder;
        }

        /* 开始 */
        @Override
        public void onAnimationStart(Animator animation) {
            mHolder.setIsRecyclable(false);
        }

        /* 结束 */
        @Override
        public void onAnimationEnd(Animator animation) {
            mHolder.setIsRecyclable(true);
        }

        /* 取消 */
        @Override
        public void onAnimationCancel(Animator animation) {
            mHolder.setIsRecyclable(true);
        }
    }

    /**
     * 设定在动画结束后View的宽度和高度分别为match_parent,warp_content
     */
    public static class LayoutParamsAnimatorListener extends AnimatorListenerAdapter {
        private final View mView;
        private final int mParamsWidth;
        private final int mParamsHeight;

        public LayoutParamsAnimatorListener(View view, int paramsWidth, int paramsHeight) {
            mView = view;
            mParamsWidth = paramsWidth;
            mParamsHeight = paramsHeight;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            final ViewGroup.LayoutParams params = mView.getLayoutParams();
            params.width = mParamsWidth;
            params.height = mParamsHeight;
            mView.setLayoutParams(params);
        }
    }

    /**
     * 动画具体操作
     *
     * @param holder ViewHolder对象
     * @return 动画
     */
    public static Animator ofItemViewHeight(RecyclerView.ViewHolder holder) {
        View parent = (View) holder.itemView.getParent();
        if (parent == null) {
            throw new IllegalStateException("Cannot animate the layout of a view that has no parent");
        }

        /* 测量起始时高度和结束时高度 */
        int start = holder.itemView.getMeasuredHeight();
        holder.itemView.measure(View.MeasureSpec.makeMeasureSpec(parent.getMeasuredWidth(), View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        int end = holder.itemView.getMeasuredHeight();

        /* 展开动画 */
        final Animator animator = com.test.rebuildintroduction.utils.LayoutAnimator.ofHeight(holder.itemView, start, end);
        /* 监听 */
        animator.addListener(new ViewHolderAnimatorListener(holder));
        /* 设定结束时item高度 */
        animator.addListener(new LayoutParamsAnimatorListener(holder.itemView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        return animator;
    }

}

