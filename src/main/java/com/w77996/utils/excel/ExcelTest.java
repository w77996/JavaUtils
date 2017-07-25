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
			ExcelHelper eh1 = JxlExcelHelper.getInstance(file1);
			eh1.writeExcel(Employee.class, employees);
			eh1.writeExcel(Employee.class, employees, fieldNames, titles);
			List<Employee> list1 = eh1.readExcel(Employee.class, fieldNames, true);
			System.out.println("-----------------JXL2003.xls-----------------");
			for (Employee user : list1) {
				System.out.println(user);
			}
			File file2 = new File("E:\\POI2003.xls");
			ExcelHelper eh2 = HssfExcelHelper.getInstance(file2);
			eh2.writeExcel(Employee.class, employees);
			eh2.writeExcel(Employee.class, employees, fieldNames, titles);
			List<Employee> list2 = eh2.readExcel(Employee.class, fieldNames, true);
			System.out.println("-----------------POI2003.xls-----------------");
			for (Employee user : list2) {
				System.out.println(user);
			}
			File file3 = new File("E:\\POI2007.xlsx");
			ExcelHelper eh3 = XssfExcelHelper.getInstance(file3);
			eh3.writeExcel(Employee.class, employees);
			eh3.writeExcel(Employee.class, employees, fieldNames, titles);
			List<Employee> list3 = eh3.readExcel(Employee.class, fieldNames, true);
			System.out.println("-----------------POI2007.xls-----------------");
			for (Employee user : list3) {
				System.out.println(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
