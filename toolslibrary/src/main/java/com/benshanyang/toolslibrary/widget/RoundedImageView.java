package com.benshanyang.toolslibrary.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.benshanyang.toolslibrary.R;
import com.benshanyang.toolslibrary.utils.DensityUtils;

/**
 * 类描述: 圆角/圆形图片 </br>
 * 时间: 2019/3/21 15:30
 *
 * @author YangKuan
 * @version 1.0.0
 * @since
 */
public class RoundedImageView extends AppCompatImageView {
    private boolean isCoverSrc; // border、innerBorder是否覆盖图片
    private boolean isCircle; // 是否显示为圆形，如果为圆形则设置的corner无效
    private int borderWidth; // 边框宽度
    private int borderColor = Color.WHITE; // 边框颜色
    private int innerBorderWidth; // 内层边框宽度
    private int innerBorderColor = Color.WHITE; // 内层边框充色

    private int cornerRadius; // 统一设置圆角半径，优先级高于单独设置每个角的半径
    private int cornerTopLeftRadius; // 左上角圆角半径
    private int cornerTopRightRadius; // 右上角圆角半径
    private int cornerBottomLeftRadius; // 左下角圆角半径
    private int cornerBottomRightRadius; // 右下角圆角半径

    private int maskColor; // 遮罩层颜色

    private Xfermode xfermode;

    private int width;
    private int height;
    private float radius;

    private float[] borderRadii;
    private float[] srcRadii;

    private RectF srcRectF; // 图片占的矩形区域
    private RectF borderRectF; // 边框的矩形区域

    private Paint paint;
    private Path path; // 用来裁剪图片的ptah
    private Path srcPath; // 图片区域大小的path

    public RoundedImageView(Context context) {
        this(context, null);
    }

