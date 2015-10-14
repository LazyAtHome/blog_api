package com.landaojia.blog.common.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具
 * @author liuxi
 */
public class DateUtil {
	public static Date strToDate( String str, String format ){
		try{
			return new SimpleDateFormat( format ).parse( str );
		}catch ( ParseException e) {
			return null;
		}
	}
	
	public static String getDate(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }
	
	public static Date strToDate( String str ){
		try{
			return new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ).parse( str );
		}catch ( ParseException e) {
			return null;
		}
	}

	public static boolean isDateFormat( String str, String fmt ){
		try{
			new SimpleDateFormat( fmt ).parse( str );
		}catch( ParseException e ){
			return false;
		}
		return true;
	}
	
	public static String dateToStr( Date date, String format ){
		return date == null? null : new SimpleDateFormat( format ).format( date );
	}

	/**
	 * 获取日期月份的第一天
	 * @param date
	 * @return
	 */
	public static String getFirstDayOfMonth(Date date){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String lastDay = df.format(calendar.getTime());
        return lastDay;
	}
	
	/**
	 * 获取日期月份的最后一天
	 * @param date
	 * @return
	 */
	public static String getLastDayOfMonth(Date date){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, 1);
        calendar.add(Calendar.DATE, -1); 
        String lastDay = df.format(calendar.getTime());
        return lastDay;
	}
	
    public static Date getCurrentDate() {
        Calendar cl = Calendar.getInstance();
        return cl.getTime();
    }
    
    public static String getDate(String format) {
        return getDate(new Date(), format);
    }

    public static String getDate() {
        return getDate(new Date(), "yyyy-MM-dd");
    }

    public static String getDateTime() {
        return getDateTime(new Date());
    }
    
    public static String getDateTime(Date date) {
        return getDate(date, "yyyy-MM-dd HH:mm:ss");
    }
    
    public static Date parse(String dataString) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dataString);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parse(String dataString, SimpleDateFormat sdf) {
        try {
            return sdf.parse(dataString);
        } catch (ParseException e) {
            return null;
        }
    }

    public static int daysBetween(Date begin, Date end) {
        Calendar cNow = Calendar.getInstance();
        Calendar cReturnDate = Calendar.getInstance();
        cNow.setTime(end);
        cReturnDate.setTime(begin);
        long todayMs = cNow.getTimeInMillis();
        long returnMs = cReturnDate.getTimeInMillis();
        long intervalMs = todayMs - returnMs;
        return millisecondsToDays(intervalMs);
    }

    private static int millisecondsToDays(long intervalMs) {
        return (int) (intervalMs / (1000 * 86400));
    }

    public static Date addSeconds(Date date, int seconds) {
        Calendar cNow = Calendar.getInstance();
        cNow.setTime(date);
        cNow.add(Calendar.SECOND, seconds);
        return new Date(cNow.getTimeInMillis());
    }

    public static Date addDays(Date date, int days) {
        Calendar cNow = Calendar.getInstance();
        cNow.setTime(date);
        cNow.add(Calendar.DAY_OF_MONTH, days);
        return new Date(cNow.getTimeInMillis());
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     * 
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 获取两个日期的间隔天数
     * @param startday
     * @param endday
     * @return
     */
    public static int getIntervalDays(Date startTime, Date endTime) {
        Calendar cStart = Calendar.getInstance();
        cStart.setTime(startTime);
        Calendar cEnd = Calendar.getInstance();
        cEnd.setTime(endTime);
        if (cStart.after(cEnd)) {
            Calendar cal = cStart;
            cStart = cEnd;
            cEnd = cal;
        }
        long sl = cStart.getTimeInMillis();
        long el = cEnd.getTimeInMillis();
        long ei = el - sl;
        return (int) (ei / (1000 * 60 * 60 * 24));
    }
    
    public static int getRemainingSeconds() {
        Calendar cal = Calendar.getInstance(); 
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0); 
        cal.set(Calendar.MINUTE, 0); 
        cal.set(Calendar.MILLISECOND, 0); 
        return (int) ((cal.getTimeInMillis() - System.currentTimeMillis())/1000);
    }
    
}
