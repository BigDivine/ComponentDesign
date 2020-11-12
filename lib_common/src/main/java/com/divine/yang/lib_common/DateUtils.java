package com.divine.yang.lib_common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * author: Divine
 * <p>
 * date: 2018/12/10
 * <p>
 * 时间格式化工具类
 */
public class DateUtils {

    /**
     * 日期字符串转换Date实体
     *
     * @param serverTime 时间字符串
     * @param format     日期格式
     * @return 设定格式的date，默认返回 "yyyy-MM-dd HH:mm:ss"HH是24小时制，hh是12小时制
     */
    public static String parseServerTime(String serverTime, String format) {
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINESE);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        if (serverTime != null) {
            return sdf.format(new Date(Long.parseLong(serverTime)));
        } else {
            return "";
        }
    }

    /**
     * Date对象获取时间字符串
     *
     * @param date   date类型日期
     * @param format 日期格式
     * @return 返回字符串，默认返回 "yyyy-MM-dd HH:mm:ss"HH是24小时制，hh是12小时制
     */
    public static String getDateStr(Date date, String format) {
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    /**
     * 时间戳转换日期格式字符串
     *
     * @param time   毫秒数
     * @param format 日期格式
     * @return 返回字符串，默认返回 "yyyy-MM-dd HH:mm:ss" HH是24小时制，hh是12小时制
     */
    public static String timeStamp2Date(long time, String format) {
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai")); // 设置北京时区
        return sdf.format(new Date(time));
    }

    /**
     * 日期格式字符串转换时间戳
     *
     * @param date   date类型日期
     * @param format 日期格式
     * @return 时间戳-毫秒数的字符串
     */
    public static String date2TimeStamp(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取某个日期前后N天的日期
     *
     * @param beginDate
     * @param distanceDay 前后几天 如获取前7天日期则传-7即可；如果后7天则传7
     * @param format      日期格式，默认"yyyy-MM-dd"
     * @return
     */
    public static String getOldDateByDay(Date beginDate, int distanceDay, String format) {
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd";
        }
        SimpleDateFormat dft = new SimpleDateFormat(format);
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) + distanceDay);
        Date endDate = null;
        try {
            endDate = dft.parse(dft.format(date.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dft.format(endDate);
    }

    /**
     * 获取前后几个月的日期
     *
     * @param beginDate
     * @param distanceMonth
     * @param format
     * @return
     */
    public static String getOldDateByMonth(Date beginDate, int distanceMonth, String format) {
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd";
        }
        SimpleDateFormat dft = new SimpleDateFormat(format);
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.MONTH, date.get(Calendar.MONTH) + distanceMonth);
        Date endDate = null;
        try {
            endDate = dft.parse(dft.format(date.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dft.format(endDate);
    }

    /**
     * 秒数转换成时分秒
     *
     * @param lSeconds 时长(单位：秒)
     * @return "x小时x分钟x秒"
     */
    public static String convertSecToTimeStringInSeconds(long lSeconds) {
        long nHour = lSeconds / 3600;
        long nMin = lSeconds % 3600;
        long nSec = nMin % 60;
        nMin = nMin / 60;
        if (nHour == 0) {
            if (nMin == 0) {
                return String.format("%02d秒", nSec);
            }
            return String.format("%02d分钟%02d秒", nMin, nSec);
        }
        return String.format("%02d小时%02d分钟%02d秒", nHour, nMin, nSec);
    }

    /**
     * 秒数转换成时分
     *
     * @param lSeconds 时长(单位：秒)
     * @return "x小时x分钟"
     */
    public static String convertSecToTimeStringInMinutes(long lSeconds) {
        long nHour = lSeconds / 3600;
        long nMin = lSeconds % 3600;
        nMin = nMin / 60;
        if (nHour == 0) {
            return String.format("%02d分钟", nMin);
        }
        return String.format("%02d小时%02d分钟", nHour, nMin);
    }

    /**
     * 获取前一天的日期
     *
     * @param date yyyy-DD-mm
     * @return
     */
    public static String getPreDay(String date) {
        String[] curDateArray = date.split("-");
        int year = Integer.parseInt(curDateArray[0]);//年
        int month = Integer.parseInt(curDateArray[1]);//月
        int day = Integer.parseInt(curDateArray[2]);
        if (day != 1) {
            day = day - 1;
        } else {
            if (month != 1) {
                month = month - 1;
                if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                    day = 31;
                } else if (month != 2) {
                    day = 30;
                } else {
                    if (year % 4 == 0) {
                        day = 29;
                    } else {
                        day = 28;
                    }
                }
            } else {
                year = year - 1;
                month = 12;
                day = 31;
            }
        }
        String newDay = "";
        String months = "";
        String days = "";
        if (month < 10) {
            months = "0" + month;
        } else {
            months = month + "";
        }
        if (day < 10) {
            days = "0" + day;
        } else {
            days = day + "";
        }
        newDay = year + "-" + months + '-' + days;
        return newDay;
    }

    /**
     * 获取后一天的日期
     *
     * @param date yyyy-DD-mm
     * @return
     */
    public static String getNextDay(String date) {
        String[] curDateArray = date.split("-");
        int year = Integer.parseInt(curDateArray[0]);//年
        int month = Integer.parseInt(curDateArray[1]);//月
        int day = Integer.parseInt(curDateArray[2]);
        if (day != 28 && day != 29 && day != 30 && day != 31) {
            day = day + 1;
        } else {
            if (month == 2) {
                if (year % 4 == 0) {
                    if (day == 29) {
                        day = 1;
                        month += 1;
                    } else {
                        day += 1;
                    }
                } else {
                    if (day == 28) {
                        day = 1;
                        month += 1;
                    } else {
                        day += 1;
                    }
                }
            } else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10) {
                if (day == 31) {
                    day = 1;
                    month += 1;
                } else {
                    day += 1;
                }
            } else if (month == 12) {
                if (day == 31) {
                    day = 1;
                    month = 1;
                    year += 1;
                } else {
                    day += 1;
                }
            } else {
                if (day == 30) {
                    day = 1;
                    month += 1;
                } else {
                    day += 1;
                }
            }
        }
        String newDay = "";
        String months = "";
        String days = "";
        if (month < 10) {
            months = "0" + month;
        } else {
            months = month + "";
        }
        if (day < 10) {
            days = "0" + day;
        } else {
            days = day + "";
        }
        newDay = year + "-" + months + '-' + days;
        return newDay;
    }

    /**
     * 获取前一月的日期
     *
     * @param date yyyy-DD-mm
     * @return
     */
    public static String getPreMonth(String date) {
        String[] curDateArray = date.split("-");
        int year = Integer.parseInt(curDateArray[0]);//年
        int month = Integer.parseInt(curDateArray[1]);//月
        if (month != 1) {
            month = month - 1;
        } else {
            year = year - 1;
            month = 12;
        }

        String newDay = "";
        String months = "";
        if (month < 10) {
            months = "0" + month;
        } else {
            months = month + "";
        }

        newDay = year + "-" + months;
        return newDay;
    }

    /**
     * 获取后一月的日期
     *
     * @param date yyyy-DD-mm
     * @return
     */
    public static String getNextMonth(String date) {
        String[] curDateArray = date.split("-");
        int year = Integer.parseInt(curDateArray[0]);//年
        int month = Integer.parseInt(curDateArray[1]);//月
        if (month == 12) {
            month = 1;
            year += 1;
        } else {
            month += 1;
        }
        String newDay = "";
        String months = "";
        if (month < 10) {
            months = "0" + month;
        } else {
            months = month + "";
        }

        newDay = year + "-" + months;
        return newDay;
    }

    /**
     * 获取年
     *
     * @return
     */
    public static int getYear() {
        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.YEAR);
    }

    /**
     * 获取月
     *
     * @return
     */
    public static int getMonth() {
        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日
     *
     * @return
     */
    public static int getDay() {
        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.DATE);
    }

    /**
     * 获取时
     *
     * @return
     */
    public static int getHour() {
        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.HOUR);
    }

    /**
     * 获取分
     *
     * @return
     */
    public static int getMinute() {
        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.MINUTE);
    }

    /**
     * 获取当前时间的时间戳
     *
     * @return
     */
    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

}
