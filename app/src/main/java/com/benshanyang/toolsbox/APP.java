package com.benshanyang.toolsbox;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import com.benshanyang.toolslibrary.manager.ActivityStackManager;
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

        //设置Activity栈 在Application中 onCreate()方法中使用
        registerActivityLifecycleCallbacks(ActivityStackManager.getInstance());

        //Activity栈使用
        /*
        ActivityStackManager.getInstance().clearStack();//清空Activity栈 用于退出程序
        Activity topActivity = ActivityStackManager.getInstance().topActivity();//获取当前Activit栈顶的activity
        //退出登录回到首页
        Intent intent = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("onlyAlive", true);
        intent.putExtras(bundle);
        startActivity(intent);
        */
    }
}
