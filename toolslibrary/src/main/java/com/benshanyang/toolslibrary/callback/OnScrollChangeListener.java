package com.benshanyang.toolslibrary.callback;

import android.support.v4.widget.NestedScrollView;

/**
 * @ClassName: OnScrollChangeListener
 * @Description: NestedScrollView滚动监听器
 * @Author: YangKuan
 * @Date: 2020/11/12 14:17
 */
public abstract class OnScrollChangeListener implements NestedScrollView.OnScrollChangeListener {

    private int[] contentLocation = new int[2];//NestedScrollView的直接子布局在Window中的位置
    private int[] scrollViewLocation = new int[2];//NestedScrollView在Window中的位置
    private int scrollDistance = 0;//滚动过的距离

    /**
     * Called when the scroll position of a view changes.
     *
     * @param v          The view whose scroll position has changed.
     * @param scrollX    Current horizontal scroll origin.
     * @param scrollY    Current vertical scroll origin.
     * @param oldScrollX Previous horizontal scroll origin.
     * @param oldScrollY Previous vertical scroll origin.
     */
    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        v.getChildAt(0).getLocationInWindow(contentLocation);
        v.getLocationInWindow(scrollViewLocation);
        scrollDistance = scrollViewLocation[1] - contentLocation[1];

        onScrollChange(v, scrollX, scrollY, oldScrollX, oldScrollY, scrollDistance);
    }

    /**
     * Called when the scroll position of a view changes.
     *
     * @param v              The view whose scroll position has changed.
     * @param scrollX        Current horizontal scroll origin.
     * @param scrollY        Current vertical scroll origin.
     * @param oldScrollX     Previous horizontal scroll origin.
     * @param oldScrollY     Previous vertical scroll origin.
     * @param scrollDistance 滚动过的距离
     */
    public abstract void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY, int scrollDistance);

}
