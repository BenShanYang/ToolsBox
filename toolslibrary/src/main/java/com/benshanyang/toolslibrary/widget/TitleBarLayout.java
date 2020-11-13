package com.benshanyang.toolslibrary.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import java.lang.reflect.Field;

/**
 * @ClassName: TitleBar
 * @Description: 标题栏父布局
 * @Author: 杨宽
 * @Date: 2019/2/16 0016 15:04
 */
public class TitleBarLayout extends FrameLayout {

    public TitleBarLayout(@NonNull Context context) {
        super(context);
        setPadding(0, 0, 0, 0);
    }

    public TitleBarLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setPadding(0, 0, 0, 0);
    }

    public TitleBarLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setPadding(0, 0, 0, 0);
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            super.setPadding(left, top + getStatusBarHeight(getContext()), right, bottom);
        } else {
            super.setPadding(left, top, right, bottom);
        }
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    private int getStatusBarHeight(Context context) {
        if (TextUtils.equals(Build.MANUFACTURER.toLowerCase(), "xiaomi")) {
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                return context.getResources().getDimensionPixelSize(resourceId);
            }
            return 0;
        }
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            if (x > 0) {
                return context.getResources().getDimensionPixelSize(x);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
