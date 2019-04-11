package com.benshanyang.toolslibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.benshanyang.toolslibrary.R;
import com.benshanyang.toolslibrary.drawable.BorderTextViewDrawable;

/**
 * 类描述: 带分割线的TextView </br>
 * 时间: 2019/3/30 20:46
 *
 * @author YangKuan
 * @version 1.0.0
 * @since
 */
public class BorderTextView extends AppCompatTextView {

    @ColorInt
    private int backgroundColor = Color.TRANSPARENT;//背景色
    @ColorInt
    private int borderColor = Color.TRANSPARENT;//边框颜色 优先于单个设置边框颜色
    @ColorInt
    private int topBorderNormalColor = Color.TRANSPARENT;//设置上边框未点击时的颜色
    @ColorInt
    private int topBorderPressedColor = Color.TRANSPARENT;//设置上边框点击时的颜色
    @ColorInt
    private int bottomBorderNormalColor = Color.TRANSPARENT;//设置下边框未点击时的颜色
    @ColorInt
    private int bottomBorderPressedColor = Color.TRANSPARENT;//设置下边框点击时的颜色
    private float topBorderWidth = 0f;//上边框的粗细
    private float bottomBorderWidth = 0f;//下边框的粗细
    private float topBorderLeftSpace = 0f;//上边框距离左侧的距离
    private float topBorderRightSpace = 0f;//上边框距离右侧的距离
    private float bottomBorderLeftSpace = 0f;//下边框距离左侧的距离
    private float bottomBorderRightSpace = 0f;//下边框距离右侧的距离

    private BorderTextViewDrawable bgdrawable;//背景样式

    public BorderTextView(Context context) {
        super(context);
        initSettings(context);
    }

