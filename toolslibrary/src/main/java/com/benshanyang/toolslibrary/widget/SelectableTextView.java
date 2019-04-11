package com.benshanyang.toolslibrary.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.benshanyang.toolslibrary.R;
import com.benshanyang.toolslibrary.drawable.SelectableTextViewDrawable;
import com.benshanyang.toolslibrary.utils.DensityUtils;
import com.benshanyang.toolslibrary.utils.ResUtils;
import com.benshanyang.toolslibrary.utils.TextUtils;

import static android.util.TypedValue.COMPLEX_UNIT_PX;

/**
 * 类描述: 可选择编辑的自定义TextView </br>
 * 时间: 2019/4/2 11:29
 *
 * @author YangKuan
 * @version 1.0.0
 * @since
 */
public class SelectableTextView extends LinearLayout {

    private TextView tvTitle;//标题文字控件
    private TextView tvContent;//内容文字控件
    private SelectableTextViewDrawable drawable;//背景Drawable
    private ColorStateList titleTextColor;//标题文字颜色
    private ColorStateList contentTextColor;//内容文字颜色
    private Drawable titleIcon = null;//左侧标题的Icon
    private Drawable contentLeftIcon = null;//内容文字左侧图标
    private Drawable contentRightIcon = null;//内容文字右侧图标

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
    private int maxLines = 0;//最多行数

    private boolean isSingleLine = false;//是否单行

    private float titleContentSpace = 0f;//标题内容的间距
    private float titleDrawablePadding = 0f;//标题文字图标的间距
    private float contentDrawablePadding = 0f;//内容文字图标的间距
    private float titleTextSize = 14f;//标题文字大小
    private float contentTextSize = 14f;//内容文字大小
    private float topBorderWidth = 0f;//上边框的粗细
    private float bottomBorderWidth = 0f;//下边框的粗细
    private float topBorderLeftSpace = 0f;//上边框距离左侧的距离
    private float topBorderRightSpace = 0f;//上边框距离右侧的距离
    private float bottomBorderLeftSpace = 0f;//下边框距离左侧的距离
    private float bottomBorderRightSpace = 0f;//下边框距离右侧的距离

    private String titleStr = "";//标题文字
    private String contentStr = "";//内容文字

    public SelectableTextView(Context context) {
        super(context);
        initWidget(context);
        initSettings(context);
    }

