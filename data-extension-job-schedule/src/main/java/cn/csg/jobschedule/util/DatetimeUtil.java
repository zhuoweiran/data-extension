package cn.csg.jobschedule.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/***
 * @Author: xushiyong
 * @Description 时间工具类
 * @Date: Created in 16:57 2018/11/8
 * @Modify By:
 **/
public class DatetimeUtil {


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

    public static String formatDate(Date date, String pattern){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }
}
