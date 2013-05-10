package com.cubead.jinjili.util;

import java.security.MessageDigest;

/**
 * md5算法实现
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * @author Weiwenqi
 * @version 1.0
 * 
 */

public class Md5Util {

	/**
	 * md5加密算法
	 * 
	 * @param value
	 *            欲使用md5算法加密的字符串
	 * @return String 已经使用md5算法加密后的字符丄1�7
	 */
	public static String MD5(String value) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(value.getBytes("UTF8"));
			byte s[] = md.digest();
			String result = "";
			for (int i = 0; i < s.length; i++) {
				result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
			}
			return result;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String MD5(String value,String enCoding) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(value.getBytes(enCoding));
			byte s[] = md.digest();
			String result = "";
			for (int i = 0; i < s.length; i++) {
				result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00)
						.substring(6);
			}
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	private static String MD5_2(String plainText) {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			System.out.println("result: " + buf.toString());// 32位
			System.out.println("result: "
					+ (result = buf.toString().substring(8, 24)));// 16位
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println(MD5("华南品牌词#唯品会扩展词#唯品会名牌时尚折扣网","GBK"));;
	}

}
