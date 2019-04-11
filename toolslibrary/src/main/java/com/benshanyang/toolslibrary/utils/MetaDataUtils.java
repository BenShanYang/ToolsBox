package com.benshanyang.toolslibrary.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;

/**
 * 类描述: 获取Manifest中<meta-data>元素的值 </br>
 * 时间: 2019/3/29 14:11
 *
 * @author YangKuan
 * @version 1.0.0
 * @since
 */
public class MetaDataUtils {

    /**
     * 获取Manifest中Activity标签下的<meta-data>元素的值
     * @param activity Activity的上下文
     * @param name meta-data的name
     * @return
     */
    public static String getActivityMetaData(Activity activity, String name) {
        ActivityInfo info = null;
        String msg = null;
        try {
            info = activity.getPackageManager().getActivityInfo(activity.getComponentName(), PackageManager.GET_META_DATA);
            msg = (info != null ? info.metaData.getString(name) : "");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return msg != null ? msg : "";
    }

    /**
     * 获取Manifest中Application标签下的<meta-data>元素的值
     * @param context Application的上下文
     * @param name meta-data的name
     * @return
     */
    public static String getApplicationMetaData(Context context,String name){
        ApplicationInfo appInfo = null;
        String msg = null;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            msg = (appInfo != null ? appInfo.metaData.getString(name) : "");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return msg != null ? msg : "";
    }

    /**
     * 获取Manifest中Service标签下的<meta-data>元素的值
     * @param context Serviece的上下文
     * @param clazz 对应的Service类
     * @param name meta-data的name
     * @return
     */
    public static String getServiceMetaData(Context context,Class<?> clazz, String name){
        ServiceInfo info= null;
        String msg = null;
        try {
            ComponentName cn=new ComponentName(context, clazz);
            info = context.getPackageManager().getServiceInfo(cn, PackageManager.GET_META_DATA);
            msg = (info != null ? info.metaData.getString(name) : "");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return msg != null ? msg : "";
    }

    /**
     * 获取Manifest中Receiver标签下的<meta-data>元素的值
     * @param context Receiver的上下文
     * @param clazz 对应的Receiver类
     * @param name meta-data的name
     * @return
     */
    public static String getReceiverMetaData(Context context, Class<?> clazz, String name){
        ActivityInfo info= null;
        String msg = null;
        try {
            ComponentName cn=new ComponentName(context, clazz);
            info = context.getPackageManager().getReceiverInfo(cn, PackageManager.GET_META_DATA);
            msg = (info != null ? info.metaData.getString(name) : "");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return msg != null ? msg : "";
    }

}
