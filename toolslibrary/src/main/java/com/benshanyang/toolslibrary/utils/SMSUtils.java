package com.benshanyang.toolslibrary.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.PhoneNumberUtils;

/**
 * 类描述: 发送短信工具类 </br>
 * 时间: 2019/4/11 9:15
 *
 * @author YangKuan
 * @version 1.0.0
 * @since
 */
public class SMSUtils {

    /**
     * 调起系统发短信功能 需要添加发送短信的权限 <uses-permission android:name="android.permission.SEND_SMS" />
     *
     * @param context
     * @param phoneNumber
     * @param message
     */
    public void sendSMS(Context context, String phoneNumber, String message) {
        if (PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)) {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
            intent.putExtra("sms_body", message);
            context.startActivity(intent);
        }
    }

}
