package com.example.ms_demo.util;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * 生成导出Excel文件对象
 *
 * @author caihua
 *
 */
public class ExcelWriter {
	// 设置cell编码解决中文高位字节截断
//	private static short XLS_ENCODING = HSSFCell.ENCODING_UTF_16;

	// 定制浮点数格式
	public static String NUMBER_FORMAT = "#,##0.00";

	// 定制日期格式
	public static String DATE_FORMAT = "m/d/yy"; // "m/d/yy h:mm"

	public OutputStream out = null;

	private HSSFWorkbook workbook = null;

	public HSSFSheet sheet = null;

	public HSSFRow row = null;

	public ExcelWriter() {}

	/**
	 * 初始化Excel
	 *
	 */
	public ExcelWriter(OutputStream out) {
		this.out = out;
		this.workbook = new HSSFWorkbook();
		this.sheet = workbook.createSheet();
	}
	
	public ExcelWriter(OutputStream out,String sheetName ) {
		this.out = out;
		this.workbook = new HSSFWorkbook();
		this.sheet = workbook.createSheet(sheetName);
	}

	/**
	 * 导出Excel文件
	 *
	 * @throws IOException
	 */
	public void export() throws FileNotFoundException, IOException {
		try {
			workbook.write(out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			throw new IOException(" 生成导出Excel文件出错! ", e);
		} catch (IOException e) {
			throw new IOException(" 写入Excel文件出错! ", e);
		}
	}

	 /**
	  * 增加一行
	  *
	  * @param index
	  *            行号
	  */
	public void createRow(int index) {
		this.row = this.sheet.createRow(index);
	}

	 /**
	  * 获取单元格的值
	  *
	  * @param index
	  *            列号
	  */
	public String getCell(int index) {	
		HSSFCell cell = this.row.getCell(index);
		String strExcelCell = "";
		if (cell != null) { // add this condition
			// judge
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_FORMULA:
				strExcelCell = "FORMULA ";
				break;
			case HSSFCell.CELL_TYPE_NUMERIC: {
				strExcelCell = String.valueOf(cell.getNumericCellValue());
			}
			break;
			case HSSFCell.CELL_TYPE_STRING:
				strExcelCell = cell.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				strExcelCell = "";
				break;
				default:
				strExcelCell = "";
				break;
			}
		}
		return strExcelCell;
	}

	 /**
	  * 设置单元格
	  *
	  * @param index
	  *            列号
	  * @param value
	  *            单元格填充值
	  */
	public void setCell(int index, int value) {
		HSSFCell cell = this.row.createCell(index);
		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);
	}

	 /**
	  * 设置单元格
	  *
	  * @param index
	  *            列号
	  * @param value
	  *            单元格填充值
	  */
	public HSSFCellStyle setCellStyle() {
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中 
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中

		 // 背景色
		style.setFillForegroundColor(HSSFColor.YELLOW.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); 
		style.setFillBackgroundColor(HSSFColor.YELLOW.index); 

		// 设置边框
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
		// 自动换行  
		style.setWrapText(true);  

		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 10);
		font.setColor(HSSFColor.RED.index);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");

		// 把字体 应用到当前样式
		style.setFont(font);
		
		return style;
	}
	/**
	 * 设置单元格
	 *
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充值
	 */
	public void setCell(int index, double value) {
		HSSFCell cell = this.row.createCell(index);
		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);
		HSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式
		HSSFDataFormat format = workbook.createDataFormat();
		cellStyle.setDataFormat(format.getFormat(NUMBER_FORMAT)); // 设置cell样式为定制的浮点数格式
		cell.setCellStyle(cellStyle); // 设置该cell浮点数的显示格式
	}

	 /**
	  * 设置单元格
	  *
	  * @param index
	  *            列号
	  * @param value
	  *            单元格填充值
	  */
	public void setCell(int index, String value) {
		HSSFCell cell = this.row.createCell( index);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		//cell.setEncoding(XLS_ENCODING);
		cell.setCellValue(value);
	}

	 /**
	  * 设置单元格
	  *
	  * @param index
	  *            列号
	  * @param value
	  *            单元格填充值
	  */
	public void setCell(int index, Calendar value) {
		HSSFCell cell = this.row.createCell(index);
		//cell.setEncoding(XLS_ENCODING);
		cell.setCellValue(value.getTime());
		HSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat(DATE_FORMAT)); // 设置cell样式为定制的日期格式
		cell.setCellStyle(cellStyle); // 设置该cell日期的显示格式
	}

}