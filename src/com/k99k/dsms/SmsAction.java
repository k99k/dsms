/**
 * 
 */
package com.k99k.dsms;

import com.k99k.khunter.Action;
import com.k99k.khunter.ActionMsg;

/**
 * @author Keel
 *
 */
public class SmsAction extends Action {

	/**
	 * @param name
	 */
	public SmsAction(String name) {
		super(name);
	}
	
	private String smsGateIP = "";
	
	

	@Override
	public ActionMsg act(ActionMsg msg) {
		// TODO 接收网关MO消息,注意限制IP地址
		
		
		// TODO 解析短信内容,验证是否有效（允许结算）
		
		

		return super.act(msg);
	}
	
	void dealMO(String sms){
		//解密
		
		//判断产品状态
		
		//记录
		
		//是否发回调
		
	}
	
	
	void sendMT(String phoneNum,String txt){
		
	}
	
	String buildSms(String feeId,String imsi,int fee,int channel,String memo){
		StringBuilder sb = new StringBuilder();
		
		//TODO 根据协议拼出短信内容，并加密
		
		return sb.toString();
	}

	public final String getSmsGateIP() {
		return smsGateIP;
	}

	public final void setSmsGateIP(String smsGateIP) {
		this.smsGateIP = smsGateIP;
	}
	

	

}
