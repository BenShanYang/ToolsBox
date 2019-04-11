package com.benshanyang.toolslibrary.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.benshanyang.toolslibrary.callback.BorderDrawable;

/**
 * 类描述: 圆角按钮的背景边框样式 </br>
 * 时间: 2019/3/22 13:52
 *
 * @author YangKuan
 * @version 1.0.0
 * @since
 */
public class RoundedButtonDrawable extends Drawable implements BorderDrawable {

    private Paint paint;//画笔
    private float borderWidth = 0;//边框宽度
    private float width = 0f;//宽度
    private float height = 0f;//高度
    private float radius = 0f;//圆角矩形半径
    private boolean isShowBorder = false;//默认不显示边框
    @ColorInt
    private int borderColor = Color.TRANSPARENT;//边框颜色
    @ColorInt
    private int backgroundColor = Color.TRANSPARENT;//背景色

    public RoundedButtonDrawable() {
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
    public void setBorderColor(@ColorInt int borderColor) {
        if (this.borderColor != borderColor) {
            this.borderColor = borderColor;
            invalidateSelf();
        }
    }

    /**
     * 设置背景色
     *
     * @param backgroundColor 颜色值
     */
    public void setBackgroundColor(@ColorInt int backgroundColor) {
        if (this.backgroundColor != backgroundColor) {
            this.backgroundColor = backgroundColor;
            invalidateSelf();
        }
    }

    /**
     * 设置圆角半径
     *
     * @param radius 半径值
     */
    public void setRadius(float radius) {
        if (this.radius != radius) {
            this.radius = radius;
            invalidateSelf();
        }
    }

    /**
     * 设置边框的宽度
     *
     * @param borderWidth 边框的宽度值
     */
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
        RectF bgRect = new RectF(borderWidth / 2.0f, borderWidth / 2.0f, width - borderWidth / 2.0f, height - borderWidth / 2.0f);
        canvas.drawRoundRect(bgRect, radius, radius, paint);

        if (isShowBorder) {
            Path borderPath = new Path();
            RectF borderRect = new RectF(borderWidth / 2.0f, borderWidth / 2.0f, width - borderWidth / 2.0f, height - borderWidth / 2.0f);
            borderPath.addRoundRect(borderRect, radius, radius, Path.Direction.CCW);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(borderColor);
            paint.setStrokeWidth(borderWidth);
            canvas.drawPath(borderPath, paint);
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
