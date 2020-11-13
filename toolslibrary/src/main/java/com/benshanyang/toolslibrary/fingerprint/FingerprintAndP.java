package com.benshanyang.toolslibrary.fingerprint;

import android.content.Context;
import android.content.DialogInterface;
import android.hardware.biometrics.BiometricPrompt;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.annotation.RequiresApi;

/**
 * @ClassName: FingerprintImplForAndP
 * @Description: Android9及以上的指纹设备操作类
 * @Author: YangKuan
 * @Date: 2020/9/10 9:15
 */
@RequiresApi(api = Build.VERSION_CODES.P)
class FingerprintAndP implements IFingerprint {
    private static FingerprintAndP fingerprintImplForAndrP;
    private static BiometricPrompt.CryptoObject cryptoObject;
    private FingerprintCallback fingerprintCallback;
    private CancellationSignal cancellationSignal;
    private int fingerPrintErrorCount = 0;

    private FingerprintAndP() {
    }

    public static FingerprintAndP getInstance() {
        if (fingerprintImplForAndrP == null) {
            synchronized (FingerprintAndP.class) {
                if (fingerprintImplForAndrP == null) {
                    fingerprintImplForAndrP = new FingerprintAndP();
                }
            }
        }
        try {
            cryptoObject = new BiometricPrompt.CryptoObject(new CipherHelper().createCipher());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fingerprintImplForAndrP;
    }

    @Override
    public void authenticate(Context context, FingerprintCallback callback) {
        fingerprintCallback = callback;
        //取消扫描，每次取消后需要重新创建新示例
        cancellationSignal = new CancellationSignal();
        //构建 BiometricPrompt
        BiometricPrompt.Builder builder = new BiometricPrompt.Builder(context)
                .setTitle("指纹验证")
                .setDescription("请验证已有指纹")
                .setNegativeButton("取消", context.getMainExecutor(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cancellationSignal.cancel();
                        fingerprintCallback.onCancel();
                    }
                });
        BiometricPrompt biometricPrompt = builder.build();
        biometricPrompt.authenticate(cryptoObject, cancellationSignal, context.getMainExecutor(), authenticationCallback);
    }

    /**
     * 认证结果回调
     */
    private BiometricPrompt.AuthenticationCallback authenticationCallback = new BiometricPrompt.AuthenticationCallback() {
        @Override
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            super.onAuthenticationError(errorCode, errString);
            if (fingerprintCallback != null) {
                if (errorCode == 5) {
                    //errMsgId==5 指纹操作已取消
                    fingerprintCallback.onFailed(FingerprintCallback.FINGERPRINT_CANCEL, errString);
                } else if (errorCode == 7) {
                    //errMsgId==7 指纹尝试次数已经到了最大值了
                    fingerprintCallback.onFailed(fingerPrintErrorCount > 0 ? FingerprintCallback.VALIDATION_FAILED : FingerprintCallback.DISABLED, fingerPrintErrorCount > 0 ? "指纹识别失败" : "指纹功能处于禁用期");
                } else if (errorCode == 9) {
                    //errMsgId==9 尝试次数过多指纹传感器已停用
                    fingerprintCallback.onFailed(FingerprintCallback.FINGERPRINT_READER_DISABLED, errString);
                }
            }
        }

        @Override
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
            super.onAuthenticationHelp(helpCode, helpString);
        }

        @Override
        public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            if (fingerprintCallback != null)
                fingerprintCallback.onSuccee();
        }

        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
            fingerPrintErrorCount += 1;
        }
    };


    /*
     * 在 Android Q，Google 提供了 Api BiometricManager.canAuthenticate() 用来检测指纹识别硬件是否可用及是否添加指纹
     * 不过尚未开放，标记为"Stub"(存根)
     * 所以暂时还是需要使用 Andorid 6.0 的 Api 进行判断
     * */
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
