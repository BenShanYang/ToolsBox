package com.benshanyang.toolslibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 类描述: SharedPreferences工具类 </br>
 * 时间: 2019/4/9 18:45
 *
 * @author YangKuan
 * @version 1.0.0
 * @since
 */
public class SPUtils {

    private SharedPreferences share;
    private SharedPreferences.Editor editor;
    private static SPUtils instance;

    /**
     * SharedPreferences工具类的私有构造方法
     *
     * @param context 上下文
     * @param name    SharedPreferences名称
     */
    private SPUtils(Context context, String name) {
        share = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        editor = share.edit();
    }

    /**
     * 初始化SharedPreferences
     *
     * @param context 上下文
     * @param spName  SharedPreferences名称
     * @return
     */
    public static SPUtils init(Context context, String spName) {
        if (instance == null) {
            synchronized (SPUtils.class) {
                if (instance == null) {
                    instance = new SPUtils(context, spName);
                }
            }
        }
        return instance;
    }

    /**
     * 保存int类型的数据
     *
     * @param key   SP的键名
     * @param value 值
     */
    public void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 获取int类型的数据
     *
     * @param key          SP的键名
     * @param defaultvalue 默认值
     * @return
     */
    public int getInt(String key, int defaultvalue) {
        return share.getInt(key, defaultvalue);
    }

    /**
     * 获取int类型的数据
     *
     * @param key SP的键名
     * @return
     */
    public int getInt(String key) {
        return share.getInt(key, 0);
    }

    /**
     * 保存String类型的数据
     *
     * @param key   SP的键名
     * @param value 值
     */
    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 获取String类型的数据
     *
     * @param key          SP的键名
     * @param defaultvalue 默认值
     * @return
     */
    public String getString(String key, String defaultvalue) {
        return share.getString(key, defaultvalue);
    }

    /**
     * 获取String类型的数据
     *
     * @param key SP的键名
     * @return
     */
    public String getString(String key) {
        return share.getString(key, "");
    }

    /**
     * 保存boolean类型的值
     *
     * @param key   SP的键名
     * @param value 值
     */
    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 获取boolean类型的数据
     *
     * @param key      SP的键名
     * @param defValue 默认值
     * @return
     */
    public boolean getBoolean(String key, boolean defValue) {
        return share.getBoolean(key, defValue);
    }

    /**
     * 获取boolean类型的数据
     *
     * @param key SP的键名
     * @return
     */
    public boolean getBoolean(String key) {
        return share.getBoolean(key, false);
    }

    /**
     * 保存float类型的数据
     *
     * @param key   SP的键名
     * @param value 值
     */
    public void putFloat(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

    /**
     * 获取float类型的数据
     *
     * @param key      SP的键名
     * @param defValue 默认值
     * @return
     */
    public float getFloat(String key, float defValue) {
        return share.getFloat(key, defValue);
    }

    /**
     * 获取float类型的数据
     *
     * @param key SP的键名
     * @return
     */
    public float getFloat(String key) {
        return share.getFloat(key, 0f);
    }

    /**
     * 保存long类型的数据
     *
     * @param key   SP的键名
     * @param value 值
     */
    public void putLong(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * 获取long类型的数据
     *
     * @param key      SP的键名
     * @param defValue 默认值
     * @return
     */
    public long getLong(String key, long defValue) {
        return share.getLong(key, defValue);
    }

    /**
     * 获取long类型的数据
     *
     * @param key SP的键名
     * @return
     */
    public long getLong(String key) {
        return share.getLong(key, 0L);
    }

    /**
     * 清空SP里所有数据 谨慎调用
     */
    public void clear() {
        editor.clear();//清空
        editor.commit();//提交
    }

    /**
     * 删除SP里指定key对应的数据项
     *
     * @param key SP的键名
     */
    public void remove(String key) {
        editor.remove(key);//删除掉指定的值
        editor.commit();//提交
    }

    /**
     * 查看sp文件里面是否存在此 key
     *
     * @param key SP的键名
     * @return
     */
    public boolean contains(String key) {
        return share.contains(key);
    }

}
