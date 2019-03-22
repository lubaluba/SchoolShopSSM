package com.pre.zlm.o2o.utils;

import java.util.UUID;

public class UUIDUtils {
 
	public static String getUUID_48() {
		String[] arr1 = UUID.randomUUID().toString().split("-");
		StringBuffer uuid = new StringBuffer();
		for(String s : arr1) {
			uuid.append(s);
		}
		
		String[] arr2 = UUID.randomUUID().toString().subSequence(0, 16).toString().split("-");
		for(String s : arr2) {
			uuid.append(s);
		}
		
		return uuid.toString();
	}
	
	public static String getUUID_32() {
		String[] arr = UUID.randomUUID().toString().split("-");
		StringBuffer uuid = new StringBuffer();
		for(String s : arr) {
			uuid.append(s);
		}
		return uuid.toString();
		
	}
	
	public static String getUUID_16() {
		String[] arr = UUID.randomUUID().toString().split("-");
		StringBuffer uuid = new StringBuffer();
		for(String s : arr) {
			uuid.append(s);
		}
		return uuid.substring(0, 16).toString();
		
	}
}
