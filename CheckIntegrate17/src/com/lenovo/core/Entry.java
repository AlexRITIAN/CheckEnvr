package com.lenovo.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import com.lenovo.core.Core;

public class Entry {
//	private static String sys;
//	private static String enviroment;
	public static void main(String[] args) {
		List<List<String>> result = new ArrayList<>();
		String classPath = System.getProperty("java.class.path");
		String path = classPath.substring(0, classPath.indexOf("CheckIntegrate.jar"));
		try {
			Core core = new Core();
			core.init(path);
			long start = System.currentTimeMillis();
//			sys = args[0];
//			enviroment = args[1];
			List<String> typeAndValues = core.readExcel();
			System.out.println("Total : " + typeAndValues.size() + " loaded!");
			System.out.println("searching....please wait a moment");
			for (String typeAndValue : typeAndValues) {
				String[] split = typeAndValue.split("=",3);
				result.add(core.excutCMD(split[1],split[2]));
			}
			core.outResult(result,core.createFile(path));
			long end = System.currentTimeMillis();
			System.out.println("Elapsed Time : " + (end - start));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}