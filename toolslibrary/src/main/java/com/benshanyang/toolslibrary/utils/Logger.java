package com.benshanyang.toolslibrary.utils;

import android.util.Log;

/**
 * 类描述: 日志帮助类 (部分手机需要在开发者选项中设置才能输出v、d级别的日志)</br>
 * 时间: 2019/3/29 10:32
 *
 * @author YangKuan
 * @version 1.0.0
 * @since
 */
public class Logger {

    /** 日志开关 */
    public static boolean isDebug = true;
    /** 设置默认的 */
    public static String appTag = "Logger";

    /**
     * 获取相关数据:类名,方法名,行号等.用来定位行
     */
    private static String getFunctionName() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        if (sts != null) {
            for (StackTraceElement st : sts) {
                if (st.isNativeMethod()) {
                    continue;
                }
                if (st.getClassName().equals(Thread.class.getName())) {
                    continue;
                }
                if (st.getClassName().equals(Logger.class.getName())) {
                    continue;
                }
                return "[ Thread:" + Thread.currentThread().getName() + ", " + st.getFileName() + ":" + st.getLineNumber() + " ]";
            }
        }
        return null;
    }


    /**
     * 输出格式定义
     * @param msg 要打印的消息
     */
    private static String getMsgFormat(String msg) {
        String prefix = getFunctionName();
        if(TextUtils.isEmpty(prefix)){
            return msg;
        }else{
            return prefix + " -- " + msg;
        }
    }

    /**
     * 设置Verbose日志
     * @param msg 日志信息
     */
    public static void v(String msg) {
        if (isDebug) {
            Log.v(appTag, getMsgFormat(msg));
        }
    }

    /**
     * 设置Verbose日志
     * @param tag 日志标签
     * @param msg 日志信息
     */
    public static void v(String tag, String msg) {
        if (isDebug) {
            Log.v(tag, getMsgFormat(msg));
        }
    }

    /**
     * 设置Debug日志
     * @param msg 日志信息
     */
    public static void d(String msg) {
        if (isDebug) {
            Log.d(appTag, getMsgFormat(msg));
        }
    }

    /**
     * 设置Debug日志
     * @param tag 日志标签
     * @param msg 日志信息
     */
    public static void d(String tag, String msg) {
        if (isDebug) {
            Log.d(tag, getMsgFormat(msg));
        }
    }

    /**
     * 设置Info日志
     * @param msg 日志信息
     */
    public static void i(String msg) {
        if (isDebug) {
            Log.i(appTag, getMsgFormat(msg));
        }
    }

    /**
     * 设置Info日志
     * @param tag 日志标签
     * @param msg 日志信息
     */
    public static void i(String tag, String msg) {
        if (isDebug) {
            Log.i(tag, getMsgFormat(msg));
        }
    }

    /**
     * 设置Warn日志
     * @param msg 日志信息
     */
    public static void w(String msg) {
        if (isDebug) {
            Log.w(appTag, getMsgFormat(msg));
        }
    }

    /**
     * 设置Warn日志
     * @param tag 日志标签
     * @param msg 日志信息
     */
    public static void w(String tag, String msg) {
        if (isDebug) {
            Log.w(tag, getMsgFormat(msg));
        }
    }

    /**
     * 设置Error日志
     * @param msg 日志信息
     */
    public static void e(String msg) {
        if (isDebug) {
            Log.e(appTag, getMsgFormat(msg));
        }
    }

    /**
     * 设置Error日志
     * @param tag 日志标签
     * @param msg 日志信息
     */
    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.e(tag, getMsgFormat(msg));
        }
    }

}