    public SelectableTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initWidget(context);
        initAttrs(context, attrs);
        initSettings(context);
    }

    public SelectableTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWidget(context);
        initAttrs(context, attrs);
        initSettings(context);
    }

    private void initWidget(Context context) {
        tvTitle = new TextView(context);
        tvContent = new TextView(context);

        tvTitle.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        tvContent.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);

        addView(tvTitle, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
        addView(tvContent, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        drawable = new SelectableTextViewDrawable();
        setBackground(drawable);
        setOrientation(HORIZONTAL);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SelectableTextView, 0, 0);
        if (typedArray != null) {
            for (int i = 0; i < typedArray.getIndexCount(); i++) {
                int attr = typedArray.getIndex(i);
                if (attr == R.styleable.SelectableTextView_titleIcon) {
                    //标题的Icon
                    titleIcon = typedArray.getDrawable(attr);
                } else if (attr == R.styleable.SelectableTextView_contentRightIcon) {
                    //内容左侧的Icon
                    contentRightIcon = typedArray.getDrawable(attr);
                } else if (attr == R.styleable.SelectableTextView_contentLeftIcon) {
                    //内容右侧的Icon
                    contentLeftIcon = typedArray.getDrawable(attr);
                } else if (attr == R.styleable.SelectableTextView_titleDrawablePadding) {
                    //标题文字图标的间距
                    titleDrawablePadding = typedArray.getDimension(attr, 0f);
                } else if (attr == R.styleable.SelectableTextView_contentDrawablePadding) {
                    //内容文字图标的间距
                    contentDrawablePadding = typedArray.getDimension(attr, 0f);
                } else if (attr == R.styleable.SelectableTextView_titleContentSpace) {
                    //标题和内容的间距
                    titleContentSpace = typedArray.getDimension(attr, 0f);
                } else if (attr == R.styleable.SelectableTextView_titleTextSize) {
                    //标题文字的大小
                    titleTextSize = typedArray.getDimension(attr, 14f);
                } else if (attr == R.styleable.SelectableTextView_titleTextColor) {
                    //标题文字的颜色
                    titleTextColor = typedArray.getColorStateList(attr);
                } else if (attr == R.styleable.SelectableTextView_titleText) {
                    //标题文字
                    titleStr = typedArray.getString(attr);
                } else if (attr == R.styleable.SelectableTextView_contentTextSize) {
                    //内容文字的大小
                    contentTextSize = typedArray.getDimension(attr, 14f);
                } else if (attr == R.styleable.SelectableTextView_contentTextColor) {
                    //内容文字的颜色
                    contentTextColor = typedArray.getColorStateList(attr);
                } else if (attr == R.styleable.SelectableTextView_contentText) {
                    //内容文字
                    contentStr = typedArray.getString(attr);
                } else if (attr == R.styleable.SelectableTextView_singleLine) {
                    //内容文字是否单行
                    isSingleLine = typedArray.getBoolean(attr, false);
                } else if (attr == R.styleable.SelectableTextView_maxLines) {
                    //内容是否多行
                    maxLines = typedArray.getInt(attr, 0);
                } else if (attr == R.styleable.SelectableTextView_backgroundColor) {
                    //背景色
                    backgroundColor = typedArray.getColor(attr, Color.TRANSPARENT);
                } else if (attr == R.styleable.SelectableTextView_borderColor) {
                    //分割线颜色
                    borderColor = typedArray.getColor(attr, Color.TRANSPARENT);
                } else if (attr == R.styleable.SelectableTextView_topBorderNormalColor) {
                    //上分割线未点击时的颜色
                    topBorderNormalColor = typedArray.getColor(attr, Color.TRANSPARENT);
                } else if (attr == R.styleable.SelectableTextView_topBorderPressedColor) {
                    //上分割线点击时的颜色
                    topBorderPressedColor = typedArray.getColor(attr, Color.TRANSPARENT);
                } else if (attr == R.styleable.SelectableTextView_bottomBorderNormalColor) {
                    //底部分割线未点击时的颜色
                    bottomBorderNormalColor = typedArray.getColor(attr, Color.TRANSPARENT);
                } else if (attr == R.styleable.SelectableTextView_bottomBorderPressedColor) {
                    //底部分割线点击时的颜色
                    bottomBorderPressedColor = typedArray.getColor(attr, Color.TRANSPARENT);
                } else if (attr == R.styleable.SelectableTextView_topBorderWidth) {
                    //上分割线的粗细
                    topBorderWidth = typedArray.getDimension(attr, 0f);
                } else if (attr == R.styleable.SelectableTextView_bottomBorderWidth) {
                    //下分割线的粗细
                    bottomBorderWidth = typedArray.getDimension(attr, 0f);
                } else if (attr == R.styleable.SelectableTextView_topBorderLeftSpace) {
                    //上分割线距最左侧的距离
                    topBorderLeftSpace = typedArray.getDimension(attr, 0f);
                } else if (attr == R.styleable.SelectableTextView_topBorderRightSpace) {
                    //上分割线距最右侧的距离
                    topBorderRightSpace = typedArray.getDimension(attr, 0f);
                } else if (attr == R.styleable.SelectableTextView_bottomBorderLeftSpace) {
                    //下分割线距最左侧的距离
                    bottomBorderLeftSpace = typedArray.getDimension(attr, 0f);
                } else if (attr == R.styleable.SelectableTextView_bottomBorderRightSpace) {
                    //下分割线距最右侧的距离
                    bottomBorderRightSpace = typedArray.getDimension(attr, 0f);
                }
            }
            typedArray.recycle();
        }
    }

    private void initSettings(Context context) {
        if (drawable != null) {
            drawable.setTopBorderWidth(topBorderWidth);
            drawable.setBottomBorderWidth(bottomBorderWidth);
            drawable.setTopBorderLeftSpace(topBorderLeftSpace);
            drawable.setTopBorderRightSpace(topBorderRightSpace);
            drawable.setBottomBorderLeftSpace(bottomBorderLeftSpace);
            drawable.setBottomBorderRightSpace(bottomBorderRightSpace);
            drawable.setBackgroundColor(backgroundColor);
            drawable.setBottomBorderColor(borderColor != Color.TRANSPARENT ? borderColor : bottomBorderNormalColor);
            drawable.setTopBorderColor(borderColor != Color.TRANSPARENT ? borderColor : topBorderNormalColor);
        }

        if (tvTitle != null) {
            tvTitle.setTextSize(DensityUtils.px2sp(context, titleTextSize));
            TextUtils.setDrawable(tvTitle, titleStr, titleIcon, (int) titleDrawablePadding, com.benshanyang.toolslibrary.constant.Gravity.LEFT);
            if (titleTextColor != null) {
                tvTitle.setTextColor(titleTextColor);
            }
        }

        if (tvContent != null) {
            ((LayoutParams) tvContent.getLayoutParams()).leftMargin = (int) titleContentSpace;
            tvContent.setTextSize(DensityUtils.px2sp(context, contentTextSize));
            TextUtils.setDrawable(tvContent, contentStr, (int) contentDrawablePadding, contentLeftIcon, null, contentRightIcon, null);
            if (isSingleLine) {
                tvContent.setSingleLine(isSingleLine);
                tvContent.setEllipsize(android.text.TextUtils.TruncateAt.END);
            } else {
                if (maxLines > 0) {
                    tvContent.setMaxLines(maxLines);
                    tvContent.setEllipsize(android.text.TextUtils.TruncateAt.END);
                }
            }

            if (contentTextColor != null) {
                tvContent.setTextColor(contentTextColor);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();//获取控件的宽度
        int height = getMeasuredHeight();//获取控件的高度
        if (drawable != null) {
            drawable.setWidth(width);
            drawable.setHeight(height);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isClickable() && borderColor == Color.TRANSPARENT) {
            int listener = event.getAction();
            if (listener == MotionEvent.ACTION_DOWN) {
                //按下
                drawable.setTopBorderColor(topBorderPressedColor);
                drawable.setBottomBorderColor(bottomBorderPressedColor);
            } else if (listener == MotionEvent.ACTION_MOVE) {
                //移动
                drawable.setTopBorderColor(topBorderPressedColor);
                drawable.setBottomBorderColor(bottomBorderPressedColor);
            } else {
                drawable.setTopBorderColor(topBorderNormalColor);
                drawable.setBottomBorderColor(bottomBorderNormalColor);
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 设置标题和Icon的距离
     *
     * @param drawable        标题Icon
     * @param drawablePadding 标题和Icon的距离
     */
    public void setTitleIcon(Drawable drawable, int drawablePadding) {
        this.titleIcon = drawable;
        TextUtils.setDrawable(tvTitle, drawable, drawablePadding, com.benshanyang.toolslibrary.constant.Gravity.LEFT);
    }

    /**
     * 设置标题和Icon的距离
     *
     * @param resId           图片资源Id
     * @param drawablePadding 标题和Icon的距离
     */
    public void setTitleIcon(@DrawableRes int resId, int drawablePadding) {
        this.titleIcon = ResUtils.getDrawable(getContext(), resId);
        setTitleIcon(titleIcon, drawablePadding);
    }

    /**
     * 设置内容和Icon的距离
     *
     * @param leftDrawable    内容左侧的Icon
     * @param rightDrawable   内容右侧的Icon
     * @param drawablePadding 内容和Icon的距离
     */
    public void setContentIcon(Drawable leftDrawable, Drawable rightDrawable, int drawablePadding) {
        this.contentLeftIcon = leftDrawable;
        this.contentRightIcon = rightDrawable;
        TextUtils.setDrawable(tvContent, drawablePadding, leftDrawable, null, rightDrawable, null);
    }

    /**
     * 设置内容和Icon的距离
     *
     * @param leftResId       左侧图片资源Id
     * @param rightResId      右侧图片资源Id
     * @param drawablePadding 内容和Icon的距离
     */
    public void setContentIcon(@DrawableRes int leftResId, @DrawableRes int rightResId, int drawablePadding) {
        this.contentLeftIcon = ResUtils.getDrawable(getContext(), leftResId);
        this.contentRightIcon = ResUtils.getDrawable(getContext(), rightResId);
        setContentIcon(contentLeftIcon, contentRightIcon, drawablePadding);
    }

    /**
     * 内容和标题的距离
     *
     * @param space 内容和标题的距离
     */
    public void setTitleContentSpace(int space) {
        if (tvContent != null) {
            ((LayoutParams) tvContent.getLayoutParams()).leftMargin = space;
        }
    }

    /**
     * 设置内容是否单行显示
     *
     * @param isSingleLine 是否是单行显示
     */
    public void setSingleLine(boolean isSingleLine) {
        this.isSingleLine = isSingleLine;
        if (tvContent != null) {
            tvContent.setSingleLine(isSingleLine);
            tvContent.setEllipsize(android.text.TextUtils.TruncateAt.END);
        }
    }

    /**
     * 设置内容文字最多显示的行数
     *
     * @param lines 行数
     */
    public void setMaxLines(int lines) {
        this.maxLines = lines;
        if (tvContent != null && !isSingleLine) {
            tvContent.setMaxLines(lines);
            tvContent.setEllipsize(android.text.TextUtils.TruncateAt.END);
            //tvContent.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    /**
     * 设置标题文字
     *
     * @param title 标题内容
     */
    public void setTitle(CharSequence title) {
        if (tvTitle != null && !TextUtils.isEmpty(title)) {
            this.titleStr = title.toString();
            tvTitle.setText(title);
        }
    }

    /**
     * 设置内容
     *
     * @param content 内容文字
     */
    public void setContent(CharSequence content) {
        if (tvContent != null && !TextUtils.isEmpty(content)) {
            this.contentStr = content.toString();
            tvContent.setText(content);
        }
    }

    /**
     * 获取标题文字
     * @return
     */
    public CharSequence getTitle() {
        if (tvTitle != null) {
            return tvTitle.getText();
        }
        return "";
    }

    /**
     * 获取内容文字
     * @return
     */
    public CharSequence getContent() {
        if (tvContent != null) {
            return tvContent.getText();
        }
        return "";
    }

    /**
     * 设置标题文字颜色
     *
     * @param textColor 标题文字颜色
     */
    public void setTitleTextColor(@ColorInt int textColor) {
        if (tvTitle != null) {
            titleTextColor = ColorStateList.valueOf(textColor);
            tvTitle.setTextColor(titleTextColor);
        }
    }

    /**
     * 设置内容文字颜色
     *
     * @param textColor 内容文字颜色
     */
    public void setContentTextColor(@ColorInt int textColor) {
        if (tvContent != null) {
            contentTextColor = ColorStateList.valueOf(textColor);
            tvContent.setTextColor(contentTextColor);
        }
    }

    /**
     * 设置标题文字颜色
     *
     * @param textColor 标题文字颜色
     */
    public void setTitleTextColor(ColorStateList textColor) {
        if (tvTitle != null && textColor != null) {
            this.titleTextColor = textColor;
            tvTitle.setTextColor(titleTextColor);
        }
    }

    /**
     * 设置内容文字颜色
     *
     * @param textColor 内容文字颜色
     */
    public void setContentTextColor(ColorStateList textColor) {
        if (tvContent != null && textColor != null) {
            this.contentTextColor = textColor;
            tvContent.setTextColor(contentTextColor);
        }
    }

    /**
     * 设置标题文字大小
     *
     * @param textSize 标题文字字号
     */
    public void setTitleTextSize(int textSize) {
        if (tvTitle != null) {
            tvTitle.setTextSize(COMPLEX_UNIT_PX, textSize);
        }
    }

    /**
     * 设置内容文字大小
     *
     * @param textSize 内容文字字号
     */
    public void setContentTextSize(int textSize) {
        if (tvContent != null) {
            tvContent.setTextSize(COMPLEX_UNIT_PX, textSize);
        }
    }

    /**
     * 设置标题文字大小
     *
     * @param unit     单位
     * @param textSize 字号
     */
    public void setTitleTextSize(int unit, int textSize) {
        if (tvTitle != null) {
            tvTitle.setTextSize(unit, textSize);
        }
    }

    /**
     * 设置内容文字大小
     *
     * @param unit     单位
     * @param textSize 字号
     */
    public void setContentTextSize(int unit, int textSize) {
        if (tvContent != null) {
            tvContent.setTextSize(unit, textSize);
        }
    }

    /**
     * 设置背景色
     *
     * @param backgroundColor 背景色
     */
    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor) {
        if (this.backgroundColor != backgroundColor && drawable != null) {
            this.backgroundColor = backgroundColor;
            drawable.setBackgroundColor(backgroundColor);
        }
    }

    /**
     * 设置上下边框的颜色
     *
     * @param borderColor 上下边框的颜色
     */
    public void setBorderColor(int borderColor) {
        if (this.borderColor != borderColor && drawable != null) {
            this.borderColor = borderColor;
            drawable.setBottomBorderColor(borderColor);
            drawable.setTopBorderColor(borderColor);
        }
    }

    /**
     * 设置上边框未点击时的颜色
     *
     * @param topBorderNormalColor 上边框未点击时的颜色
     */
    public void setTopBorderNormalColor(int topBorderNormalColor) {
        if (this.topBorderNormalColor != topBorderNormalColor && drawable != null) {
            this.topBorderNormalColor = topBorderNormalColor;
            if (borderColor == Color.TRANSPARENT) {
                drawable.setTopBorderColor(topBorderNormalColor);
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
                drawable.setBottomBorderColor(bottomBorderNormalColor);
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
            if (drawable != null) {
                drawable.setTopBorderWidth(topBorderWidth);
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
            if (drawable != null) {
                drawable.setBottomBorderWidth(bottomBorderWidth);
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
            if (drawable != null) {
                drawable.setTopBorderLeftSpace(topBorderLeftSpace);
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
            if (drawable != null) {
                drawable.setTopBorderRightSpace(topBorderRightSpace);
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
            if (drawable != null) {
                drawable.setBottomBorderLeftSpace(bottomBorderLeftSpace);
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
            if (drawable != null) {
                drawable.setBottomBorderRightSpace(bottomBorderRightSpace);
            }
        }
    }

}
