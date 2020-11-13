package com.benshanyang.toolslibrary.manager;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.Stack;

/**
 * @ClassName: ActivityStackManager
 * @Description: activity栈
 * @Author: YangKuan
 * @Date: 2020/6/30 10:20
 */
public class ActivityStackManager implements Application.ActivityLifecycleCallbacks {

    private final Stack<Activity> activityStack = new Stack<Activity>();

    private static ActivityStackManager activityStackManager;

    private ActivityStackManager() {

    }

    public static ActivityStackManager getInstance() {
        if (activityStackManager == null) {
            activityStackManager = new ActivityStackManager();
        }
        return activityStackManager;
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
        //可以在这里add
        activityStack.add(activity);
        Intent intent = activity.getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (intent.getBooleanExtra("onlyAlive", false) || (bundle != null && bundle.getBoolean("onlyAlive"))) {
                aliveLastActivity(activity);
            }
        }
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        //可以在这里remove
        activityStack.remove(activity);
    }

    /**
     * 获取栈顶Activity
     *
     * @return 返回栈顶activity
     */
    public Activity topActivity() {
        return activityStack.peek();
    }

    /**
     * 清空栈内所有Activity
     */
    public void clearStack() {
        for (Activity activity : activityStack) {
            activity.finish();
        }
        activityStack.clear();
    }

    /**
     * 除去要保活的Activity，其他Activity全部关闭并从栈中移除
     *
     * @param activity 要保活的Activity
     */
    private void aliveLastActivity(Activity activity) {
        for (Activity item : activityStack) {
            if (item != activity) {
                item.finish();
            }
        }
    }

}
