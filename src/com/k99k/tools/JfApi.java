package com.k99k.tools;

import java.util.HashMap;

public class JfApi {

	private int ver = 1;
	private String appCode = "jquer";
	private String channel = "12001";
	private String key = "testkey";

//	private String apiUrlPre = "http://localhost:15000/fake/";
	private String apiUrlPre = "http://kf.loyoo.co/jquer/fake/";
	int timeOut = 2000;
	private String encode = "utf-8";

	public HashMap<String, Object> increase(String pointSeq,String pointReqTranSeq,int pointAmount, int addtype,String attach,String customerId,int payType,int realAmount) {
		HashMap<String, Object> req = new HashMap<String, Object>();
		req.put("pointSeq", pointSeq);
		req.put("pointReqTranSeq", pointReqTranSeq);
//		req.put("reqDate", reqDate);
		req.put("pointAmount", pointAmount);
		req.put("addtype", addtype);
		req.put("attach", attach);
		req.put("customerId", customerId);
		req.put("payType", payType);
		req.put("realAmount", realAmount);
		String data = ApiUtil.mkApiReq("increase", req, key, appCode, channel,
				ver);
		System.out.println("data:" + data);
		String re = ApiUtil.postUrl(apiUrlPre + "increase", data, null, null, timeOut,
				true, encode);
		System.out.println(re);
		@SuppressWarnings("unchecked")
		HashMap<String, Object> reObj = (HashMap<String, Object>) JSON.read(re);
		
		return reObj;
	}
	
	public static void main(String[] args) {
		JfApi jf = new JfApi();
		HashMap<String, Object> re = jf.increase("pointSeq1","pointReqTranSeq1",100,1,"attach","customerId",1,10);
		System.out.println(re.get("re"));
		System.out.println(re.get("data"));
		
	}
	
	
	

	public int getVer() {
		return ver;
	}

	public void setVer(int ver) {
		this.ver = ver;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getApiUrlPre() {
		return apiUrlPre;
	}

	public void setApiUrlPre(String apiUrlPre) {
		this.apiUrlPre = apiUrlPre;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}
	
	
	

}
