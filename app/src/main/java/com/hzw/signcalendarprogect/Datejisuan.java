package com.hzw.signcalendarprogect;

import java.util.Calendar;
import java.util.Date;

public class Datejisuan {



    /**
     * 比较两个Date类型的日期大小

     * @return result返回结果(0--相同  1--前者大  2--后者大)
     * */
    private static int compareDate(Date sDate, Date eDate)
    {
        int result = 0;
        //将开始时间赋给日历实例
        Calendar sC = Calendar.getInstance();
        sC.setTime(sDate);
        //将结束时间赋给日历实例
        Calendar eC = Calendar.getInstance();
        eC.setTime(eDate);
        //比较
        result = sC.compareTo(eC);
        //返回结果
        return result;
    }


    /**
     * 计算两个日期的时间间隔
     *
     * @return interval时间间隔
     * */
    public static int calInterval(Date sDate, Date eDate, String type)
    {
        //时间间隔，初始为0
        int interval = 0;

        /*比较两个日期的大小，如果开始日期更大，则交换两个日期*/
        //标志两个日期是否交换过
        boolean reversed = false;
        if(compareDate(sDate, eDate) > 0)
        {
            Date dTest = sDate;
            sDate = eDate;
            eDate = dTest;
            //修改交换标志
            reversed = true;
        }

        /*将两个日期赋给日历实例，并获取年、月、日相关字段值*/
        Calendar sCalendar = Calendar.getInstance();
        sCalendar.setTime(sDate);
        int sYears = sCalendar.get(Calendar.YEAR);
        int sMonths = sCalendar.get(Calendar.MONTH);
        int sDays = sCalendar.get(Calendar.DAY_OF_YEAR);

        Calendar eCalendar = Calendar.getInstance();
        eCalendar.setTime(eDate);
        int eYears = eCalendar.get(Calendar.YEAR);
        int eMonths = eCalendar.get(Calendar.MONTH);
        int eDays = eCalendar.get(Calendar.DAY_OF_YEAR);

        //年
        if(cTrim(type).equals("Y") || cTrim(type).equals("y"))
        {
            interval = eYears - sYears;
            if(eMonths < sMonths)
            {
                --interval;
            }
        }
        //月
        else if(cTrim(type).equals("M") || cTrim(type).equals("m"))
        {
            interval = 12 * (eYears - sYears);
            interval += (eMonths - sMonths);
        }
        //日
        else if(cTrim(type).equals("D") || cTrim(type).equals("d"))
        {
            interval = 365 * (eYears - sYears);
            interval += (eDays - sDays);
            //除去闰年天数
            while(sYears < eYears)
            {
                if(isLeapYear(sYears))
                {
                    --interval;
                }
                ++sYears;
            }
        }
        //如果开始日期更大，则返回负值
        if(reversed)
        {
            interval = -interval;
        }
        //返回计算结果
        return interval;
    }
    /**
     *
     * 字符串去除两头空格，如果为空，则返回""，如果不空，则返回该字符串去掉前后空格
     *
     *
     * @return 如果为空，则返回""，如果不空，则返回该字符串去掉前后空格
     *
     */
    public static String cTrim(String tStr)
    {
        String ttStr = "";
        if (tStr == null)
        {}
        else
        {
            ttStr = tStr.trim();
        }
        return ttStr;
    }
    private static boolean isLeapYear(int year)
    {
        return (year%400 == 0 || (year%4 == 0 && year%100 != 0));
    }

}
