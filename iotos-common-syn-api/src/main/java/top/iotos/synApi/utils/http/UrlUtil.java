package top.iotos.synApi.utils.http;

import java.util.*;

public class UrlUtil {

	/**
	 * 将参数集合转换为get请求地址
	 * @param url
	 * @param params
	 * @return
	 */
	public static String getUrl(String url, Map<String, Object> params) {
		// 添加url参数
		if (params != null) {
			Iterator<String> it = params.keySet().iterator();
			StringBuffer sb = null;
			while (it.hasNext()) {
				String key = it.next();
				Object value = "";
				if (params.get(key) != null) {
					value = params.get(key);
				}
				if (sb == null) {
					sb = new StringBuffer();
					sb.append("?");
				} else {
					sb.append("&");
				}
				sb.append(key);
				sb.append("=");
				sb.append(value);
			}
			url += sb.toString();
		}
		//System.out.println(url);
		return url;
	}


	/**
	 * 将参数集合转换为get请求地址
	 * @param url
	 * @param params
	 * @return
	 */
	public static String getUrl(String url, Map<String, Object> params,boolean isOrderBy) {
		// 添加url参数
		if (params != null) {
			List<String> list=new ArrayList<>(params.keySet());
			if(isOrderBy){
				Collections.sort(list);
			}
			StringBuffer sb = null;
			for(int i=0;i<list.size();i++){
				String k =list.get(i);
				String v=(String )params.get(k);
				if (sb == null) {
					sb = new StringBuffer();
					sb.append("?");
				} else {
					sb.append("&");
				}
				sb.append(k).append("=").append(v);
			}
			url += sb.toString();
		}
		return url;
	}




	/**
	 * 中文转Unicode
	 * @param gbString 待转换的字符串
	 * @return 字符串
	 */
	public static String gbEncoding(String gbString) { 
		char[] utfBytes = gbString.toCharArray(); 
		String unicodeBytes = "";
		for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
			String hexB = Integer.toHexString(utfBytes[byteIndex]); // 转换为16进制整型字符串
			if (hexB.length() <= 2) {
				hexB = "00" + hexB;
			}
			unicodeBytes = unicodeBytes + "\\u" + hexB;
		}
		return unicodeBytes;
	}


}
