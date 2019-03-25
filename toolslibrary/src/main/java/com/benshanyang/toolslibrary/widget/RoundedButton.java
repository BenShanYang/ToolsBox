package com.benshanyang.toolslibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;

import com.benshanyang.toolslibrary.R;
import com.benshanyang.toolslibrary.drawable.RoundedButtonDrawable;

/**
 * 类描述: 自定义圆角按钮 </br>
 * 时间: 2019/3/21  17:25
 *
 * @author YangKuan
 * @version 1.0.0
 * @since
 */
public class RoundedButton extends AppCompatTextView {

    private float cornerRadius = 0.0f;//圆角半径
    private float borderWidth = 0.0f;//边框宽度
    private int pressedBorderColor = Color.TRANSPARENT;//按下时边框颜色
    private int normalBorderColor = Color.TRANSPARENT;//未按下时边框颜色
    private int pressedBackgroundColor = Color.TRANSPARENT;//按下时背景色
    private int normalBackgroundColor = Color.TRANSPARENT;//未按下时背景色
    @ColorInt private int pressedTextColor = 0xFF333333;//按下时文字颜色
    @ColorInt private int normalTextColor = 0xFF333333;//未按下时文字颜色
    private int width = 0;//控件宽
    private int height = 0;//空间高
    private boolean isShowBorder = false;//是否显示边框
    private RoundedButtonDrawable drawable = null;//背景样式

    public RoundedButton(Context context) {
        super(context);
        init(context, null);
    }

    public RoundedButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundedButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundedButton);
        if (typedArray != null) {
            cornerRadius = typedArray.getDimension(R.styleable.RoundedButton_cornerRadius, 0);//圆角半径
            borderWidth = typedArray.getDimension(R.styleable.RoundedButton_borderWidth, 0);//边框宽度
            pressedBorderColor = typedArray.getColor(R.styleable.RoundedButton_pressedBorderColor, Color.TRANSPARENT);//按下时边框颜色
            normalBorderColor = typedArray.getColor(R.styleable.RoundedButton_normalBorderColor, Color.TRANSPARENT);//未按下时边框颜色
            pressedBackgroundColor = typedArray.getColor(R.styleable.RoundedButton_pressedBackgroundColor, Color.TRANSPARENT);//按下时背景色
            normalBackgroundColor = typedArray.getColor(R.styleable.RoundedButton_normalBackgroundColor, Color.TRANSPARENT);//未按下时背景色
            pressedTextColor = typedArray.getColor(R.styleable.RoundedButton_pressedTextColor, 0xFF333333);//按下时文字颜色
            normalTextColor = typedArray.getColor(R.styleable.RoundedButton_normalTextColor, 0xFF333333);//未按下时文字颜色
            isShowBorder = typedArray.getBoolean(R.styleable.RoundedButton_isShowBorder, false);//是否显示边框

            typedArray.recycle();
        }

        drawable = new RoundedButtonDrawable();
        setBackgroundDrawable(drawable);
        if(isShowBorder){
            drawable.isShowBorder(isShowBorder);
        }
        setting(normalBorderColor,normalBackgroundColor,normalTextColor);
        setGravity(Gravity.CENTER);
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(isClickable()){
            int listener = event.getAction();
            if(listener == MotionEvent.ACTION_DOWN){
                //按下
                setting(pressedBorderColor,pressedBackgroundColor,pressedTextColor);
            }else if(listener == MotionEvent.ACTION_MOVE){
                //移动
                setting(pressedBorderColor,pressedBackgroundColor,pressedTextColor);
            }else{
                setting(normalBorderColor,normalBackgroundColor,normalTextColor);
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 设置按钮相关属性
     * @param borderColor 边线颜色
     * @param bgColor 背景颜色
     * @param textColor 文字颜色
     */
    private void setting(@ColorInt int borderColor, @ColorInt int bgColor, @ColorInt int textColor){
        if (drawable != null) {
            drawable.setWidth(width);
            drawable.setHeight(height);
            drawable.setBorderWidth(borderWidth);
            drawable.setRadius(cornerRadius);
            drawable.setBorderColor(borderColor);
            drawable.setBackgroundColor(bgColor);
            setTextColor(textColor);
        }
    }

    /**
     * 是否显示边框
     * @param isShow
     */
    public void setShowBorder(boolean isShow){
        this.isShowBorder = isShow;
        if(drawable!=null){
            drawable.isShowBorder(isShow);
        }
    }

    /**
     * 设置按钮的圆角半径
     * @param cornerRadius 圆角半径
     */
    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
        if(drawable!=null){
            drawable.setRadius(cornerRadius);
        }
    }

    /**
     * 设置边框的宽度
     * @param borderWidth 边框的宽度
     */
    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
        if(drawable!=null){
            drawable.setBorderWidth(borderWidth);
        }
    }

    /**
     * 设置点击时的边框颜色
     * @param pressedBorderColor 边框的色值
     */
    public void setPressedBorderColor(int pressedBorderColor) {
        this.pressedBorderColor = pressedBorderColor;
    }

    /**
     * 设置未点击时的边框颜色
     * @param normalBorderColor 边框色值
     */
    public void setNormalBorderColor(int normalBorderColor) {
        this.normalBorderColor = normalBorderColor;
        if(drawable!=null){
            drawable.setBorderColor(normalBorderColor);
        }
    }

    /**
     * 设置点击时的背景色
     * @param pressedBackgroundColor 背景色值
     */
    public void setPressedBackgroundColor(int pressedBackgroundColor) {
        this.pressedBackgroundColor = pressedBackgroundColor;
    }

    /**
     * 设置未点击时的背景颜色
     * @param normalBackgroundColor 背景色值
     */
    public void setNormalBackgroundColor(int normalBackgroundColor) {
        this.normalBackgroundColor = normalBackgroundColor;
        if(drawable!=null){
            drawable.setBackgroundColor(normalBackgroundColor);
        }
    }

    /**
     * 设置点击时的文字颜色
     * @param pressedTextColor 色值
     */
    public void setPressedTextColor(int pressedTextColor) {
        this.pressedTextColor = pressedTextColor;
    }

    /**
     * 设置未点击时的文字颜色
     * @param normalTextColor 色值
     */
    public void setNormalTextColor(int normalTextColor) {
        this.normalTextColor = normalTextColor;
        setTextColor(normalTextColor);
    }

}
