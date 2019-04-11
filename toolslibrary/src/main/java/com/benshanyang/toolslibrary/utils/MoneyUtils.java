package com.benshanyang.toolslibrary.utils;

import java.math.BigDecimal;

/**
 * 类描述: 金钱工具类 </br>
 * 时间: 2019/3/28 16:13
 *
 * @author YangKuan
 * @version 1.0.0
 * @since
 */
public class MoneyUtils {

    /**
     * 默认保留位数
     */
    private static final int DEF_DIV_SCALE = 10;

    /**
     * 提供精确的加法运算
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的加法运算
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static BigDecimal add(String v1, String v2) {
        try {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.add(b2);
        } catch (Exception e) {

        }
        return new BigDecimal(0);
    }

    /**
     * 提供精确的加法运算
     *
     * @param v1    被加数
     * @param v2    加数
     * @param scale 保留scale 位小数
     * @return 两个参数的和
     */
    public static String add(String v1, String v2, int scale) {
        if (scale < 0) {
            return "0";
        }
        try {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.add(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
        } catch (Exception e) {

        }
        return "0";
    }

    /**
     * 提供精确的减法运算
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double substract(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static BigDecimal substract(String v1, String v2) {
        try {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.subtract(b2);
        } catch (Exception e) {

        }
        return new BigDecimal(0);
    }

    /**
     * 提供精确的减法运算
     *
     * @param v1    被减数
     * @param v2    减数
     * @param scale 保留scale 位小数
     * @return 两个参数的差
     */
    public static String substract(String v1, String v2, int scale) {
        if (scale < 0) {
            return "0";
        }
        try {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.subtract(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
        } catch (Exception e) {

        }
        return "0";
    }

    /**
     * 提供精确的乘法运算
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double multiply(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算
     *
     * @param v1    被乘数
     * @param v2    乘数
     * @param scale 保留scale 位小数
     * @return 两个参数的积
     */
    public static double multiply(double v1, double v2, int scale) {
        if (scale < 0) {
            return 0d;
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return round(b1.multiply(b2).doubleValue(), scale);
    }

    /**
     * 提供精确的乘法运算
     *
     * @param v1    被乘数
     * @param v2    乘数
     * @param scale 保留scale 位小数
     * @return 两个参数的积
     */
    public static String multiply(String v1, String v2, int scale) {
        if (scale < 0) {
            return "0";
        }
        try {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.multiply(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
        } catch (Exception e) {

        }
        return "0";
    }

    /**
     * 提供(相对)精确的除法运算,当发生除不尽的情况时,
     * 精确到小数点以后10位,以后的数字四舍五入.
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double divide(double v1, double v2) {
        return divide(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供(相对)精确的除法运算.
     * 当发生除不尽的情况时,由scale参数指 定精度,以后的数字四舍五入.
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示需要精确到小数点以后几位
     * @return 两个参数的商
     */
    public static double divide(double v1, double v2, int scale) {
        if (scale < 0) {
            return 0d;
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供(相对)精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示需要精确到小数点以后几位
     * @return 两个参数的商
     */
    public static String divide(String v1, String v2, int scale) {
        if (scale < 0) {
            return "0";
        }
        try {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v1);
            return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toString();
        } catch (Exception e) {

        }
        return "0";
    }

    /**
     * 提供精确的小数位四舍五入处理
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            return 0d;
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static String round(String v, int scale) {
        if (scale < 0) {
            return "0";
        }
        try {
            BigDecimal b = new BigDecimal(v);
            return b.setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
        } catch (Exception e) {

        }
        return "0";
    }

    /**
     * 提供精确的小数位获取
     *
     * @param v     需要处理的数字
     * @param scale 小数点后保留几位
     * @param round BigDecimal.ROUND_DOWN - 直接删除多余的小数位,如2.35变成2.3
     *              BigDecimal.ROUND_UP - 进位处理,如2.35变成2.4
     *              BigDecimal.ROUND_HALF_UP - 四舍五入,如2.35变成2.4
     *              BigDecimal.ROUND_HALF_DOWN - 四舍五入,如2.35变成2.3
     * @return 最后的结果
     */
    public static double round(double v, int scale, int round) {
        if (scale < 0) {
            return 0d;
        }
        // https://www.cnblogs.com/liqforstudy/p/5652517.html
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, round).doubleValue();
    }

    /**
     * 比较大小
     *
     * @param amount  输入的数值
     * @param compare 被比较的数字
     * @return true 大于被比较的数
     */
    public static Boolean compareBigDecimal(String amount, double compare) {
        try {
            BigDecimal lenth = new BigDecimal(amount);
            if (lenth.compareTo(BigDecimal.valueOf(compare)) == -1) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}
