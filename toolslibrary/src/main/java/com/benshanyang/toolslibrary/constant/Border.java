package com.benshanyang.toolslibrary.constant;

import android.support.annotation.IntDef;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 类描述: 设置密码输入框的下边线是否显示的工具类 </br>
 * 时间: 2019/3/20 10:49
 *
 * @author YangKuan
 * @version 1.0.0
 * @since
 */
@IntDef({Border.VISIBLE, Border.INVISIBLE, Border.GONE})
@Retention(RetentionPolicy.SOURCE)
public @interface Border {
    /** 显示 */
    int VISIBLE = View.VISIBLE;
    /** 占位隐藏 */
    int INVISIBLE = View.INVISIBLE;
    /** 不占位隐藏 */
    int GONE = View.GONE;
}
