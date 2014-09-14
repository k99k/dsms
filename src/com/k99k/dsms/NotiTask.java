/**
 * 
 */
package com.k99k.dsms;

import com.k99k.khunter.Action;
import com.k99k.khunter.ActionMsg;

/**
 * 回调任务
 * @author Keel
 *
 */
public class NotiTask extends Action {

	/**
	 * @param name
	 */
	public NotiTask(String name) {
		super(name);
	}

	@Override
	public ActionMsg act(ActionMsg msg) {
		
		//TODO 发送成功或失败回调
		
		return super.act(msg);
	}
	
	
	

}
