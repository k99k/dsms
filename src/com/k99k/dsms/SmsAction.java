/**
 * 
 */
package com.k99k.dsms;


import javax.servlet.http.HttpServletRequest;

import com.k99k.khunter.Action;
import com.k99k.khunter.ActionMsg;
import com.k99k.khunter.HttpActionMsg;
import com.k99k.khunter.JOut;

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
		HttpActionMsg httpmsg = (HttpActionMsg)msg;
		HttpServletRequest req = httpmsg.getHttpReq();
		//TODO IP限制
//		Enumeration<String> headers = req.getHeaderNames();
//		while (headers.hasMoreElements()) {
//			String h = (String) headers.nextElement();
//			System.out.println(h+":"+req.getHeader(h));
//		}
		String ip = (req.getHeader("x-real-ip") != null) ? (req.getHeader("x-real-ip")) : req.getRemoteAddr();
		if (ip.equals("")) {
			JOut.err(403, httpmsg);
			return super.act(msg);
		}
		
		//TODO 需要加解密内容
		
		// 接收网关MO消息
		String destNum = req.getParameter("n");
		String txt = req.getParameter("t");
		String id = req.getParameter("id");
		
		// TODO 解析短信内容,验证是否有效（允许结算）
		System.out.println("n:"+destNum+" t:"+txt+" id:"+id+" ip:"+ip);
		
		
		
		msg.addData(ActionMsg.MSG_PRINT, "1");
		return super.act(msg);
	}
	
	void dealMO(String destNum,String txt){
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
