package com.benshanyang.toolslibrary.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * @ClassName: BaseParentFragment
 * @Description: java类作用描述
 * @Author: mayn
 * @Date: 2020/11/12 10:46
 */
public abstract class BaseParentFragment extends Fragment {

    public Context context;
    public Activity activity;
    private Toast toast = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        activity = getActivity();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view, savedInstanceState);
        setListener(view, savedInstanceState);
    }

    public abstract void initView(@NonNull View view, @Nullable Bundle savedInstanceState);

    public abstract void setListener(@NonNull View view, @Nullable Bundle savedInstanceState);

    /**
     * 获取资源文件中的颜色
     *
     * @param id 颜色的资源id
     * @return 返回颜色的色值
     */
    @ColorInt
    public int getResColor(@ColorRes int id) {
        try {
            return getResources().getColor(id);
        } catch (Exception e) {
            return Color.TRANSPARENT;
        }
    }

    /**
     * 获取资源文件图片或者drawable
     *
     * @param id 资源文件的id
     * @return 返回的资源文件
     */
    public Drawable getResDrawable(@DrawableRes int id) {
        try {
            return getResources().getDrawable(id);
        } catch (Exception e) {
            return new ColorDrawable(Color.TRANSPARENT);
        }
    }

    /**
     * 获取布局文件
     *
     * @param resource 布局id
     * @return 返回的布局view
     */
    public View getLayout(@LayoutRes int resource) {
        return LayoutInflater.from(activity).inflate(resource, null);
    }

    /**
     * 获取布局文件
     *
     * @param resource 布局id
     * @param root     布局要加入的父布局ViewGroup
     * @return 返回的布局view
     */
    public View getLayout(@LayoutRes int resource, @Nullable ViewGroup root) {
        return LayoutInflater.from(activity).inflate(resource, root);
    }

    /**
     * 获取布局文件
     *
     * @param resource     布局id
     * @param root         布局要加入的父布局ViewGroup
     * @param attachToRoot
     * @return 返回的布局view
     */
    public View getLayout(@LayoutRes int resource, @Nullable ViewGroup root, boolean attachToRoot) {
        return LayoutInflater.from(activity).inflate(resource, root, attachToRoot);
    }

    /**
     * 弹出提示
     */
    public void showToast(CharSequence message) {
        if (message != null) {
            if (toast == null) {
                toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM, 0, dp2px(context, 100));
            }
            toast.setText(message);
            toast.show();
        }
    }

    /**
     * 弹出提示
     */
    public void showToast(CharSequence message, int gravity) {
        if (message != null) {
            if (toast == null) {
                toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                toast.setGravity(gravity, 0, 0);
            }
            toast.setText(message);
            toast.show();
        }
    }

    /**
     * 弹出提示
     */
    public void showToast(CharSequence message, int gravity, int xOffset, int yOffset) {
        if (message != null) {
            if (toast == null) {
                toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                toast.setGravity(gravity, 0, 0);
            }
            toast.setText(message);
            toast.show();
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context
     * @param dpValue
     * @return
     */
    public int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param context
     * @param spValue
     * @return
     */
    public int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 打开指定Activity
     *
     * @param clazz 指定Activity
     */
    public void toActivity(Class<?> clazz) {
        if (activity != null && !activity.isDestroyed() && !activity.isFinishing()) {
            activity.startActivity(new Intent(activity, clazz));
        }
    }

    /**
     * 打开指定Activity
     *
     * @param clazz  指定Activity
     * @param bundle 携带的数据源
     */
    public void toActivity(Class<?> clazz, Bundle bundle) {
        if (activity != null && !activity.isDestroyed() && !activity.isFinishing()) {
            Intent intent = new Intent(activity, clazz);
            intent.putExtras(bundle);
            activity.startActivity(intent);
        }
    }

    /**
     * 打开指定Activity
     *
     * @param clazz       指定Activity
     * @param requestCode 请求码
     */
    public void toActivity(Class<?> clazz, int requestCode) {
        if (activity != null && !activity.isDestroyed() && !activity.isFinishing()) {
            activity.startActivityForResult(new Intent(activity, clazz), requestCode);
        }
    }

    /**
     * 打开指定Activity
     *
     * @param clazz       指定Activity
     * @param requestCode 请求码
     */
    public void toActivity(Class<?> clazz, long requestCode) {
        if (activity != null && !activity.isDestroyed() && !activity.isFinishing()) {
            Intent intent = new Intent();
            intent.setClass(activity, clazz);
            intent.putExtra("requestCode", requestCode);
            activity.startActivity(intent);
        }
    }

    /**
     * 打开指定Activity
     *
     * @param clazz       指定Activity
     * @param bundle      数据bundle
     * @param requestCode 请求码
     */
    public void toActivity(Class<?> clazz, Bundle bundle, long requestCode) {
        if (activity != null && !activity.isDestroyed() && !activity.isFinishing()) {
            Intent intent = new Intent();
            intent.setClass(activity, clazz);
            intent.putExtra("requestCode", requestCode);
            intent.putExtras(bundle);
            activity.startActivity(intent);
        }
    }

    /**
     * 打开指定Activity
     *
     * @param clazz       指定Activity
     * @param bundle      携带的数据源
     * @param requestCode 请求码
     */
    public void toActivity(Class<?> clazz, Bundle bundle, int requestCode) {
        if (activity != null && !activity.isDestroyed() && !activity.isFinishing()) {
            Intent intent = new Intent(activity, clazz);
            intent.putExtras(bundle);
            activity.startActivityForResult(intent, requestCode);
        }
    }

}
