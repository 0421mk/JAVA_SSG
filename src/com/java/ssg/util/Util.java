package com.java.ssg.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Util {
	public static String getNowDateStr() {
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
		Calendar time = Calendar.getInstance();
	       
		String format_time1 = format1.format(time.getTime());
		
		return format_time1;
	}
	
	public static boolean isNumeric(String str) {
		boolean check = str.matches("-?\\d+");

		return check;
	}
}
