package com.benshanyang.toolslibrary.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 类描述: 描述位置的常量类 </br>
 * 时间: 2019/3/20 10:48
 *
 * @author YangKuan
 * @version 1.0.0
 * @since
 */
@IntDef({Gravity.LEFT, Gravity.TOP, Gravity.RIGHT, Gravity.BOTTOM,
        Gravity.CENTER, Gravity.CENTER_VERTICAL, Gravity.CENTER_HORIZONTAL})
@Retention(RetentionPolicy.SOURCE)
public @interface Gravity {
    /**
     * 左
     */
    int LEFT = 0;
    /**
     * 上
     */
    int TOP = 1;
    /**
     * 右
     */
    int RIGHT = 2;
    /**
     * 下
     */
    int BOTTOM = 3;
    /**
     * 中间
     */
    int CENTER = 4;
    /**
     * 上下居中
     */
    int CENTER_VERTICAL = 5;
    /**
     * 左右居中
     */
    int CENTER_HORIZONTAL = 6;
}
