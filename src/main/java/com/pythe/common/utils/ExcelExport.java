package com.pythe.common.utils;

import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.alibaba.fastjson.JSONObject;

public class ExcelExport {
	public static String export(List<JSONObject> results) {
		String retUrl = "https://ingcart.com/login/file/";
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		sheet.setDefaultColumnWidth(20);
		HSSFFont headfont = workbook.createFont();
		headfont.setFontName("黑体");
		headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		HSSFCellStyle headstyle = workbook.createCellStyle();
		headstyle.setFont(headfont);
		headstyle.setLocked(true);
		try {
			long time = new Date().getTime();
			String fileName = "/home/opt/pythe-server/rest-8081-pythe/webapps/login/file/" + time + ".xls";
			retUrl = retUrl + time + ".xls";
			FileOutputStream fileout = new FileOutputStream(fileName);
			HSSFRow rowtitle = sheet.createRow(0);
			HSSFCell cell0=rowtitle.createCell(0);
			cell0.setCellValue(new HSSFRichTextString("园区"));
			cell0.setCellStyle(headstyle);
			HSSFCell cell1=rowtitle.createCell(1);
			cell1.setCellValue(new HSSFRichTextString("日期"));
			cell1.setCellStyle(headstyle);
			HSSFCell cell2=rowtitle.createCell(2);
			cell2.setCellValue(new HSSFRichTextString("时间"));
			cell2.setCellStyle(headstyle);
			HSSFCell cell3=rowtitle.createCell(3);
			cell3.setCellValue(new HSSFRichTextString("开始时间"));
			cell3.setCellStyle(headstyle);
			HSSFCell cell4=rowtitle.createCell(4);
			cell4.setCellValue(new HSSFRichTextString("结束时间"));
			cell4.setCellStyle(headstyle);
			HSSFCell cell5=rowtitle.createCell(5);
			cell5.setCellValue(new HSSFRichTextString("还车方式"));
			cell5.setCellStyle(headstyle);
			HSSFCell cell6=rowtitle.createCell(6);
			cell6.setCellValue(new HSSFRichTextString("手机号"));
			cell6.setCellStyle(headstyle);
			HSSFCell cell7=rowtitle.createCell(7);
			cell7.setCellValue(new HSSFRichTextString("金额"));
			cell7.setCellStyle(headstyle);

			for (int i = 0; i < results.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				row.createCell(0).setCellValue(new HSSFRichTextString(results.get(0).getString("distribution_name")));
				row.createCell(1).setCellValue(new HSSFRichTextString(results.get(0).getString("date")));
				row.createCell(2).setCellValue(new HSSFRichTextString(results.get(0).getString("start_time")));
				row.createCell(3).setCellValue(new HSSFRichTextString(results.get(0).getString("stop_time")));
				row.createCell(4).setCellValue(new HSSFRichTextString(results.get(0).getString("car_number")));
				row.createCell(5).setCellValue(new HSSFRichTextString(results.get(0).getString("typeName")));
				row.createCell(6).setCellValue(new HSSFRichTextString(results.get(0).getString("phone_number")));
				row.createCell(7).setCellValue(new HSSFRichTextString(results.get(0).getString("sum")));

			}
			workbook.write(fileout);
			fileout.flush();
			fileout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retUrl;
	}
}
