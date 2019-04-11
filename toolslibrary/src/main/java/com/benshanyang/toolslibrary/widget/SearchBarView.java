package com.benshanyang.toolslibrary.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.benshanyang.toolslibrary.R;
import com.benshanyang.toolslibrary.callback.TextWatchListener;
import com.benshanyang.toolslibrary.constant.Gravity;
import com.benshanyang.toolslibrary.drawable.SearchBarViewDrawable;
import com.benshanyang.toolslibrary.utils.DensityUtils;
import com.benshanyang.toolslibrary.utils.ResUtils;
import com.benshanyang.toolslibrary.utils.TextUtils;

import static android.util.TypedValue.COMPLEX_UNIT_PX;

/**
 * 类描述: 搜索框 </br>
 * 时间: 2019/4/8 17:24
 *
 * @author YangKuan
 * @version 1.0.0
 * @since
 */
public class SearchBarView extends LinearLayout {

    private EditText editText;
    private ImageButton imageButton;

    @ColorInt
    private int backgroundColor = Color.TRANSPARENT;//背景色 等级最高
    @ColorInt
    private int normalBackgroundColor = Color.TRANSPARENT;//未获取焦点时的背景色
    @ColorInt
    private int focusedBackgroundColor = Color.TRANSPARENT;//获取焦点时的背景色
    @ColorInt
    private int borderColor = Color.TRANSPARENT;//边框颜色
    @ColorInt
    private int normalBorderColor = Color.TRANSPARENT;//未获取焦点时的边框颜色
    @ColorInt
    private int focusedBorderColor = Color.TRANSPARENT;//获取焦点时的颜色
    private int maxLength = Integer.MAX_VALUE;//最大输入字数
    private boolean isShowBorder = false;//是否显示边框
    private boolean isShowActionButton = false;//是否显示最右侧的按钮
    private float textSize = 28f;//输入框文字字号
    private float editIconPadding;//左侧图片和文字的间距
    private float cornerRadius = 0f;//圆角半径
    private float borderWidth = 0f;//边框的宽度

    private String digits = "";//过滤条件
    private String hint = "";//提示文字
    private String text = "";//内容文字
    private ColorStateList textColor;//文字颜色
    private ColorStateList textColorHint;//提示文字颜色
    private Drawable actionIcon;//右侧功能按钮图片
    private Drawable editIcon;//左侧图片Icon

    private InputFilter inputFilter;//过滤器
    private SearchBarViewDrawable drawable;//背景样式
    private TextWatchListener textWatchListener;//输入监听
    private OnFocusChangeListener onFocusChangeListener;//输入框的焦点改变监听
    private OnTextChangedListener onTextChangedListener;//内容的改变监听

    public SearchBarView(Context context) {
        super(context);
        initWidget(context);
        initSetting(context);
    }

