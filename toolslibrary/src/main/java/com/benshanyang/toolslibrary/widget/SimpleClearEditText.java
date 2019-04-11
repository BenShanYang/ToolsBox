package com.benshanyang.toolslibrary.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
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
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.benshanyang.toolslibrary.R;
import com.benshanyang.toolslibrary.callback.BorderDrawable;
import com.benshanyang.toolslibrary.callback.TextWatchListener;
import com.benshanyang.toolslibrary.drawable.SimpleEditTextDrawable;
import com.benshanyang.toolslibrary.utils.DensityUtils;
import com.benshanyang.toolslibrary.utils.TextUtils;

import static android.util.TypedValue.COMPLEX_UNIT_PX;

/**
 * 类描述: 简单的带清除功能的输入框 </br>
 * 时间: 2019/3/30 9:36
 *
 * @author YangKuan
 * @version 1.0.0
 * @since
 */
public class SimpleClearEditText extends LinearLayout {

    @ColorInt
    private int borderColor = Color.TRANSPARENT;//设置底边颜色设置后获取焦点和为获取焦点都是该颜色
    @ColorInt
    private int backgroundColor = Color.TRANSPARENT;//设置带边框的背景色
    @ColorInt
    private int normalBorderColor = Color.TRANSPARENT;//输入框未获取焦点时候的底边颜色
    @ColorInt
    private int focusedBorderColor = Color.TRANSPARENT;//输入框获取焦点时候的底边颜色
    private int gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;//文字显示的位置 默认居左垂直居中
    private int maxLength = Integer.MAX_VALUE;//最大输入长度
    private int width = 0;//控件宽
    private int height = 0;//空间高
    private float borderWidth = 0f;//底边的线粗
    private float textSize = 0f;//文字大小
    private boolean isShowBorder = false;//是否显示底边

    private EditText editText;//输入框
    private ImageButton clearButton;//清除按钮
    private ColorStateList textColorHint;//提示文字的颜色
    private ColorStateList textColor;//文字的颜色
    private Drawable iconClear;//清除图标
    private Drawable normalBackground = null;//未获取焦点时的背景
    private Drawable focusedBackground = null;//获取焦点时的背景
    private BorderDrawable drawable;//默认下划线Drawable
    private String digits = "";//输入框的过滤条件
    private String hint = "";//提示文字
    private String text = "";//设置文字

    private InputFilter inputFilter;//过滤器
    private TextWatchListener textWatchListener;//输入监听
    private OnFocusChangeListener onFocusChangeListener;//输入框的焦点改变监听

    public SimpleClearEditText(Context context) {
        super(context);
        initWidget(context);
        initSetting();
    }

