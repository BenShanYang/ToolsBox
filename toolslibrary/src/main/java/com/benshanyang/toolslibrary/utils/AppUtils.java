package com.benshanyang.toolslibrary.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.util.List;

/**
 * 类描述: 获取App基本信息 </br>
 * 时间: 2019/3/29 10:17
 *
 * @author YangKuan
 * @version 1.0.0
 * @since
 */
public class AppUtils {

    /**
     * 判断是否安装某个App
     *
     * @param context     上下文
     * @param packageName 包名
     * @return
     */
    public static boolean isInstalled(Context context, String packageName) {
        if (context == null || TextUtils.isEmpty(packageName)) {
            return false;
        }
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);

        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equalsIgnoreCase(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否安装了QQ
     *
     * @param context 上下文
     * @return 安装了true, 未安装false
     */
    public static boolean isInstalledQQ(Context context) {
        return isInstalled(context, "com.tencent.mobileqq");
    }

    /**
     * 判断是否安装了微信
     *
     * @param context 上下文
     * @return 安装了true, 未安装false
     */
    public static boolean isInstalledWeiChat(Context context) {
        return isInstalled(context, "com.tencent.mm");
    }

    /**
     * 判断是否安装了新浪微博
     * @param context 上下文
     * @return 安装了true, 未安装false
     */
    public static boolean isInstalledWeiBo(Context context) {
        return isInstalled(context, "com.sina.weibo");
    }

    /**
     * 获取应用程序名称
     *
     * @param context 上下文
     * @return 返回应用名称
     */
    public static synchronized String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用程序版本名称信息
     *
     * @param context 上下文
     * @return 当前应用的版本名称
     */
    public static synchronized String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用程序版本名称信息
     *
     * @param context 上下文
     * @return 当前应用的版本名称
     */
    public static synchronized int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取应用程序版本名称信息
     *
     * @param context 上下文
     * @return 当前应用的版本名称
     */
    public static synchronized String getPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取图标 bitmap
     *
     * @param context 上下文
     * @return 返回App图标的Bitmap
     */
    public static synchronized Bitmap getBitmap(Context context) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getAppDrawable(context);
        Bitmap bitmap = bitmapDrawable.getBitmap();
        return bitmap;
    }

    /**
     * 获取图标 drawable
     *
     * @param context 上下文
     * @return 返回App图标的Drawable
     */
    public static synchronized Drawable getDrawable(Context context) {
        return getAppDrawable(context);
    }

    private static Drawable getAppDrawable(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        return packageManager.getApplicationIcon(applicationInfo);
    }

}
