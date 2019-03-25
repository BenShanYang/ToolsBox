package com.benshanyang.toolslibrary.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 类描述: 网格列表分割线 </br>
 * 时间: 2019/3/25 16:46
 *
 * @author YangKuan
 * @version 1.0.0
 * @since
 */
public class GridDivider extends RecyclerView.ItemDecoration {

    @ColorInt private int borderColor = 0xFFD5D5D5;//分割线颜色
    private int borderWidth = 2;//分割线宽度

    public GridDivider() {
    }

    public GridDivider(@ColorInt int borderColor, int borderWidth) {
        this.borderColor = borderColor;
        this.borderWidth = borderWidth;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int column = 0;//网格列表的列数
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager){
            //获取网格列表的列数
            column = ((GridLayoutManager) layoutManager).getSpanCount();
        }
        //先初始化一个Paint来简单指定一下Canvas的颜色，就黑的吧！
        Paint paint = new Paint();
        paint.setStrokeWidth(borderWidth);
        paint.setAntiAlias(true);
        //paint.setColor(parent.getContext().getResources().getColor(android.R.color.black));
        paint.setColor(borderColor);

        //获得RecyclerView中总条目数量
        int childCount = parent.getChildCount();

        //遍历一下
        for (int i = 0; i < childCount; i++) {
            //获得子View，也就是一个条目的View，准备给他画上边框
            View childView = parent.getChildAt(i);

            //先获得子View的长宽，以及在屏幕上的位置，方便我们得到边框的具体坐标
            float x = childView.getX();
            float y = childView.getY();
            int width = childView.getWidth();
            int height = childView.getHeight();

            //根据这些点画条目的四周的线
            c.drawLine(x, y, x + width, y, paint);//上边线
            c.drawLine(x, y, x, y + height, paint);//左边线
            c.drawLine(x + width, y, x + width, y + height, paint);//右边线
            c.drawLine(x, y + height, x + width, y + height, paint);//下边线

        }
        super.onDraw(c, parent, state);
    }
}
