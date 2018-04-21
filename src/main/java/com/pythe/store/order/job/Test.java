package com.pythe.store.order.job;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.joda.time.DateTime;
import org.springframework.util.concurrent.ListenableFutureTask;

import com.pythe.common.utils.DateUtils;

public class Test {
	public static void main(String[] args) {
//		System.out.println(DateUtils.formatTime(new Date()));
//		System.out.println(DateUtils.parseTime(DateUtils.getTodayDate()+" 20:00:00"));
		//System.out.println(RandomUtils.nextInt(1, 3));
//		List<Integer> list = new LinkedList<Integer>();
//		list.add(1);
//		list.add(2);
//		list.add(3);
//		Collections.shuffle(list);
//		for (Integer integer : list) {
//			System.out.println(integer);
//		}
		
//		List<String> list = getSequenceDate("2018-02-01 00:00:00","2018-04-19 23:59:59");
//		for (String string : list) {
//			System.out.println(string);
//		}
		
System.out.println(DateUtils.formatTime(new DateTime(new Date()).plusMinutes(60).toDate()));
		
	}
	
	public static List<String> getSequenceDate(String start_date,String end_date){
		List<String> result = new LinkedList<String>();
		Date start = DateUtils.parseTime(start_date);
		Date end = DateUtils.parseTime(end_date);
		while(true){
			result.add(DateUtils.formatDate(start));
			start = new DateTime(start).plusDays(1).toDate();
			if (start.after(end)) {
				break;
			}
		}
		return result;
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
