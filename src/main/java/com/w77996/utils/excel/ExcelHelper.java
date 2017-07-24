package com.w77996.utils.excel;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

/**
 * 
 * @ClassName:       ExcelHelper
 * @Description:     Excel工具抽象类
 * @author:          w77996
 * @date:            2017年7月24日        下午2:00:41
 */
public abstract class ExcelHelper {
	/**
	 * 对象序列化版本号名称
	 */
	public static final String UID = "serialVersionUID";
	/**
	 * 
	 * @Title:           readExcel
	 * @Description:     将指定excel文件中的数据转换成数据列表
	 * @param:           @param clazz
	 * @param:           @param sheetNo
	 * @param:           @param hasTitle
	 * @param:           @return
	 * @param:           @throws Exception   
	 * @return:          List<T>   
	 * @throws
	 */
	public <T> List<T> readExcel(Class<T> clazz,int sheetNo,boolean hasTitle) throws Exception{
		Field[] fields = clazz.getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		for(int i = 0; i < fields.length; i++){
			String fieldName = fields[i].getName();
			fieldNames[i] = fieldName;
		}
		return readExcel(clazz, fieldNames, sheetNo,hasTitle);
 	}
	/**
	 * 
	 * @Title:           readExcel
	 * @Description:     将指定excel文件中的数据转换成数据列表
	 * @param:           @param clazz
	 * @param:           @param fieldNames
	 * @param:           @param hasTitle
	 * @param:           @return
	 * @param:           @throws Exception   
	 * @return:          List<T>   
	 * @throws
	 */
	public <T> List<T> readExcel(Class<T> clazz,String[] fieldNames,boolean hasTitle) throws Exception{
		return readExcel(clazz, fieldNames, 0,hasTitle);
	}
	/**
	 * 
	 * @Title:           readExcel
	 * @Description:     抽象方法：将指定excel文件中的数据转换成数据列表，由子类实现
	 * @param:           @param clazz�������
	 * @param:           @param fieldNames�����б�
	 * @param:           @param sheetNo��������
	 * @param:           @param hasTitle�Ƿ���б���
	 * @param:           @return
	 * @param:           @throws Exception   
	 * @return:          List<T>   
	 * @throws			 
	 */
	public abstract <T> List<T> readExcel(Class<T> clazz,String[] fieldNames,int sheetNo,boolean hasTitle) throws Exception;

	/**
	 * 
	 * @Title:           writeExcel
	 * @Description:     写入数据到指定excel文件中
	 * @param:           @param clazz
	 * @param:           @param dataModels
	 * @param:           @throws Exception   
	 * @return:          void   
	 * @throws
	 */
	public <T> void writeExcel(Class<T> clazz,List<T> dataModels) throws Exception{
		Field[] fields = clazz.getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		for(int i = 0; i < fields.length; i++){
			String fieldName = fields[i].getName();
			fieldNames[i] = fieldName;
		}
		writeExcel(clazz, dataModels,fieldNames,fieldNames);
	}
	/**
	 * 
	 * @Title:           writeExcel
	 * @Description:     抽象方法：写入数据到指定excel文件中，由子类实现
	 * @param:           @param clazz
	 * @param:           @param dataModels
	 * @param:           @param fieldNames
	 * @param:           @param title
	 * @param:           @throws Exception   
	 * @return:          void   
	 * @throws
	 */
	public abstract <T> void writeExcel(Class<T> clazz,List<T> dataModels,String[] fieldNames,String[] title) throws Exception;
	/**
	 * 
	 * @Title:           isDateType
	 * @Description:     判断属性是否为日期类型
	 * @param:           @param clazz
	 * @param:           @param fieldName
	 * @param:           @return   
	 * @return:          boolean   
	 * @throws
	 */
	protected <T> boolean isDateType(Class<T> clazz,String fieldName){
		boolean flag = false;
		try {
			Field field = clazz.getDeclaredField(fieldName);
			Object typeObject = field.getType().newInstance();
			flag = typeObject instanceof Date;
		} catch (Exception e) {
			// 忽略异常直接返回false
		}
		return flag;
	}
	/**
	 * 
	 * @Title:           parseValueWithType
	 * @Description:     根据类型将指定参数转换成对应的类型
	 * @param:           @param value
	 * @param:           @param type
	 * @param:           @return   
	 * @return:          Object   
	 * @throws
	 */
	protected <T> Object parseValueWithType(String value,Class<?> type){
		Object result = null;
		try {
			if(Boolean.TYPE == type){
				result = Boolean.parseBoolean(value);
			}else if(Byte.TYPE == type){
				result = Byte.parseByte(value);
			}else if(Short.TYPE == type){
				result = Short.parseShort(value);
			}else if(Integer.TYPE == type){
				result = Integer.parseInt(value);
			}else if(Long.TYPE == type){
				result = Long.parseLong(value);
			}else if(Float.TYPE == type) {
				result = Float.parseFloat(value);
			}else if(Double.TYPE == type){
				result = Double.parseDouble(value);
			}else{
				result = (Object)value;
			}
		} catch (Exception e) {
			// 忽略异常直接返回null
		}
		return result;
	}
}
