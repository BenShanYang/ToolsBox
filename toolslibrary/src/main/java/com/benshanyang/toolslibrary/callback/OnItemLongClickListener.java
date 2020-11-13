package com.benshanyang.toolslibrary.callback;

import android.view.View;

/**
 * @ClassName: OnItemLongClickListener
 * @Description: java类作用描述
 * @Author: mayn
 * @Date: 2020/6/2 11:46
 */
public interface OnItemLongClickListener<T> {
    void onItemClick(T bean, int position);
}
