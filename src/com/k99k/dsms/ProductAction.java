/**
 * 
 */
package com.k99k.dsms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.k99k.khunter.Action;
import com.k99k.khunter.ActionMsg;
import com.k99k.khunter.HttpActionMsg;
import com.k99k.khunter.KFilter;
import com.k99k.khunter.KObject;

/**
 * @author Keel
 *
 */
public class ProductAction extends Action {

	/**
	 * @param name
	 */
	public ProductAction(String name) {
		super(name);
	}
	
	public static final int STATE_NOT_EXSIT = 0;
	public static final int STATE_ONLINE = 1;
	public static final int STATE_OFFLINE = 2;
	

	@Override
	public ActionMsg act(ActionMsg msg) {
		HttpActionMsg httpmsg = (HttpActionMsg)msg;
		HttpServletRequest request =  httpmsg.getHttpReq();
		HttpServletResponse response = httpmsg.getHttpResp();
		String subact = KFilter.actPath(msg, 2, "");
		if (subact.equals("add")) {
			msg.addData(ActionMsg.MSG_PRINT, "add");
			return super.act(msg);
		}
		
		// TODO 业务操作,CRUD
		
		return super.act(msg);
	}
	
//	/**
//	 * 由feeId算出pid，最好是有固定算法(pid+价格)，不查数据库  //需要考虑冲突和算法,还是直接查缓存返回Product对象吧
//	 * @param feeId
//	 * @return
//	 */
//	static final long findProductId(long feeId){
//		
//		return 0;
//	}
	
	/**
	 * TODO findProductFromPid
	 * @param feeId
	 * @return
	 */
	final static KObject findProductFromPid(long pid){
		
		
		return null;
	}
	
	/**
	 * @param pid
	 * @return
	 */
	final static KObject findCpfromPid(long pid){
		
		
		return null;
	}
	
//	final static String createFeeId(long pid,int fee){
//		
//		return "";
//	}
	
	
	final String makeFeeInfoFile(long pid){
		
		
		return "";
	}
	
	final void addProduct(){
		
	}
	
	final void delProduct(){
		
	}
	
	final void updateProduct(){
		
	}
}
