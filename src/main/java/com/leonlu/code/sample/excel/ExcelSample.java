package com.leonlu.code.sample.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 读写xls/xlsx文件的示例代码
 * 
 * Maven dependencies:
 *	<dependency>
 *		<groupId>org.apache.poi</groupId>
 *		<artifactId>poi</artifactId>
 *		<version>3.12</version>
 *	</dependency>
 *
 *	<dependency>
 *		<groupId>org.apache.poi</groupId>
 *		<artifactId>poi-ooxml</artifactId>
 * 		<version>3.12</version>
 *	</dependency>
 * 
 */
public class ExcelSample {
	
	/**
	 * 读取outputFilePath所指定的文件
	 * @param inputFilePath
	 */
	public static void read(String inputFilePath) {
		String fileType = inputFilePath.substring(inputFilePath.lastIndexOf(".") + 1, inputFilePath.length());
		InputStream stream;
		Workbook wb = null;
		try {
			stream = new FileInputStream(inputFilePath);

			if (fileType.equals("xls")) {
				wb = new HSSFWorkbook(stream);
			} else if (fileType.equals("xlsx")) {
				wb = new XSSFWorkbook(stream);
			} else {
				System.out.println("您输入的excel格式不正确");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		Sheet sheet1 = wb.getSheetAt(0);
		for (Row row : sheet1) {
			for (Cell cell : row) {
				System.out.print(cell.getStringCellValue() + " ");
			}
			System.out.println();
		}
	}

	/**
	 * 写入outputFilePath所指定的文件，内容：
	 * 第1行第1列 第1行第2列 第1行第3列 
	 * 第2行第1列 第2行第2列 第2行第3列 
	 * 第3行第1列 第3行第2列 第3行第3列 

	 * @param outputFilePath
	 * @return
	 * @throws Exception
	 */
	public static boolean write(String outputFilePath) throws Exception {
		String fileType = outputFilePath.substring(outputFilePath.lastIndexOf(".") + 1, outputFilePath.length());
		// 创建工作文档对象
		Workbook wb = null;
		if (fileType.equals("xls")) {
			wb = new HSSFWorkbook();
		} else if (fileType.equals("xlsx")) {
			wb = new XSSFWorkbook();
		} else {
			System.out.println("您的文档格式不正确！");
			return false;
		}
		// 创建sheet对象
		Sheet sheet1 = (Sheet) wb.createSheet("sheet1");
		// 循环写入行数据
		for (int i = 0; i < 3; i++) {
			Row row = (Row) sheet1.createRow(i);
			// 循环写入列数据
			for (int j = 0; j < 3; j++) {
				Cell cell = row.createCell(j);
				cell.setCellValue("第" + (i + 1) + "行第" + (j + 1) + "列");
			}
		}
		// 创建文件流
		OutputStream stream = new FileOutputStream(outputFilePath);
		// 写入数据
		wb.write(stream);
		// 关闭文件流
		stream.close();
		return true;
	}

}
