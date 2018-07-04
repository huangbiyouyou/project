package com.project.demo.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Create by Victor(forvictorzhong@gmail.com)
 * 2017-08-25 21:37
 **/
public class TimeUtil {

    public static String getTime(String pattern) {
        return new SimpleDateFormat(pattern).format(new Date());
    }

    public static String getTime(Long time, String pattern) {
        return new SimpleDateFormat(pattern).format(new Date(time));
    }

    public static String getTime() {
        return getTime("yyyyMMdd_HH:mm:ss");
    }

    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public static int getDate() {
        return Calendar.getInstance().get(Calendar.DATE);
    }

    public static int getDay() {
        // 获得星期几（注意（这个与Date类是不同的）：1代表星期日、2代表星期1、3代表星期二，以此类推）
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 设置时间
     * @param year
     * @param month
     * @param date
     * @return
     */
    public static Calendar setCalendar(int year,int month,int date){
        Calendar cl = Calendar.getInstance();
        cl.set(year, month-1, date);
        return cl;
    }

    /**
     * 获取当前时间的前一天时间
     * @param cl
     * @return
     */
    public static Calendar getBeforeDay(Calendar cl){
        int day = cl.get(Calendar.DATE);
        cl.set(Calendar.DATE, day-1);
        return cl;
    }

    public static String getStringBeforeDay(String yyyyMMdd){
        Calendar calendar = setCalendar(Integer.parseInt(yyyyMMdd.substring(0, 4)), Integer.parseInt(yyyyMMdd.substring(4, 6)), Integer.parseInt(yyyyMMdd.substring(6)));
        Calendar beforeDay = getBeforeDay(calendar);
        String beforeTime = printCalendar(beforeDay);
        return beforeTime;
    }

    /**
     * 打印时间
     * @param cl
     */
    public static String printCalendar(Calendar cl){
        int year = cl.get(Calendar.YEAR);
        int month = cl.get(Calendar.MONTH)+1;
        int day = cl.get(Calendar.DATE);
        String monthStr="";
        String dayStr="";
        if (month < 10) {
            monthStr="0"+String.valueOf(month);
        }else {
            monthStr=String.valueOf(month);
        }

        if (day < 10) {
           dayStr="0"+String.valueOf(day);
        }else {
            dayStr=String.valueOf(day);
        }
        return String.valueOf(year)+monthStr+dayStr;
    }

    public static String getMonthStr() {
        int month = getMonth();
        if (month % 10 == 1) {
            return month + "";
        }
        return "0" + month;
    }

    public static String getNextMonthStr() {
        int month = getMonth() + 1;
        if (month % 10 == 1) {
            return month + "";
        }
        return "0" + month;
    }

    public static String getDateStr() {
        int date = getDate();
        if (date % 10 >= 1) {
            return date + "";
        }
        return "0" + date;
    }

    /**
     * @param time1 yyyy-MM-dd
     * @param time2 yyyy-MM-dd
     * @return 1 => time1大于time2
     * 0 => time1不大于time2
     */
    public static int compareTime(String time1, String time2) {
        String[] arr1 = time1.split("-"), arr2 = time2.split("-");


        int y1 = Integer.parseInt(arr1[0]), m1 = Integer.parseInt(arr1[1]), d1 = Integer.parseInt(arr1[2]),
                y2 = Integer.parseInt(arr2[0]), m2 = Integer.parseInt(arr2[1]), d2 = Integer.parseInt(arr2[2]);

        if (y1 > y2 && m1 > m2 && d1 > d2)
            return 1;
        return 0;
    }

    public static Long getUTCTimeFromDate(Integer year, Integer month, Integer day) {
        Calendar calendar = new Calendar.Builder().setDate(year, month, day).build();
        return calendar.getTime().getTime() / 1000;
    }


    public static void main(String[] args) {
        //字符串转Date
        Calendar calendar = new Calendar.Builder().setDate(2050, 9, 10).build();
        Calendar.getInstance().set(2050, 1, 20);

//Date转字符串
//        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(calendar.getTime().getTime() / 1000);
    }
}
