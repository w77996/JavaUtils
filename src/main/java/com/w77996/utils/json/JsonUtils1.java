package com.w77996.utils.json;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @ClassName:       JsonUtils1
 * @Description:     fastjson工具类
 * @author:          w77996
 * @date:            2017年7月25日        下午3:21:53
 */
public class JsonUtils1 {


	private JsonUtils1() {

	}

	/**
	 * 
	 * @Title: requestJson
	 * @Description: TODO
	 * @param: @param request
	 * @param: @return
	 * @return: JSONObject
	 * @throws
	 */
	public static JSONObject requestJson(HttpServletRequest request) {
		StringBuffer sBuffer = new StringBuffer();
		String line = null;
		JSONObject jsonObject = null;
		try {
			BufferedReader bReader = request.getReader();
			while ((line = bReader.readLine()) != null) {
				sBuffer.append(line);
			}
			bReader.close();
			jsonObject = JSONObject.parseObject(sBuffer.toString());
		} catch (IOException e) {
			// e.printStackTrace();
			//LOG.error(e);
		}
		return jsonObject;
	}

	/**
	 * 
	 * @Title: responseJson
	 * @Description: TODO
	 * @param: @param response
	 * @param: @param object
	 * @return: void
	 * @throws
	 */
	public static void responseJson(HttpServletResponse response, Object object) {
		Object obj = JSONObject.toJSON(object);

		try {
			response.getWriter().write(obj.toString());
		} catch (IOException e) {
			// e.printStackTrace();
			//LOG.error(e);
		}
	}

	/**
	 * 
	 * @Title: responseJson
	 * @Description: TODO
	 * @param: @param jsonObject
	 * @param: @param code
	 * @param: @return
	 * @return: String
	 * @throws
	 */
	public static String responseJson(JSONObject jsonObject, int code) {
		jsonObject.put("resultCode", code);
		return jsonObject.toJSONString();
	}
}
