package com.w77996.utils.string;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;



import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
/**
 * 
 * @ClassName:       StringUtils
 * @Description:     TODO
 * @author:          w77996
 * @date:            2017年7月24日        下午6:46:32
 */
public class StringUtils {
	
	
	private StringUtils(){
		
	}
	/**
	 * 从json数据中获取字符串
	 * @Title:           getStringFromJson
	 * @Description:     TODO
	 * @param:           @param json
	 * @param:           @param str
	 * @param:           @return   
	 * @return:          String   
	 * @throws
	 */
	public static String getStringFromJson(JSONObject json,String str){
		return json.containsKey(str)?json.getString(str):"";
	}
	/**
	 * 
	 * @Title:           getIntFromJSon
	 * @Description:     TODO
	 * @param:           @param json
	 * @param:           @param str
	 * @param:           @return   
	 * @return:          int   
	 * @throws
	 */
	public static int getIntFromJSon(JSONObject json,String str){
		return json.containsKey(str)?json.getInteger(str):0;
	}
	/**
	 * 
	 * @Title:           getJsonArrayFromJson
	 * @Description:     TODO
	 * @param:           @param json
	 * @param:           @param str
	 * @param:           @return   
	 * @return:          JSONArray   
	 * @throws
	 */
	public static JSONArray getJsonArrayFromJson(JSONObject json,String str){
		return json.containsKey(str)?json.getJSONArray(str):null;
	}
	/**
	 * 
	 * @Title:           getFloatFromJson
	 * @Description:     TODO
	 * @param:           @param json
	 * @param:           @param str
	 * @param:           @return   
	 * @return:          float   
	 * @throws
	 */
	public static float getFloatFromJson(JSONObject json,String str){
		return json.containsKey(str)?json.getFloat(str):0L;
	}
	/**
	 * 
	 * @Title:           getDateFromJson
	 * @Description:     TODO
	 * @param:           @param json
	 * @param:           @param str
	 * @param:           @return   
	 * @return:          Date   
	 * @throws
	 */
	public static Date getDateFromJson(JSONObject json,String str){
		return json.containsKey(str)?json.getDate(str):null;
	}
	/**
	 * 
	 * @Title:           isEmpty
	 * @Description:     TODO
	 * @param:           @param str
	 * @param:           @return   
	 * @return:          boolean   
	 * @throws
	 */
	public static boolean isEmpty(String str){
		return null == str || "".equals(str);
	}
	/**
	 * 
	 * @Title:           isEmpty
	 * @Description:     TODO
	 * @param:           @param objects
	 * @param:           @return   
	 * @return:          boolean   
	 * @throws
	 */
	public static boolean isEmpty(Object[] objects){
		return null == objects || 0 == objects.length;
	}
	/**
	 * 
	 * @Title:           isEmpty
	 * @Description:     TODO
	 * @param:           @param object
	 * @param:           @return   
	 * @return:          boolean   
	 * @throws
	 */
	public static boolean isEmpty(Object object){
		if(null == object){
			return true;
		}
		if(object instanceof String){
			return ((String)object).trim().isEmpty();
		}
		return !(object instanceof Number)?false:false;
	}
	/**
	 * 
	 * @Title:           isEmpty
	 * @Description:     TODO
	 * @param:           @param list
	 * @param:           @return   
	 * @return:          boolean   
	 * @throws
	 */
	public static boolean isEmpty(List<?> list){
		return null == list || list.isEmpty();
	}
	
	public static boolean isEmpty(Map<?, ?> map){
		return null == map || map.isEmpty();
	}
	/**
	 * 
	 * @Title:           getExt
	 * @Description:     TODO
	 * @param:           @param filename
	 * @param:           @return   
	 * @return:          String   
	 * @throws
	 */
	public static String getExt(String filename){
		return filename.substring(filename.lastIndexOf(".")+1);
	}
	/**
	 * 
	 * @Title:           get32UUID
	 * @Description:     TODO
	 * @param:           @return   
	 * @return:          String   
	 * @throws
	 */
	public static String get32UUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	/**
	 * 
	 * @Title:           getUUID
	 * @Description:     TODO
	 * @param:           @return   
	 * @return:          String   
	 * @throws
	 */
	public static String getUUID(){
		return UUID.randomUUID().toString();
	}
	/**
	 * 验证一个字符串是否完全由数字组成
	 * @Title:           isNumeric
	 * @Description:     TODO
	 * @param:           @param str
	 * @param:           @return   
	 * @return:          boolean   
	 * @throws
	 */
	public static boolean isNumeric(String str){
		if(StringUtils.isBlank(str)){
			return false;
		}else{
			return str.matches("\\d*");
		}
	}
	/**
	 * 源码中isBlank
	 * @Title:           isBlank
	 * @Description:     TODO
	 * @param:           @param str
	 * @param:           @return   
	 * @return:          boolean   
	 * @throws
	 */
	public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }
}
