/**
 * 
 */
package com.k99k.dsms;


import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.k99k.khunter.Action;
import com.k99k.khunter.ActionMsg;
import com.k99k.khunter.HttpActionMsg;
import com.k99k.khunter.JOut;
import com.k99k.khunter.KObject;
import com.k99k.tools.RandomUtil;
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
	
	static final Logger log = Logger.getLogger(SmsAction.class);
	
	private String smsGateIP = "";
	/**
	 * feeId的key位置,因为feeId为7位，所以keyPo为0-6的值
	 */
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
			JOut.err(403,SMSErr.ERR_SMS_IP, httpmsg);
			return super.act(msg);
		}
		
		// 接收网关MO消息
		String destNum = req.getParameter("n");
		String txt = req.getParameter("t");
		String linkId = req.getParameter("id");
		
		System.out.println("n:"+destNum+" t:"+txt+" linkId:"+linkId+" ip:"+ip);
		// 解析短信内容,验证是否有效（允许结算）
		int re = this.dealMO(destNum, txt, linkId);
		if (re != SMSErr.OK) {
			log.error("dealMO ERR:"+re+" n:"+destNum+" id:"+linkId+" t:"+txt);
		}
		
		
		msg.addData(ActionMsg.MSG_PRINT, "1");
		return super.act(msg);
	}
	
	
	
	
	public static void main(String[] args) {
		int v = 9;
		String encV = encVersion(v);
		int decV = decVersion(encV.charAt(0), encV.charAt(1));
		System.out.println("encV:["+encV+"] decV:"+decV);
		
		Object obj = v;
		System.out.println(obj.equals(9));
	}
	
	/**
	 * 分解处理MO，格式: [vv][eekeeee][fee@channel@cpPara@imsi@imei]  v=veriosn,e=feeId,k=解feeId的key位置
	 * @param destNum
	 * @param txt
	 * @param linkId
	 * @return 错误码,1为成功
	 */
	private int dealMO(String mtNum,String txt,String linkId){
		if (txt.length()< 15) {
			return SMSErr.ERR_MO_LEN;
		}
		//取版本号
		int ver = decVersion(txt.charAt(0), txt.charAt(1));
		if (ver<1 || ver > 75) {
			return SMSErr.ERR_MO_VER;
		}
		//获取feeId
		String feeIdEnc = txt.substring(2,9);
		long feeId = Enc.numDec(feeIdEnc, this.keyPo);
		if (feeId < 0) {
			//feeId解密失败
			return SMSErr.ERR_MO_FEEID;
		}
		
		//根据feeId获得key
		KObject product = ProductAction.findProductFromFeeId(feeId);
		String keyStr = (String) product.getProp("key");
		if (keyStr == null) {
			//feeId获取key失败
			return SMSErr.ERR_MO_FEEID_KEY;
		}
		int keyLen = keyStr.length();
		byte[] key = new byte[keyLen];
		for (int i = 0; i < keyLen; i++) {
			key[i] = (byte) keyStr.charAt(i);
		}
		
		//解后面的部分
		String restEnc = txt.substring(9);
		String restDec = Enc.decrypt(restEnc, key);
		if (restDec == null) {
			//解密后面部分失败
			return SMSErr.ERR_MO_DEC_REST;
		}
		//后面部分：fee@channel@cpPara@imsi@imei
		String[] ms = restDec.split("@");
		if (ms.length<5) {
			//后面部分解出的明文不合法
			return SMSErr.ERR_MO_REST;
		}
		String feeStr = ms[0];
		String chanStr = ms[1];
		String cpPara =  ms[2];
		String imsi = ms[3];
		String imei = ms[4];
		
		//判断产品状态,暂时只根据产品状态商用判为有效
		if (product.getState() == ProductAction.STATE_ONLINE) {
			//TODO 记录到库
			
			//TODO 是否发回调
			
			
			//是否发MT
			Object mtObj = product.getProp("isMT");
			Object mtContentObj = product.getProp("mtContent");
			if (mtObj != null && mtObj.equals(1) && mtContentObj != null) {
				this.sendMT(mtNum, String.valueOf(mtContentObj),linkId);
			}
			
			
		}else{
			//TODO 记录到错误库
			
			
		}
		return SMSErr.OK;
	}
	
	
	
	
	/**
	 * TODO 发送mt
	 * @param phoneNum
	 * @param txt
	 * @param linkId
	 */
	private void sendMT(String phoneNum,String txt,String linkId){
		
	}
	
	
	
	/**
	 * TODO 创建加密后的短信内容
	 * @param ver
	 * @param feeId
	 * @param feeKeyPo
	 * @param key
	 * @param fee
	 * @param channel
	 * @param imsi
	 * @param imei
	 * @return
	 */
	private static final String buildSms(int ver,long feeId,int feeKeyPo,String key,int fee,int channel,String imsi,String imei){
		StringBuilder sb = new StringBuilder();
		String verEnc = encVersion(ver);
		if (verEnc == null) {
			return null;
		}
		sb.append(verEnc);
		char fKey = (char)RandomUtil.getRandomInt(48, 120);
		String feeEnc = Enc.numEnc(feeId, 6, feeKeyPo, fKey);
		
		
		
		return sb.toString();
	}
	
	
	
	

	public final String getSmsGateIP() {
		return smsGateIP;
	}

	public final void setSmsGateIP(String smsGateIP) {
		this.smsGateIP = smsGateIP;
	}




	public final int getKeyPo() {
		return keyPo;
	}




	public final void setKeyPo(int keyPo) {
		this.keyPo = keyPo;
	}




	/**
		 * 加密版本号为两位可显ascii字符,注意版本号取值为1-75
		 * @param ver 为1-75整数
		 * @return
		 */
		public static final String encVersion(int ver){
			if (ver <1 || ver >75) {
				return null;
			}
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
	

	

}
