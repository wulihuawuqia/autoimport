package net.riking.util;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Create by Xuhui on 2006-10-20 All rights reserved by Vxichina ESBU
 */
public class DateUtil implements Constants {
    public static String getInputDate(Date date) {
        return parseDateToInput(date, CONST_INPUT_DATE_FORMAT);
    }

    /*
     * 代码code 目前考虑D,W,M三种方式 如：000D�? 000W00D�? 000M00D
     * 
     * 算法：判断code是否为合法，如果合法，先取第四位，再取相应�?�进行计�?

     */
    public static Date computeDate(Date date, String code) {
        if (code.length() == 4) {
            String flag = code.substring(3, 4);
            if ("D".equalsIgnoreCase(flag)) {
                date = getDateByType(date, Calendar.DATE, Integer.parseInt(code.substring(0, 3)));
            } else if ("W".equalsIgnoreCase(flag)) {
                date = getDateByType(date, Calendar.WEEK_OF_YEAR, Integer.parseInt(code.substring(0, 3)));
            } else if ("M".equalsIgnoreCase(flag)) {
                date = getDateByType(date, Calendar.MONTH, Integer.parseInt(code.substring(0, 3)));
            }

        } else {
            String flag = code.substring(3, 4);
            if ("W".equalsIgnoreCase(flag)) {
                date = setDateByWeek(date, Integer.parseInt(code.substring(0, 3)), code.substring(4, 6));
            } else {
                date = setDateByMonth(date, Integer.parseInt(code.substring(0, 3)), code.substring(4, 6));
            }
        }
        return date;

    }
    /**
     * 根据date得到上一月的日期
     * @param date
     * @param offset
     * @return
     */
	public static Date addMonthByDate(Date date, int offset) {
		return DateUtils.addMonths(date, offset);
	}
	/**
	 * 获得某一个月份的�?后一�?
	 * 
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	public static Date getLastDayByMonth(Date date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);// 设为当前月的1�?
		cal.add(Calendar.MONTH, 1);// 加一个月，变为下月的1�?
		cal.add(Calendar.DATE, -1);// 减去�?天，变为当月�?后一�?

		return formatDate(cal.getTime(), "yyyy-MM-dd");
	}
	/**
	 * 获得上一个月份的�?后一�?
	 * 
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	public static Date getFrontMonth(Date date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);// 设为当前月的1�?
		cal.add(Calendar.DATE, -1);// 减去�?天，变为当月�?后一�?
		return formatDate(cal.getTime(), "yyyy-MM-dd");
	}
	/**
	 * 将一个日期转换为指定格式的日期类�?
	 * 
	 * @param date
	 *            要转换的日期
	 * @param dateFormat
	 *            日期格式
	 * @return 转换后的日期对象
	 * @throws ParseException 
	 */
	public static Date formatDate(Date date, String dateFormat) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return strToDate(sdf.format(date), dateFormat);
	}
	/**
	 * 将字符串按指定的格式转换为日期类�?
	 * 
	 * @param str
	 *            日期字符�?
	 * @param format
	 *            指定格式
	 * @return 格式化后的日期对�?
	 * @throws ParseException 
	 */
	public static Date strToDate(String str, String format) throws ParseException {

//		SimpleDateFormat dtFormat = null;
//		try {
//			dtFormat = new SimpleDateFormat(format);
//
//			return dtFormat.parse(str);
//
//		} catch (Exception e) {
//
//			e.printStackTrace();
//
//		}
//		return null;
		
		SimpleDateFormat dtFormat = new SimpleDateFormat(format);
		return dtFormat.parse(str);
	} 
    /*
     * 代码code 目前考虑两种方式 分别为：XXXXX & XX:XX
     * 
     * 算法：判断code是否为合法，如果合法，先取第四位，再取相应�?�进行计�?

     */
    public static Date computeTime(Date date, String code) {
        if (":".equals(code.substring(2, 3))) {
            date = setDateByTime(date, Calendar.HOUR_OF_DAY, Integer.parseInt(code.substring(0, 2)));
            date = setDateByTime(date, Calendar.MINUTE, Integer.parseInt(code.substring(3, 5)));
        } else {
            date = getDateByType(date, Calendar.MINUTE, Integer.parseInt(code));
        }
        return date;
    }
    /**
     * 通过星期获取日期
     * @param date 日期
     * @param numOfWeek �?年中第几�?

     * @param dayOfWeek 星期�?

     * @return
     */
    public static Date setDateByWeek(Date date, int numOfWeek, String dayOfWeek) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.WEEK_OF_YEAR, numOfWeek);
        cal.set(Calendar.DAY_OF_WEEK, Integer.parseInt(dayOfWeek) + 1);
        return cal.getTime();
    }
    /**
     * 通过月份获取日期
     * @param date 日期
     * @param numOfWeek
     * @param dayOfWeek 
     * @return
     */
    public static Date setDateByMonth(Date date, int numOfMonth, String dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, numOfMonth);
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dayOfMonth));
        return cal.getTime();
    }

    /**
     * @param date --
     *            当前日期
     * @param type --
     *            类型
     * @param num --
     *            该类型添加数�?

     * @return --添加后的日期
     */
    public static Date getDateByType(Date date, int type, int num) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(type, num);
        return cal.getTime();
    }

    /**
     * @param date --
     *            当前日期
     * @param type --
     *            类型
     * @param num --
     *            该类型添加数�?

     * @return --添加后的日期的前�?�?

     */
    public static Date getDateByTypeBefore(Date date, int type, int num) {
        Calendar cal = Calendar.getInstance();
        Calendar calStart = Calendar.getInstance();
        cal.setTime(date);
        calStart.setTime(date);
        cal.add(type, num);
        if(calStart.get(Calendar.DAY_OF_MONTH)<=cal.get(Calendar.DAY_OF_MONTH)){
        	cal.add(Calendar.DATE, -1);
        }
        return cal.getTime();
    }
    
    /**
     * @param date --
     *            当前日期
     * @param type --
     *            类型
     * @param num --
     *            该类型设置数�?

     * @return -- 设置后的日期
     */
    public static Date setDateByTime(Date date, int type, int num) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(type, num);

        return cal.getTime();
    }

    public static String getDateString(String date, Integer myunit, Integer myfield) {
        try {
            if (myfield == null) {
                return "";
            }
            int field = myfield.intValue();
            int unit = myunit.intValue();
            Calendar c = Calendar.getInstance();
            DateFormat df = new SimpleDateFormat(CONST_INPUT_DATE_FORMAT);
            c.setTime(df.parse(date));

            if (unit == 131001) {
                c.add(Calendar.YEAR, field);
            }
            if (unit == 131002) {
                c.add(Calendar.MONTH, field);
            }

            if (unit == 131003) {
                c.add(Calendar.WEEK_OF_MONTH, field);
            }

            if (unit == 131004) {
                c.add(Calendar.DATE, field);
            }
            if (unit != 131004 && field > 0) {
                c.add(Calendar.DATE, -1);
            }

            return df.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    public static String getDateString(String date, Integer myunit, Integer myfield,String format) {
        try {
            if (myfield == null) {
                return "";
            }
            int field = myfield.intValue();
            int unit = myunit.intValue();
            Calendar c = Calendar.getInstance();
            DateFormat df = new SimpleDateFormat(format);
            c.setTime(df.parse(date));

            if (unit == 131001) {
                c.add(Calendar.YEAR, field);
            }
            if (unit == 131002) {
                c.add(Calendar.MONTH, field);
            }

            if (unit == 131003) {
                c.add(Calendar.WEEK_OF_MONTH, field);
            }

            if (unit == 131004) {
                c.add(Calendar.DATE, field);
            }
            if (unit != 131004 && field > 0) {
                c.add(Calendar.DATE, -1);
            }

            return df.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    public static String getDateString(String date, String myunit, String myfield) {
        try {
            if (StringUtils.isEmpty(myfield)) {
                return "";
            }
            int field = Integer.parseInt(myfield);
            int unit = Integer.parseInt(myunit);
            Calendar c = Calendar.getInstance();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            c.setTime(parseStrToDate(date));

            if (unit == 1) {
                c.add(Calendar.YEAR, field);
            }
            if (unit == 2) {
                c.add(Calendar.MONTH, field);
            }

            if (unit == 3) {
                c.add(Calendar.WEEK_OF_MONTH, field);
            }

            if (unit == 4) {
                c.add(Calendar.DATE, field);
            }
            if (field > 0) {
                c.add(Calendar.DATE, -1);
            }

            return df.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
   /**
    * 判断指定日期是否位于两个日期之间
    * @param curDay
    * @param from
    * @param to
    * @return
    */
    public static boolean isBetween(Date curDay, Date from, Date to) {
        if (curDay == null || from == null || to == null) {
            return false;
        }
        if (curDay.compareTo(from) >= 0 && curDay.compareTo(to) <= 0) {
            return true;
        }
        return false;
    }
    
    public static boolean isBetweenNotEqule(Date curDay, Date from, Date to) {
        if (curDay == null || from == null || to == null) {
            return false;
        }
        if (from.compareTo(to) == 0) {
            return false;
        }
        if (curDay.compareTo(from) >= 0 && curDay.compareTo(to) <= 0) {
            return true;
        }
        return false;
    }

    public static int compare(Date curDay, Date oldDate) {
        if (curDay == null || oldDate == null) {
            return -1;
        }

        return curDay.compareTo(oldDate);
    }
    /**
     * 日期格式转化 String 转化为Date
     * @param input
     * @return
     * @throws ParseException
     */
    public static Date parseStrToDate(String input) throws ParseException {
        return parseStrToDate(input, CONST_INPUT_DATE_FORMAT);
    }
    /**
     * 日期格式转化为指定格式的Spring
     * @param input
     * @param format
     * @return
     * @throws ParseException
     */
    public static Date parseStrToDate(String input, String format) throws ParseException {
        if (StringUtils.isEmpty(input)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(input);
    }

    public static String getInput(Date date) {
        return parseDateToInput(date, CONST_INPUT_DATE_FORMAT);
    }

    public static String getFullInput(Date date) {
        return parseDateToInput(date, CONST_DATE_TIME_FORMAT);
    }

    public static String getTimeInput(Date date) {
        return parseDateToInput(date, CONST_TIME_FORMAT);
    }
    /**
     * 将日期转化成特定类型的字符串
     * @param date
     * @param format
     * @return
     */
    public static String parseDateToInput(Date date, String format) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
    /**
     * 
     * @param date
     * @param unit
     * @param field
     * @return
     */
    public static Date getDate(Date date, int unit, int field) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (unit == 1) {
            c.add(Calendar.YEAR, field);
        }
        if (unit == 2) {
            c.add(Calendar.MONTH, field);
        }

        if (unit == 3) {
            c.add(Calendar.WEEK_OF_MONTH, field);
        }

        if (unit == 4) {
            c.add(Calendar.DATE, field);
        }

        if (field > 0) {
            c.add(Calendar.DATE, -1);
        }
        return c.getTime();
    }

    public static Date getDate(String input, String format, int unit, int field) throws ParseException {
        return getDate(parseStrToDate(input, format), unit, field);
    }
   /**
    * 验证时间字符串是否有�?

    * 格式：hh:ss
    * @param timeStr
    * @return
    */
    public static boolean checkValidTime(String timeStr) {
        String[] arr = StringUtils.split(timeStr, ":");
        if (arr.length != 2) {
            return false;
        }
        int hour = Integer.parseInt(arr[0]);
        int min = Integer.parseInt(arr[1]);
        if (hour < 24 && hour >= 0 && min < 60 && min >= 0) {
            return true;
        }
        return false;
    }
    /**
     * 验证日期字符串是否有�?

     * 格式：hh:ss
     * @param timeStr
     * @return
     */
    public static boolean checkValidDate(String dateStr) {
        String[] arr = StringUtils.split(dateStr, "-");
        if (arr.length != 3) {
            return false;
        }
        int year = Integer.parseInt(arr[0]);
        int month = Integer.parseInt(arr[1]);
        int day = Integer.parseInt(arr[2]);

        if (year > 1970 && year < 9999 && month > 0 && month < 13) {
            Calendar cal = Calendar.getInstance();
            cal.set(year, month - 1, 1);
            if (day <= cal.getActualMaximum(Calendar.DAY_OF_MONTH) && day > 0) {
                return true;
            }
        }
        return false;
    }
    /**
     * 将指定日期类型转化为当天初始时间
     * �? 2010-04-03 00:00:00.0
     * @param dateStr
     * @return
     */
    public static Date getStartTimeOfDate(String dateStr) {
        return Timestamp.valueOf(dateStr + " 00:00:00.0");
    }
    /**
     * 将指定日期类型转化为当天末端时间
     * �? 2010-04-03 23:59:59.0
     * @param dateStr
     * @return
     */
    public static Date getEndTimeOfDate(String dateStr) {
        return Timestamp.valueOf(dateStr + " 23:59:59.0");
    }
    
    public static Date getDelayDate(Date date,int field,int input){
    	//System.out.println(date.toString());
    	  Calendar c=Calendar.getInstance();
 	     c.setTime(date);
 	    // c.set(field, c.get(field)+input);
 	      c.set(2, c.get(2)+3);
 	     return c.getTime();
    }
    
    /**
     * 取得系统当前时间前n个月的相对应的一�?


     * @param n int
     * @return String yyyy-mm-dd
     */
    @SuppressWarnings("static-access")
	public static String getNMonthBeforeCurrentDay(int n) {
        Calendar c = Calendar.getInstance();
        c.add(c.MONTH, -n);
        String year = String.valueOf(c.get(c.YEAR));
        String month = String.valueOf(c.get(c.MONTH)+1);
        String date = String.valueOf(c.get(c.DATE));
        if(month.length()<2){
      	  month = "0"+month;
        }
        if(date.length()<2){
      	  date = "0"+date;
        }
        return   year+ "-"+month+ "-"+date; 
    }
    /**  
     * 获取某年第一天日�?  
     * @param year 年份  
     * @return Date  
     */  
    public static Date getCurrYearFirst(int year){   
        Calendar calendar = Calendar.getInstance();   
        calendar.clear();   
        calendar.set(Calendar.YEAR, year);   
        Date currYearFirst = calendar.getTime();   
        return currYearFirst;   
    }  
       
    /**  
     * 获取某年�?后一天日�?  
     * @param year 年份  
     * @return Date  
     */  
    public static Date getCurrYearLast(int year){   
        Calendar calendar = Calendar.getInstance();   
        calendar.clear();   
        calendar.set(Calendar.YEAR, year);   
        calendar.roll(Calendar.DAY_OF_YEAR, -1);   
        Date currYearLast = calendar.getTime();   
           
        return currYearLast;   
    } 
    
    /**
     *将指定日期格式的字符串转换成指定日期格式
     * @param sourceDate	源日期（2018-10-10或�??20181010�?
     * @param targetFormat	�?要转换成的格式（-或�??.�?
     * @return
     * @throws ParseException
     */
    public static String parseStrToStrDate(String sourceDate, String targetFormat) throws ParseException{
    	String strDate = sourceDate.replaceAll("\\.", "").replaceAll("\\-", "");
    	String newDate = strDate.substring(0, 4)+targetFormat+strDate.substring(4, 6)+targetFormat+strDate.substring(6, 8);
    	return newDate;
    }
    
    public static void main(String[] args)throws Exception {
//    	 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
//        Date d1=sdf.parse("2015-09-30");  
//        Date d2=sdf.parse("2015-12-15");  
//        System.out.println(daysBetween(d2,d1));  
      //  System.out.println(daysBetween("2012-09-08 10:10:10","2012-09-15 00:00:00"));
    	System.out.println(parseStrToStrDate("20180810", ""));

    }
    public static int daysBetween(Date smdate,Date bdate) throws ParseException    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
        return Integer.parseInt(String.valueOf(between_days));           
    }    
      

}
