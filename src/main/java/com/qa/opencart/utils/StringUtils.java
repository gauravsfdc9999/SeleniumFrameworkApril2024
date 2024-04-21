package com.qa.opencart.utils;

public class StringUtils {
	
	public static String getRandomEmailId() {
		//testautomation
		String emailId = "testauto"+System.currentTimeMillis()+"@opencart.com";
		System.out.println("Email ID : "+emailId);
		return emailId;
	}

}
