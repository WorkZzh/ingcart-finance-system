package com.pythe.store.order.job;

import java.util.Date;

import com.pythe.common.utils.DateUtils;

public class Test {
	public static void main(String[] args) {
		System.out.println(DateUtils.formatTime(new Date()));
		System.out.println(DateUtils.parseTime(DateUtils.getTodayDate()+" 20:00:00"));
		
		
		
	}
}
