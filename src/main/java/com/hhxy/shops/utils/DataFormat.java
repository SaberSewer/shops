package com.hhxy.shops.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataFormat {
    /**
     * YYYYMMDD格式
     */
    public static final Integer FMT_YYYYMMDD = 1;
    /**
     * YYYY-MM-DD格式
     */
    public static final Integer FMT_YYYY_MM_DD = 2;
    /**
     * YYYYMMDDHHmmSS格式
     */
    public static final Integer FMT_YYYYMMDD_HHMMSS = 3;
    /**
     * YYYY-MM-DD hh:mm:ss格式
     */
    public static final Integer FMT_YYYY_MM_DD_HH_MM_SS = 4;
    public static final Long DAY = 1000L * 60L * 60L * 24L;
    private static SimpleDateFormat sdf = null;
    private static DecimalFormat nf = null;


    /**
     * 数字格式化
     *
     * @param number 要格式化的数
     * @param length 长度
     * @return
     */
    public static String formatNumber(Long number, Long length) {
        return String.format("%0" + length + "d", number);
    }
    public static String formatNumber(Double number, Long length) {
        return String.format("%0" + length + "d", number);
    }

    /**
     * 将Date格式化成String类型日期
     *
     * @param date 时间类型
     * @param fmt  格式化方式
     * @return
     */
    public static String formatDate(Date date, Integer fmt) {
        switchPaten(fmt);
        return sdf.format(date);
    }

    /**
     * 毫秒时转换成String类型日期
     *
     * @param time 毫秒时
     * @param fmt 格式化方式
     * @return
     */
    public static String formatDate(Long time, Integer fmt) {
        return formatDate(new Date(time), fmt);
    }

    /**
     * 字符串转成Date类型
     * @param time 毫秒时
     * @param fmt 格式化方式
     * @return
     */
    public static Date strToDate(String time, Integer fmt) {
        switchPaten(fmt);
        try {
            return sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    /**
     * 获取对应天数之前的日期
     * @param date 当前日期
     * @param day 天数
     * @return 对应日期
     */
    public static Date beforeDay(Date date, Integer day) {
        Date d = new Date(date.getTime() - day * DAY);
        return d;
    }

    /**
     * 获取对应天数之后的日期
     * @param date 当前日期
     * @param day 天数
     * @return 对应日期
     */
    public static Date afterDay(Date date, Integer day) {
        Date d = new Date(date.getTime() + day * DAY);
        return d;
    }

    private static void switchPaten(Integer fmt) {
        switch (fmt) {
            case 1:
                sdf = new SimpleDateFormat("yyyyMMdd");
                break;
            case 2:
                sdf = new SimpleDateFormat("yyy-MM-dd");
                break;
            case 3:
                sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                break;
            case 4:
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                break;
            default:
                sdf = new SimpleDateFormat("yyyyMMdd");
        }
    }
}
