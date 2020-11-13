package com.benshanyang.toolslibrary.fingerprint;

/**
 * @ClassName: FingerprintCallback
 * @Description: 识别指纹后的回调接口
 * @Author: YangKuan
 * @Date: 2020/9/10 9:12
 */
public interface FingerprintCallback {

    //指纹处于禁用期
    int DISABLED = 0;
    //指纹多次验证失败
    int VALIDATION_FAILED = 1;
    //尝试次数过多，系统指纹识别器已停用
    int FINGERPRINT_READER_DISABLED = 2;
    //指纹操作已取消
    int FINGERPRINT_CANCEL = 3;
    //系统指纹功能打开失败
    int FINGERPRINT_FAILED = 4;

    //无指纹硬件或者指纹硬件不可用
    void onHmUnavailable();

    //未添加指纹
    void onNoneEnrolled();

    //设备支持指纹并且已经录入指纹并且设备也打开了指纹识别
    void fingerprintOk();

    //指纹识别成功
    void onSuccee();

    //指纹识别失败
    void onFailed(int errorCode, CharSequence errString);

    //取消指纹识别
    void onCancel();
}
