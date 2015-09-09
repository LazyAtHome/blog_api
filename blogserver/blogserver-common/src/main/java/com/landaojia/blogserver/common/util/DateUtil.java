package com.landaojia.blogserver.common.util;

import java.text.ParseException;
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
}
