package com.w77996.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.openxmlformats.schemas.drawingml.x2006.chart.STTickLblPos;

import com.fasterxml.jackson.databind.deser.impl.InnerClassProperty;
import com.w77996.utils.date.DateUtil;
import com.w77996.utils.string.StringUtil;

public class HssfExcelHelper extends ExcelHelper{

	private static HssfExcelHelper instance = null;
	
	private File file;
	
	private HssfExcelHelper(File file){
		super();
		this.file = file;
	}
	
	
	public File getFile() {
		return file;
	}


	public void setFile(File file) {
		this.file = file;
	}
	/**
	 * 
	 * @Title:           getInstance
	 * @Description:     单例
	 * @param:           @param file
	 * @param:           @return   
	 * @return:          HssfExcelHelper   
	 * @throws
	 */
	public static HssfExcelHelper getInstance(File file){
		if(instance == null){
			synchronized (HssfExcelHelper.class) {
				if(instance == null){
					instance = new HssfExcelHelper(file);
				}
			}
		}else{
			if(!file.equals(instance.getFile())){
				instance.setFile(file);
			}
		}
		return instance;
	}
	/**
	 * 
	 * @Title:           getInstance
	 * @Description:     单例初始化
	 * @param:           @param filePath
	 * @param:           @return   
	 * @return:          HssfExcelHelper   
	 * @throws
	 */
	public static HssfExcelHelper getInstance(String filePath){
		return getInstance(new File(filePath));
	}
	/**
	 * 
	 * @Title:           readExcel
	 * @Description:     TODO
	 * @param:           @param clazz
	 * @param:           @param fieldNames
	 * @param:           @param sheetNo
	 * @param:           @param hasTitle
	 * @param:           @return
	 * @param:           @throws Exception   
	 * @return:          List<T>   
	 * @throws
	 */
	@Override
	public <T> List<T> readExcel(Class<T> clazz, String[] fieldNames,
			int sheetNo, boolean hasTitle) throws Exception {
		List<T> dataModels = new ArrayList<T>();
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
		HSSFSheet sheet = workbook.getSheetAt(sheetNo);
		
		int start = sheet.getFirstRowNum()+(hasTitle ? 1 : 0);
		
		for(int i = start; i <= sheet.getLastRowNum(); i++){
			HSSFRow row = sheet.getRow(i);
			if(row == null){
				continue;
			}
			//生成实力并通过反射调用setter方法
			T target = clazz.newInstance();
			for(int j = 0; j< fieldNames.length;j++){
				String fieldName = fieldNames[j];
				if(fieldName == null || UID.equals(fieldName)){
					continue;
				}
				HSSFCell cell = row.getCell(j);
				if(cell == null){
					continue;
				}
				String content = cell.getStringCellValue();
				//如果属性是日期则将内容转换成日期对象
				if(isDateType(clazz, fieldName)){
					
					ReflectUtil.invokeSetter(target, fieldName, DateUtil.parse(content));
				}else{
					Field field = clazz.getDeclaredField(fieldName);
					ReflectUtil.invokeSetter(target, fieldName, parseValueWithType(content, field.getType()));
				}
			}
			dataModels.add(target);
		}
		return dataModels;
	}

	@Override
	public <T> void writeExcel(Class<T> clazz, List<T> dataModels,
			String[] fieldNames, String[] title) throws Exception {
		HSSFWorkbook workbook = null;
		if(file.exists()){
			FileInputStream fis = new FileInputStream(file);
			workbook = new HSSFWorkbook(fis);
		}else{
			workbook = new HSSFWorkbook();
		}
		//根据当前工作表数量创建相应编号的工作表
		String sheetName = DateUtil.format(new Date(),"yyyyMMddHHmmssSS");
		HSSFSheet sheet = workbook.createSheet(sheetName);
		HSSFRow headRow = sheet.createRow(0);
		//添加表格标题
		for(int i = 0 ; i < title.length;i++){
			HSSFCell cell = headRow.createCell(i);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(title[i]);
			//设置字体加粗
			HSSFCellStyle cellStyle = workbook.createCellStyle();
			HSSFFont font = workbook.createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			cellStyle.setFont(font);
			//设置自动换行
			cellStyle.setWrapText(true);
			cell.setCellStyle(cellStyle);
			//设置单元格宽度
			sheet.setColumnWidth(i, title[i].length()*1000);
		}
		//添加表格内容
		for(int i = 0; i < dataModels.size();i++){
			HSSFRow row = sheet.createRow(i+1);
			for(int j = 0;j <fieldNames.length; j++){
				String fieldName = fieldNames[j];
				if(fieldName == null ||UID.equals(fieldName)){
					continue;
				}
				Object result = ReflectUtil.invokeGetter(dataModels.get(i), fieldName);
				
				HSSFCell cell = row.createCell(j);
				cell.setCellValue(StringUtil.toString(result));
				//如果是日期类型则进行格式化处理
				if(isDateType(clazz, fieldName)){
					cell.setCellValue(DateUtil.format((Date)result));
				}
			}
		}
		//将数据写到磁盘上
		FileOutputStream fos = new FileOutputStream(file);
		try {
			workbook.write(new FileOutputStream(file));
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(fos != null){
				fos.close();
			}
		}
	}

}
