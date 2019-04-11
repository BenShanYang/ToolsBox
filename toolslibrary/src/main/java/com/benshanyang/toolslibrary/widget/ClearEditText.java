package com.benshanyang.toolslibrary.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.benshanyang.toolslibrary.R;
import com.benshanyang.toolslibrary.callback.TextWatchListener;
import com.benshanyang.toolslibrary.utils.DensityUtils;
import com.benshanyang.toolslibrary.utils.TextUtils;

import static android.util.TypedValue.COMPLEX_UNIT_PX;

/**
 * 类描述: 带清除功能的输入框 </br>
 * 时间: 2019/3/20 11:07
 *
 * @author YangKuan
 * @version 1.0.0
 * @since
 */
public class ClearEditText extends FrameLayout {

    private ImageView ivIcon;//输入框图标
    private EditText etInput;//输入框
    private ImageButton ibClear;//清除按钮
    private View borderView;//底边

    private String digits = "";//过滤条件
    private float borderWidth = 0;//底部分割线宽度
    private int maxLength = 0;//最大输入长度
    private int focusedBorderColor = 0xFF0087f3;//输入框获取焦点时候的底边颜色
    private int normalBorderColor = 0xFFD5D5D5;//输入框未获取焦点时候的底边颜色
    private boolean isShowBorder = false;//是否显示底部分割线 默认不现实

    private InputFilter inputFilter;//过滤器
    private TextWatchListener textWatchListener;//输入监听
    private OnFocusChangeListener onFocusChangeListener;//输入框的焦点改变监听

