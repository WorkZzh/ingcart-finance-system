package com.pythe.common.utils;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.alibaba.fastjson.JSONObject;

public class ExcelExport {
	public static void getHeadStyleRow(Sheet sheet, Font font, CellStyle style, String[] headcontent) {
		font.setFontName("宋体");
		font.setBoldweight((short) 40);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		Row headRow = sheet.createRow(0);
		for (int i = 0; i < headcontent.length; i++) {
			sheet.setColumnWidth(i, 5000);
			Cell time = headRow.createCell(i);
			time.setCellValue(headcontent[i]);
			time.setCellStyle(style);
		}
	}

	public static Double round2(Double num) {
		double retTemp = (double) (Math.round(num * 100) / 100.0);
		DecimalFormat df = new DecimalFormat("0.00");
		Double ret = Double.parseDouble(df.format(retTemp));
		return ret;
	}

	public static void getBodyMonthStyleRow(Sheet sheet, Font font, CellStyle style, String[] monthcontent,
			int rowCount, int cellCount, int type) {
		font.setFontName("宋体");
		font.setBoldweight((short) 30);
		style.setFont(font);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		Row headRow = sheet.createRow(rowCount);
		for (int i = 0; i < monthcontent.length; i++) {
			Cell time = headRow.createCell(i + cellCount);
			if (type == 1) {
				if (i > 0) {
					style.setDataFormat((short) 2);
					time.setCellValue(round2(Double.parseDouble(monthcontent[i])));
					time.setCellStyle(style);
				} else {
					time.setCellValue(monthcontent[i]);
					time.setCellStyle(style);
				}
			} else if (type == 2) {
				if (i == 6) {
					style.setDataFormat((short) 2);
					time.setCellValue(round2(Double.parseDouble(monthcontent[i])));
					time.setCellStyle(style);
				} else {
					time.setCellValue(monthcontent[i]);
					time.setCellStyle(style);
				}
			}
		}
	}

	public static void getBodyDayStyleRow(Sheet sheet, Font font, CellStyle style, List<JSONObject> results,
			int rowCount) {
		font.setFontName("宋体");
		font.setBoldweight((short) 30);
		style.setFont(font);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		int rowCountDESC = rowCount;
		for (int i = 0; i < results.size(); i++) {
			String monthcontent[] = { results.get(i).getString("time"), results.get(i).getString("sum"),
					results.get(i).getString("refundAmount"), results.get(i).getString("givingAmount"),
					results.get(i).getString("qrId") };
			getBodyMonthStyleRow(sheet, font, style, monthcontent, rowCountDESC, 0, 1);
			rowCountDESC++;
		}
	}

	public static void getBodyDayDetailRow(Sheet sheet0, Font headFont, CellStyle headStyle, List<JSONObject> results,
			int rowCount) {
		headFont.setFontName("宋体");
		headFont.setBoldweight((short) 30);
		headStyle.setFont(headFont);
		headStyle.setAlignment(CellStyle.ALIGN_CENTER);
		for (int i = 0; i < results.size(); i++) {
			String monthcontent[] = { results.get(i).getString("date"), results.get(i).getString("car_number"),
					results.get(i).getString("phone_number"), results.get(i).getString("distribution_name"),
					results.get(i).getString("start_time"), results.get(i).getString("stop_time"),
					results.get(i).getString("sum"), results.get(i).getString("typeName") };
			getBodyMonthStyleRow(sheet0, headFont, headStyle, monthcontent, rowCount + i, 0, 2);
		}
	}

	// 流传输
	public static void export(JSONObject recordMonthJson, List<JSONObject> results, List<JSONObject> daydetails,
			int dtype, HttpServletResponse response) {
		if (results.size() != 0) {
			Workbook workbook = new SXSSFWorkbook(100);
			Sheet sheet0 = workbook.createSheet("交易汇总");
			// 交易汇总sheet 头
			CellStyle headStyle = workbook.createCellStyle();
			CellStyle bodyStyle = workbook.createCellStyle();
			Font headFont = workbook.createFont();
			Font bodyFont = workbook.createFont();
			String headcontent[] = { "时间", "总销售额", "总退还押金", "总优惠额", "总次数" };
			getHeadStyleRow(sheet0, headFont, headStyle, headcontent);
			// 体
			String monthcontent[] = { recordMonthJson.getString("time"), recordMonthJson.getString("sum"),
					recordMonthJson.getString("refundAmount"), recordMonthJson.getString("givingAmount"),
					recordMonthJson.getString("qrId") };
			getBodyMonthStyleRow(sheet0, bodyFont, bodyStyle, monthcontent, 1, 0, 1);
			getBodyDayStyleRow(sheet0, bodyFont, bodyStyle, results, 2);
			if (dtype == 0) {
				// 日明细
				String dateTemp = daydetails.get(0).getString("date");
				List<JSONObject> days = new LinkedList<JSONObject>();
				for (int i = 0; i < daydetails.size(); i++) {
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
				String dateTemp = daydetails.get(0).getString("date").substring(0, 7);
				List<JSONObject> days = new LinkedList<JSONObject>();
				for (int i = 0; i < daydetails.size(); i++) {
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
			BufferedOutputStream out = null;
			OutputStream outRes = null;
			try {
				String filename = "婴咖财务" + timeL + ".xlsx";
				filename = URLEncoder.encode(filename, "UTF-8");
				response.reset();
				response.setHeader("Content-Disposition", "attachment;filename=" + filename);
				response.setContentType("multipart/form-data");
				out = new BufferedOutputStream(response.getOutputStream());
				outRes = response.getOutputStream();
				workbook.write(out);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (outRes != null) {
					try {
						outRes.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		} else {
			Workbook workbook = new SXSSFWorkbook(100);
			Sheet sheet0 = workbook.createSheet("交易汇总");
			// 交易汇总sheet 头
			CellStyle headStyle = workbook.createCellStyle();
			Font headFont = workbook.createFont();
			String headcontent[] = { "时间", "总销售额", "总退还押金", "总优惠额", "总次数" };
			getHeadStyleRow(sheet0, headFont, headStyle, headcontent);
			long timeL = new Date().getTime();
			BufferedOutputStream out = null;
			OutputStream outRes = null;
			try {
				String filename = "婴咖财务" + timeL + ".xlsx";
				filename = URLEncoder.encode(filename, "UTF-8");
				response.reset();
				response.setHeader("Content-Disposition", "attachment;filename=" + filename);
				response.setContentType("multipart/form-data");
				out = new BufferedOutputStream(response.getOutputStream());
				outRes = response.getOutputStream();
				workbook.write(out);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (outRes != null) {
					try {
						outRes.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		}

	}

}
