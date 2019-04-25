package cn.csg.jobschedule.util;

import cn.csg.jobschedule.constants.DatetimeConstants;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/***
 * @Author: xushiyong
 * @Description 时间工具类
 * @Date: Created in 16:57 2018/11/8
 * @Modify By:
 **/
public class DatetimeUtil {

    /**
     * 字符串转日期
     * @param str
     * @param pattern
     * @return
     */
    public static Date toDate(String str, String pattern) {
        DateTimeFormatter format = DateTimeFormat.forPattern(pattern);
        Date date = DateTime.parse(str, format).toDate();
        return date;
    }


    /**
     * 日期转字符串
     * @param date
     * @param pattern
     * @return
     */
    public static String toStr(Date date, String pattern) {
        SimpleDateFormat dayFormat = new SimpleDateFormat(pattern);
        String format = dayFormat.format(date);

        return format ;
    }

    /**
     * UTC转CST
     * @param UTCStr
     * @return
     * @throws ParseException
     */
    public static Date UTCToCST(String UTCStr) throws ParseException {
        Date resDate = new Date();
        SimpleDateFormat df = new SimpleDateFormat(DatetimeConstants.YYYY_MM_DD_T_HH_MM_SS_SSS);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        resDate = df.parse(UTCStr);
        return resDate;
    }

    /***
     * 得到一周中的第一天，星期一为一周开始的第一天
     * @param dt
     * @param pattern
     * @return
     */
    public static String getFirstDayOfWeek(Date dt, String pattern){
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(dt);
        int weekYear = calendar.get(Calendar.YEAR);
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        calendar.setWeekDate(weekYear, weekOfYear, 2);
        Date startTime = calendar.getTime();

        SimpleDateFormat df = new SimpleDateFormat(pattern);
        String dateStart = df.format(startTime);
        return dateStart;
    }

    /**
     * 获取本月第一天
     * @param dt
     * @param pattern
     * @return
     */
    public static String getFirstDayOfMonth(Date dt, String pattern){
        Calendar calendar1=Calendar.getInstance();
        calendar1.setTime(dt);
        calendar1.set(Calendar.DAY_OF_MONTH, 1);

        SimpleDateFormat df = new SimpleDateFormat(pattern);
        String dateStart = df.format(calendar1.getTime());

        return dateStart ;
    }

    /**
     * 将日期DateTime按照pattern格式转成日期字符串
     * @param dt
     * @param pattern
     * @return String
     */
    public static String format(DateTime dt, String pattern) {
        return dt.toString(pattern);
    }

    /**
     * 获取两个日期相差的秒数
     * @param lastDate
     * @param beforeDate
     * @return long
     */
    public static long getSeconds(Date lastDate, Date beforeDate) {
        long sec = (lastDate.getTime() - beforeDate.getTime()) / 1000L;
        return sec;
    }

    public static void main(String[] args)  {
//        try {
//            //UTCToCST("2018-10-19T08:22:44.433") ;
//            UTCToCST("11211") ;
//        } catch (Exception e) {
//            System.out.println("11111") ;
//        }
//
//        System.out.println("22222") ;

        System.out.println(getFirstDayOfMonth(new Date(),DatetimeConstants.YYYYMMDD)) ;


    }

}
