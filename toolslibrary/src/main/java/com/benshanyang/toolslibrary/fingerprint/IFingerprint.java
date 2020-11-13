package com.benshanyang.toolslibrary.fingerprint;

import android.content.Context;

/**
 * Created by 王鹏程 on 2019/9/4.
 * 类(IFingerprint)解释:
 * 指纹接口
 */
interface IFingerprint {
    /**
     * 初始化并调起指纹验证
     *
     * @param context
     * @param callback
     */
    void authenticate(Context context, FingerprintCallback callback);

    /**
     * 判断是否支持指纹
     *
     * @param context
     * @param callback
     * @return
     */
    boolean canAuthenticate(Context context, FingerprintCallback callback);
}
