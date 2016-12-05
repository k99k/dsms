package com.k99k.tools;

import java.util.HashMap;

public class JfApi {

	private int ver = 1;
	private String appCode = "jquer";
	private String channel = "12001";
	private String key = "STaDs5GnkBXAqxRq";

	private String apiUrlPre = "http://localhost:15001/jifen/";
	private String apiUrlPre2 = "http://localhost:15001/card/";
//	private String apiUrlPre = "http://kf.loyoo.co/jquer/jifen/";
//	private String apiUrlPre2 = "http://kf.loyoo.co/jquer/card/";
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
	
	public HashMap<String, Object> decrease(String pointSeq,String pointReqTranSeq,int pointAmount, int subType,String attach,String customerId,String orderSeq) {
		HashMap<String, Object> req = new HashMap<String, Object>();
		req.put("pointSeq", pointSeq);
		req.put("pointReqTranSeq", pointReqTranSeq);
//		req.put("reqDate", reqDate);
		req.put("pointAmount", pointAmount);
		req.put("subType", subType);
		req.put("attach", attach);
		req.put("customerId", customerId);
		req.put("orderSeq", orderSeq);
		String data = ApiUtil.mkApiReq("decrease", req, key, appCode, channel,
				ver);
		System.out.println("data:" + data);
		String re = ApiUtil.postUrl(apiUrlPre + "decrease", data, null, null, timeOut,
				true, encode);
		System.out.println(re);
		@SuppressWarnings("unchecked")
		HashMap<String, Object> reObj = (HashMap<String, Object>) JSON.read(re);
		
		return reObj;
	}
	
	/**
	 * 查询余额
	 * @param customerId
	 * @return
	 */
	public HashMap<String, Object> userBalance(String customerId) {
		HashMap<String, Object> req = new HashMap<String, Object>();
		req.put("customerId", customerId);
		String data = ApiUtil.mkApiReq("userBalance", req, key, appCode, channel,
				ver);
		System.out.println("data:" + data);
		String re = ApiUtil.postUrl(apiUrlPre + "userBalance", data, null, null, timeOut,
				true, encode);
		System.out.println(re);
		@SuppressWarnings("unchecked")
		HashMap<String, Object> reObj = (HashMap<String, Object>) JSON.read(re);
		
		return reObj;
	}
	
	/**
	 * 查询流水
	 * @param customerId
	 * @param startTime "yyyy-mm-dd hh:mm:ss"
	 * @param endTime "yyyy-mm-dd hh:mm:ss"
	 * @return
	 */
	public HashMap<String, Object> pointLog(String customerId,String startTime,String endTime,int type,String val) {
		HashMap<String, Object> req = new HashMap<String, Object>();
		req.put("customerId", customerId);
		req.put("startTime", startTime);
		req.put("endTime", endTime);
		req.put("type", type);
		req.put("val", val);
		String data = ApiUtil.mkApiReq("pointLog", req, key, appCode, channel,ver);
		System.out.println("data:" + data);
		String re = ApiUtil.postUrl(apiUrlPre + "pointLog", data, null, null, timeOut,
				true, encode);
		System.out.println(re);
		@SuppressWarnings("unchecked")
		HashMap<String, Object> reObj = (HashMap<String, Object>) JSON.read(re);
		
		return reObj;
	}
	
	
	//创建一批卡
	public HashMap<String, Object> createCards(int fee,String reason,String createUser,int createUserId,int batchCount,String expireTime,int type) {
		HashMap<String, Object> req = new HashMap<String, Object>();
		req.put("fee", fee);
		req.put("reason", reason);
		req.put("createUser", createUser);
		req.put("createUserId", createUserId);
		req.put("batchCount", batchCount);
		req.put("expireTime", expireTime);
		req.put("type", type);
		String data = ApiUtil.mkApiReq("createCards", req, key, appCode, channel,ver);
		System.out.println("data:" + data);
		String re = ApiUtil.postUrl(apiUrlPre2 + "createCards", data, null, null, timeOut,
				true, encode);
		System.out.println(re);
		@SuppressWarnings("unchecked")
		HashMap<String, Object> reObj = (HashMap<String, Object>) JSON.read(re);
		
		return reObj;
	}
	
	
	//使用卡充积分
	public HashMap<String, Object> useCard(String cardNo,String cardPwd,String customerId,String orderSeq) {
		HashMap<String, Object> req = new HashMap<String, Object>();
		req.put("cardNo", cardNo);
		req.put("cardPwd", cardPwd);
		req.put("customerId", customerId);
		req.put("orderSeq", orderSeq);
		String data = ApiUtil.mkApiReq("useCard", req, key, appCode, channel,ver);
		System.out.println("data:" + data);
		String re = ApiUtil.postUrl(apiUrlPre2 + "useCard", data, null, null, timeOut,
				true, encode);
		System.out.println(re);
		@SuppressWarnings("unchecked")
		HashMap<String, Object> reObj = (HashMap<String, Object>) JSON.read(re);
		
		return reObj;
	}
	
	
	//更新单张卡状态,0为有效,1为已使用,2为暂停,3为冻结,4为禁用
	public HashMap<String, Object> updateCard(String cardNo,int state) {
		HashMap<String, Object> req = new HashMap<String, Object>();
		req.put("cardNo", cardNo);
		req.put("state", state);
		String data = ApiUtil.mkApiReq("updateCard", req, key, appCode, channel,ver);
		System.out.println("data:" + data);
		String re = ApiUtil.postUrl(apiUrlPre2 + "updateCard", data, null, null, timeOut,
				true, encode);
		System.out.println(re);
		@SuppressWarnings("unchecked")
		HashMap<String, Object> reObj = (HashMap<String, Object>) JSON.read(re);
		
		return reObj;
	}
	
