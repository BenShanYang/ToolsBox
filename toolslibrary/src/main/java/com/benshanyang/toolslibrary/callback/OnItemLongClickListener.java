package com.benshanyang.toolslibrary.callback;

/**
 * 类描述: RecyclerView列表item长点击事件 </br>
 * 时间: 2020/11/13 14:58
 *
 * @author YangKuan
 * @version 1.0.0
 * @since
 */
public interface OnItemLongClickListener<T> {
    void onItemClick(T bean, int position);
}
