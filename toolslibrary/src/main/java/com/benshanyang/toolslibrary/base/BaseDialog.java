package com.benshanyang.toolslibrary.base;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.benshanyang.toolslibrary.R;

/**
 * @ClassName: BaseDialog
 * @Description: 弹窗的基类
 * @Author: YangKuan
 * @Date: 2020/9/9 18:06
 */
public abstract class BaseDialog extends Dialog {

    public BaseDialog(@NonNull Context context) {
        super(context, R.style.BaseDialogTheme);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        windowConfig();
    }

    @Override
    public void setContentView(@NonNull View view) {
        super.setContentView(view);
        windowConfig();
    }

    @Override
    public void setContentView(@NonNull View view, @Nullable ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        windowConfig();
    }

    private void windowConfig() {
        Window window = getWindow();
        if (window != null) {
            window.setWindowAnimations(R.style.BaseDialogWindowAnim);
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
            window.setGravity(getGravity());
        }
    }

    /**
     * 获取显示内容的居中方式
     *
     * @return Gravity.CENTER、Gravity.BOTTOM 、Gravity.CENTER_HORIZONTAL 等等
     */
    public abstract int getGravity();

}