    public ClearEditText(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public ClearEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ClearEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_clear_edittext, this, false);
        initView(view);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClearEditText);
        if (typedArray != null) {

            boolean singleLine = typedArray.getBoolean(R.styleable.ClearEditText_singleLine, false);//是否是一行
            isShowBorder = typedArray.getBoolean(R.styleable.ClearEditText_isShowBorder, false);//是否显示底部分割线
            int gravity = typedArray.getInt(R.styleable.ClearEditText_gravity, -1);//文字显示的位置

            float iconPaddingLeft = typedArray.getDimension(R.styleable.ClearEditText_iconPaddingLeft, 0);//icon和左边的距离
            float iconTextPaddingLeft = typedArray.getDimension(R.styleable.ClearEditText_iconTextPaddingLeft, etInput.getPaddingLeft());//icon和文字间的距离
            float clearIconPaddingLeft = typedArray.getDimension(R.styleable.ClearEditText_clearIconPaddingLeft, ibClear.getPaddingLeft());//清除按钮的左边距
            float clearIconPaddingRight = typedArray.getDimension(R.styleable.ClearEditText_clearIconPaddingRight, ibClear.getPaddingLeft());//清除按钮的右边距
            float minLines = typedArray.getDimension(R.styleable.ClearEditText_minLines, -1);//最小输入行数
            int maxLines = typedArray.getInt(R.styleable.ClearEditText_maxLines, -1);//最大输入行数
            float textSize = typedArray.getDimension(R.styleable.ClearEditText_textSize, 0);//文字的大小

            ColorStateList textColor = typedArray.getColorStateList(R.styleable.ClearEditText_textColor);//设置密码字体颜色
            ColorStateList textColorHint = typedArray.getColorStateList(R.styleable.ClearEditText_textColorHint);//提示文字的颜色

            String text = typedArray.getString(R.styleable.ClearEditText_text);//设置输入框文字
            String hint = typedArray.getString(R.styleable.ClearEditText_hint);//提示文字

            Drawable icon = typedArray.getDrawable(R.styleable.ClearEditText_icon);//输入框最左侧小图标
            Drawable iconClear = typedArray.getDrawable(R.styleable.ClearEditText_iconClear);//输入框最左侧小图标

            normalBorderColor = typedArray.getColor(R.styleable.ClearEditText_normalBorderColor, 0xFFD5D5D5);//失去焦点时底边颜色
            focusedBorderColor = typedArray.getColor(R.styleable.ClearEditText_focusedBorderColor, 0xFF0087f3);//获取焦点时底边颜色
            borderWidth = typedArray.getDimension(R.styleable.ClearEditText_borderWidth, 0);//底部分割线宽度
            digits = typedArray.getString(R.styleable.ClearEditText_digits);//过滤字符串
            maxLength = typedArray.getInteger(R.styleable.ClearEditText_maxLength, Integer.MAX_VALUE);//最大输入的长度

            //设置左侧图标和控件左边的距离
            if (ivIcon != null) {
                ivIcon.setPadding((int) iconPaddingLeft, 0, 0, 0);
                //设置左侧图标
                if (icon != null) {
                    ivIcon.setImageDrawable(icon);
                }
            }

            //设置清除按钮的左右内边距
            if (ibClear != null) {
                ibClear.setPadding((int) clearIconPaddingLeft, 0, (int) clearIconPaddingRight, 0);
                //设置清除按钮的图标
                if (iconClear != null) {
                    ibClear.setImageDrawable(iconClear);
                }
            }

            if (etInput != null) {
                //设置图标和文字的间距
                etInput.setPadding((int) iconTextPaddingLeft, 0, 0, 0);
                //设置最小行数
                if (minLines > 0) {
                    etInput.setMinLines((int) minLines);
                }
                //设置最大行数
                if (maxLines > 0) {
                    etInput.setMaxLines(maxLines);
                }
                //是否只显示一行
                etInput.setSingleLine(singleLine);
                //设置字体大小
                if (textSize > 0) {
                    etInput.setTextSize(DensityUtils.px2sp(context, textSize));
                }
                //设置字体颜色
                if (textColor != null) {
                    etInput.setTextColor(textColor);
                }
                //设置提示文字颜色
                if (textColorHint != null) {
                    etInput.setHintTextColor(textColorHint);
                }
                //设置文字
                if (!TextUtils.isEmpty(text)) {
                    etInput.setText(text);
                }
                //设置提示文字
                if (!TextUtils.isEmpty(hint)) {
                    etInput.setHint(hint);
                }
                //设置文字显示的方向
                switch (gravity) {
                    case 0:
                        //left
                        etInput.setGravity(Gravity.LEFT);
                        break;
                    case 1:
                        //right
                        etInput.setGravity(Gravity.RIGHT);
                        break;
                    case 2:
                        //top
                        etInput.setGravity(Gravity.TOP);
                        break;
                    case 3:
                        //bottom
                        etInput.setGravity(Gravity.BOTTOM);
                        break;
                    case 4:
                        //center
                        etInput.setGravity(Gravity.CENTER);
                        break;
                    case 5:
                        //center_vertical
                        etInput.setGravity(Gravity.CENTER_VERTICAL);
                        break;
                    case 6:
                        //left_center_vertical
                        etInput.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                        break;
                    case 7:
                        //right_center_vertical
                        etInput.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                        break;
                    case 8:
                        //center_horizontal
                        etInput.setGravity(Gravity.CENTER_HORIZONTAL);
                        break;
                    case 9:
                        //top_center_horizontal
                        etInput.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                        break;
                    case 10:
                        //bottom_center_horizontal
                        etInput.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
                        break;
                    default:
                        etInput.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                        break;
                }
            }

            //设置底边
            if (borderView != null) {
                if (isShowBorder) {
                    //显示底边
                    borderView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) borderWidth));
                    borderView.setBackgroundColor(normalBorderColor);
                    borderView.setVisibility(VISIBLE);
                } else {
                    //不显示底边
                    borderView.setVisibility(GONE);
                }
            }

            typedArray.recycle();
        }

        addView(view);
        initListener();
    }


    /**
     * 初始化监听事件
     */
    private void initListener() {
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

        //清空输入框
        if (ibClear != null) {
            ibClear.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    etInput.setText("");
                }
            });
        }

        if (etInput != null) {
            //设置过滤器
            etInput.setFilters(new InputFilter[]{inputFilter, new InputFilter.LengthFilter(maxLength)});

            etInput.setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    String s = etInput.getText().toString();
                    if (hasFocus) {
                        // 此处为得到焦点时的处理内容
                        if (!TextUtils.isEmpty(s)) {
                            ibClear.setVisibility(VISIBLE);
                        } else {
                            ibClear.setVisibility(GONE);
                        }
                        if (isShowBorder) {
                            borderView.setBackgroundColor(focusedBorderColor);//输入框获取焦点的底边颜色
                        }
                    } else {
                        // 此处为失去焦点时的处理内容
                        ibClear.setVisibility(GONE);
                        if (isShowBorder) {
                            borderView.setBackgroundColor(normalBorderColor);//输入框失去焦点的底边颜色
                        }
                    }
                    //焦点改变的监听回调接口
                    if (onFocusChangeListener != null) {
                        onFocusChangeListener.onFocusChange(v, hasFocus);
                    }
                }
            });

            //输入内容改变时的监听
            etInput.addTextChangedListener(new TextWatcher() {
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
                        ibClear.setVisibility(VISIBLE);
                    } else {
                        ibClear.setVisibility(GONE);
                    }
                    if (textWatchListener != null) {
                        textWatchListener.afterTextChanged(s);
                    }
                }
            });
        }
    }

    /**
     * 初始化控件
     *
     * @param view
     */
    private void initView(View view) {
        ivIcon = (ImageView) view.findViewById(R.id.iv_icon);//输入框图标
        etInput = (EditText) view.findViewById(R.id.et_inputbar);//输入框
        ibClear = (ImageButton) view.findViewById(R.id.ib_clearbtn);//清除按钮
        borderView = view.findViewById(R.id.borderview);//底边
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
     * 设置是否只显示一行
     *
     * @param isSinglieLine 设置是否只显示一行
     */
    public void setSingleLine(boolean isSinglieLine) {
        if (etInput != null) {
            etInput.setSingleLine(isSinglieLine);
        }
    }

    /**
     * 是否显示底边
     *
     * @param isShow 是否显示底部分割线
     */
    public void isShowBorder(boolean isShow) {
        this.isShowBorder = isShow;
        if (isShow) {
            //显示底边
            borderView.setVisibility(VISIBLE);
        } else {
            //不显示底边
            borderView.setVisibility(GONE);
        }
    }

    /**
     * 设置文字显示的方向
     *
     * @param gravity 文字方向 使用android.view.Gravity的相关常量
     */
    public void setGravity(int gravity) {
        if (etInput != null) {
            etInput.setGravity(gravity);
        }
    }

    /**
     * 设置左侧图标和控件左边的距离
     *
     * @param padding 距离值
     */
    public void setIconPaddingLeft(int padding) {
        if (ivIcon != null) {
            ivIcon.setPadding(padding, 0, 0, 0);
        }
    }

    /**
     * 设置左侧图标和文字的距离
     *
     * @param padding 距离值
     */
    public void setIconTextPaddingLeft(int padding) {
        if (etInput != null) {
            etInput.setPadding(padding, 0, 0, 0);
        }
    }

    /**
     * 设置清除按钮的左右内边距
     *
     * @param padding 距离值
     */
    public void setClearIconPaddingLeft(int padding) {
        if (ibClear != null) {
            ibClear.setPadding(padding, 0, ibClear.getPaddingRight(), 0);
        }
    }

    /**
     * 设置清除按钮的左右内边距
     *
     * @param padding 距离值
     */
    public void setClearIconPaddingRight(int padding) {
        if (ibClear != null) {
            ibClear.setPadding(ibClear.getPaddingLeft(), 0, padding, 0);
        }
    }

    /**
     * 设置最小行数
     *
     * @param lines 行数
     */
    public void setMinLines(int lines) {
        if (etInput != null) {
            etInput.setMinLines(lines);
        }
    }

    /**
     * 设置最大行数
     *
     * @param lines 行数
     */
    public void setMaxLines(int lines) {
        if (etInput != null) {
            etInput.setMaxLines(lines);
        }
    }

    /**
     * 设置字体大小
     *
     * @param fontSize 字号
     */
    public void setTextSize(float fontSize) {
        if (etInput != null) {
            etInput.setTextSize(COMPLEX_UNIT_PX,fontSize);
        }
    }

    /**
     * 设置字体颜色
     *
     * @param textColor 字体颜色色值
     */
    public void setTextColor(@ColorInt int textColor) {
        if (etInput != null) {
            etInput.setTextColor(textColor);
        }
    }

    /**
     * 设置文字内容
     *
     * @param str 文字内容
     */
    public void setText(String str) {
        if (etInput != null) {
            etInput.setText(str);
        }
    }

    /**
     * 返回输入框内容
     *
     * @return
     */
    public String getText() {
        Editable text = null;
        if (etInput != null) {
            text = etInput.getText();
        }

        if (TextUtils.isEmpty(text)) {
            return null;
        } else {
            return text.toString();
        }
    }

    /**
     * 设置提示文字颜色
     *
     * @param colorHint 提示文字颜色
     */
    public void setHintTextColor(@ColorInt int colorHint) {
        if (etInput != null) {
            etInput.setHintTextColor(colorHint);
        }
    }

    /**
     * 设置提示文字
     *
     * @param hint 提示文字
     */
    public void setHint(CharSequence hint) {
        if (etInput != null) {
            etInput.setHint(hint);
        }
    }

    /**
     * 设置提示文字
     *
     * @param resId 提示文字资源id
     */
    public void setHint(@StringRes int resId) {
        if (etInput != null) {
            etInput.setHint(resId);
        }
    }

    /**
     * 设置左侧icon
     *
     * @param drawable 左侧图标
     */
    public void setIcon(@Nullable Drawable drawable) {
        if (ivIcon != null) {
            ivIcon.setImageDrawable(drawable);
        }
    }

    /**
     * 设置左侧icon
     *
     * @param resId 左侧图标资源id
     */
    public void setIcon(@DrawableRes int resId) {
        if (ivIcon != null) {
            ivIcon.setImageResource(resId);
        }
    }

    /**
     * 设置左侧icon
     *
     * @param bitmap 左侧图标
     */
    public void setIcon(Bitmap bitmap) {
        if (ivIcon != null) {
            ivIcon.setImageBitmap(bitmap);
        }
    }

    /**
     * 设置清除按钮的图标
     *
     * @param drawable 图标的drawable
     */
    public void setClearIcon(@Nullable Drawable drawable) {
        if (ibClear != null) {
            ibClear.setImageDrawable(drawable);
        }
    }

    /**
     * 设置清除按钮的图标
     *
     * @param resId 图标的资源id
     */
    public void setClearIcon(@DrawableRes int resId) {
        if (ibClear != null) {
            ibClear.setImageResource(resId);
        }
    }

    /**
     * 设置清除按钮的图标
     *
     * @param bitmap 清除按钮图标
     */
    public void setClearIcon(Bitmap bitmap) {
        if (ibClear != null) {
            ibClear.setImageBitmap(bitmap);
        }
    }

    /**
     * 设置底边的颜色和样式
     *
     * @param borderWidth  边线的宽度
     * @param focusedColor 获取焦点的时候线的颜色
     * @param normalColor  失去焦点的时候线的颜色
     */
    public void setBottomBorder(int borderWidth, @ColorInt int focusedColor, @ColorInt int normalColor) {
        //设置线的宽度
        if (borderView != null) {
            if (isShowBorder) {
                borderView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, borderWidth));
                borderView.setBackgroundColor(normalColor);
                borderView.setVisibility(VISIBLE);
            } else {
                //不显示底边
                borderView.setVisibility(GONE);
            }
        }

        focusedBorderColor = focusedColor;//输入框获取焦点时候的底边颜色
        normalBorderColor = normalColor;//输入框未获取焦点时候的底边颜色
    }

    /**
     * 设置过滤字符串的正则表达式
     *
     * @param regex 必须是正则表达式,否则过滤不正确
     */
    public void setDigits(String regex) {
        this.digits = regex;
    }

    /**
     * 设置字符串最大输入长度
     *
     * @param maxLength 字符串最大输入长度
     */
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        if (etInput != null) {
            etInput.setFilters(new InputFilter[]{inputFilter, new InputFilter.LengthFilter(maxLength)});
        }
    }

}