    public RoundedImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundedImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundedImageView, 0, 0);
        for (int i = 0; i < ta.getIndexCount(); i++) {
            int attr = ta.getIndex(i);
            if (attr == R.styleable.RoundedImageView_isCoverSrc) {
                isCoverSrc = ta.getBoolean(attr, isCoverSrc);// border、innerBorder是否覆盖图片
            } else if (attr == R.styleable.RoundedImageView_isCircle) {
                isCircle = ta.getBoolean(attr, isCircle);// 是否显示为圆形，如果为圆形则设置的corner无效
            } else if (attr == R.styleable.RoundedImageView_borderWidth) {
                borderWidth = ta.getDimensionPixelSize(attr, borderWidth);// 边框宽度
            } else if (attr == R.styleable.RoundedImageView_borderColor) {
                borderColor = ta.getColor(attr, borderColor);// 边框颜色
            } else if (attr == R.styleable.RoundedImageView_innerBorderWidth) {
                innerBorderWidth = ta.getDimensionPixelSize(attr, innerBorderWidth);// 内层边框宽度
            } else if (attr == R.styleable.RoundedImageView_innerBorderColor) {
                innerBorderColor = ta.getColor(attr, innerBorderColor);// 内层边框充色
            } else if (attr == R.styleable.RoundedImageView_cornerRadius) {
                cornerRadius = ta.getDimensionPixelSize(attr, cornerRadius);// 统一设置圆角半径，优先级高于单独设置每个角的半径
            } else if (attr == R.styleable.RoundedImageView_cornerTopLeftRadius) {
                cornerTopLeftRadius = ta.getDimensionPixelSize(attr, cornerTopLeftRadius);// 左上角圆角半径
            } else if (attr == R.styleable.RoundedImageView_cornerTopRightRadius) {
                cornerTopRightRadius = ta.getDimensionPixelSize(attr, cornerTopRightRadius);// 右上角圆角半径
            } else if (attr == R.styleable.RoundedImageView_cornerBottomLeftRadius) {
                cornerBottomLeftRadius = ta.getDimensionPixelSize(attr, cornerBottomLeftRadius);// 左下角圆角半径
            } else if (attr == R.styleable.RoundedImageView_cornerBottomRightRadius) {
                cornerBottomRightRadius = ta.getDimensionPixelSize(attr, cornerBottomRightRadius);// 右下角圆角半径
            } else if (attr == R.styleable.RoundedImageView_maskColor) {
                maskColor = ta.getColor(attr, maskColor);// 遮罩层颜色
            }
        }
        ta.recycle();

        borderRadii = new float[8];
        srcRadii = new float[8];

        borderRectF = new RectF();
        srcRectF = new RectF();

        paint = new Paint();
        path = new Path();

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1) {
            xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        } else {
            xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
            srcPath = new Path();
        }

        calculateRadii();
        clearInnerBorderWidth();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;

        initBorderRectF();
        initSrcRectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 使用图形混合模式来显示指定区域的图片
        canvas.saveLayer(srcRectF, null, Canvas.ALL_SAVE_FLAG);
        if (!isCoverSrc) {
            float sx = 1.0f * (width - 2 * borderWidth - 2 * innerBorderWidth) / width;
            float sy = 1.0f * (height - 2 * borderWidth - 2 * innerBorderWidth) / height;
            // 缩小画布，使图片内容不被borders覆盖
            canvas.scale(sx, sy, width / 2.0f, height / 2.0f);
        }
        super.onDraw(canvas);
        paint.reset();
        path.reset();
        if (isCircle) {
            path.addCircle(width / 2.0f, height / 2.0f, radius, Path.Direction.CCW);
        } else {
            path.addRoundRect(srcRectF, srcRadii, Path.Direction.CCW);
        }

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setXfermode(xfermode);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1) {
            canvas.drawPath(path, paint);
        } else {
            srcPath.addRect(srcRectF, Path.Direction.CCW);
            // 计算tempPath和path的差集
            srcPath.op(path, Path.Op.DIFFERENCE);
            canvas.drawPath(srcPath, paint);
        }
        paint.setXfermode(null);

        // 绘制遮罩
        if (maskColor != 0) {
            paint.setColor(maskColor);
            canvas.drawPath(path, paint);
        }
        // 恢复画布
        canvas.restore();
        // 绘制边框
        drawBorders(canvas);
    }

    private void drawBorders(Canvas canvas) {
        if (isCircle) {
            if (borderWidth > 0) {
                drawCircleBorder(canvas, borderWidth, borderColor, radius - borderWidth / 2.0f);
            }
            if (innerBorderWidth > 0) {
                drawCircleBorder(canvas, innerBorderWidth, innerBorderColor, radius - borderWidth - innerBorderWidth / 2.0f);
            }
        } else {
            if (borderWidth > 0) {
                drawRectFBorder(canvas, borderWidth, borderColor, borderRectF, borderRadii);
            }
        }
    }

    private void drawCircleBorder(Canvas canvas, int borderWidth, int borderColor, float radius) {
        initBorderPaint(borderWidth, borderColor);
        path.addCircle(width / 2.0f, height / 2.0f, radius, Path.Direction.CCW);
        canvas.drawPath(path, paint);
    }

    private void drawRectFBorder(Canvas canvas, int borderWidth, int borderColor, RectF rectF, float[] radii) {
        initBorderPaint(borderWidth, borderColor);
        path.addRoundRect(rectF, radii, Path.Direction.CCW);
        canvas.drawPath(path, paint);
    }

    private void initBorderPaint(int borderWidth, int borderColor) {
        path.reset();
        paint.setStrokeWidth(borderWidth);
        paint.setColor(borderColor);
        paint.setStyle(Paint.Style.STROKE);
    }

    /**
     * 计算外边框的RectF
     */
    private void initBorderRectF() {
        if (!isCircle) {
            borderRectF.set(borderWidth / 2.0f, borderWidth / 2.0f, width - borderWidth / 2.0f, height - borderWidth / 2.0f);
        }
    }

    /**
     * 计算图片原始区域的RectF
     */
    private void initSrcRectF() {
        if (isCircle) {
            radius = Math.min(width, height) / 2.0f;
            srcRectF.set(width / 2.0f - radius, height / 2.0f - radius, width / 2.0f + radius, height / 2.0f + radius);
        } else {
            srcRectF.set(0, 0, width, height);
            if (isCoverSrc) {
                srcRectF = borderRectF;
            }
        }
    }

    /**
     * 计算RectF的圆角半径
     */
    private void calculateRadii() {
        if (isCircle) {
            return;
        }
        if (cornerRadius > 0) {
            for (int i = 0; i < borderRadii.length; i++) {
                borderRadii[i] = cornerRadius;
                srcRadii[i] = cornerRadius - borderWidth / 2.0f;
            }
        } else {
            borderRadii[0] = borderRadii[1] = cornerTopLeftRadius;
            borderRadii[2] = borderRadii[3] = cornerTopRightRadius;
            borderRadii[4] = borderRadii[5] = cornerBottomRightRadius;
            borderRadii[6] = borderRadii[7] = cornerBottomLeftRadius;

            srcRadii[0] = srcRadii[1] = cornerTopLeftRadius - borderWidth / 2.0f;
            srcRadii[2] = srcRadii[3] = cornerTopRightRadius - borderWidth / 2.0f;
            srcRadii[4] = srcRadii[5] = cornerBottomRightRadius - borderWidth / 2.0f;
            srcRadii[6] = srcRadii[7] = cornerBottomLeftRadius - borderWidth / 2.0f;
        }
    }

    private void calculateRadiiAndRectF(boolean reset) {
        if (reset) {
            cornerRadius = 0;
        }
        calculateRadii();
        initBorderRectF();
        invalidate();
    }

    /**
     * 目前圆角矩形情况下不支持inner_border，需要将其置0
     */
    private void clearInnerBorderWidth() {
        if (!isCircle) {
            this.innerBorderWidth = 0;
        }
    }

    /**
     * 内边框和外边框是否覆盖图片
     *
     * @param isCoverSrc 覆盖标志位
     */
    public void isCoverSrc(boolean isCoverSrc) {
        this.isCoverSrc = isCoverSrc;
        initSrcRectF();
        invalidate();
    }

    /**
     * 是否显示为圆形, 如果为圆形则设置的corner无效
     *
     * @param isCircle 圆角标识位
     */
    public void isCircle(boolean isCircle) {
        this.isCircle = isCircle;
        clearInnerBorderWidth();
        initSrcRectF();
        invalidate();
    }

    /**
     * 设置外边框的边框宽度
     *
     * @param borderWidth 宽度值
     */
    public void setBorderWidth(int borderWidth) {
        this.borderWidth = DensityUtils.dp2px(getContext(), borderWidth);
        calculateRadiiAndRectF(false);
    }

    /**
     * 设置外边框的边框颜色
     *
     * @param borderColor 边框色值
     */
    public void setBorderColor(@ColorInt int borderColor) {
        this.borderColor = borderColor;
        invalidate();
    }

    /**
     * 设置内边框的边框宽度
     *
     * @param innerBorderWidth 宽度值
     */
    public void setInnerBorderWidth(int innerBorderWidth) {
        this.innerBorderWidth = DensityUtils.dp2px(getContext(), innerBorderWidth);
        clearInnerBorderWidth();
        invalidate();
    }

    /**
     * 设置内边框的边框颜色
     *
     * @param innerBorderColor 边框色值
     */
    public void setInnerBorderColor(@ColorInt int innerBorderColor) {
        this.innerBorderColor = innerBorderColor;
        invalidate();
    }

    /**
     * 设置圆角图片的统一圆角半径，优先级高于单独设置每个角的半径
     *
     * @param cornerRadius 圆角半径
     */
    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = DensityUtils.dp2px(getContext(), cornerRadius);
        calculateRadiiAndRectF(false);
    }

    /**
     * 设置图片左上圆角半径
     *
     * @param cornerTopLeftRadius 圆角半径
     */
    public void setCornerTopLeftRadius(int cornerTopLeftRadius) {
        this.cornerTopLeftRadius = DensityUtils.dp2px(getContext(), cornerTopLeftRadius);
        calculateRadiiAndRectF(true);
    }

    /**
     * 设置图片右上圆角半径
     *
     * @param cornerTopRightRadius 圆角半径
     */
    public void setCornerTopRightRadius(int cornerTopRightRadius) {
        this.cornerTopRightRadius = DensityUtils.dp2px(getContext(), cornerTopRightRadius);
        calculateRadiiAndRectF(true);
    }

    /**
     * 设置图片左下圆角半径
     *
     * @param cornerBottomLeftRadius 圆角半径
     */
    public void setCornerBottomLeftRadius(int cornerBottomLeftRadius) {
        this.cornerBottomLeftRadius = DensityUtils.dp2px(getContext(), cornerBottomLeftRadius);
        calculateRadiiAndRectF(true);
    }

    /**
     * 设置图片右下圆角半径
     *
     * @param cornerBottomRightRadius 圆角半径
     */
    public void setCornerBottomRightRadius(int cornerBottomRightRadius) {
        this.cornerBottomRightRadius = DensityUtils.dp2px(getContext(), cornerBottomRightRadius);
        calculateRadiiAndRectF(true);
    }

    /**
     * 遮罩层颜色
     *
     * @param maskColor 遮罩层颜色色值
     */
    public void setMaskColor(@ColorInt int maskColor) {
        this.maskColor = maskColor;
        invalidate();
    }
}
