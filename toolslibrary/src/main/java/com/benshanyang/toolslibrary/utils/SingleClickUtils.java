package com.benshanyang.toolslibrary.utils;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.view.View;
import android.view.ViewGroup;

/**
 * 类描述: 按钮的去重点击工具类 </br>
 * 时间: 2019/4/11 9:15
 *
 * @author YangKuan
 * @version 1.0.0
 * @since
 */
public class SingleClickUtils {

    public static int INTERVAL = 500;  // 快速点击间隔

    /**
     * 控件去重复点击
     *
     * @param parent 父布局
     * @param ids    控件的id数组
     * @param o      点击事件的回调接口
     */
    public static void onClick(ViewGroup parent, @IdRes int[] ids, View.OnClickListener o) {
        for (int i = 0; i < ids.length; i++) {
            onClick(parent, ids[i], o);
        }
    }

    /**
     * 控件去重复点击
     *
     * @param parent 父布局
     * @param id     控件的id
     * @param o      点击事件的回调接口
     */
    public static void onClick(ViewGroup parent, @IdRes int id, View.OnClickListener o) {
        View view = parent.findViewById(id);
        onClick(view, o);
    }

    /**
     * 控件去重复点击
     *
     * @param parent   父布局
     * @param ids      控件的id数组
     * @param interval 两次点击的时间间隔(毫秒)
     * @param o        点击事件的回调接口
     */
    public static void onClick(ViewGroup parent, @IdRes int[] ids, long interval, View.OnClickListener o) {
        for (int i = 0; i < ids.length; i++) {
            onClick(parent, ids[i], interval, o);
        }
    }

    /**
     * 控件去重复点击
     *
     * @param parent   父布局
     * @param id       控件的id
     * @param interval 两次点击的时间间隔(毫秒)
     * @param o        点击事件的回调接口
     */
    public static void onClick(ViewGroup parent, @IdRes int id, long interval, View.OnClickListener o) {
        View view = parent.findViewById(id);
        onClick(view, interval, o);
    }

    /**
     * 控件去重复点击
     *
     * @param activity 所在的Activity
     * @param ids      控件的id数组
     * @param o        点击事件的回调接口
     */
    public static void onClick(Activity activity, @IdRes int[] ids, View.OnClickListener o) {
        for (int i = 0; i < ids.length; i++) {
            onClick(activity, ids[i], o);
        }
    }

    /**
     * 控件去重复点击
     *
     * @param activity 所在的Activity
     * @param id       控件的id
     * @param o        点击事件的回调接口
     */
    public static void onClick(Activity activity, @IdRes int id, View.OnClickListener o) {
        View view = activity.findViewById(id);
        onClick(view, o);
    }

    /**
     * 控件去重复点击
     *
     * @param activity 所在的Activity
     * @param ids      控件的id数组
     * @param inteval  两次点击的时间间隔(毫秒)
     * @param o        点击事件的回调接口
     */
    public static void onClick(Activity activity, @IdRes int[] ids, long inteval, View.OnClickListener o) {
        for (int i = 0; i < ids.length; i++) {
            onClick(activity, ids[i], inteval, o);
        }
    }

    /**
     * 控件去重复点击
     *
     * @param activity 所在的Activity
     * @param id       控件的id
     * @param interval 两次点击的时间间隔(毫秒)
     * @param o        点击事件的回调接口
     */
    public static void onClick(Activity activity, @IdRes int id, long interval, View.OnClickListener o) {
        View view = activity.findViewById(id);
        onClick(view, interval, o);
    }

    /**
     * 控件去重复点击
     *
     * @param views 控件数组
     * @param o     点击事件
     */
    public static void onClick(View[] views, View.OnClickListener o) {
        for (int i = 0; i < views.length; i++) {
            onClick(views[i], o);
        }
    }

    /**
     * 控件去重复点击
     *
     * @param view 控件
     * @param o    点击事件
     */
    public static void onClick(View view, final View.OnClickListener o) {
        if (view != null) {

            view.setOnClickListener(new View.OnClickListener() {

                private long lastClickTime = 0L;

                @Override
                public void onClick(View v) {
                    long clickTime = System.currentTimeMillis();
                    if (clickTime - lastClickTime >= INTERVAL) {
                        lastClickTime = clickTime;
                        if (o != null) {
                            o.onClick(v);
                        }
                    }
                }
            });

        }
    }

    /**
     * 控件去重复点击
     *
     * @param views    控件数组
     * @param interval 两次点击的时间间隔(毫秒)
     * @param o        点击事件
     */
    public static void onClick(View[] views, long interval, View.OnClickListener o) {
        for (int i = 0; i < views.length; i++) {
            onClick(views[i], interval, o);
        }
    }

    /**
     * 控件去重复点击
     *
     * @param view     控件
     * @param interval 两次点击的时间间隔(毫秒)
     * @param o        点击事件
     */
    public static void onClick(View view, final long interval, final View.OnClickListener o) {
        if (view != null) {

            view.setOnClickListener(new View.OnClickListener() {

                private long lastClickTime = 0L;

                @Override
                public void onClick(View v) {
                    long clickTime = System.currentTimeMillis();
                    if (clickTime - lastClickTime >= (interval >= 0 ? interval : INTERVAL)) {
                        lastClickTime = clickTime;
                        if (o != null) {
                            o.onClick(v);
                        }
                    }
                }
            });

        }
    }

}
