package com.lenovo.core;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Core {
	private static String cmd = "xconfmanager -d ";
	private static String file;
	private String[] envp;
	
	public List<String> excutCMD(String suffix,String value) throws IOException {
		List<String> result = new ArrayList<>();
		Process process = Runtime.getRuntime().exec(cmd + suffix);// 执行cmd命令
		BufferedReader input = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(process.getInputStream())));// 获取控制台输入流
		BufferedReader error = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(process.getErrorStream())));// 获取控制台输入流
		String line = "";
		int n = 1;
		while ((line = input.readLine()) != null) {
			n++;
			if(n == 4 && !"Values:".equals(line.substring(3))){
				result.add("False");
				result.add(suffix);
				result.add(value);
				result.add(line.substring(3));
				break;
			}
			if(n == 5){
				if(value != ""){
					if(!value.equals(line.substring(5))){
						result.add("False");
					}else{
						result.add("True");
					}
				}else{
					if(line.substring(5) == ""){
						result.add("True");
						
					}else{
						result.add("False");
					}
				}
				result.add(suffix);
				result.add(value);
				result.add(line.substring(5));
				break;
			}
		}
		while ((line = error.readLine()) != null) {
			System.out.println("Error Message:");
			System.out.println(line);
		}
		input.close();
		error.close();
		return result;
	}
	
	public List<String> readExcel() throws IOException {
		System.out.println("loading comparison file......");
		List<String> typeAndValues = new ArrayList<String>();
		//1)创建一个【读取流】，指向【硬盘上的excel文档】
		InputStream in = new FileInputStream(file);
       //2) 通过【读取流】将【硬盘上的excel文档】加载到内存
		XSSFWorkbook excelObj = new XSSFWorkbook(in);
	   //3）定位【内存中excel文档】中的第一个小页对象
		XSSFSheet sheetObj=excelObj.getSheetAt(0);
	   // 4）读取小页对象中，所有【拥有数据的】行对象，保存到Iterator<HSSFRow>
		Iterator<?> it=sheetObj.rowIterator();
	  //5）循环Iteator
		while(it.hasNext()){
			  //5.1)每次循环，读取一个【行对象】
			XSSFRow rowObj = (XSSFRow) it.next();
			typeAndValues.add(rowObj.getCell(0).getStringCellValue() + "=" + rowObj.getCell(1).getStringCellValue());
		}
		excelObj.close();
		return typeAndValues;
	}
	
	public void outResult(List<List<String>> result,File file) throws IOException{
		FileWriter fw = new FileWriter(file);
		int n = 0;
		for (List<String> list : result) {
			if("False".equals(list.get(0))){
				fw.write("[ " + list.get(0) + " ] " + "[ " + list.get(1) + " ]\r\n");
				fw.write("comparsion value = [ " + list.get(2) + " ]\r\n");
				fw.write("current value = [ " + list.get(3) + " ]\r\n");
				n++;
			}
		}
		System.out.println("Check completed! " + n + " Faled,Please check \"Result.txt\" get detail.");
		fw.close();
	}
	
	public File createFile(String path) throws IOException{
		File file = new File(path + "Result.txt");
		if(file.exists()){
			file.delete();
		}
		file.createNewFile();
		System.out.println("Result.txt created Success!");
		return file;
	}
	public void init(String path) throws IOException{
		String url;
		System.out.println("Loading config......");
		FileInputStream in = new FileInputStream(new File(path + "check_conf.properties"));
		Properties pro = new Properties();
		pro.load(in);
//		sys = pro.getProperty("sys");
//		enviroment = pro.getProperty("enviroment");
		file = path + pro.getProperty("file");
//		url = pro.getProperty("url") + pro.getProperty("sub_url");
//		String envpstr =  ClientUtil.get(url);
//		System.out.println(envpstr);
	}

}
