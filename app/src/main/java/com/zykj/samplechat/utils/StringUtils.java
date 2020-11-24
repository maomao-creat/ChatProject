package com.zykj.samplechat.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * //如果文本内容为空 返回"" 否则返回文本 Isnull
 * 如果文本内容为空 返回"0" 否则返回文本 Isnullint
 */
public class StringUtils {
	//如果文本内容为空 返回"" 否则返回文本
	public static String Isnull(String obj){
		if(isEmpty(obj)){
			return "";
		}
		return obj;
	}
	//如果文本内容为空 返回"0" 否则返回文本
	public static String Isnullint(String obj){
		if(isEmpty(obj)){
			return "0";
		}
		return obj;
	}
	/**
	 * 数值转字符串，注意仅限普通的数值类型。
	 * 可以直接用 ""+obj 代替.
	 * @param obj String、Int、Float等.
	 * @return
	 */
	public static String toString(Object obj){
		if (obj==null)return "";
		if (obj.getClass() == String.class){
			return (String)obj;
		}
		return String.valueOf(obj);
	}

	/**
	 * 将字符串数组用分隔符分割
	 * 
	 * @param array
	 *            -数组
	 * @param separator
	 *            -分隔符
	 * @return
	 */
	public static String join(String[] array, String separator) {
		StringBuffer sb = new StringBuffer();
		if (array.length == 0)
			return "";
		sb.append(array[0]);
		for (int i = 1; i < array.length; i++) {
			sb.append(separator);
			sb.append(array[i]);
		}
		return sb.toString();
	}

	public static String join(Set<String> set, String separator) {
		String[] array = new String[set.size()];
		array = set.toArray(array);
		return join(array, separator);
	}

	public static String join(List<String> list, String separator) {
		StringBuffer sb = new StringBuffer();
		if (list.size() == 0)
			return "";
		sb.append(list.get(0));
		for (int i = 1; i < list.size(); i++) {
			sb.append(separator);
			sb.append(list.get(i));
		}
		return sb.toString();
	}

	public static List<String> split(String str, String separator) {
		List<String> list = new ArrayList<String>();
		if (StringUtils.isEmpty(str))
			return list;
		String[] array = str.split(separator);
		for (String item : array) {
			list.add(item);
		}
		return list;
	}

	public static List<String> split(String str) {
		return split(str, "\\|");
	}

	/**
	 * 是否为空 null或者长度为0
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(CharSequence str) {
		if (str == null || str.length() == 0)
			return true;
		else
			return false;
	}
	public static CharSequence checkEmpty(CharSequence str, CharSequence strDefault) {
		if (isEmpty(str))
			return strDefault;
		else
			return str;
	}
	public static String checkEmpty(String str) {
		return checkEmpty(str,"");
	}
	public static String checkEmpty(String str, String strDefault) {
		if (isEmpty(str))
			return strDefault;
		else
			return str;
	}
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 字符串转整数
	 * 
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static int toInt(String str, int defValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
		}
		return defValue;
	}

	/**
	 * 对象转整数
	 * 
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static int toInt(Object obj) {
		if (obj == null)
			return 0;
		return toInt(obj.toString(), 0);
	}

	public static String leftPad(int num, int length, char pad) {
		String format = "%" + pad + length + "d";
		return String.format(Locale.CHINA, format, num);
	}
}
