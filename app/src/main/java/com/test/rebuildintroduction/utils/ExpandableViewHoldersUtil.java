package com.test.rebuildintroduction.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.test.rebuildintroduction.R;
import com.test.rebuildintroduction.databinding.FragmentT2sBinding;


/**
 * 大屏幕展开工具类
 *
 * @author sm2886
 */

public class ExpandableViewHoldersUtil {

    /**
     * 图片旋转动画
     *
     * @param mImage ImageView
     * @param from   起始
     * @param to     终止
     */
    public static void rotateExpandIcon(final ImageView mImage, float from, float to) {

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);
        valueAnimator.setDuration(500);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(valueAnimator1 -> mImage.setRotation((Float) valueAnimator1.getAnimatedValue()));
        valueAnimator.start();
    }

    /**
     * 列表展开动画
     * <p>
     * View.VISIBLE     View可见
     * View.INVISIBLE   View不可以见，但仍然占据可见时的大小和位置。
     * View.GONE        View不可见，且不占据空间。
     *
     * @param holder     ViewHolder
     * @param expandView 待展开
     * @param animate    标志位，true则有动画效果
     */
    public static void openH(final RecyclerView.ViewHolder holder, final View expandView, final boolean animate) {
        if (animate) {
            expandView.setVisibility(View.VISIBLE);
            /* 动画 */
            final Animator animator = ViewHolderAnimator.ofItemViewHeight(holder);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    /* 从透明到不透明 */
                    final ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(expandView, View.ALPHA, 1);
                    alphaAnimator.addListener(new ViewHolderAnimator.ViewHolderAnimatorListener(holder));
                    alphaAnimator.start();
                }
            });
            animator.start();
        } else {
            expandView.setVisibility(View.VISIBLE);
            expandView.setAlpha(1);
        }
    }

    /**
     * 关闭
     *
     * @param holder     ViewHolder
     * @param expandView 待关闭
     * @param animate    标志位，true则有动画效果
     */
    public static void closeH(final RecyclerView.ViewHolder holder, final View expandView, final boolean animate) {
        if (animate) {
            expandView.setVisibility(View.GONE);
            // TODO 布局动画 动画效果 属性动画翻转
            final Animator animator = ViewHolderAnimator.ofItemViewHeight(holder);
            expandView.setVisibility(View.VISIBLE);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    expandView.setVisibility(View.GONE);
                    expandView.setAlpha(0);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    expandView.setVisibility(View.GONE);
                    expandView.setAlpha(0);
                }
            });
            animator.start();

        } else {
            expandView.setVisibility(View.GONE);
            expandView.setAlpha(0);
        }
    }

    /**
     * 图片渐隐渐现
     *
     * @param binding databinding
     * @param start   开始位置
     * @param end     结束位置
     */
    public static void imageAnimation(FragmentT2sBinding binding, int start, int end) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(start, end);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setDuration(830);
        binding.backgroundImage.startAnimation(alphaAnimation);
    }


    public interface Expandable {
        /**
         * 获取展开部分view
         *
         * @return view对象
         */
        View getExpandView();
    }

    /**
     * 对adapter当中的ViewHolder进行操作
     *
     * @param <VH> ViewHolder范型 Expandable是一个返回展开部分的接口
     */
    public static class KeepOneH<VH extends RecyclerView.ViewHolder & Expandable> {

        private int expandedPosition = -1;

        /**
         * Adapter onBindViewHolder 调用
         * 绑定控件
         *
         * @param holder ViewHolder
         * @param pos    下标
         */
        public void bind(VH holder, int pos) {
            if (pos == expandedPosition) {
                ExpandableViewHoldersUtil.openH(holder, holder.getExpandView(), false);
            } else {
                ExpandableViewHoldersUtil.closeH(holder, holder.getExpandView(), false);
            }
        }

        /**
         * Item点击事件
         *
         * @param holder    ViewHolder
         * @param imageView imageView
         */
        @SuppressWarnings("unchecked")
        public void toggle(VH holder, ImageView imageView) {
            if (expandedPosition == holder.getAdapterPosition()) {
                /* 点击的item已经展开，关闭，并置为-1*/
                expandedPosition = -1;
                ExpandableViewHoldersUtil.closeH(holder, holder.getExpandView(), true);
                ExpandableViewHoldersUtil.rotateExpandIcon(imageView, 180f, 0f);
            } else {
                /* 点击的item未展开，展开并关闭已经打开的，open位 置为点击位 */
                int previous = expandedPosition;
                expandedPosition = holder.getAdapterPosition();
                ExpandableViewHoldersUtil.openH(holder, holder.getExpandView(), true);
                ExpandableViewHoldersUtil.rotateExpandIcon(imageView, 0f, 180f);
                /* 关闭之前打开的item */
                final VH oldHolder = (VH) ((RecyclerView) holder.itemView.getParent()).findViewHolderForAdapterPosition(previous);
                if (oldHolder != null) {
                    ExpandableViewHoldersUtil.closeH(oldHolder, oldHolder.getExpandView(), true);
                    ExpandableViewHoldersUtil.rotateExpandIcon(oldHolder.itemView.findViewById(R.id.iv_1), 180f, 0f);
                }
            }
        }
    }
}
