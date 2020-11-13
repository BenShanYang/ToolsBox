package com.benshanyang.toolslibrary.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.benshanyang.toolslibrary.R;
import com.benshanyang.toolslibrary.utils.DensityUtils;
import com.benshanyang.toolslibrary.utils.TextUtils;

import static android.util.TypedValue.COMPLEX_UNIT_PX;

/**
 * 类描述: 倒计时按钮 </br>
 * 时间: 2019/3/28 17:04
 *
 * @author YangKuan
 * @version 1.0.0
 * @since
 */
public class CountDownButton extends FrameLayout {

    private float textSize = 0f;//文字大小
    private long millisInFuture = 10000L;//倒计时的总时长
    private long countDownInterval = 1000L;//倒计时的间隔
    private String startText = "获取验证码";//按钮获取验证码的默认文字
    private String restartText = "重新获取";//按钮重新获取验证码的默认文字
    private String unit = "秒";//计时单位
    private String timePrefix = "";//计时前缀

    private CountDownTimer countDownTimer;//计时器
    private TextView countDownText;//倒计时显示的控件
    private Drawable normalBg;//计时按钮正常状态下的背景样式
    private Drawable timingBg;//计时按钮计时状态下的背景样式
    private ColorStateList normalTextColor;//计时按钮正常状态下的文字颜色
    private ColorStateList timingTextColor;//计时按钮计时状态下的文字颜色

    public CountDownButton(Context context) {
        super(context);
        initWidget(context);
    }

