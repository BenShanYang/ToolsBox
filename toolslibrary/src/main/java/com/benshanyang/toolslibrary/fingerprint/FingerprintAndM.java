package com.benshanyang.toolslibrary.fingerprint;

import android.content.Context;
import android.content.DialogInterface;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;
import android.text.TextUtils;

/**
 * @ClassName: FingerprintImplForAndM
 * @Description: Android9以下指纹设备的操作类
 * @Author: YangKuan
 * @Date: 2020/9/10 9:15
 */
@RequiresApi(api = Build.VERSION_CODES.M)
class FingerprintAndM implements IFingerprint {
    private FingerprintCallback fingerprintCallback;
    private FingerprintManagerCompat fingerprintManagerCompat;
    private CancellationSignal cancellationSignal;
    //指纹加密
    private static FingerprintManagerCompat.CryptoObject cryptoObject;
    private FingerprintDialog dialog;
    private static FingerprintAndM fingerprintImplForAndrM;
    private int fingerPrintErrorCount = 0;//指纹识别错误次数

    public static FingerprintAndM getInstance() {
        if (fingerprintImplForAndrM == null) {
            synchronized (FingerprintAndM.class) {
                if (fingerprintImplForAndrM == null) {
                    fingerprintImplForAndrM = new FingerprintAndM();
                }
            }
        }
        //指纹加密，提前进行cipher初始化，防止指纹认证，还没有初始化完成
        try {
            cryptoObject = new FingerprintManagerCompat.CryptoObject(new CipherHelper().createCipher());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fingerprintImplForAndrM;
    }

    @Override
    public void authenticate(Context context, FingerprintCallback callback) {
        this.fingerprintCallback = callback;
        this.fingerPrintErrorCount = 0;
        //android 6.0指纹管理
        fingerprintManagerCompat = FingerprintManagerCompat.from(context);
        //取消扫描，每次取消后要重新创建
        cancellationSignal = new CancellationSignal();
        //调起指纹验证
        fingerprintManagerCompat.authenticate(null, 0, cancellationSignal, new AuthenticationCallback(), null);
        //指纹验证对话框
        dialog = new FingerprintDialog(context);
        dialog.setOnCancelListener(new FingerprintDialog.OnCancelListener() {
            @Override
            public void onCancel() {
                if (fingerprintCallback != null) {
                    fingerprintCallback.onCancel();
                }
            }
        }).setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dia) {
                if (cancellationSignal != null && !cancellationSignal.isCanceled()) {
                    cancellationSignal.cancel();//完成指纹验证后，关闭指纹验证
                }
            }
        });
    }

    /**
     * 指纹验证结果回调
     */
    private class AuthenticationCallback extends FingerprintManagerCompat.AuthenticationCallback {
        private boolean isCall = false;//回调函数是否被调用

        public AuthenticationCallback() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!isCall) {
                        fingerprintCallback.onFailed(FingerprintCallback.FINGERPRINT_FAILED, "指纹功能打开失败");
                    }
                }
            }, 700);
        }

        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            super.onAuthenticationError(errMsgId, errString);
            isCall = true;
            //errMsgId==5 指纹操作已取消
            //errMsgId==7 指纹尝试次数已经到了最大值了
            //errMsgId==9 尝试次数过多指纹传感器已停用
            if (errMsgId == 7 && fingerprintCallback != null) {
                fingerprintCallback.onFailed(fingerPrintErrorCount > 0 ? FingerprintCallback.VALIDATION_FAILED : FingerprintCallback.DISABLED, fingerPrintErrorCount > 0 ? "指纹识别失败" : "指纹功能处于禁用期");
            } else if (errMsgId == 9 && fingerprintCallback != null) {
                fingerprintCallback.onFailed(FingerprintCallback.FINGERPRINT_READER_DISABLED, errString);
            } else if (errMsgId == 5 && fingerprintCallback != null) {
                fingerprintCallback.onFailed(FingerprintCallback.FINGERPRINT_CANCEL, errString);
            }
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            super.onAuthenticationHelp(helpMsgId, helpString);
            isCall = true;
            if (dialog != null && !dialog.isShowing()) {
                dialog.show();
            }
            if (dialog != null && dialog.isShowing() && !TextUtils.isEmpty(helpString)) {
                dialog.setHelpTip(helpString);
            }
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            isCall = true;
            if (dialog != null && dialog.isShowing()) {
                dialog.setSuccess("指纹验证成功!");
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (fingerprintCallback != null) {
                        fingerprintCallback.onSuccee();
                    }
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }, 500);

        }

        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
            isCall = true;
            fingerPrintErrorCount += 1;
            if (dialog != null && dialog.isShowing()) {
                dialog.setFail("指纹不匹配，请重试");
            }
        }
    }

    @Override
    public boolean canAuthenticate(Context context, FingerprintCallback callback) {
        FingerprintManager fingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
        if (fingerprintManager != null) {
            if (!fingerprintManager.isHardwareDetected()) {//硬件不支持
                callback.onHmUnavailable();
                return false;
            }
            if (!fingerprintManager.hasEnrolledFingerprints()) {//没有录入指纹
                callback.onNoneEnrolled();
                return false;
            }
            return true;
        } else {
            callback.onHmUnavailable();
            return false;
        }
    }
}
