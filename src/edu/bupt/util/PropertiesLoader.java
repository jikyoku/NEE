package edu.bupt.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
	public static void main(String[] args) {
		Properties p = new Properties();
		try {
			p.load(new FileInputStream("src/sys.properties"));
			System.out.println(p.get("url"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
