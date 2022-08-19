package com.test.rebuildintroduction.utils;


import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;

/**
 * 大屏幕动画工具类-布局
 * @author sm2886
 */
public class LayoutAnimator {

    /**
     * 监听动画变化并改变view高度
     */
    public static class LayoutHeightUpdateListener implements ValueAnimator.AnimatorUpdateListener {

        private final View mView;

        public LayoutHeightUpdateListener(View view) {
            mView = view;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            final ViewGroup.LayoutParams lp = mView.getLayoutParams();
            lp.height = (int) animation.getAnimatedValue();
            mView.setLayoutParams(lp);
        }

    }

    /**
     * 使用ValueAnimator.ofInt()生成一系列高度值，然后添加上面的监听，实现展开动画
     *
     * @param view  View对象
     * @param start 起始
     * @param end   结束
     * @return
     */

    public static Animator ofHeight(View view, int start, int end) {
        final ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new LayoutHeightUpdateListener(view));
        return animator;
    }
}

