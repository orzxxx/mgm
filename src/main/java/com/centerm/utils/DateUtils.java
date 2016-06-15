package com.centerm.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	public static String getCurrentDate(){
		String patten = "yyyyMMddHHmmss";
		return formatDate(new Date(), patten);
	}
	
	public static String getCurrentDate(String patten){
		return formatDate(new Date(), patten);
	}
	
	public static String formatDate(Date date, String patten){
		SimpleDateFormat sdf = new SimpleDateFormat(patten);
		return sdf.format(date);
	}
}
