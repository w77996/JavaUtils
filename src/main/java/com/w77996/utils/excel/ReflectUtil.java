package com.w77996.utils.excel;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.hiwatch.watch.utils.StringUtils;

/**
 * 反射工具类
 * @ClassName:       ReflectUtil
 * @Description:     TODO
 * @author:          w77996
 * @date:            2017年7月24日        下午4:28:07
 */
public class ReflectUtil {

	/**
	 * 
	 * @Title:           invokeConstructor
	 * @Description:     反射调用指定构造方法创建对象
	 * @param:           @param clazz 对象类型
	 * @param:           @param argTypes 参数类型
	 * @param:           @param args 构造参数
	 * @param:           @return
	 * @param:           @throws NoSuchMethodException
	 * @param:           @throws SecurityException
	 * @param:           @throws InstantiationException
	 * @param:           @throws IllegalAccessException
	 * @param:           @throws IllegalArgumentException
	 * @param:           @throws InvocationTargetException   
	 * @return:          T   
	 * @throws
	 */
	public static <T> T invokeConstructor(Class<T> clazz,Class<?>[] argTypes,Object[] args) throws NoSuchMethodException,SecurityException,InstantiationException,IllegalAccessException,IllegalArgumentException,InvocationTargetException{
		Constructor<T> constructor = clazz.getConstructor(argTypes);
		return constructor.newInstance(args);
	}
	
	public static <T> Object invokeGetter(T target,String fieldName)throws NoSuchMethodException,SecurityException,IllegalAccessException,IllegalArgumentException,InvocationTargetException{
		String methodNameString = "get" +StringUtils
	}
}
