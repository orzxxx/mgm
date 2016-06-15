package com.centerm.utils;

public class StringUtils {
	
	public static boolean isNull(String str){
		return str == null || str.trim().equals("") ;
	}
	
	public static String join(String[] strArr, char separator){
		if (strArr.length == 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < strArr.length; i++){ 
			sb.append(strArr[i].trim() + separator); 
		} 
		return sb.toString().substring(0, sb.toString().length() - 1);
	}
}
