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
	
	/**
	 * 由feeId算出pid，最好是有固定算法(pid+价格)，不查数据库
	 * @param feeId
	 * @return
	 */
	static final long findProductId(String feeId){
		
		return 0;
	}
	
	final static String createFeeId(long pid,int fee){
		
		return "";
	}
	
	final KObject findProduct(long id){
		
		
		return null;
	}
	
	final KObject findProduct(String feeId){
		
		
		return null;
	}
	
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
