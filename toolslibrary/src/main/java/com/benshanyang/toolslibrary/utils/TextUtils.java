package com.benshanyang.toolslibrary.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.benshanyang.toolslibrary.constant.Gravity;

/**
 * 类描述: 字符串处理工具类 </br>
 * 时间: 2019/3/20 10:52
 *
 * @author YangKuan
 * @version 1.0.0
 * @since
 */
public class TextUtils {

    /**
     * 判断字符串是否为空
     *
     * @param str 输入的内容
     * @return
     */
    public static boolean isEmpty(CharSequence str) {
        return (str == null || str.length() == 0);
    }

    /**
     * 判断字符串是否为空
     *
     * @param str    字符串
     * @param isTrim 是否将前后空格也算做内容
     * @return
     */
    public static boolean isEmpty(CharSequence str, boolean isTrim) {
        if (isTrim) {
            return (str == null || str.toString().trim().length() == 0);
        } else {
            return isEmpty(str);
        }
    }

    /**
     * 判断两个字符串是否相等
     *
     * @param a 字符串a
     * @param b 字符串b
     * @return
     */
    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 为TextView设置Drawable图片
     *
     * @param textView
     * @param charSequence 设置的文字
     * @param resId        图片的资源id
     * @param gravity      位置
     */
    public static void setDrawable(TextView textView, CharSequence charSequence, int resId, @Gravity int gravity) {
        setDrawable(textView, charSequence, resId, 0, gravity);
    }

    /**
     * 为TextView设置Drawable图片
     *
     * @param textView
     * @param charSequence    设置的文字
     * @param resId           图片的资源id
     * @param drawablePadding 文字和Icon的距离
     * @param gravity         位置
     */
    public static void setDrawable(TextView textView, CharSequence charSequence, int resId, int drawablePadding, @Gravity int gravity) {
        if (textView != null) {
            Context context = textView.getContext();
            Drawable imgDrawable = context.getResources().getDrawable(resId);//获取资源图片
            setDrawable(textView, imgDrawable, drawablePadding, gravity);
            textView.setText(isEmpty(charSequence) ? "" : charSequence);
        }
    }

    /**
     * 为TextView设置Drawable图片
     *
     * @param textView
     * @param charSequence 设置的文字
     * @param imgDrawable  图片
     * @param gravity      位置
     */
    public static void setDrawable(TextView textView, CharSequence charSequence, Drawable imgDrawable, @Gravity int gravity) {
        setDrawable(textView, charSequence, imgDrawable, 0, gravity);
    }

    /**
     * 为TextView设置Drawable图片
     *
     * @param textView
     * @param charSequence    设置的文字
     * @param imgDrawable     图片
     * @param drawablePadding 文字和Icon的距离
     * @param gravity         位置
     */
    public static void setDrawable(TextView textView, CharSequence charSequence, Drawable imgDrawable, int drawablePadding, @Gravity int gravity) {
        if (textView != null) {
            setDrawable(textView, imgDrawable, drawablePadding, gravity);
            textView.setText(isEmpty(charSequence) ? "" : charSequence);
        }
    }

    /**
     * 为TextView设置Drawable图片
     *
     * @param textView
     * @param resId    图片的资源id
     * @param gravity  位置
     */
    public static void setDrawable(TextView textView, int resId, @Gravity int gravity) {
        setDrawable(textView, resId, 0, gravity);
    }

    /**
     * 为TextView设置Drawable图片
     *
     * @param textView
     * @param resId           图片的资源id
     * @param drawablePadding 图片和文字的间距
     * @param gravity         位置
     */
    public static void setDrawable(TextView textView, int resId, int drawablePadding, @Gravity int gravity) {
        if (textView != null) {
            Context context = textView.getContext();
            Drawable imgDrawable = context.getResources().getDrawable(resId);//获取资源图片

            setDrawable(textView, imgDrawable, drawablePadding, gravity);
        }
    }

    /**
     * 为TextView设置Drawable图片
     *
     * @param textView
     * @param imgDrawable 图片
     * @param gravity     位置
     */
    public static void setDrawable(TextView textView, Drawable imgDrawable, @Gravity int gravity) {
        setDrawable(textView, imgDrawable, 0, gravity);
    }

    /**
     * 为TextView设置Drawable图片
     *
     * @param textView
     * @param imgDrawable     图片
     * @param drawablePadding 图片和文字的间距
     * @param gravity         位置
     */
    public static void setDrawable(TextView textView, Drawable imgDrawable, int drawablePadding, @Gravity int gravity) {
        if (textView != null) {
            switch (gravity) {
                case Gravity.LEFT:
                    //左侧icon
                    textView.setCompoundDrawablesWithIntrinsicBounds(imgDrawable, null, null, null);
                    break;
                case Gravity.TOP:
                    //上边icon
                    textView.setCompoundDrawablesWithIntrinsicBounds(null, imgDrawable, null, null);
                    break;
                case Gravity.RIGHT:
                    //右侧icon
                    textView.setCompoundDrawablesWithIntrinsicBounds(null, null, imgDrawable, null);
                    break;
                case Gravity.BOTTOM:
                    //下边icon
                    textView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, imgDrawable);
                    break;
            }
            textView.setCompoundDrawablePadding(drawablePadding);
        }
    }

    /**
     * 为TextView设置Drawable图片
     *
     * @param textView
     * @param drawablePadding 图片和文字的间距
     * @param leftDrawable    左图片
     * @param topDrawable     上图片
     * @param rightDrawable   右图片
     * @param bottomDrawable  下图片
     */
    public static void setDrawable(TextView textView, int drawablePadding, Drawable leftDrawable, Drawable topDrawable, Drawable rightDrawable, Drawable bottomDrawable) {
        setDrawable(textView, "", drawablePadding, leftDrawable, topDrawable, rightDrawable, bottomDrawable);
    }

    /**
     * 为TextView设置Drawable图片
     *
     * @param textView
     * @param charSequence    文字内容
     * @param drawablePadding 图片和文字的间距
     * @param leftDrawable    左图片
     * @param topDrawable     上图片
     * @param rightDrawable   右图片
     * @param bottomDrawable  下图片
     */
    public static void setDrawable(TextView textView, CharSequence charSequence, int drawablePadding, Drawable leftDrawable, Drawable topDrawable, Drawable rightDrawable, Drawable bottomDrawable) {
        if (textView != null) {
            textView.setText(isEmpty(charSequence) ? "" : charSequence);
            textView.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, topDrawable, rightDrawable, bottomDrawable);
            textView.setCompoundDrawablePadding(drawablePadding);
        }
    }

}
