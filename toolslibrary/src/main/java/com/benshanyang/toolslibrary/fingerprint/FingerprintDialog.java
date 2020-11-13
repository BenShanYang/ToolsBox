package com.benshanyang.toolslibrary.fingerprint;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.widget.TextView;

import com.benshanyang.toolslibrary.R;
import com.benshanyang.toolslibrary.base.BaseDialog;

/**
 * @ClassName: FingerprintDialog
 * @Description: Android9系统以下指纹识别的对话框
 * @Author: YangKuan
 * @Date: 2020/9/10 9:11
 */
class FingerprintDialog extends BaseDialog {
    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvHelpTip;
    private TextView tvFail;
    private TextView tvSuccess;
    private TextView tvCancel;
    private OnCancelListener onCancelListener;

    public FingerprintDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_fingerprint);
        setCanceledOnTouchOutside(false);
    }

    @Override
    public int getGravity() {
        return Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle = findViewById(R.id.tv_title);//主标题
        tvDescription = findViewById(R.id.tv_description);//改变的副标题
        tvHelpTip = findViewById(R.id.tv_help_tip);//手指触摸状态提示

        tvFail = findViewById(R.id.tv_fail);//错误的提示信息
        tvSuccess = findViewById(R.id.tv_success);//指纹验证成功的提示

        tvCancel = findViewById(R.id.tv_cancel);//取消按钮

        setText(tvTitle, "指纹验证", 0.8f, 0xFF333333);
        tvDescription.setText("请验证已有指纹");
        setText(tvCancel, "取消", 0.3f, 0xFF0383A3);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCancelListener != null) {
                    onCancelListener.onCancel();
                }
                dismiss();
            }
        });
    }

    /**
     * 设置弹窗标题
     *
     * @param title
     * @return
     */
    public FingerprintDialog setDialogTitle(CharSequence title) {
        if (tvTitle != null && !TextUtils.isEmpty(title)) {
            setText(tvTitle, title, 0.8f, 0xFF333333);
        }
        return FingerprintDialog.this;
    }

    /**
     * 设置弹窗副标题
     *
     * @param description
     * @return
     */
    public FingerprintDialog setDescription(CharSequence description) {
        if (tvDescription != null && !TextUtils.isEmpty(description)) {
            setText(tvDescription, description, 0.8f, 0xFF333333);
        }
        return FingerprintDialog.this;
    }

    /**
     * 设置指纹提示信息
     *
     * @param text
     */
    public FingerprintDialog setHelpTip(CharSequence text) {
        if (tvHelpTip != null && !TextUtils.isEmpty(text)) {
            tvHelpTip.setText(text);
        }
        return FingerprintDialog.this;
    }

    /**
     * 设置错误提示
     *
     * @param text
     */
    public FingerprintDialog setFail(CharSequence text) {
        if (tvFail != null && !TextUtils.isEmpty(text)) {
            tvSuccess.setVisibility(View.GONE);
            tvFail.setVisibility(View.VISIBLE);
            tvFail.setText(text);
            tvFail.animate().translationX(20)
                    .setInterpolator(new CycleInterpolator(4))
                    .setDuration(500)
                    .start();
        }
        return FingerprintDialog.this;
    }

    /**
     * 设置指纹验证成功的提示
     *
     * @param text
     */
    public FingerprintDialog setSuccess(CharSequence text) {
        if (tvSuccess != null && !TextUtils.isEmpty(text)) {
            tvSuccess.setText(text);
            tvSuccess.setVisibility(View.VISIBLE);
            tvFail.setVisibility(View.GONE);
        }
        return FingerprintDialog.this;
    }

    public FingerprintDialog setOnCancelListener(OnCancelListener onCancelListener) {
        this.onCancelListener = onCancelListener;
        return FingerprintDialog.this;
    }

    /**
     * 设置字体粗细
     *
     * @param textView  显示文字的控件
     * @param text      要显示的文字
     * @param thickness 文字的粗细程度
     * @param color     文字的颜色
     */
    public static void setText(@NonNull TextView textView, CharSequence text, final float thickness, @ColorInt final int color) {
        if (textView != null && !TextUtils.isEmpty(text)) {
            SpannableStringBuilder spannableString = new SpannableStringBuilder(text);
            spannableString.setSpan(new CharacterStyle() {
                @Override
                public void updateDrawState(TextPaint textPaint) {
                    //tp.setFakeBoldText(true);//一种伪粗体效果，比原字体加粗的效果弱一点
                    textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                    textPaint.setColor(color);//字体颜色
                    textPaint.setStrokeWidth(thickness > 0 ? thickness : 0);//控制字体加粗的程度
                }
            }, 0, TextUtils.isEmpty(text) ? 0 : text.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            textView.setText(spannableString);
        }
    }

    /**
     * 点击关闭按钮取消弹窗的时候回调方法
     */
    public interface OnCancelListener {
        void onCancel();
    }
}