    public BorderTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        initSettings(context);
    }

    public BorderTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initSettings(context);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BorderTextView, 0, 0);
        if (typedArray != null) {
            for (int i = 0; i < typedArray.getIndexCount(); i++) {
                int attr = typedArray.getIndex(i);
                if (attr == R.styleable.BorderTextView_backgroundColor) {
                    //背景色
                    backgroundColor = typedArray.getColor(attr, Color.TRANSPARENT);
                } else if (attr == R.styleable.BorderTextView_borderColor) {
                    //分割线颜色
                    borderColor = typedArray.getColor(attr, Color.TRANSPARENT);
                } else if (attr == R.styleable.BorderTextView_topBorderNormalColor) {
                    //上分割线未点击时的颜色
                    topBorderNormalColor = typedArray.getColor(attr, Color.TRANSPARENT);
                } else if (attr == R.styleable.BorderTextView_topBorderPressedColor) {
                    //上分割线点击时的颜色
                    topBorderPressedColor = typedArray.getColor(attr, Color.TRANSPARENT);
                } else if (attr == R.styleable.BorderTextView_bottomBorderNormalColor) {
                    //底部分割线未点击时的颜色
                    bottomBorderNormalColor = typedArray.getColor(attr, Color.TRANSPARENT);
                } else if (attr == R.styleable.BorderTextView_bottomBorderPressedColor) {
                    //底部分割线点击时的颜色
                    bottomBorderPressedColor = typedArray.getColor(attr, Color.TRANSPARENT);
                } else if (attr == R.styleable.BorderTextView_topBorderWidth) {
                    //上分割线的粗细
                    topBorderWidth = typedArray.getDimension(attr, 0f);
                } else if (attr == R.styleable.BorderTextView_bottomBorderWidth) {
                    //下分割线的粗细
                    bottomBorderWidth = typedArray.getDimension(attr, 0f);
                } else if (attr == R.styleable.BorderTextView_topBorderLeftSpace) {
                    //上分割线距最左侧的距离
                    topBorderLeftSpace = typedArray.getDimension(attr, 0f);
                } else if (attr == R.styleable.BorderTextView_topBorderRightSpace) {
                    //上分割线距最右侧的距离
                    topBorderRightSpace = typedArray.getDimension(attr, 0f);
                } else if (attr == R.styleable.BorderTextView_bottomBorderLeftSpace) {
                    //下分割线距最左侧的距离
                    bottomBorderLeftSpace = typedArray.getDimension(attr, 0f);
                } else if (attr == R.styleable.BorderTextView_bottomBorderRightSpace) {
                    //下分割线距最右侧的距离
                    bottomBorderRightSpace = typedArray.getDimension(attr, 0f);
                }
            }
            typedArray.recycle();
        }
    }

    private void initSettings(Context context) {
        bgdrawable = new BorderTextViewDrawable();
        bgdrawable.setTopBorderWidth(topBorderWidth);
        bgdrawable.setBottomBorderWidth(bottomBorderWidth);
        bgdrawable.setTopBorderLeftSpace(topBorderLeftSpace);
        bgdrawable.setTopBorderRightSpace(topBorderRightSpace);
        bgdrawable.setBottomBorderLeftSpace(bottomBorderLeftSpace);
        bgdrawable.setBottomBorderRightSpace(bottomBorderRightSpace);
        bgdrawable.setBackgroundColor(backgroundColor);
        bgdrawable.setBottomBorderColor(borderColor != Color.TRANSPARENT ? borderColor : bottomBorderNormalColor);
        bgdrawable.setTopBorderColor(borderColor != Color.TRANSPARENT ? borderColor : topBorderNormalColor);

        setBackground(bgdrawable);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();//获取控件的宽度
        int height = getMeasuredHeight();//获取控件的高度
        if (bgdrawable != null) {
            bgdrawable.setWidth(width);
            bgdrawable.setHeight(height);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isClickable() && borderColor == Color.TRANSPARENT) {
            int listener = event.getAction();
            if (listener == MotionEvent.ACTION_DOWN) {
                //按下
                bgdrawable.setTopBorderColor(topBorderPressedColor);
                bgdrawable.setBottomBorderColor(bottomBorderPressedColor);
            } else if (listener == MotionEvent.ACTION_MOVE) {
                //移动
                bgdrawable.setTopBorderColor(topBorderPressedColor);
                bgdrawable.setBottomBorderColor(bottomBorderPressedColor);
            } else {
                bgdrawable.setTopBorderColor(topBorderNormalColor);
                bgdrawable.setBottomBorderColor(bottomBorderNormalColor);
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 设置背景色
     *
     * @param backgroundColor 背景色
     */
    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor) {
        if (this.backgroundColor != backgroundColor && bgdrawable != null) {
            this.backgroundColor = backgroundColor;
            bgdrawable.setBackgroundColor(backgroundColor);
        }
    }

    /**
     * 设置上下边框的颜色
     *
     * @param borderColor 上下边框的颜色
     */
    public void setBorderColor(int borderColor) {
        if (this.borderColor != borderColor && bgdrawable != null) {
            this.borderColor = borderColor;
            bgdrawable.setBottomBorderColor(borderColor);
            bgdrawable.setTopBorderColor(borderColor);
        }
    }

    /**
     * 设置上边框未点击时的颜色
     *
     * @param topBorderNormalColor 上边框未点击时的颜色
     */
    public void setTopBorderNormalColor(int topBorderNormalColor) {
        if (this.topBorderNormalColor != topBorderNormalColor && bgdrawable != null) {
            this.topBorderNormalColor = topBorderNormalColor;
            if (borderColor == Color.TRANSPARENT) {
                bgdrawable.setTopBorderColor(topBorderNormalColor);
            }
        }
    }

    /**
     * 设置下边框未点击时的颜色
     *
     * @param bottomBorderNormalColor 下边框未点击时的颜色
     */
    public void setBottomBorderNormalColor(int bottomBorderNormalColor) {
        if (this.bottomBorderNormalColor != bottomBorderNormalColor) {
            this.bottomBorderNormalColor = bottomBorderNormalColor;
            if (borderColor == Color.TRANSPARENT) {
                bgdrawable.setBottomBorderColor(bottomBorderNormalColor);
            }
        }
    }

    /**
     * 设置上边框点击时的颜色
     *
     * @param topBorderPressedColor 上边框点击时的颜色
     */
    public void setTopBorderPressedColor(int topBorderPressedColor) {
        if (this.topBorderPressedColor != topBorderPressedColor) {
            this.topBorderPressedColor = topBorderPressedColor;
        }
    }

    /**
     * 设置下边框点击时的颜色
     *
     * @param bottomBorderPressedColor 下边框点击时的颜色
     */
    public void setBottomBorderPressedColor(int bottomBorderPressedColor) {
        if (this.bottomBorderPressedColor != bottomBorderPressedColor) {
            this.bottomBorderPressedColor = bottomBorderPressedColor;
        }
    }

    /**
     * 上边框的宽度
     *
     * @param topBorderWidth 上边框的宽度(边框粗细)
     */
    public void setTopBorderWidth(float topBorderWidth) {
        if (this.topBorderWidth != topBorderWidth) {
            this.topBorderWidth = topBorderWidth;
            if (bgdrawable != null) {
                bgdrawable.setTopBorderWidth(topBorderWidth);
            }
        }
    }

    /**
     * 下边框的宽度
     *
     * @param bottomBorderWidth 下边框的宽度(边框粗细)
     */
    public void setBottomBorderWidth(float bottomBorderWidth) {
        if (this.bottomBorderWidth != bottomBorderWidth) {
            this.bottomBorderWidth = bottomBorderWidth;
            if (bgdrawable != null) {
                bgdrawable.setBottomBorderWidth(bottomBorderWidth);
            }
        }
    }

    /**
     * 设置上边框距离左侧的距离
     *
     * @param topBorderLeftSpace 上边框距离左侧的距离
     */
    public void setTopBorderLeftSpace(float topBorderLeftSpace) {
        if (this.topBorderLeftSpace != topBorderLeftSpace) {
            this.topBorderLeftSpace = topBorderLeftSpace;
            if (bgdrawable != null) {
                bgdrawable.setTopBorderLeftSpace(topBorderLeftSpace);
            }
        }
    }

    /**
     * 设置上边框距离右侧的距离
     *
     * @param topBorderRightSpace
     */
    public void setTopBorderRightSpace(float topBorderRightSpace) {
        if (this.topBorderRightSpace != topBorderRightSpace) {
            this.topBorderRightSpace = topBorderRightSpace;
            if (bgdrawable != null) {
                bgdrawable.setTopBorderRightSpace(topBorderRightSpace);
            }
        }
    }

    /**
     * 设置下边框距离左侧的距离
     *
     * @param bottomBorderLeftSpace 下边框距离左侧的距离
     */
    public void setBottomBorderLeftSpace(float bottomBorderLeftSpace) {
        if (this.bottomBorderLeftSpace != bottomBorderLeftSpace) {
            this.bottomBorderLeftSpace = bottomBorderLeftSpace;
            if (bgdrawable != null) {
                bgdrawable.setBottomBorderLeftSpace(bottomBorderLeftSpace);
            }
        }
    }

    /**
     * 设置下边框距离右侧的距离
     *
     * @param bottomBorderRightSpace 下边框距离右侧的距离
     */
    public void setBottomBorderRightSpace(float bottomBorderRightSpace) {
        if (this.bottomBorderRightSpace != bottomBorderRightSpace) {
            this.bottomBorderRightSpace = bottomBorderRightSpace;
            if (bgdrawable != null) {
                bgdrawable.setBottomBorderRightSpace(bottomBorderRightSpace);
            }
        }
    }
}