    public SimpleClearEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initWidget(context);
        initAttrs(context, attrs);
        initSetting();
    }

    public SimpleClearEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWidget(context);
        initAttrs(context, attrs);
        initSetting();
    }

    private void initWidget(Context context) {
        setOrientation(HORIZONTAL);
        editText = new EditText(context);//内容输入框
        editText.setBackgroundColor(Color.TRANSPARENT);
        clearButton = new ImageButton(context); //清除按钮
        clearButton.setBackgroundColor(Color.TRANSPARENT);
        clearButton.setVisibility(GONE);

        LayoutParams editParams = new LayoutParams(0, LayoutParams.MATCH_PARENT);
        editParams.weight = 1f;
        addView(editText, editParams);
        addView(clearButton, new LayoutParams(DensityUtils.dp2px(context, 36), LayoutParams.MATCH_PARENT));

    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SimpleClearEditText, 0, 0);
        if (typedArray != null) {
            for (int i = 0; i < typedArray.getIndexCount(); i++) {
                int attr = typedArray.getIndex(i);
                if (attr == R.styleable.SimpleClearEditText_text) {
                    text = typedArray.getString(attr);//设置文字
                } else if (attr == R.styleable.SimpleClearEditText_hint) {
                    hint = typedArray.getString(attr);//提示文字
                } else if (attr == R.styleable.SimpleClearEditText_textSize) {
                    textSize = typedArray.getDimension(attr, 0);//文字大小
                } else if (attr == R.styleable.SimpleClearEditText_textColor) {
                    textColor = typedArray.getColorStateList(attr);//文字的颜色
                } else if (attr == R.styleable.SimpleClearEditText_textColorHint) {
                    textColorHint = typedArray.getColorStateList(attr);//提示文字的颜色
                } else if (attr == R.styleable.SimpleClearEditText_gravity) {
                    gravity = typedArray.getInt(attr, Gravity.LEFT | Gravity.CENTER_VERTICAL);//文字显示的位置 默认居左垂直居中
                } else if (attr == R.styleable.SimpleClearEditText_iconClear) {
                    iconClear = typedArray.getDrawable(attr);//清空图标
                } else if (attr == R.styleable.SimpleClearEditText_digits) {
                    digits = typedArray.getString(attr);//输入框的过滤条件 只能是正则表达式
                } else if (attr == R.styleable.SimpleClearEditText_maxLength) {
                    maxLength = typedArray.getInteger(attr, Integer.MAX_VALUE);//最大输入的长度
                } else if (attr == R.styleable.SimpleClearEditText_isShowBorder) {
                    isShowBorder = typedArray.getBoolean(attr, false);//是否显示底边
                } else if (attr == R.styleable.SimpleClearEditText_borderWidth) {
                    borderWidth = typedArray.getDimension(attr, 0);//底边的线粗
                } else if (attr == R.styleable.SimpleClearEditText_backgroundColor) {
                    backgroundColor = typedArray.getColor(attr, Color.TRANSPARENT);//设置带边框的背景色
                } else if (attr == R.styleable.SimpleClearEditText_normalBorderColor) {
                    normalBorderColor = typedArray.getColor(attr, 0xFFD5D5D5);//输入框未获取焦点时候的底边颜色
                } else if (attr == R.styleable.SimpleClearEditText_focusedBorderColor) {
                    focusedBorderColor = typedArray.getColor(attr, 0xFF0087f3);//输入框获取焦点时候的底边颜色
                } else if (attr == R.styleable.SimpleClearEditText_borderColor) {
                    borderColor = typedArray.getColor(attr, Color.TRANSPARENT);//设置底边颜色设置后获取焦点和为获取焦点都是该颜色
                } else if (attr == R.styleable.SimpleClearEditText_normalBackground) {
                    normalBackground = typedArray.getDrawable(attr);//未获取焦点时的背景
                } else if (attr == R.styleable.SimpleClearEditText_focusedBackground) {
                    focusedBackground = typedArray.getDrawable(attr);//获取焦点时的背景
                }
            }
            typedArray.recycle();
        }

    }

    private void initSetting() {
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

        drawable = new SimpleEditTextDrawable();
        if (normalBackground == null && focusedBackground == null) {
            setBackground((Drawable) drawable);
        } else {
            setBackground(normalBackground != null ? normalBackground : focusedBackground);
        }
        if (editText != null && clearButton != null) {
            editText.setText(TextUtils.isEmpty(text) ? "" : text);//设置文字
            editText.setHint(TextUtils.isEmpty(hint) ? "" : hint);//设置提示文字
            if (textSize > 0) {
                editText.setTextSize(DensityUtils.px2sp(getContext(), textSize));//设置文字大小
            }
            if (textColor != null) {
                editText.setTextColor(textColor);//设置提示文字颜色
            }
            if (textColorHint != null) {
                editText.setHintTextColor(textColorHint);//设置提示文字颜色
            }
            //设置文字显示的方向
            switch (gravity) {
                case 0:
                    //left
                    editText.setGravity(Gravity.LEFT);
                    break;
                case 1:
                    //right
                    editText.setGravity(Gravity.RIGHT);
                    break;
                case 2:
                    //top
                    editText.setGravity(Gravity.TOP);
                    break;
                case 3:
                    //bottom
                    editText.setGravity(Gravity.BOTTOM);
                    break;
                case 4:
                    //center
                    editText.setGravity(Gravity.CENTER);
                    break;
                case 5:
                    //center_vertical
                    editText.setGravity(Gravity.CENTER_VERTICAL);
                    break;
                case 6:
                    //left_center_vertical
                    editText.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                    break;
                case 7:
                    //right_center_vertical
                    editText.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                    break;
                case 8:
                    //center_horizontal
                    editText.setGravity(Gravity.CENTER_HORIZONTAL);
                    break;
                case 9:
                    //top_center_horizontal
                    editText.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                    break;
                case 10:
                    //bottom_center_horizontal
                    editText.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
                    break;
                default:
                    editText.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                    break;
            }

            if (isShowBorder && drawable != null) {
                drawable.setBackgroundColor(backgroundColor);//设置背景色
                drawable.isShowBorder(isShowBorder);
                drawable.setBorderWidth(borderWidth);//设置底边粗细的尺寸
                drawable.setBorderColor(borderColor != Color.TRANSPARENT ? borderColor : normalBorderColor != Color.TRANSPARENT ? normalBorderColor : focusedBorderColor);//设置底边的颜色
            }

            //设置过滤器
            editText.setFilters(new InputFilter[]{inputFilter, new InputFilter.LengthFilter(maxLength)});

            editText.setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    String s = editText.getText().toString();
                    if (hasFocus) {
                        // 此处为得到焦点时的处理内容
                        if (!TextUtils.isEmpty(s)) {
                            clearButton.setVisibility(VISIBLE);
                        } else {
                            clearButton.setVisibility(GONE);
                        }
                        if (focusedBackground == null && normalBackground == null) {
                            if (isShowBorder && drawable != null) {
                                drawable.setBorderColor(borderColor != Color.TRANSPARENT ? borderColor : focusedBorderColor != Color.TRANSPARENT ? focusedBorderColor : normalBorderColor);//输入框获取焦点的底边颜色
                            }
                        } else {
                            setBackground(focusedBackground != null ? focusedBackground : normalBackground);
                        }

                    } else {
                        // 此处为失去焦点时的处理内容
                        clearButton.setVisibility(GONE);
                        if (normalBackground == null && focusedBackground == null) {
                            if (isShowBorder && drawable != null) {
                                drawable.setBorderColor(borderColor != Color.TRANSPARENT ? borderColor : normalBorderColor != Color.TRANSPARENT ? normalBorderColor : focusedBorderColor);//输入框失去焦点的底边颜色
                            }
                        } else {
                            setBackground(normalBackground != null ? normalBackground : focusedBackground);
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
                    if (!TextUtils.isEmpty(s.toString())) {
                        clearButton.setVisibility(VISIBLE);
                    } else {
                        clearButton.setVisibility(GONE);
                    }
                    if (textWatchListener != null) {
                        textWatchListener.afterTextChanged(s);
                    }
                }
            });

            if (iconClear != null) {
                clearButton.setImageDrawable(iconClear);
            } else {
                clearButton.setImageResource(R.drawable.ic_clear);
            }
            //清空输入框
            clearButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editText != null) {
                        editText.setText("");
                    }
                }
            });

        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();//获取控件的宽度
        height = getMeasuredHeight();//获取控件的高度
        if (drawable != null) {
            drawable.setWidth(width);
            drawable.setHeight(height);
        }
    }

    /**
     * 设置背景色
     *
     * @param backgroundColor
     */
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        if (drawable != null) {
            drawable.setBackgroundColor(backgroundColor);
        }
    }

    /**
     * 设置底部边框颜色
     *
     * @param borderColor 颜色色值
     */
    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        if (drawable != null) {
            drawable.setBorderColor(borderColor);
        }
    }

    /**
     * 设置未获取焦点时的底部边框的颜色
     *
     * @param normalBorderColor 颜色色值
     */
    public void setNormalBorderColor(int normalBorderColor) {
        this.normalBorderColor = normalBorderColor;
        if (drawable != null && borderColor == Color.TRANSPARENT) {
            drawable.setBorderColor(normalBorderColor);
        }
    }

    /**
     * 设置获取焦点时的底部边框的颜色
     *
     * @param focusedBorderColor 颜色色值
     */
    public void setFocusedBorderColor(int focusedBorderColor) {
        this.focusedBorderColor = focusedBorderColor;
    }

    /**
     * 设置文字居中方式
     *
     * @param gravity
     */
    @Override
    public void setGravity(int gravity) {
        if (editText != null) {
            editText.setGravity(gravity);
        }
    }

    /**
     * 设置最大文字输入长度
     *
     * @param maxLength 最大输入字数
     */
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        editText.setFilters(new InputFilter[]{inputFilter, new InputFilter.LengthFilter(maxLength)});
    }

    /**
     * 设置底边宽度
     *
     * @param borderWidth 底边的宽度
     */
    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
        if (drawable != null) {
            drawable.setBorderWidth(borderWidth);
        }
    }

    /**
     * 是否显示底边
     *
     * @param showBorder true显示底边,false不显示底边
     */
    public void isShowBorder(boolean showBorder) {
        isShowBorder = showBorder;
        if (drawable != null) {
            drawable.isShowBorder(showBorder);
        }
    }

    /**
     * 设置文字大小
     *
     * @param textSize 文字字号
     */
    public void setTextSize(float textSize) {
        this.textSize = textSize;
        if (editText != null) {
            editText.setTextSize(COMPLEX_UNIT_PX,textSize);
        }
    }

    /**
     * 设置提示文字颜色
     *
     * @param textColorHint 示文字颜色色值
     */
    public void setHintTextColor(@ColorInt int textColorHint) {
        this.textColorHint = ColorStateList.valueOf(textColorHint);
        if (editText != null) {
            editText.setHintTextColor(textColorHint);
        }
    }

    /**
     * 设置文字颜色
     *
     * @param textColor 文字颜色色值
     */
    public void setTextColor(@ColorInt int textColor) {
        this.textColor = ColorStateList.valueOf(textColor);
        if (editText != null) {
            editText.setTextColor(textColor);
        }
    }

    /**
     * 设置提示文字
     *
     * @param hint 提示文字
     */
    public void setHint(String hint) {
        if (!TextUtils.isEmpty(hint)) {
            this.hint = hint;
            if (editText != null) {
                editText.setHint(hint);
            }
        }
    }

    /**
     * 获取输入的文字
     *
     * @return
     */
    public String getText() {
        return text;
    }

    /**
     * 设置文字
     *
     * @param text
     */
    public void setText(String text) {
        if (!TextUtils.isEmpty(text)) {
            this.text = text;
            if (editText != null) {
                editText.setHint(text);
            }
        }
    }

    /**
     * 设置过滤条件
     *
     * @param digits 过滤条件,必须为正则表达式
     */
    public void setDigits(String digits) {
        if (!TextUtils.isEmpty(digits)) {
            this.digits = digits;
        }
    }

    /**
     * 设置清除内容的Icon
     *
     * @param drawable
     */
    public void setClearIcon(@Nullable Drawable drawable) {
        if (clearButton != null) {
            if (drawable != null) {
                this.iconClear = drawable;
            }
            clearButton.setImageDrawable(drawable);
        }
    }

    /**
     * 设置清除内容的Icon
     *
     * @param bitmap
     */
    public void setClearIcon(Bitmap bitmap) {
        if (clearButton != null) {
            clearButton.setImageBitmap(bitmap);
        }
    }

    /**
     * 设置清除内容的Icon
     *
     * @param resId
     */
    public void setClearIcon(@DrawableRes int resId) {
        if (clearButton != null) {
            clearButton.setImageResource(resId);
        }
    }

    /**
     * 设置未获取焦点时的背景样式
     *
     * @param normalBackground
     */
    public void setNormalBackground(Drawable normalBackground) {
        if (normalBackground != null) {
            this.normalBackground = normalBackground;
            setBackground(normalBackground);
        }
    }

    /**
     * 设置获取焦点时的背景样式
     *
     * @param focusedBackground
     */
    public void setFocusedBackground(Drawable focusedBackground) {
        if (focusedBackground != null) {
            this.focusedBackground = focusedBackground;
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
}
