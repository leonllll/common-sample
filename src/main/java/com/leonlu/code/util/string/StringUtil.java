package com.leonlu.code.util.string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	private static Random random = new Random();
	private static Pattern emailRegex = Pattern.compile("^(?:[a-zA-Z0-9]+[_\\-\\+\\.]?)" +
			"*[a-zA-Z0-9]+@(?:([a-zA-Z0-9]+[_\\-]?)*[a-zA-Z0-9]+\\.)+([a-zA-Z]{2,})+$");
	private static Pattern userNameRegex = Pattern.compile("^[a-zA-Z0-9][a-zA-Z0-9\\-\\_]{2,20}$");
	/**
	 * 获得去掉-的uuid
	 * @return
	 */
	public static String getUUID(){
		String str =  UUID.randomUUID().toString();
		return str.substring(0,8) + str.substring(9,13) + 
				str.substring(14,18) + str.substring(19,23) + 
				str.substring(24); 
	}
	
	/**
	 * 将字符串中的内容按照map替换
	 * @param content
	 * @param varMap
	 * @return
	 */
	public static String replateTamplerVariable(String content, HashMap<String, String> varMap){
	    Iterator iter = varMap.entrySet().iterator();
	    while (iter.hasNext()) {
	      Map.Entry entry = (Map.Entry)iter.next();
	      content = content.replace((CharSequence)entry.getKey(), (CharSequence)entry.getValue());
	    }
	    return content;
	}
	/**
	 * 验证是否是邮箱地址
	 * @param email
	 * @return
	 */
	public static boolean isEmailAddress(String email){
		return emailRegex.matcher(email).matches();
	}
	/**
	 * 判断是否是一个合法的用户名，要求由数字、字母、_或-组成，以数字或字母开头，长度在3到20之间
	 * @param userName
	 * @return
	 */
	public static boolean isValidUserName(String userName){
		return userNameRegex.matcher(userName).matches();
	}
	public static boolean isNull(String str){
		return str==null||"".equals(str.trim())||"null".equalsIgnoreCase(str.trim());
	}
	public static String convertUUIDFileName(String fileName){
		String uuid = getUUID();
		int point = fileName.lastIndexOf('.');
		if(point == -1) 
			return uuid;
		else
			return uuid + fileName.substring(point);
	}
	public static int getRandom(){
		return random.nextInt(99999999);
	}
	
	public static int getRandom(int range){
		return random.nextInt(range);
	}
	
	public static String getRandomPassword(){
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<6;i++){
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}
	
	public static String getUtf8Encode(String src){
		try {
			src=URLEncoder.encode(src, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return src;
	}
	
	public static String convertToUTF8(String str) {
		if (str == null)
			return null;
		String retStr = str;
		byte b[];
		try {
			b = str.getBytes("ISO8859_1");
			for (int i = 0; i < b.length; i++) {
				byte b1 = b[i];
				if (b1 == 63)
					break; // 1
				else if (b1 > 0)
					continue;// 2
				else if (b1 < 0) { // 不可能为0，0为字符串结束符
					retStr = new String(b, "UTF-8");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		return retStr;
	}
	/**
	 * 从字符串中提取符合正则表达式的内容
	 * @param regex 正则表达式
	 * @param source 目标字符串
	 * @return
	 */
	public static String getMatchedString(String regex, String source) {
		String result = "";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(source);
		while (matcher.find()) {
			result = matcher.group(1);//只取第一组
		}
		return result;
	}
	
	public static List<String> getMatchedStringAll(String regex, String source) {
		ArrayList<String> result = new ArrayList<String>();
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(source);
		while (matcher.find()) {
			result.add(matcher.group(1));
		}
		return result;
	}
	
	/**
	 * 将输入流转换为字符串
	 * @param is 输入流
	 * @return
	 * @throws IOException
	 */
	public static String convertStream(InputStream is) throws IOException {
		String output;
		StringBuffer outputBuffer = new StringBuffer();
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(
				is));
		while ((output = streamReader.readLine()) != null) {
			outputBuffer.append(output);
			outputBuffer.append("\n");
		}
		return outputBuffer.toString();
	}
	
}
