package com.znl.common.tools.date;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Suyj  2016年12月19日 17:25:57
 * <p>
 * <yizhishita@126.com>
 */
public class DateUtils {
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final String DATETIMENOCONNECTOR="yyyyMMddHHmmss";   //无连接符的日期时间格式化

    private static ThreadLocal<SimpleDateFormat> threadLocalDatetimeFormatter = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DATETIME_FORMAT);
        }
    };
    private static ThreadLocal<SimpleDateFormat> threadLocalDateFormatter = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DATE_FORMAT);
        }
    };
    private static ThreadLocal<SimpleDateFormat> threadLocalTimeFormatter = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(TIME_FORMAT);
        }
    };
    private static ThreadLocal<SimpleDateFormat> threadLocalDateTimeNoConnectorFormatter = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DATETIMENOCONNECTOR);
        }
    };
    private static ConcurrentHashMap<String, ThreadLocal<SimpleDateFormat>> dateFormmaterCacheHashMap = new ConcurrentHashMap();

    /**
     * @param dateFormat
     * @return
     */
    public final static SimpleDateFormat fetchSimpleDateFormatter(String dateFormat) {
        if (dateFormmaterCacheHashMap.get(dateFormat) == null) {
            synchronized (dateFormmaterCacheHashMap) {
                if (dateFormmaterCacheHashMap.get(dateFormat) == null) {
                    ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>() {
                        @Override
                        protected SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(dateFormat);
                        }
                    };
                    dateFormmaterCacheHashMap.put(dateFormat, threadLocal);
                }
            }
        }
        return dateFormmaterCacheHashMap.get(dateFormat).get();
    }

    public final static SimpleDateFormat simpleDatetimeFormatter() {
        SimpleDateFormat simpleDateFormat = threadLocalDatetimeFormatter.get();
        return simpleDateFormat;
    }


    public final static SimpleDateFormat SimpleDatetimeNoConnectorFormat(){
        SimpleDateFormat simpleDateFormat =  threadLocalDateTimeNoConnectorFormatter.get();
        return simpleDateFormat;
    }

    /**
     * @return
     */
    public final static SimpleDateFormat simpleDateFormatter() {
        return threadLocalDateFormatter.get();
    }

    /**
     * @return
     */
    public final static SimpleDateFormat simpleTimeFormatter() {
        return threadLocalTimeFormatter.get();
    }

    public final static Date dateAdd(Date date, int years, int months, int days, int hours, int minutes, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, years);
        calendar.add(Calendar.MONTH, months);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        calendar.add(Calendar.HOUR, hours);
        calendar.add(Calendar.MINUTE, minutes);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    public final static int minuteBetween(Date date1, Date date2) {
        long seconds = (date1.getTime() - date2.getTime()) / 1000;
        return (int) seconds / (60);
    }

    public final static int hoursBetween(Date date1, Date date2) {
        long seconds = (date1.getTime() - date2.getTime()) / 1000;
        return (int) seconds / (60 * 60);
    }
//    public final static int daysBetween(Date date1, Date date2) {
//        long seconds = (date1.getTime() - date2.getTime()) / 1000;
//        return (int) seconds / (60 * 60 * 24);
//    }

    /**
     * @param startTime
     * @param endTime
     * @return
     */
    public final static int daysBetween(Date startTime, Date endTime) {
        int days = 0;
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startTime);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endTime);
        int year1 = startCalendar.get(Calendar.YEAR);
        int year2 = endCalendar.get(Calendar.YEAR);
        Calendar calendar;
        if (startCalendar.before(endCalendar)) {
            days -= startCalendar.get(Calendar.DAY_OF_YEAR);
            days += endCalendar.get(Calendar.DAY_OF_YEAR);
            calendar = startCalendar;
        } else {
            days -= endCalendar.get(Calendar.DAY_OF_YEAR);
            days += startCalendar.get(Calendar.DAY_OF_YEAR);
            calendar = endCalendar;
        }
        for (int i = 0; i < Math.abs(year2 - year1); i++) {
            days += calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
            calendar.add(Calendar.YEAR, 1);
        }
        return days;
    }

    public final static int monthsBetween(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);
        int year1 = c1.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int year2 = c2.get(Calendar.YEAR);
        int month2 = c2.get(Calendar.MONTH);
        int result;
        if (year1 == year2) {
            result = month2 - month1;
        } else {
            result = 12 * (year2 - year1) + month2 - month1;
        }
        return result;
    }

    public final static int yearsBetween(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        return year2 - year1;
    }
}