package com.benshanyang.toolslibrary.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 类描述: 密码输入框右侧功能按钮的类型 </br>
 * 时间: 2019/3/20 10:46
 *
 * @author YangKuan
 * @version 1.0.0
 * @since
 */
@IntDef({PWDActionType.CLEAR_PASSWORD,PWDActionType.PASSWORD_CLEAR})
@Retention(RetentionPolicy.SOURCE)
public @interface PWDActionType {
    /** 清除按钮 - 密码显示隐藏按钮 */
    int CLEAR_PASSWORD = 0;
    /** 密码显示隐藏按钮 - 清除按钮 */
    int PASSWORD_CLEAR = 1;
}
