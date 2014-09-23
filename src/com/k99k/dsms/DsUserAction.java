/**
 * 
 */
package com.k99k.dsms;

import com.k99k.khunter.Action;
import com.k99k.khunter.ActionMsg;

/**
 * 短信用户，即最终用户
 * @author Keel
 *
 */
public class DsUserAction extends Action {

	/**
	 * @param name
	 */
	public DsUserAction(String name) {
		super(name);
	}

	@Override
	public ActionMsg act(ActionMsg msg) {
		// TODO USER相关的crud等操作
		return super.act(msg);
	}
	
	

}
