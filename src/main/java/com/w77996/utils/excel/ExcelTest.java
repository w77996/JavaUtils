package com.w77996.utils.excel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;



public class ExcelTest {

	public static void main(String[] args) {
		List<Employee> employees = new ArrayList<Employee>();
		employees.add(new Employee(1000, "Jones", 40, "Manager", 2975));
		employees.add(new Employee(1001, "Blake", 40, "Manager", 2850));
		employees.add(new Employee(1002, "Clark", 40, "Manager", 2450));
		employees.add(new Employee(1003, "Scott", 30, "Analyst", 3000));
		employees.add(new Employee(1004, "King", 50, "President", 5000));
		String[] titles = new String[]{"工号", "姓名", "年龄", "职称", "薪资（美元）", "入职时间"};
		String[] fieldNames = new String[]{"id", "name", "age", "job", "salery", "addtime"};
		try {
			File file1 = new File("E:\\JXL2003.xls");
			if(file1.exists()){
				System.out.println("file1 exists");
			}
			ExcelHelper eh1 = JxlExcelHelper.getInstance(file1);
			if(eh1 == null){
				System.out.println("null");
			}
			eh1.writeExcel(Employee.class, employees);
			eh1.writeExcel(Employee.class, employees, fieldNames, titles);
			List<Employee> list1 = eh1.readExcel(Employee.class, fieldNames, true);
			if(list1 == null || list1.size() <= 0){
				System.out.println("list null");
			}
			System.out.println("-----------------JXL2003.xls-----------------");
			for (Employee user : list1) {
				System.out.println(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
