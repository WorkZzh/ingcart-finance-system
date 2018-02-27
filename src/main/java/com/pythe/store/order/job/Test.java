package com.pythe.store.order.job;

import java.util.Date;

import com.pythe.common.utils.DateUtils;

public class Test {
	public static void main(String[] args) {
//		System.out.println(DateUtils.formatTime(new Date()));
//		System.out.println(DateUtils.parseTime(DateUtils.getTodayDate()+" 20:00:00"));
		System.out.println(toHourMinute(3600l));
		
		
	}
	
	public static String toHourMinute(long minutes) {
		String str = "";
		Double hour = Math.floor(minutes / 60);
		if (hour <= 9l) {
			str = str + "0" + hour.intValue();
		} else {
			str = str + hour.intValue();
		}
		long mi = minutes % 60l;
		if (mi < 9l) {
			str = str + ":" + "0" + mi;
		} else {
			str = str + ":" + mi;
		}
		return str;
	}
	
	
}
