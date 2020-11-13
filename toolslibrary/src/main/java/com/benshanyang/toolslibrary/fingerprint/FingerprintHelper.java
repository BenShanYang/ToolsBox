package com.benshanyang.toolslibrary.fingerprint;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;

/**
 * @ClassName: FingerprintVerifyManager
 * @Description: 指纹识别管理类
 * @Author: YangKuan
 * @Date: 2020/9/10 9:13
 */
public class FingerprintHelper {

    @SuppressLint("NewApi")
    public FingerprintHelper(Builder builder) {
        IFingerprint iFingerprint = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            //android9.0以上
            iFingerprint = FingerprintAndP.getInstance();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //android6.0以上
            iFingerprint = FingerprintAndM.getInstance();
        }

        if (iFingerprint != null) {
            if (iFingerprint.canAuthenticate(builder.context, builder.callback)) {
                //初始化并调起指纹验证
                builder.callback.fingerprintOk();
                iFingerprint.authenticate(builder.context, builder.callback);
            }
        } else {
            //检测硬件不支持或者没有录入指纹 //6.0以下
            builder.callback.onHmUnavailable();
        }
    }

    public static class Builder {
        private Context context;
        private FingerprintCallback callback;

        public Builder(@NonNull Context context) {
            this.context = context;
        }

        public Builder callback(FingerprintCallback callback) {
            this.callback = callback;
            return this;
        }

        public FingerprintHelper build() {
            return new FingerprintHelper(this);
        }
    }
}
