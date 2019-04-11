package com.benshanyang.toolslibrary.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.benshanyang.toolslibrary.callback.BorderDrawable;

/**
 * 类描述: 底部分割线Drawable </br>
 * 时间: 2019/3/30 11:00
 *
 * @author YangKuan
 * @version 1.0.0
 * @since
 */
public class SimpleEditTextDrawable extends Drawable implements BorderDrawable {

    private Paint paint;//画笔
    private float borderWidth = 0;//边框宽度
    private float width = 0f;//宽度
    private float height = 0f;//高度
    private boolean isShowBorder = false;//默认不显示边框
    @ColorInt
    private int borderColor = Color.TRANSPARENT;//边框颜色
    @ColorInt
    private int backgroundColor = Color.TRANSPARENT;//背景色

    public SimpleEditTextDrawable() {
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
    }

    /**
     * 设置背景的宽
     *
     * @param width 宽度值
     */
    @Override
    public void setWidth(float width) {
        if (this.width != width) {
            this.width = width;
            invalidateSelf();
        }
    }

    /**
     * 设置背景的高
     *
     * @param height 宽度高
     */
    @Override
    public void setHeight(float height) {
        if (this.height != height) {
            this.height = height;
            invalidateSelf();
        }
    }

    /**
     * 设置边框的颜色
     *
     * @param borderColor 颜色值
     */
    @Override
    public void setBorderColor(@ColorInt int borderColor) {
        if (this.borderColor != borderColor) {
            this.borderColor = borderColor;
            invalidateSelf();
        }
    }

    /**
     * 设置背景色
     *
     * @param backgroundColor
     */
    @Override
    public void setBackgroundColor(int backgroundColor) {
        if (this.backgroundColor != backgroundColor) {
            this.backgroundColor = backgroundColor;
            invalidateSelf();
        }
    }

    @Override
    public void setRadius(float radius) {

    }

    /**
     * 设置边框的宽度
     *
     * @param borderWidth 边框的宽度值
     */
    @Override
    public void setBorderWidth(float borderWidth) {
        if (this.borderWidth != borderWidth) {
            this.borderWidth = borderWidth;
            invalidateSelf();
        }
    }

    /**
     * 是否显示边框
     *
     * @param isShow 边框显示标识位
     */
    @Override
    public void isShowBorder(boolean isShow) {
        if (this.isShowBorder != isShow) {
            this.isShowBorder = isShow;
            invalidateSelf();
        }
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(backgroundColor);
        RectF bgRect = new RectF(0, 0, width, height);
        canvas.drawRoundRect(bgRect, 0, 0, paint);

        if (isShowBorder) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(borderColor);
            paint.setStrokeWidth(borderWidth);
            canvas.drawLine(0, height, width, height, paint);
        } else {
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.TRANSPARENT);
            paint.setStrokeWidth(0);
            canvas.drawLine(0, 0, 0, 0, paint);
        }
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
