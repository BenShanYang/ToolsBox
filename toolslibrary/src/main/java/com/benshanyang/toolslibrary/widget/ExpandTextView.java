package com.benshanyang.toolslibrary.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * @ClassName: ExpandTextView
 * @Description: 可展开折叠的文字展示控件 https://blog.csdn.net/fshj1106/article/details/106208749/
 * @Author: YangKuan
 * @Date: 2020/11/11 14:54
 */
public class ExpandTextView extends AppCompatTextView {

    public static final int NORMAL_TEXTCOLOR = -1;
    public static final int NORMAL_TEXTSIZE = -1;

    private String TEXT_EXPAND = "展开";
    private String TEXT_CLOSE = "收起";

    private int initWidth = 0;// TextView可展示宽度
    private int maxLines = Integer.MAX_VALUE;// TextView最大行数
    private String originText = "";// 原始内容文本
    private String iconFontAssetsPath = "";//文字图标库的路径

    private TextTooLong textTooLong;//文字是否超过规定的最大行数监听器
    private ExpandStateListener expandStateListener;//文字展开收起监听器

    @ColorInt
    private int expandTextColor = -1;//展开文字颜色
    @ColorInt
    private int closeTextColor = -1;//收起文字言责

    private float expandTextSize = -1;//展开文字大小
    private float closeTextSize = -1;//收起文字大小

    private boolean expandUnderLine = false;//展开文字是否有下划线
    private boolean closeUnderLine = false;//收起文字是否有下划线

    public ExpandTextView(Context context) {
        super(context);
    }

    public ExpandTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = initWidth(widthMeasureSpec);
        if (initWidth != width) {
            if (initWidth != 0) {
                ExpandTextView.super.setMaxLines(maxLines);
            }
            initWidth = width;
            setCloseText();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setText(String text) {
        if (text != null && !TextUtils.equals(originText, text)) {
            originText = text;
        }
        if (initWidth != 0) {
            ExpandTextView.super.setMaxLines(maxLines);
            setCloseText();
        }
    }

    private void setCloseText() {
        boolean appendShowAll = false;// true 不需要展开收起功能， false 需要展开收起功能
        maxLines = getMaxLines();
        String workingText = new StringBuilder(originText).toString();
        if (maxLines != -1) {
            Layout layout = createWorkingLayout(workingText);
            if (layout.getLineCount() > maxLines) {
                //获取一行显示字符个数，然后截取字符串数
                // 收起状态原始文本截取展示的部分
                workingText = originText.substring(0, layout.getLineEnd(maxLines - 1)).trim();
                String showText = originText.substring(0, layout.getLineEnd(maxLines - 1)).trim() + "... " + TEXT_EXPAND;
                Layout layout2 = createWorkingLayout(showText);
                // 对workingText进行-1截取，直到展示行数==最大行数，并且添加 SPAN_CLOSE 后刚好占满最后一行
                while (layout2.getLineCount() > maxLines) {
                    int lastSpace = workingText.length() - 1;
                    if (lastSpace == -1) {
                        break;
                    }
                    workingText = workingText.substring(0, lastSpace);
                    layout2 = createWorkingLayout(workingText + "... " + TEXT_EXPAND);
                }
                appendShowAll = true;
                workingText = workingText + "... ";
            }
        }

        if (appendShowAll) {
            //文字超过指定行数
            String content = workingText + TEXT_EXPAND;
            SpannableString SPAN_CLOSE = new SpannableString(content);
            SPAN_CLOSE.setSpan(new ClickableSpan() {
                @Override
                public void updateDrawState(TextPaint textPaint) {
                    //设置展开文字样式
                    if (expandTextColor != -1) {
                        textPaint.setColor(expandTextColor);
                    }
                    if (expandTextSize != -1) {
                        textPaint.setTextSize(dip2px(expandTextSize));
                    }
                    textPaint.setUnderlineText(expandUnderLine);
                    if (!TextUtils.isEmpty(iconFontAssetsPath)) {
                        textPaint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), iconFontAssetsPath));
                    }
                }

                @Override
                public void onClick(@NonNull View widget) {
                    ExpandTextView.super.setMaxLines(Integer.MAX_VALUE);
                    setExpandText(originText);

                    //展开
                    if (expandStateListener != null) {
                        expandStateListener.expandText(true);
                    }
                }
            }, workingText.length(), content.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            ExpandTextView.super.setText(SPAN_CLOSE, BufferType.SPANNABLE);
            setMovementMethod(LinkMovementMethod.getInstance());
            //注意，设置了ClickableSpan后，点击将会触发TextView在该区域的高亮效果，即一个背景变化，我们手动去除
            setHighlightColor(Color.TRANSPARENT);

        } else {
            //文字没有超过指定行数
            ExpandTextView.super.setText(workingText, BufferType.NORMAL);
        }

