package com.benshanyang.toolslibrary.callback;

import android.support.annotation.ColorInt;

/**
 * 类描述: 设置边框的接口 </br>
 * 时间: 2019/3/30 11:25
 *
 * @author YangKuan
 * @since
 */
public interface BorderDrawable {

    void setHeight(float height);

    void setWidth(float width);

    void setBorderColor(@ColorInt int borderColor);

    void setBackgroundColor(@ColorInt int backgroundColor);

    void setRadius(float radius);

    void setBorderWidth(float borderWidth);

    void isShowBorder(boolean isShow);

}
