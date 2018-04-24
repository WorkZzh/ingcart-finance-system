package com.pythe.common.utils;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.alibaba.fastjson.JSONObject;

public class ExcelExport {
	public static void getHeadStyleRow(Sheet sheet0, Font headFont, CellStyle headStyle, String[] headcontent) {
		headFont.setFontName("宋体");
		headFont.setBoldweight((short) 40);
		headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headStyle.setFont(headFont);
		headStyle.setAlignment(CellStyle.ALIGN_CENTER);
		Row headRow = sheet0.createRow(0);
		for (int i = 0; i < headcontent.length; i++) {
			sheet0.setColumnWidth(i, 5000);
			Cell time = headRow.createCell(i);
			time.setCellValue(headcontent[i]);
			time.setCellStyle(headStyle);
		}
	}

	public static void getBodyMonthStyleRow(Sheet sheet0, Font headFont, CellStyle headStyle, String[] monthcontent,
			int rowCount, int cellCount) {
		headFont.setFontName("宋体");
		headFont.setBoldweight((short) 30);
		headStyle.setFont(headFont);
		headStyle.setAlignment(CellStyle.ALIGN_CENTER);
		Row headRow = sheet0.createRow(rowCount);
		for (int i = 0; i < monthcontent.length; i++) {
			Cell time = headRow.createCell(i + cellCount);
			time.setCellValue(monthcontent[i]);
			time.setCellStyle(headStyle);
		}
	}

	public static void getBodyDayStyleRow(Sheet sheet0, Font headFont, CellStyle headStyle, List<JSONObject> results,
			int rowCount) {
		headFont.setFontName("宋体");
		headFont.setBoldweight((short) 30);
		headStyle.setFont(headFont);
		headStyle.setAlignment(CellStyle.ALIGN_CENTER);
		int rowCountDESC = rowCount + results.size() - 1;
		for (int i = 0; i < results.size(); i++) {
			/*
			 * Row headRow = sheet0.createRow(rowCount + i);
			 * 
			 * Cell cell = headRow.createCell(0);
			 * cell.setCellType(Cell.CELL_TYPE_FORMULA);
			 * cell.setCellFormula("hyperlink(\"#" + i + "日!A1\",\"" +
			 * results.get(i).getString("time") + "\")");
			 * cell.setCellStyle(headStyle);
			 */

			String monthcontent[] = { results.get(i).getString("time"), results.get(i).getString("sum"),
					results.get(i).getString("refundAmount"), results.get(i).getString("givingAmount"),
					results.get(i).getString("qrId") };
			getBodyMonthStyleRow(sheet0, headFont, headStyle, monthcontent, rowCountDESC, 0);
			rowCountDESC--;
		}
	}

	public static void getBodyDayDetailRow(Sheet sheet0, Font headFont, CellStyle headStyle, List<JSONObject> results,
			int rowCount) {
		headFont.setFontName("宋体");
		headFont.setBoldweight((short) 30);
		headStyle.setFont(headFont);
		headStyle.setAlignment(CellStyle.ALIGN_CENTER);
		for (int i = 0; i < results.size(); i++) {
			String monthcontent[] = { results.get(i).getString("date"),results.get(i).getString("car_number"), results.get(i).getString("phone_number"),
					results.get(i).getString("distribution_name"), results.get(i).getString("start_time"),
					results.get(i).getString("stop_time"), results.get(i).getString("sum"),
					results.get(i).getString("typeName") };
			getBodyMonthStyleRow(sheet0, headFont, headStyle, monthcontent, rowCount + i, 0);
		}
	}

	public static String export(JSONObject recordMonthJson, List<JSONObject> results, List<JSONObject> daydetails,
			int dtype) {
		Workbook workbook = new SXSSFWorkbook(100);
		Sheet sheet0 = workbook.createSheet("交易汇总");
		// 交易汇总sheet 头
		CellStyle headStyle = workbook.createCellStyle();
		CellStyle bodyStyle = workbook.createCellStyle();
		Font headFont = workbook.createFont();
		Font bodyFont = workbook.createFont();
		String headcontent[] = { "时间", "总销售额", "总退款额", "总优惠额", "总次数" };
		getHeadStyleRow(sheet0, headFont, headStyle, headcontent);
		// 体
		String monthcontent[] = { recordMonthJson.getString("time"), recordMonthJson.getString("sum"),
				recordMonthJson.getString("refundAmount"), recordMonthJson.getString("givingAmount"),
				recordMonthJson.getString("qrId") };
		getBodyMonthStyleRow(sheet0, bodyFont, bodyStyle, monthcontent, 1, 0);
		getBodyDayStyleRow(sheet0, bodyFont, bodyStyle, results, 2);
		if (dtype == 0) {
			// 日明细
			String dateTemp = daydetails.get(daydetails.size() - 1).getString("date");
			List<JSONObject> days = new LinkedList<JSONObject>();
			for (int i = daydetails.size() - 1; i >= 0; i--) {
				if (daydetails.get(i).getString("date").equals(dateTemp)) {
					days.add(daydetails.get(i));
				} else {
					Sheet daySheet = workbook.createSheet(dateTemp);
					dateTemp = daydetails.get(i).getString("date");
					String daycontent[] = { "时间", "车号", "手机", "园区", "开始", "结束", "消费额", "备注" };
					getHeadStyleRow(daySheet, headFont, headStyle, daycontent);
					getBodyDayDetailRow(daySheet, bodyFont, bodyStyle, days, 1);
					days.clear();
					days = new LinkedList<JSONObject>();
					days.add(daydetails.get(i));
				}
			}
			Sheet daySheet = workbook.createSheet(dateTemp);
			String daycontent[] = { "时间", "车号", "手机", "园区", "开始", "结束", "消费额", "备注" };
			getHeadStyleRow(daySheet, headFont, headStyle, daycontent);
			getBodyDayDetailRow(daySheet, bodyFont, bodyStyle, days, 1);
			days.clear();

		} else {
			// 月明细
			String dateTemp = daydetails.get(daydetails.size() - 1).getString("date").substring(0, 7);
			List<JSONObject> days = new LinkedList<JSONObject>();
			for (int i = daydetails.size() - 1; i >= 0; i--) {
				if ((daydetails.get(i).getString("date").substring(0, 7)).equals(dateTemp)) {
					days.add(daydetails.get(i));
				} else {
					Sheet daySheet = workbook.createSheet(dateTemp);
					dateTemp = daydetails.get(i).getString("date").substring(0, 7);
					String daycontent[] = { "时间", "车号", "手机", "园区", "开始", "结束", "消费额", "备注" };
					getHeadStyleRow(daySheet, headFont, headStyle, daycontent);
					getBodyDayDetailRow(daySheet, bodyFont, bodyStyle, days, 1);
					days.clear();
					days = new LinkedList<JSONObject>();
					days.add(daydetails.get(i));
				}
			}

			Sheet daySheet = workbook.createSheet(dateTemp);
			String daycontent[] = { "时间", "车号", "手机", "园区", "开始", "结束", "消费额", "备注" };
			getHeadStyleRow(daySheet, headFont, headStyle, daycontent);
			getBodyDayDetailRow(daySheet, bodyFont, bodyStyle, days, 1);
			days.clear();
		}

		long timeL = new Date().getTime();
		String fileName = "https://ingcart.com/login/file/" + timeL + ".xlsx";
		try {
			FileOutputStream fileout = new FileOutputStream(fileName);
			workbook.write(fileout);
			fileout.flush();
			fileout.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return fileName;

	}
}