        if (textTooLong != null) {
            textTooLong.tooLong(appendShowAll);
        }
    }

    private void setExpandText(String text) {
        String content;
        String textStr;

        Layout layout1 = createWorkingLayout(text);
        Layout layout2 = createWorkingLayout(text + TEXT_CLOSE);

        // 展示全部原始内容时 如果 TEXT_CLOSE 需要换行才能显示完整，则直接将TEXT_CLOSE展示在下一行
        if (layout2.getLineCount() > layout1.getLineCount()) {
            textStr = text + "\n";
        } else {
            textStr = text + " ";
        }
        content = textStr + TEXT_CLOSE;

        SpannableString SPAN_EXPAND = new SpannableString(content);
        SPAN_EXPAND.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint textPaint) {
                //收起文字样式
                if (closeTextColor != -1) {
                    textPaint.setColor(closeTextColor);
                }
                if (closeTextSize != -1) {
                    textPaint.setTextSize(dip2px(closeTextSize));
                }
                textPaint.setUnderlineText(closeUnderLine);
                if (!TextUtils.isEmpty(iconFontAssetsPath)) {
                    textPaint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), iconFontAssetsPath));
                }
            }

            @Override
            public void onClick(@NonNull View widget) {
                ExpandTextView.super.setMaxLines(maxLines);
                setCloseText();

                //收起
                if (expandStateListener != null) {
                    expandStateListener.expandText(false);
                }
            }
        }, textStr.length(), content.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        ExpandTextView.super.setText(SPAN_EXPAND, BufferType.SPANNABLE);
        setMovementMethod(LinkMovementMethod.getInstance());
        //注意，设置了ClickableSpan后，点击将会触发TextView在该区域的高亮效果，即一个背景变化，我们手动去除
        setHighlightColor(Color.TRANSPARENT);
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
     * 设置文字大小
     *
     * @param dipValue
     * @return
     */
    private int dip2px(float dipValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 设置文字图标库路径
     *
     * @param iconFontAssetsPath
     */
    public void setIconFontAssetsPath(String iconFontAssetsPath) {
        this.iconFontAssetsPath = iconFontAssetsPath;
    }

    /**
     * 设置展开图标
     *
     * @param fontCode 图标编码
     * @param isFront  true图标在前面 false图标在后面
     */
    public void setExpandIcon(String fontCode, boolean isFront) {
        if (isFront) {
            TEXT_EXPAND = fontCode + TEXT_EXPAND;
        } else {
            TEXT_EXPAND = TEXT_EXPAND + fontCode;
        }
    }

    /**
     * 设置收起图标
     *
     * @param fontCode 图标编码
     * @param isFront  true图标在前面 false图标在后面
     */
    public void setCloseIcon(String fontCode, boolean isFront) {
        if (isFront) {
            TEXT_CLOSE = fontCode + TEXT_CLOSE;
        } else {
            TEXT_CLOSE = TEXT_CLOSE + fontCode;
        }
    }

    /**
     * 设置展开文字的样式
     *
     * @param textColor
     * @param textSize
     * @param underLine
     */
    public void setExpandTextStyle(@ColorInt int textColor, float textSize, boolean underLine) {
        this.expandTextColor = textColor;
        this.expandTextSize = textSize;
        this.expandUnderLine = underLine;
    }

    /**
     * 设置收起文字的样式
     *
     * @param textColor
     * @param textSize
     * @param underLine
     */
    public void setCloseTextStyle(@ColorInt int textColor, float textSize, boolean underLine) {
        this.closeTextColor = textColor;
        this.closeTextSize = textSize;
        this.closeUnderLine = underLine;
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
