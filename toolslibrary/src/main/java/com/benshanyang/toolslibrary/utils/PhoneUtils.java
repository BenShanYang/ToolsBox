package com.benshanyang.toolslibrary.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * 类描述: 判断是否是手机号 </br>
 * 时间: 2019/3/20 10:52
 *
 * @author YangKuan
 * @version 1.0.0
 * @since
 */
public class PhoneUtils {
    /**
     * 判断字符串是否符合手机号码格式
     * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
     * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
     * 电信号段: 133,149,153,170,173,177,180,181,189
     *
     * @param mobileNums 待检测的手机号字符串
     * @return
     */
    public static boolean isMobileNO(String mobileNums) {
        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";
        // "[1]"代表下一位为数字可以是几，"[0-9]"代表可以为0-9中的一个，"[5,7,9]"表示可以是5,7,9中的任意一位,[^4]表示除4以外的任何一个,\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums)) {
            return false;
        } else {
            return mobileNums.matches(telRegex);
        }
    }

    /**
     * 拨打电话 当直接拨打电话时需要添加波打电话的相关权限
     *
     * @param phoneNum 手机号
     * @param isCall   true - 直接拨打电话, false - 跳转到拨号界面，用户手动点击拨打
     */
    public static void callPhone(Context context, String phoneNum, boolean isCall) {
        Intent intent = new Intent();
        if (isCall) {
            //直接拨打电话
            intent.setAction(Intent.ACTION_CALL);
        } else {
            //跳转到拨号界面，用户手动点击拨打
            intent.setAction(Intent.ACTION_DIAL);
        }
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        context.startActivity(intent);
    }

}
