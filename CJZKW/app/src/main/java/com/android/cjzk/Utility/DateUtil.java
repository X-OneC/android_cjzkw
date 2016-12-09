package com.android.cjzk.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
    /***************************************************************************
     * 返回之前几个月或之后几个月的今天
     *
     * @param date
     * @param num
     * @return
     */
    public static Date getDateOfLastMonth(Date date, int num) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.MONTH, num);
        return cal.getTime();
    }

    public static Date getFirstDayOfMonth(Date date) {
        return getFirstDayOfMonth(date, 0);
    }

    /**
     * 取得便宜offset月的第一天
     **/
    public static Date getFirstDayOfMonth(Date date, int offsetMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MONTH, offsetMonth);
        return calendar.getTime();
    }

    /***
     * 取得当前这周的星期天
     **/
    public static Date getFirstDayOfWeek(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal = getFirstDayOfWeek(cal);
        return truncateDay(cal.getTime());
    }

    /**
     * 根据日历得到那一天的星期天是哪天 注意：输入的calendar最后的值同返回值一样
     *
     * @param calendar
     * @return
     */
    public static Calendar getFirstDayOfWeek(Calendar calendar) {
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (week == 0) {
            week = 7;
        }
        calendar.add(Calendar.DATE, -week);
        return calendar;
    }

    /**
     * 把date truncate到日期为止,去掉时分秒
     *
     * @param date
     * @return
     */
    public static Date truncateDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 转化日期成yyyy-mm-dd的String格式
     *
     * @param date
     * @return
     */
    public static String formatToYYYYMMDD(Date date) {
        if (date == null)
            return "";
        String result = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            result = format.format(date);
        } catch (Exception e) {
            return "";
        }
        return result;
    }

    public static String formatToYYYYMMDDhhmmss(Date date) {
        if (date == null)
            return "";
        String result = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            result = format.format(date);
        } catch (Exception e) {
            return "";
        }
        return result;
    }

    public static String formatToTightYYYYMMDDhhmmss(Date date) {
        if (date == null)
            return "";
        String result = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            result = format.format(date);
        } catch (Exception e) {
            return "";
        }
        return result;
    }

    public static Date getAfterDay(Date date, int num) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, num);
        return truncateDay(cal.getTime());
    }

    public static String getTimeDesc(Date _date, Date _now) {
        long diff = _now.getTime() - _date.getTime();
        StringBuffer bufferTime = new StringBuffer();
        long _day = diff / 24L / 3600000L;
        long _hour = diff % (24L * 3600000L) / 3600000L;
        long _mini = diff % 3600000L / 60000L;
        long _second = diff % 60000L / 1000L;
        if (_day >= 0) {
            bufferTime.append(DateUtil.formatToTightYYYYMMDDhhmmss(_date));
        } else {
            // if (_day > 0) {
            // bufferTime.append(String.valueOf(_day) + "天");
            // }
            if (_hour > 0) {
                bufferTime.append(String.valueOf(_hour) + "小时");
            }
            if (_mini > 0 && _day == 0) {
                bufferTime.append(String.valueOf(_mini) + "分钟");
            }
            if (_day == 0 && _hour == 0 && _mini == 0 && _second >= 0) {
                bufferTime.append(String.valueOf(_second + 1) + "秒");
            }
            bufferTime.append("前");
        }
        return bufferTime.toString();

    }

    /****
     * im时间描述
     ****/
    public static String getTimeDesc(Date _date, Date befDate, Date _now) {
        if (_date == null) {
            return null;
        }
        if (befDate == null) {
            return getTimeDesc(_date, _now);
        }
        long _befDate = befDate.getTime();
        if (!formatToYYYYMMDD(_date).equals(formatToYYYYMMDD(befDate))) {
            return getTimeDesc(_date, _now);
        }
        long diff = _date.getTime() - _befDate;
        if (diff > 5 * 60000) {
            return getTimeDesc(_date, _now);
        }
        return null;
    }

    /**
     * 描述：String类型的日期时间转化为Date类型.
     *
     * @param strDate String形式的日期时间
     * @param format  格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return Date Date类型日期时间
     */
    public static Date getDateByFormat(String strDate, String format) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = mSimpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