	//更新卡批次,主要是卡延期
	public HashMap<String, Object> updateCardBatch(int batchNo,String expireTime) {
		HashMap<String, Object> req = new HashMap<String, Object>();
		req.put("batchNo", batchNo);
		req.put("expireTime", expireTime);
		String data = ApiUtil.mkApiReq("updateCardBatch", req, key, appCode, channel,ver);
		System.out.println("data:" + data);
		String re = ApiUtil.postUrl(apiUrlPre2 + "updateCardBatch", data, null, null, timeOut,
				true, encode);
		System.out.println(re);
		@SuppressWarnings("unchecked")
		HashMap<String, Object> reObj = (HashMap<String, Object>) JSON.read(re);
		
		return reObj;
	}
	
	
	//获取单卡信息
	public HashMap<String, Object> cardInfo(String cardNo) {
		HashMap<String, Object> req = new HashMap<String, Object>();
		req.put("cardNo", cardNo);
		String data = ApiUtil.mkApiReq("cardInfo", req, key, appCode, channel,ver);
		System.out.println("data:" + data);
		String re = ApiUtil.postUrl(apiUrlPre2 + "cardInfo", data, null, null, timeOut,
				true, encode);
		System.out.println(re);
		@SuppressWarnings("unchecked")
		HashMap<String, Object> reObj = (HashMap<String, Object>) JSON.read(re);
		
		return reObj;
	}
	
	
	//获取卡批次信息
	public HashMap<String, Object> batchInfo(int batchNo) {
		HashMap<String, Object> req = new HashMap<String, Object>();
		req.put("batchNo", batchNo);
		String data = ApiUtil.mkApiReq("batchInfo", req, key, appCode, channel,ver);
		System.out.println("data:" + data);
		String re = ApiUtil.postUrl(apiUrlPre2 + "batchInfo", data, null, null, timeOut,
				true, encode);
		System.out.println(re);
		@SuppressWarnings("unchecked")
		HashMap<String, Object> reObj = (HashMap<String, Object>) JSON.read(re);
		
		return reObj;
	}
	
	public static void main(String[] args) {
		JfApi jf = new JfApi();
		HashMap<String, Object> re = null;
//		re = jf.pointLog("customer1", "2016-09-22 15:19:14", "2016-09-23 18:19:14",1,"customer1");
//		re = jf.userBalance("customer1");
//		re = jf.increase("pointSeq1233","pointReqTranSeq1",100,1,"中文字","customer1",1,10);
//		System.out.println(re);
//		re = jf.decrease("pointSe123","pointReqTranSeq12",120,1,"attach","customer1","orderSeq111");
//		re = jf.createCards(500, "搞活动送500积分", "keel", 1, 20, "2016-12-30 23:13:19", 1);
//		re = jf.useCard("500000798918", "963837", "customer1", "orderSeq112");
//		re = jf.updateCard("500000798918", 4);
//		re = jf.updateCardBatch(5, "2016-12-30 23:53:19");
//		re = jf.cardInfo("500000798918");
		re = jf.batchInfo(5);
		System.out.println(re);
		
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
