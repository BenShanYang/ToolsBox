package com.benshanyang.toolslibrary.callback;

import android.text.TextWatcher;

/**
 * 类描述: 输入框的监听事件 </br>
 * 时间: 2019/3/20 10:49
 *
 * @author YangKuan
 * @version 1.0.0
 * @since
 */
public abstract class TextWatchListener implements TextWatcher {
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }
}
