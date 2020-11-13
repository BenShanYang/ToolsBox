package com.benshanyang.toolslibrary.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * @ClassName: SimpleExpandTextView
 * @Description: java类作用描述
 * @Author: mayn
 * @Date: 2020/11/11 14:53
 */
public class SimpleExpandTextView extends AppCompatTextView {

    private int initWidth = 0;// TextView可展示宽度
    private int maxLines = Integer.MAX_VALUE;// TextView最大行数
    private boolean isTooLong = false;//文字是否超过超度限制
    private boolean isExpand = false;//当前展开的状态
    private String originText = "";// 原始内容文本

    private TextTooLong textTooLong;//文字是否超过规定的最大行数监听器
    private ExpandStateListener expandStateListener;//文字展开收起监听器

    public SimpleExpandTextView(Context context) {
        super(context);
        init();
    }

    public SimpleExpandTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SimpleExpandTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        maxLines = getMaxLines();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = initWidth(widthMeasureSpec);
        if (initWidth != width) {
            initWidth = width;
            closeText();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setText(String text) {
        if (text != null && !TextUtils.equals(originText, text)) {
            originText = text;
        }
        if (initWidth != 0) {
            closeText();
        }
    }

    /**
     * 将文字折叠
     */
    public void closeText() {
        isExpand = false;//收缩状态
        isTooLong = false;// true 不需要展开收起功能， false 需要展开收起功能
        String workingText = new StringBuilder(originText).toString();
        if (maxLines != -1) {
            Layout layout = createWorkingLayout(workingText);
            if (layout.getLineCount() > maxLines) {
                //获取一行显示字符个数，然后截取字符串数
                // 收起状态原始文本截取展示的部分
                workingText = originText.substring(0, layout.getLineEnd(maxLines - 1)).trim();
                String showText = originText.substring(0, layout.getLineEnd(maxLines - 1)).trim() + "...";
                Layout layout2 = createWorkingLayout(showText);
                // 对workingText进行-1截取，直到展示行数==最大行数，并且添加 SPAN_CLOSE 后刚好占满最后一行
                while (layout2.getLineCount() > maxLines) {
                    int lastSpace = workingText.length() - 1;
                    if (lastSpace == -1) {
                        break;
                    }
                    workingText = workingText.substring(0, lastSpace);
                    layout2 = createWorkingLayout(workingText + "...");
                }
                isTooLong = true;
                workingText = workingText + "...";
            }
        }

        SimpleExpandTextView.super.setMaxLines(maxLines);
        SimpleExpandTextView.super.setText(workingText);

        if (textTooLong != null) {
            //true文字超过指定行数 false文字没有超过指定行数
            textTooLong.tooLong(isTooLong);
        }

        //收起
        if (expandStateListener != null) {
            expandStateListener.expandText(false);
        }
    }

    /**
     * 展开折叠的文字
     */
    public void expandText() {
        isExpand = true;//展开状态
        SimpleExpandTextView.super.setMaxLines(Integer.MAX_VALUE);
        SimpleExpandTextView.super.setText(originText);

        //展开
        if (expandStateListener != null) {
            expandStateListener.expandText(true);
        }
    }

    /**
     * 获取测量大小
     */
    private int initWidth(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            //match_parent或确切值
            //确切大小,所以将得到的尺寸给view
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            //wrap_content
            //如果View的宽高模式为AT_MOST （包裹内容），最终宽高也是填充父控件
            result = Math.max(0, specSize);
        } else {
            result = 0;
        }
        return result;
    }

    //返回textview的显示区域的layout，该textview的layout并不会显示出来，只是用其宽度来比较要显示的文字是否过长
    @SuppressLint("ObsoleteSdkInt")
    private Layout createWorkingLayout(@NonNull String workingText) {
        //int width = getContext().getResources().getDisplayMetrics().widthPixels;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return new StaticLayout(workingText, getPaint(), initWidth - getPaddingLeft() - getPaddingRight(),
                    Layout.Alignment.ALIGN_NORMAL, getLineSpacingMultiplier(), getLineSpacingExtra(), false);
        } else {
            return new StaticLayout(workingText, getPaint(), initWidth - getPaddingLeft() - getPaddingRight(),
                    Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        }
    }

    /**
     * 设置文字超过最大长度监听器
     *
     * @param textTooLong
     */
    public void addTextTooLong(TextTooLong textTooLong) {
        this.textTooLong = textTooLong;
    }

    /**
     * 设置文字展开收起监听
     *
     * @param expandStateListener
     */
    public void addExpandStateListener(ExpandStateListener expandStateListener) {
        this.expandStateListener = expandStateListener;
    }

    /**
     * 获取文字是否超过了最大行数限制
     *
     * @return true超过了 false没有超过
     */
    public boolean isExpand() {
        return isExpand;
    }

    /**
     * 文字是否超过规定的最大行数监听器
     */
    public interface TextTooLong {
        /**
         * 文字是否太长
         *
         * @param tooLong true文字过长超过最大行数 false文字没有超过指定行数
         */
        void tooLong(boolean tooLong);
    }

    /**
     * 文字收起展开监听
     */
    public interface ExpandStateListener {
        /**
         * 文字是否展开
         *
         * @param expand true展开 false收起
         */
        void expandText(boolean expand);
    }

}
