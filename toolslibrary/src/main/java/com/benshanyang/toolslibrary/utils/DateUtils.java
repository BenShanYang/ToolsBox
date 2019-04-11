package com.benshanyang.toolslibrary.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类描述: 日期格式化工具类 </br>
 * 时间: 2019/3/28 15:37
 *
 * @author YangKuan
 * @version 1.0.0
 * @since
 */
public class DateUtils {

    /**
     * 通过时间戳获取格式化后的时间串
     * @param timeStamp 时间戳(毫秒)
     * @param template 格式化模板。例如: yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatTimeStamp(Long timeStamp,String template){
        String templateStr = "yyyy-MM-dd HH:mm:ss";
        if(!TextUtils.isEmpty(template)){
            templateStr = template;
        }
        SimpleDateFormat format=new SimpleDateFormat(templateStr);
        Date date=new Date(timeStamp);
        return format.format(date);
    }
}