    public SearchBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initWidget(context);
        initAttr(context, attrs);
        initSetting(context);
    }

    public SearchBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWidget(context);
        initAttr(context, attrs);
        initSetting(context);
    }

    private void initWidget(Context context) {
        setOrientation(HORIZONTAL);

        editText = new EditText(context);
        imageButton = new ImageButton(context);

        editText.setPadding(0, 0, 0, 0);
        editText.setBackgroundColor(Color.TRANSPARENT);
        imageButton.setBackgroundColor(Color.TRANSPARENT);
        imageButton.setImageResource(R.drawable.ic_clear);

        LayoutParams editParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        editParams.weight = 1;
        addView(editText, editParams);
        addView(imageButton, new LayoutParams(DensityUtils.dp2px(context, 40), LayoutParams.MATCH_PARENT));
        setBackground(drawable = new SearchBarViewDrawable());
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SearchBarView, 0, 0);
        if (typedArray != null) {
            for (int i = 0; i < typedArray.getIndexCount(); i++) {
                int attr = typedArray.getIndex(i);
                if (attr == R.styleable.SearchBarView_hint) {
                    //提示文字
                    hint = typedArray.getString(attr);
                } else if (attr == R.styleable.SearchBarView_text) {
                    //设置内容文字
                    text = typedArray.getString(attr);
                } else if (attr == R.styleable.SearchBarView_textColor) {
                    //设置文字颜色
                    textColor = typedArray.getColorStateList(attr);
                } else if (attr == R.styleable.SearchBarView_textColorHint) {
                    //设置提示文字颜色
                    textColorHint = typedArray.getColorStateList(attr);
                } else if (attr == R.styleable.SearchBarView_textSize) {
                    //设置文字字号
                    textSize = typedArray.getDimension(attr, 28f);
                } else if (attr == R.styleable.SearchBarView_actionIcon) {
                    //右侧功能按钮图片
                    actionIcon = typedArray.getDrawable(attr);
                } else if (attr == R.styleable.SearchBarView_editIcon) {
                    //左侧图片Icon
                    editIcon = typedArray.getDrawable(attr);
                } else if (attr == R.styleable.SearchBarView_editIconPadding) {
                    //左侧图片和文字的间距
                    editIconPadding = typedArray.getDimension(attr, 0f);
                } else if (attr == R.styleable.SearchBarView_isShowBorder) {
                    //是否显示边框
                    isShowBorder = typedArray.getBoolean(attr, false);
                } else if (attr == R.styleable.SearchBarView_maxLength) {
                    //最大输入字数
                    maxLength = typedArray.getInt(attr, Integer.MAX_VALUE);
                } else if (attr == R.styleable.SearchBarView_digits) {
                    //输入的过滤条件
                    digits = typedArray.getString(attr);
                } else if (attr == R.styleable.SearchBarView_cornerRadius) {
                    //圆角
                    cornerRadius = typedArray.getDimension(attr, 0f);
                } else if (attr == R.styleable.SearchBarView_backgroundColor) {
                    //背景色
                    backgroundColor = typedArray.getColor(attr, Color.TRANSPARENT);
                } else if (attr == R.styleable.SearchBarView_normalBackgroundColor) {
                    //为获取焦点时的背景色
                    normalBackgroundColor = typedArray.getColor(attr, Color.TRANSPARENT);
                } else if (attr == R.styleable.SearchBarView_focusedBackgroundColor) {
                    //获取焦点时的背景色
                    focusedBackgroundColor = typedArray.getColor(attr, Color.TRANSPARENT);
                } else if (attr == R.styleable.SearchBarView_borderColor) {
                    //边框颜色
                    borderColor = typedArray.getColor(attr, Color.TRANSPARENT);
                } else if (attr == R.styleable.SearchBarView_normalBorderColor) {
                    //未获取焦点时的边框颜色
                    normalBorderColor = typedArray.getColor(attr, Color.TRANSPARENT);
                } else if (attr == R.styleable.SearchBarView_focusedBorderColor) {
                    //获取焦点时的边框颜色
                    focusedBorderColor = typedArray.getColor(attr, Color.TRANSPARENT);
                } else if (attr == R.styleable.SearchBarView_borderWidth) {
                    //边框宽度
                    borderWidth = typedArray.getDimension(attr, 0f);
                } else if (attr == R.styleable.SearchBarView_isShowActionButton) {
                    //是否显示最右侧Button
                    isShowActionButton = typedArray.getBoolean(attr, false);
                }
            }
            typedArray.recycle();
        }
    }

    private void initSetting(Context context) {
        inputFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                if (!TextUtils.isEmpty(charSequence)) {
                    String inputStr = charSequence.toString();
                    if (TextUtils.isEmpty(digits)) {
                        //如果没有匹配条件就不去匹配
                        return null;
                    } else if (inputStr.matches(digits)) {
                        //设置了匹配条件 且符合匹配条件
                        return null;
                    } else {
                        //设置了匹配条件但不符合匹配条件
                        return "";
                    }
                } else {
                    //输入的为空则过滤掉
                    return "";
                }
            }
        };

        if (editText != null) {
            editText.setSingleLine();
            editText.setGravity(android.view.Gravity.CENTER_VERTICAL);
            if (TextUtils.isEmpty(text)) {
                editText.setHint(hint);
            } else {
                editText.setText(text);
            }
            editText.setTextSize(DensityUtils.px2sp(context, textSize));
            if (textColor != null) {
                editText.setTextColor(textColor);
            }
            if (textColorHint != null) {
                editText.setHintTextColor(textColorHint);
            }
            TextUtils.setDrawable(editText, editIcon, (int) editIconPadding, Gravity.LEFT);

            //设置过滤器
            editText.setFilters(new InputFilter[]{inputFilter, new InputFilter.LengthFilter(maxLength)});

            editText.setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        // 此处为得到焦点时的处理内容
                        if (drawable != null) {
                            drawable.setBackgroundColor(backgroundColor != Color.TRANSPARENT ? backgroundColor : focusedBackgroundColor != Color.TRANSPARENT ? focusedBackgroundColor : normalBackgroundColor);
                            drawable.setBorderColor(borderColor != Color.TRANSPARENT ? borderColor : focusedBorderColor != Color.TRANSPARENT ? focusedBorderColor : normalBorderColor);
                        }
                    } else {
                        // 此处为失去焦点时的处理内容
                        if (drawable != null) {
                            drawable.setBackgroundColor(backgroundColor != Color.TRANSPARENT ? backgroundColor : normalBackgroundColor != Color.TRANSPARENT ? normalBackgroundColor : focusedBackgroundColor);
                            drawable.setBorderColor(borderColor != Color.TRANSPARENT ? borderColor : normalBorderColor != Color.TRANSPARENT ? normalBorderColor : focusedBorderColor);
                        }
                    }
                    //焦点改变的监听回调接口
                    if (onFocusChangeListener != null) {
                        onFocusChangeListener.onFocusChange(v, hasFocus);
                    }
                }
            });

            //输入内容改变时的监听
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    if (textWatchListener != null) {
                        textWatchListener.beforeTextChanged(s, start, count, after);
                    }
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (textWatchListener != null) {
                        textWatchListener.onTextChanged(s, start, before, count);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (textWatchListener != null) {
                        textWatchListener.afterTextChanged(s);
                    }
                    if (onTextChangedListener != null) {
                        onTextChangedListener.onChanged(s, editText, imageButton);
                    }
                }
            });

        }

        if (imageButton != null) {
            imageButton.setImageDrawable(actionIcon);
            imageButton.setVisibility(isShowActionButton ? VISIBLE : GONE);
        }

        if (drawable != null) {
            drawable.setRadius(cornerRadius);
            drawable.setBackgroundColor(backgroundColor == Color.TRANSPARENT ? normalBackgroundColor : backgroundColor);
            drawable.setBorderColor(borderColor == Color.TRANSPARENT ? normalBorderColor : borderColor);
            drawable.setBorderWidth(borderWidth);
            drawable.isShowBorder(isShowBorder);
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

    /**
     * 是否显示最右侧的按钮
     *
     * @param showActionButton true显示，false不显示
     */
    public void setShowActionButton(boolean showActionButton) {
        this.isShowActionButton = showActionButton;
        if (imageButton != null) {
            imageButton.setVisibility(isShowActionButton ? VISIBLE : GONE);
        }
    }

    /**
     * 设置搜索框背景色
     *
     * @param backgroundColor 背景色优先级最高 设置BackgroundColor后NormalBackgroundColor和FocusedBackgroundColor不起作用
     */
    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor) {
        this.backgroundColor = backgroundColor;
        if (drawable != null) {
            drawable.setBackgroundColor(backgroundColor);
        }
    }

    /**
     * 设置未获取焦点时的背景色
     *
     * @param normalBackgroundColor 未获取焦点时的背景色
     */
    public void setNormalBackgroundColor(@ColorInt int normalBackgroundColor) {
        this.normalBackgroundColor = normalBackgroundColor;
        if (drawable != null) {
            drawable.setBackgroundColor(backgroundColor != Color.TRANSPARENT ? backgroundColor : normalBackgroundColor);
        }
    }

    /**
     * 设置获取焦点时的背景色
     *
     * @param focusedBackgroundColor 获取焦点时的背景色
     */
    public void setFocusedBackgroundColor(@ColorInt int focusedBackgroundColor) {
        this.focusedBackgroundColor = focusedBackgroundColor;
    }

    /**
     * 设置边框的颜色
     *
     * @param borderColor 边框的颜色
     */
    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        if (drawable != null) {
            drawable.setBorderColor(borderColor);
        }
    }

    /**
     * 设置未获取焦点时的边框颜色
     *
     * @param normalBorderColor 未获取焦点时的边框颜色
     */
    public void setNormalBorderColor(int normalBorderColor) {
        this.normalBorderColor = normalBorderColor;
        if (drawable != null) {
            drawable.setBorderColor(borderColor != Color.TRANSPARENT ? borderColor : normalBorderColor);
        }
    }

    /**
     * 设置获取焦点时的颜色
     *
     * @param focusedBorderColor 获取焦点时的颜色
     */
    public void setFocusedBorderColor(int focusedBorderColor) {
        this.focusedBorderColor = focusedBorderColor;
    }

    /**
     * 设置最大输入字数
     *
     * @param maxLength 最大输入字数
     */
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        if (editText != null) {
            editText.setFilters(new InputFilter[]{inputFilter, new InputFilter.LengthFilter(maxLength)});
        }
    }

    /**
     * 输入的过滤条件
     *
     * @param digits 过滤条件
     */
    public void setDigits(String digits) {
        this.digits = digits;
    }

    /**
     * 是否显示边框
     *
     * @param showBorder
     */
    public void setShowBorder(boolean showBorder) {
        this.isShowBorder = showBorder;
        if (drawable != null) {
            drawable.isShowBorder(showBorder);
        }
    }

    /**
     * 设置边框的宽度(粗细)
     *
     * @param borderWidth 边框的宽度(粗细)
     */
    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
        if (drawable != null) {
            drawable.setBorderWidth(borderWidth);
        }
    }

    /**
     * 设置搜索框的圆角半径
     *
     * @param radius 圆角半径
     */
    public void setCornerRadius(float radius) {
        this.cornerRadius = radius;
        if (drawable != null) {
            drawable.setRadius(radius);
        }
    }

    /**
     * 设置最左侧的Icon
     *
     * @param drawable        最左侧的Icon
     * @param drawablePadding 文字和Icon的距离
     */
    public void setEditIcon(Drawable drawable, int drawablePadding) {
        if (drawable != null) {
            this.editIcon = drawable;
        }
        this.editIconPadding = drawablePadding;
        if (editText != null) {
            TextUtils.setDrawable(editText, drawable, drawablePadding, Gravity.LEFT);
        }
    }

    /**
     * 设置最左侧的Icon
     *
     * @param resId           最左侧的Icon的资源id
     * @param drawablePadding 文字和Icon的距离
     */
    public void setEditIcon(@DrawableRes int resId, int drawablePadding) {
        this.editIcon = ResUtils.getDrawable(getContext(), resId);
        this.editIconPadding = drawablePadding;
        if (editText != null) {
            TextUtils.setDrawable(editText, resId, drawablePadding, Gravity.LEFT);
        }
    }

    /**
     * 设置右侧的Icon
     *
     * @param actionIcon
     */
    public void setActionIcon(Drawable actionIcon) {
        this.actionIcon = actionIcon;
        if (imageButton != null) {
            imageButton.setImageDrawable(actionIcon);
        }
    }

    /**
     * 设置右侧的Icon
     *
     * @param resId
     */
    public void setActionIcon(@DrawableRes int resId) {
        this.actionIcon = ResUtils.getDrawable(getContext(), resId);
        if (imageButton != null) {
            imageButton.setImageResource(resId);
        }
    }

    /**
     * 设置文字字号
     *
     * @param textSize 字号
     */
    public void setTextSize(float textSize) {
        this.textSize = textSize;
        if (editText != null) {
            editText.setTextSize(COMPLEX_UNIT_PX, textSize);
        }
    }

    /**
     * 设置提示文字
     *
     * @param hint 提示文字
     */
    public void setHint(String hint) {
        this.hint = hint;
        if (editText != null) {
            editText.setHint(hint);
        }
    }

    /**
     * 设置内容文字
     *
     * @param text 内容
     */
    public void setText(String text) {
        this.text = text;
        if (editText != null) {
            editText.setText(text);
        }
    }

    /**
     * 获取输入的内容
     *
     * @return
     */
    public String getText() {
        if (editText != null) {
            return editText.getText().toString();
        } else {
            return null;
        }
    }

    /**
     * 设置文字颜色
     *
     * @param textColor
     */
    public void setTextColor(ColorStateList textColor) {
        this.textColor = textColor;
        if (editText != null) {
            editText.setTextColor(textColor);
        }
    }

    /**
     * 设置文字颜色
     *
     * @param textColor
     */
    public void setTextColor(@ColorInt int textColor) {
        this.textColor = ColorStateList.valueOf(textColor);
        if (editText != null) {
            editText.setTextColor(textColor);
        }
    }

    /**
     * 设置提示文字颜色
     *
     * @param textColorHint
     */
    public void setTextColorHint(ColorStateList textColorHint) {
        this.textColorHint = textColorHint;
        if (editText != null) {
            editText.setHintTextColor(textColorHint);
        }
    }

    /**
     * 设置提示文字颜色
     *
     * @param textColorHint
     */
    public void setTextColorHint(@ColorInt int textColorHint) {
        this.textColorHint = ColorStateList.valueOf(textColorHint);
        if (editText != null) {
            editText.setHintTextColor(textColorHint);
        }
    }

    /**
     * 设置输入监听
     *
     * @param textWatchListener 输入监听回调接口
     */
    public void addTextWatchListener(TextWatchListener textWatchListener) {
        this.textWatchListener = textWatchListener;
    }

    /**
     * 焦点改变监听
     *
     * @param onFocusChangeListener 焦点改变的回调接口
     */
    public void setClearEditTextFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        this.onFocusChangeListener = onFocusChangeListener;
    }

    /**
     * 设置内容的改变监听回调接口
     *
     * @param onTextChangedListener 内容的改变监听回调接口
     */
    public void setOnTextChangedListener(OnTextChangedListener onTextChangedListener) {
        this.onTextChangedListener = onTextChangedListener;
    }

    /**
     * 设置内容的改变监听回调接口
     *
     * @param onTextChangedListener 内容的改变监听回调接口
     * @param onClickListener       最右侧按钮的点击事件
     */
    public void setOnTextChangedListener(OnTextChangedListener onTextChangedListener, View.OnClickListener onClickListener) {
        this.onTextChangedListener = onTextChangedListener;
        if (imageButton != null) {
            imageButton.setOnClickListener(onClickListener);
        }
    }

    /**
     * 设置右侧按钮的点击事件
     *
     * @param onClickListener 最右侧按钮的点击事件
     */
    public void setOnActionButtonClickListener(View.OnClickListener onClickListener) {
        if (imageButton != null) {
            imageButton.setOnClickListener(onClickListener);
        }
    }

    /**
     * 内容的改变监听
     */
    public interface OnTextChangedListener {
        void onChanged(Editable s, EditText editText, ImageButton imageButton);
    }

}
