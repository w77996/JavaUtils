package com.w77996.utils.excel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

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
						
					}else{
						
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public <T> void writeExcel(Class<T> clazz, List<T> dataModels,
			String[] fieldNames, String[] title) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
}
