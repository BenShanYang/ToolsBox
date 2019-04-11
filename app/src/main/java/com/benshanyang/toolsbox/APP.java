package com.benshanyang.toolsbox;

import android.app.Application;
import android.graphics.Typeface;

import com.benshanyang.toolslibrary.utils.ToastUtils;

/**
 * @ClassName: APP
 * @Description: java类作用描述
 * @Author: mayn
 * @Date: 2019/4/10 15:39
 */
public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        /*ToastUtils.Config.getInstance()
                .setToastTypeface(Typeface.createFromAsset(getAssets(), "simkai.ttf"))
                .allowQueue(false)
                .apply();*/
        //ToastUtils.Config.reset(); // Use this if you want to use the configuration above only once
    }
}
