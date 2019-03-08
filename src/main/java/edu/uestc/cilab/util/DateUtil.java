package edu.uestc.cilab.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Copyright ©2016 创联工作室@电子科技大学计算机科学与工程学院 iustu.com(http://www.iustu.com)
 * <p>
 * Create with marxism in com.iustu.marxism.util
 * Class: DateUtil.java
 * User: joe-mac
 * Email: zhangshuzhou.hi@163.com
 * Time: 2016-08-24 16:16
 * Description:
 */
public class DateUtil {

    /**
     * 获取一天的开始时间或者结束时间
     *
     * @param date
     * @return
     * @flag 0 返回yyyy-MM-dd 00:00:00日期<br>
     * 1 返回yyyy-MM-dd 23:59:59日期
     */
    public static Date weeHours(Date date, int flag) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        //时分秒（毫秒数）
        long millisecond = hour * 60 * 60 * 1000 + minute * 60 * 1000 + second * 1000;
        //凌晨00:00:00
        cal.setTimeInMillis(cal.getTimeInMillis() - millisecond);

        if (flag == 0) {
            return cal.getTime();
        } else if (flag == 1) {
            //凌晨23:59:59
            cal.setTimeInMillis(cal.getTimeInMillis() + 23 * 60 * 60 * 1000 + 59 * 60 * 1000 + 59 * 1000);
        }
        return cal.getTime();
    }

    /**
     * 获取某个特定时间的前一天的时刻
     *
     * @param specifiedTime
     */
    public static Date getSpecifiedDayBefore(Date specifiedTime) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.DATE, c.get(Calendar.DATE) - 1);

        return c.getTime();

    }
}
