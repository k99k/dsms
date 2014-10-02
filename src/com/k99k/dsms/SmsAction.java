/**
 * 
 */
package com.k99k.dsms;


import javax.servlet.http.HttpServletRequest;

import com.k99k.khunter.Action;
import com.k99k.khunter.ActionMsg;
import com.k99k.khunter.HttpActionMsg;
import com.k99k.khunter.JOut;
import com.k99k.tools.RandomUtil;
import com.k99k.tools.StringUtil;
import com.k99k.tools.enc.Enc;

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
	private char[] keys = {'a'};
	private int keyPo = 2;
	

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
	
	public static void main(String[] args) {
		int v = 9;
		String encV = encVersion(v);
		int decV = decVersion(encV.charAt(0), encV.charAt(1));
		System.out.println("encV:["+encV+"] decV:"+decV);
	}
	
	/**
	 * 加密版本号为两位可显ascii字符,注意版本号取值为1-75
	 * @param ver 为1-75整数
	 * @return
	 */
	public static final String encVersion(int ver){
//		int vx = ver;//+17;
		int ax = 48;//a,b的区间范围为48-124,，可显示ascii码
		int ay = 124;
		int a = RandomUtil.getRandomInt(ax, ay);
		int b = 0;
		int mid = 86;//ax + ((ay-ax)/2); //mid为中间值区间中间值
		if (a > mid) {
			//向右偏
			b = a - ver;
			if (b < ax) {
				//调整到区间内
				b = RandomUtil.getRandomInt(ax, (ay-ver));
				a = b + ver;
			}
		}else{
			//向左偏
			b = a +  ver;
			if (b > ay) {
				//调整到区间内
				b = RandomUtil.getRandomInt(ax+ver, ay);
				a = b - ver;
			}
		}
		char[] ca = {(char) a,(char) b};
		return new String(ca);
	}
	
	public static final int decVersion(char c1,char c2){
		int vx = (c1>c2)?(c1-c2):(c2-c1);
		return vx;//-17;
	}
	
	/**
	 * 分解MO，vvfeeId@fee@channel@imsi@cpPara@imei
	 * @param destNum
	 * @param txt
	 * @return
	 */
	int dealMO(String destNum,String txt){
		if (txt.length()< 10) {
			return -1;
		}
		//取版本号
		int ver = decVersion(txt.charAt(0), txt.charAt(1));
		if (ver<1 || ver > 75) {
			return -2;
		}
		
		//根据版本号获得key
		
		
		String enc = txt.substring(2);
		
		String[] ms = txt.substring(2).split("@");
		String feeIds = ms[0];
		String fee = ms[1];
		String channel =  ms[2];
		String imsi = ms[3];
		
		
		
		
		
		//解密
//		Enc.decrypt(sSrc, key)
		
		
		//判断产品状态
		
		//记录
		
		//是否发回调
		
		return 1;
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
