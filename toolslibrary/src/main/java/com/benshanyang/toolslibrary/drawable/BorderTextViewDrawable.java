package com.benshanyang.toolslibrary.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * 类描述: TextView分割线Drawable </br>
 * 时间: 2019/4/1 13:39
 *
 * @author YangKuan
 * @version 1.0.0
 * @since
 */
public class BorderTextViewDrawable extends Drawable {

    private Paint paint;//画笔
    private float width = 0f;//宽度
    private float height = 0f;//高度
    private float topBorderWidth = 0f;//上边框宽度
    private float bottomBorderWidth = 0f;//下边框宽度
    private float topBorderLeftSpace = 0f;//上边框距离左侧的距离
    private float topBorderRightSpace = 0f;//上边框距离右侧的距离
    private float bottomBorderLeftSpace = 0f;//上边框距离左侧的距离
    private float bottomBorderRightSpace = 0f;//下边框距离右侧的距离
    @ColorInt
    private int backgroundColor = Color.TRANSPARENT;//背景色
    @ColorInt
    private int topBorderColor = Color.TRANSPARENT;//上边框颜色
    @ColorInt
    private int bottomBorderColor = Color.TRANSPARENT;//下边框颜色

    public BorderTextViewDrawable() {
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
     * 设置上边框的宽度
     *
     * @param topBorderWidth 边框的粗细值
     */
    public void setTopBorderWidth(float topBorderWidth) {
        if (this.topBorderWidth != topBorderWidth) {
            this.topBorderWidth = topBorderWidth;
            invalidateSelf();
        }
    }

    /**
     * 设置下边框的宽度
     *
     * @param bottomBorderWidth 边框的粗细值
     */
    public void setBottomBorderWidth(float bottomBorderWidth) {
        if (this.bottomBorderWidth != bottomBorderWidth) {
            this.bottomBorderWidth = bottomBorderWidth;
            invalidateSelf();
        }
    }

    /**
     * 上边框距离左侧的距离
     *
     * @param topBorderLeftSpace 上边框距离左侧的距离
     */
    public void setTopBorderLeftSpace(float topBorderLeftSpace) {
        if (this.topBorderLeftSpace != topBorderLeftSpace) {
            this.topBorderLeftSpace = topBorderLeftSpace;
            invalidateSelf();
        }
    }

    /**
     * 上边框距离右侧的距离
     *
     * @param topBorderRightSpace 上边框距离右侧的距离
     */
    public void setTopBorderRightSpace(float topBorderRightSpace) {
        if (this.topBorderRightSpace != topBorderRightSpace) {
            this.topBorderRightSpace = topBorderRightSpace;
            invalidateSelf();
        }
    }

    /**
     * 上边框距离左侧的距离
     *
     * @param bottomBorderLeftSpace 上边框距离左侧的距离
     */
    public void setBottomBorderLeftSpace(float bottomBorderLeftSpace) {
        if (this.bottomBorderLeftSpace != bottomBorderLeftSpace) {
            this.bottomBorderLeftSpace = bottomBorderLeftSpace;
            invalidateSelf();
        }
    }

    /**
     * 下边框距离右侧的距离
     *
     * @param bottomBorderRightSpace 下边框距离右侧的距离
     */
    public void setBottomBorderRightSpace(float bottomBorderRightSpace) {
        if (this.bottomBorderRightSpace != bottomBorderRightSpace) {
            this.bottomBorderRightSpace = bottomBorderRightSpace;
            invalidateSelf();
        }
    }

    /**
     * 设置背景色
     *
     * @param backgroundColor 背景色
     */
    public void setBackgroundColor(int backgroundColor) {
        if (this.backgroundColor != backgroundColor) {
            this.backgroundColor = backgroundColor;
            invalidateSelf();
        }
    }

    /**
     * 设置上边框颜色
     *
     * @param topBorderColor 上边框颜色
     */
    public void setTopBorderColor(int topBorderColor) {
        if (this.topBorderColor != topBorderColor) {
            this.topBorderColor = topBorderColor;
            invalidateSelf();
        }
    }

    /**
     * 设置下边框颜色
     *
     * @param bottomBorderColor 下边框颜色
     */
    public void setBottomBorderColor(int bottomBorderColor) {
        if (this.bottomBorderColor != bottomBorderColor) {
            this.bottomBorderColor = bottomBorderColor;
            invalidateSelf();
        }
    }

    @Override
    public void draw(@NonNull Canvas canvas) {

        //背景
        if (backgroundColor != Color.TRANSPARENT) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(backgroundColor);
            canvas.save();
            canvas.drawRect(0, 0, width, height, paint);
            canvas.restore();
        }

        //上边框
        if (topBorderColor != Color.TRANSPARENT && topBorderWidth > 0) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(topBorderColor);
            paint.setStrokeWidth(topBorderWidth);
            canvas.save();
            canvas.drawLine(topBorderLeftSpace, topBorderWidth/2, width - topBorderRightSpace, topBorderWidth/2, paint);
            canvas.restore();
        }

        //下边框
        if (bottomBorderColor != Color.TRANSPARENT && bottomBorderWidth > 0) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(bottomBorderColor);
            paint.setStrokeWidth(bottomBorderWidth);
            canvas.save();
            canvas.drawLine(bottomBorderLeftSpace, height-bottomBorderWidth/2, width - bottomBorderRightSpace, height-bottomBorderWidth/2, paint);
            canvas.restore();
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
