package com.benshanyang.toolsbox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 * @ClassName: BaseActivity
 * @Description: Activity的基类
 * @Author: YangKuan
 * @Date: 2019/11/5 12:14
 */
public abstract class BaseActivity extends AppCompatActivity {

    public Activity activity;
    private Toast toast = null;

    public abstract void initView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置窗体为没有标题的模式
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //android系统大于6.0系统则设置标题栏字体问深色
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //android系统大于5.0系统则设置状态栏灰色
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(0x33000000);

            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            //解决华为手机底部导航栏遮盖问题  注掉此句代码
            //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

            getWindow().setNavigationBarColor(Color.BLACK);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //android系统大于4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        activity = BaseActivity.this;

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initView();
        initListener();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        initView();
        initListener();
    }

    public void setErrorHolder(int layoutResID) {
        addContentView(getLayout(layoutResID), new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
    }

    public void setErrorHolder(View view) {
        addContentView(view, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
    }

    /**
     * 设置监听事件
     */
    public void initListener() {

    }

    /**
     * 设置 app 不随着系统字体的调整而变化
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

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
                toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM, 0, dp2px(this, 100));
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
                toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
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
                toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
                toast.setGravity(gravity, 0, 0);
            }
            toast.setText(message);
            toast.show();
        }
    }

    /**
     * 打开指定Activity
     *
     * @param clazz 指定Activity
     */
    public void toActivity(Class<?> clazz) {
        startActivity(new Intent(this, clazz));
    }

    /**
     * 打开指定Activity
     *
     * @param clazz       指定Activity
     * @param requestCode 请求码
     */
    public void toActivity(Class<?> clazz, int requestCode) {
        startActivityForResult(new Intent(this, clazz), requestCode);
    }

    /**
     * 打开指定Activity
     *
     * @param clazz       指定Activity
     * @param requestCode 请求码
     */
    public void toActivity(Class<?> clazz, long requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        intent.putExtra("requestCode", requestCode);
        startActivity(intent);
    }

    /**
     * 打开指定Activity
     *
     * @param clazz       指定Activity
     * @param bundle      数据bundle
     * @param requestCode 请求码
     */
    public void toActivity(Class<?> clazz, Bundle bundle, long requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        intent.putExtra("requestCode", requestCode);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 打开指定Activity
     *
     * @param clazz  指定Activity
     * @param bundle 携带的数据源
     */
    public void toActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 打开指定Activity
     *
     * @param clazz       指定Activity
     * @param bundle      携带的数据源
     * @param requestCode 请求码
     */
    public void toActivity(Class<?> clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, clazz);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 获取携带的数据
     *
     * @return
     */
    public Bundle getBundle() {
        Intent intent = getIntent();
        Bundle bundle = null;
        if (intent != null) {
            bundle = intent.getExtras();
        }
        return bundle;
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
     * 获取请求码
     *
     * @return
     */
    public long getRequestCode() {
        Intent intent = getIntent();
        if (intent != null) {
            return intent.getLongExtra("requestCode", -1L);
        }
        return -1L;
    }

}