    public CountDownButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttributeSet(context, attrs);
    }

    public CountDownButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributeSet(context, attrs);
    }

    private void initAttributeSet(Context context, AttributeSet attrs) {
        initWidget(context);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CountDownButton, 0, 0);
        if (typedArray != null) {
            for (int i = 0; i < typedArray.getIndexCount(); i++) {
                int attr = typedArray.getIndex(i);
                if (attr == R.styleable.CountDownButton_normalBackground) {
                    normalBg = typedArray.getDrawable(attr);//计时按钮正常状态下的背景样式
                } else if (attr == R.styleable.CountDownButton_timingBackground) {
                    timingBg = typedArray.getDrawable(attr);//计时按钮计时状态下的背景样式
                } else if (attr == R.styleable.CountDownButton_normalTextColor) {
                    normalTextColor = typedArray.getColorStateList(attr);//设置密码字体颜色
                } else if (attr == R.styleable.CountDownButton_timingTextColor) {
                    timingTextColor = typedArray.getColorStateList(attr);//设置密码字体颜色
                } else if (attr == R.styleable.CountDownButton_duration) {
                    millisInFuture = typedArray.getInteger(attr, 60000);//总时长
                } else if (attr == R.styleable.CountDownButton_interval) {
                    countDownInterval = typedArray.getInteger(attr, 10000);//倒计时间隔
                } else if (attr == R.styleable.CountDownButton_startText) {
                    startText = typedArray.getString(attr);//按钮获取验证码的默认文字
                } else if (attr == R.styleable.CountDownButton_restartText) {
                    restartText = typedArray.getString(attr);//按钮重新获取验证码的默认文字
                } else if (attr == R.styleable.CountDownButton_unit) {
                    unit = typedArray.getString(attr);//计时单位
                } else if (attr == R.styleable.CountDownButton_timePrefix) {
                    timePrefix = typedArray.getString(attr);//计时前缀
                } else if (attr == R.styleable.CountDownButton_textSize) {
                    textSize = typedArray.getDimension(attr, 0f);//文字字号
                }
            }
            typedArray.recycle();
        }

        //设置正常状态下的背景样式
        if (normalBg != null) {
            setBackground(normalBg);
        }

        if (countDownText != null) {
            //设置正常状态下的文字颜色
            if (normalTextColor != null) {
                countDownText.setTextColor(normalTextColor);
            }
            //按钮获取验证码的默认文字
            if (startText != null) {
                countDownText.setText(startText);
            }

            countDownText.setTextSize(DensityUtils.px2sp(context, textSize));
        }
    }

    private void initWidget(Context context) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        countDownText = new TextView(context);
        countDownText.setClickable(false);
        countDownText.setFocusable(false);
        countDownText.setLayoutParams(params);
        countDownText.setGravity(Gravity.CENTER);
        countDownText.setBackgroundColor(Color.TRANSPARENT);

        addView(countDownText);
    }

    /**
     * 设置文字大小
     *
     * @param textSize 文字字号
     */
    public void setTextSize(float textSize) {
        if (countDownText != null) {
            countDownText.setTextSize(COMPLEX_UNIT_PX, textSize);
        }
    }

    /**
     * 设置倒计时的总时间
     *
     * @param duration 总时间
     */
    public void setDuration(int duration) {
        millisInFuture = duration;
    }

    /**
     * 设置倒计时的间隔时间
     *
     * @param interval 计时间隔
     */
    public void setInterval(int interval) {
        countDownInterval = interval;
    }

    /**
     * 按钮获取验证码的默认文字
     *
     * @param startText 开始的默认文字
     */
    public void setStartText(String startText) {
        if (countDownText != null) {
            if (!TextUtils.isEmpty(startText)) {
                this.startText = startText;
            }
            countDownText.setText(this.startText);
        }
    }

    /**
     * 按钮重新获取验证码的默认文字
     *
     * @param restartText 重新开始的默认文字
     */
    public void setRestartText(String restartText) {
        if (!TextUtils.isEmpty(restartText)) {
            this.restartText = restartText;
        }
    }

    /**
     * 计时单位
     *
     * @param unit 单位
     */
    public void setUnit(String unit) {
        if (!TextUtils.isEmpty(unit)) {
            this.unit = unit;
        }
    }

    /**
     * 计时字符串前缀
     *
     * @param timePrefix 计时字符串前缀
     */
    public void setTimePrefix(String timePrefix) {
        if (!TextUtils.isEmpty(timePrefix)) {
            this.timePrefix = timePrefix;
        }
    }

    /**
     * 设置默认按钮的字体颜色
     *
     * @param normalTextColor 默认按钮的字体颜色
     */
    public void setNormalTextColor(ColorStateList normalTextColor) {
        if (normalTextColor != null) {
            this.normalTextColor = normalTextColor;
            if (countDownText != null) {
                countDownText.setTextColor(normalTextColor);
            }
        }
    }

    /**
     * 设置计时中的字体颜色
     *
     * @param timingTextColor 计时中的字体颜色
     */
    public void setTimingTextColor(ColorStateList timingTextColor) {
        if (timingTextColor != null) {
            this.timingTextColor = timingTextColor;
        }
    }

    /**
     * 设置默认按钮的字体颜色
     *
     * @param normalTextColour 默认按钮的字体颜色
     */
    public void setNormalTextColor(@ColorInt int normalTextColour) {
        ColorStateList normalTextColor = ColorStateList.valueOf(normalTextColour);
        if (normalTextColor != null) {
            this.normalTextColor = normalTextColor;
            if (countDownText != null) {
                countDownText.setTextColor(normalTextColor);
            }
        }
    }

    /**
     * 设置计时中的字体颜色
     *
     * @param timingTextColour 计时中的字体颜色
     */
    public void setTimingTextColor(@ColorInt int timingTextColour) {
        ColorStateList timingTextColor = ColorStateList.valueOf(timingTextColour);
        if (timingTextColor != null) {
            this.timingTextColor = timingTextColor;
        }
    }

    /**
     * 设置正常状态下按钮背景色
     *
     * @param color 背景色
     */
    public void setNormalBackgroundColor(@ColorInt int color) {
        Drawable normalBgColor = new ColorDrawable(color);
        if (normalBgColor != null) {
            normalBg = normalBgColor;
            setBackground(normalBg);
        }
    }

    /**
     * 设置计时中的按钮背景色
     *
     * @param color 背景色
     */
    public void setTimingBackgroundColor(@ColorInt int color) {
        Drawable timingBgColor = new ColorDrawable(color);
        if (timingBgColor != null) {
            timingBg = timingBgColor;
        }
    }

    /**
     * 设置正常状态下按钮背景样式
     *
     * @param drawable 背景样式
     */
    public void setNormalBackground(Drawable drawable) {
        if (drawable != null) {
            this.normalBg = drawable;
            setBackground(normalBg);
        }
    }

    /**
     * 设置计时中的按钮背景样式
     *
     * @param drawable 背景样式
     */
    public void setTimingBackground(Drawable drawable) {
        if (drawable != null) {
            this.timingBg = drawable;
        }
    }

    /**
     * 开始计时
     */
    public void startTiming() {
        countDownTimer = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                setClickable(false);
                if (timingBg != null) {
                    setBackground(timingBg);
                }
                if (timingTextColor != null) {
                    countDownText.setTextColor(timingTextColor);
                }
                countDownText.setText(String.format("%s%d%s", timePrefix == null ? "" : timePrefix, millisUntilFinished / 1000, unit == null ? "" : unit));
            }

            @Override
            public void onFinish() {
                setClickable(true);
                if (normalBg != null) {
                    setBackground(normalBg);
                }
                if (normalTextColor != null) {
                    countDownText.setTextColor(normalTextColor);
                }
                countDownText.setText(restartText);
            }
        };
        countDownTimer.start();
    }

    /**
     * 取消计时
     */
    public void cancelTiming() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

}
