/**
 * 
 */
package com.k99k.tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * API接口java版本,与内部标准API协议兼容
 * 
 * @author keel
 *
 */
public class ApiUtil {

	/**
	 * 
	 */
	public ApiUtil() {
	}

	/**
	 * post数据到一个url,并获取反回的String,需要拼合参数
	 * 
	 * @param url
	 *            Url
	 * @param data
	 *            参数合成后的String
	 * @param timeOut
	 *            超时毫秒数,如3000
	 * @param breakLine
	 *            是否加入换行符
	 * @param charset
	 *            URL接受的编码，如utf-8
	 * @return 返回的结果页内容,失败则返回空String
	 */
	public final static String postUrl(String url, String data,
			String[] headerKeys, String[] headerValues, int timeOut,
			boolean breakLine, String charset) {
		try {
			URL aUrl = new URL(url);
			URLConnection conn = aUrl.openConnection();
			conn.setConnectTimeout(timeOut);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");
			if (headerValues != null) {
				for (int i = 0; i < headerValues.length; i++) {
					conn.setRequestProperty(headerKeys[i], headerValues[i]);
				}
			}

			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.write(data);
			wr.flush();

			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), charset));
			String line;
			StringBuilder sb = new StringBuilder();
			if (breakLine) {
				while ((line = rd.readLine()) != null) {
					sb.append(line);
					sb.append("\r\n"); // 添加换行,如果不需要可去除
				}
			} else {
				while ((line = rd.readLine()) != null) {
					sb.append(line);
				}
			}
			wr.close();
			rd.close();
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	public static String md5(String str) {
	    try {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        md.update(str.getBytes());
	        return new BigInteger(1, md.digest()).toString(16);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	@SuppressWarnings("unchecked")
	private static final TreeMap<String,Object> pushSortMap(TreeMap<String,Object> sortMap,HashMap<String,Object> data){
		for (Map.Entry<String,Object> entry : data.entrySet()) { 
			if(entry.getKey().equals("req")){
				sortMap = pushSortMap(sortMap,(HashMap<String, Object>) entry.getValue());
				continue;
			}
			//签名只考虑数字和string类型
		    if (entry.getValue() instanceof String || entry.getValue() instanceof Integer || entry.getValue() instanceof Long || entry.getValue() instanceof Float) {
				sortMap.put(entry.getKey(), entry.getValue());
			}
		}
		return sortMap;
	}
	
	public static String mkSign(HashMap<String,Object> data,String key){
		TreeMap<String,Object> sortMap = new TreeMap<String, Object>();
		sortMap = pushSortMap(sortMap,data);
		Set<String> keySet = sortMap.keySet();
        Iterator<String> iter = keySet.iterator();
        StringBuilder sb = new StringBuilder();
        while (iter.hasNext()) {
            String sortKey = iter.next();
            sb.append(sortKey);
            sb.append("=");
            sb.append(sortMap.get(sortKey));
            sb.append("&");
        }
        sb.append("key=").append(key);
        String signStr = sb.toString();
//        System.out.println("signStr:"+signStr);
		return md5(signStr);
	}
	
	public static final long timeStamp(){
		return new Date().getTime();
	}
	
	public static final String mkApiReq(String method, HashMap<String,Object> reqData,String key,String appCode,String channel,int ver){
		HashMap<String,Object> reqMap = new HashMap<String, Object>();
		reqMap.put("m", method);
		reqMap.put("v", ver);
		reqMap.put("a", appCode);
		reqMap.put("c", channel);
		reqMap.put("t", timeStamp());
		reqMap.put("req", reqData);
		String sign = mkSign(reqMap, key);
		reqMap.put("s", sign);
		String reqStr = JSON.write(reqMap);
		System.out.println("mkApiReq:"+reqStr);
		return reqStr;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String url = "http://localhost:15000/fake/increase";
		HashMap<String, Object> req = new HashMap<String, Object>();
		req.put("userName", "aa");
		String data = mkApiReq("increase", req, "testkey", "jquer", "12001", 1);
		System.out.println("data:"+data);
		int timeOut = 2000;
//		String[] headerKeys = {"Cookie" };
//		String[] headerValues = {"kie_kc_1=gLoCputwu%2F6W5jVB9%2BSzTn8IaYtwk2sbEJ0fUPgUaIFmOM%2FiWri41MdFK3eD4ZUxlo4A7gsqUcPv0YvChL4mZQ%3D%3D" };
//		String re = postUrl(url, data, headerKeys, headerValues, timeOut, true,"utf-8");
		
		
		String re = postUrl(url, data, null, null, timeOut, true,"utf-8");
		System.out.println(re);
		HashMap<String,Object> reObj = (HashMap<String, Object>) JSON.read(re);
		System.out.println(reObj.get("re"));
		
	}

}
