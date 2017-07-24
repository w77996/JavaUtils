package com.w77996.utils.excel;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.w77996.utils.string.StringUtil;

/**
 * 反射工具类
 * 
 * @ClassName: ReflectUtil
 * @Description: TODO
 * @author: w77996
 * @date: 2017年7月24日 下午4:28:07
 */
public class ReflectUtil {

	/**
	 * 
	 * @Title: invokeConstructor
	 * @Description: 反射调用指定构造方法创建对象
	 * @param: @param clazz 对象类型
	 * @param: @param argTypes 参数类型
	 * @param: @param args 构造参数
	 * @param: @return
	 * @param: @throws NoSuchMethodException
	 * @param: @throws SecurityException
	 * @param: @throws InstantiationException
	 * @param: @throws IllegalAccessException
	 * @param: @throws IllegalArgumentException
	 * @param: @throws InvocationTargetException
	 * @return: T
	 * @throws
	 */
	public static <T> T invokeConstructor(Class<T> clazz, Class<?>[] argTypes,
			Object[] args) throws NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Constructor<T> constructor = clazz.getConstructor(argTypes);
		return constructor.newInstance(args);
	}

	/**
	 * 
	 * @Title: invokeGetter
	 * @Description: 反射调用指定对象属性的getter方法
	 * @param: @param target 指定对象
	 * @param: @param fieldName 属性名
	 * @param: @return
	 * @param: @throws NoSuchMethodException
	 * @param: @throws SecurityException
	 * @param: @throws IllegalAccessException
	 * @param: @throws IllegalArgumentException
	 * @param: @throws InvocationTargetException
	 * @return: Object
	 * @throws
	 */
	public static <T> Object invokeGetter(T target, String fieldName)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		String methodName = "get" + StringUtil.firstCharUpperCase(fieldName);
		Method method = target.getClass().getMethod(methodName);
		return method.invoke(target);
	}

	/**
	 * 
	 * @Title: invokeSetter
	 * @Description: 反射调用指定对象属性的setter方法
	 * @param: @param target
	 * @param: @param fieldName
	 * @param: @param args
	 * @param: @throws NoSuchMethodException
	 * @param: @throws SecurityException
	 * @param: @throws NoSuchMethodException
	 * @param: @throws IllegalAccessException
	 * @param: @throws IllegalArgumentException
	 * @param: @throws InvocationTargetException
	 * @param: @throws NoSuchFieldException
	 * @return: void
	 * @throws
	 */
	public static <T> void invokeSetter(T target, String fieldName, Object args)
			throws NoSuchMethodException, SecurityException,
			NoSuchMethodException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchFieldException {
		String methodName = "set" + StringUtil.firstCharUpperCase(fieldName);
		Class<?> clazz = target.getClass();
		Field field = clazz.getDeclaredField(fieldName);
		Method method = clazz.getMethod(methodName, field.getType());
		method.invoke(target, args);
	}
	/**
	 * 
	 * @Title:           main
	 * @Description:     主程序  测试
	 * @param:           @param args   
	 * @return:          void   
	 * @throws
	 */
	public static void main(String[] args) {
		try {
			Class<Employee> clazz = Employee.class;
			Employee user = ReflectUtil.invokeConstructor(clazz,
					new Class<?>[] { long.class, String.class, int.class,
							String.class, double.class }, new Object[] { 1001,
							"Linux", 30, "123", 20.55 });
			System.out.println(user);
			ReflectUtil.invokeSetter(user, "salery", 2055);
			System.out.println(user);
			Object ret = ReflectUtil.invokeGetter(user, "salery");
			System.out.println(ret);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
