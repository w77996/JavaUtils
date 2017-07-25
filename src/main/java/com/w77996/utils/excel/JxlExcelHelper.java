package com.w77996.utils.excel;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.w77996.utils.date.DateUtil;

import com.w77996.utils.string.StringUtil;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * 
 * @ClassName:       JxlExcelHelper
 * @Description:     基于JXL实现的Excel工具类
 * @author:          w77996
 * @date:            2017年7月24日       下午3:22:36
 */
public class JxlExcelHelper extends ExcelHelper{

	private static JxlExcelHelper instance = null;// 单例对象
	
	private File file;// 操作文件

	/**
	 * 私有化构造方法
	 * @param file文件对象
	 */
	private JxlExcelHelper(File file){
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
	 * @Description:     获取单例对象并进行初始化
	 * @param:           @param file
	 * @param:           @return   
	 * @return:          JxlExcelHelper   
	 * @throws
	 */
	public static JxlExcelHelper getInstance(File file){
		if(instance == null){
			synchronized (JxlExcelHelper.class) {
				if(instance == null){
					instance = new JxlExcelHelper(file);
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
	 * @Description:     获取单例对象并进行初始化
	 * @param:           @param filePath
	 * @param:           @return   
	 * @return:          JxlExcelHelper   
	 * @throws
	 */
	public static JxlExcelHelper getInstance(String filePath){
		return getInstance(new File(filePath));
	}
	/**
	 * 
	 * Title: readExcel
	 * Description: 
	 * @param clazz
	 * @param fieldNames
	 * @param sheetNo
	 * @param hasTitle
	 * @return
	 * @throws Exception
	 * @see com.w77996.utils.excel.ExcelHelper#readExcel(java.lang.Class, java.lang.String[], int, boolean)
	 */
	@Override
	public <T> List<T> readExcel(Class<T> clazz, String[] fieldNames,
			int sheetNo, boolean hasTitle) throws Exception {
		List<T> dataModels = new ArrayList<T>();
		Workbook workbook = Workbook.getWorkbook(file);
		try {
			Sheet sheet = workbook.getSheet(sheetNo);
			int start = hasTitle ? 1 : 0;//如果有标题则从第二行开始
			for(int i = start; i < sheet.getRows();i++){
				//生成实例并通过反射调用setter方法
				T target = clazz.newInstance();
				for(int j = 0;j < fieldNames.length; j++){
					String fieldName = fieldNames[j];
					if(fieldName == null ||UID.endsWith(fieldName) ){
						continue;//过滤serialVersionUID属性
					}
					//获取excel单元格的内容
					Cell cell = sheet.getCell(j,i);
					if(cell == null){
						continue;
					}
					String content = cell.getContents();
					//如果属性是日期类型则将内容转换成日期对象
					if(isDateType(clazz, fieldName)){
						ReflectUtil.invokeSetter(target, fieldName,
								DateUtil.parse(content));
					} else {
						Field field = clazz.getDeclaredField(fieldName);
						ReflectUtil.invokeSetter(target, fieldName, parseValueWithType(content,field.getType()));
					}
				}
				dataModels.add(target);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(workbook != null){
				workbook.close();
			}
		}
		return dataModels;
	}
	/**
	 * 
	 * Title: writeExcel
	 * Description: 将数据
	 * @param clazz
	 * @param dataModels
	 * @param fieldNames
	 * @param title
	 * @throws Exception
	 * @see com.w77996.utils.excel.ExcelHelper#writeExcel(java.lang.Class, java.util.List, java.lang.String[], java.lang.String[])
	 */
	@Override
	public <T> void writeExcel(Class<T> clazz, List<T> dataModels,
			String[] fieldNames, String[] title) throws Exception {
		WritableWorkbook workbook = null;
		try {
			//检测文件是否存在，如果存在则修改文件，否则创建文件
			if(file.exists()){
				Workbook book = Workbook.getWorkbook(file);
				workbook = Workbook.createWorkbook(file, book);
			}else{
				workbook = Workbook.createWorkbook(file);
			}
			//根据当前工作表数量创建相应编号的工作表
			int sheetNo = workbook.getNumberOfSheets() +1;
			String sheetName = DateUtil.format(new Date(),"yyyyMMddHHmmssSS");
			WritableSheet sheet = workbook.createSheet(sheetName, sheetNo);
			//添加表格标题
			for(int i = 0;i < title.length;i++){
				WritableFont font = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD);
				WritableCellFormat format = new WritableCellFormat(font);
				
				//设置自动换行
				format.setWrap(true);
				Label label = new Label(i,0,title[i],format);
				sheet.addCell(label);
				//设置单元格宽度
				sheet.setColumnView(i,title[i].length()+10);
			}
			//添加表格内容
			for(int i = 0;i < dataModels.size();i++){
				//遍历列表属性
				for(int j = 0;j <fieldNames.length;j++){
					T target = dataModels.get(i);
					String fieldName = fieldNames[j];
					if(fieldName == null || UID.equals(fieldName)){
						continue;
					}
					Object result  = ReflectUtil.invokeGetter(target, fieldName);
					Label label = new Label(j,i+1,StringUtil.toString(result));
					
					//如果是日期则进行格式化处理
					if(isDateType(clazz, fieldName)){
						label.setString(DateUtil.format((Date)result));
					}
					sheet.addCell(label);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(workbook != null){
				workbook.write();
				workbook.close();
			}
		}
		
	}
	
	
}
