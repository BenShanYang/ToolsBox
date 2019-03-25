package com.benshanyang.toolslibrary.callback;

import android.view.View;

/**
 * 类描述: RecyclerView列表item点击事件 </br>
 * 时间: 2019/3/25 16:54
 *
 * @author YangKuan
 * @version 1.0.0
 * @since
 */
public interface OnItemClickListener<T> {
    void onItemClick(View view,T bean, int position);
}
