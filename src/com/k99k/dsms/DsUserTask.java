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
public class DsUserTask extends Action {

	/**
	 * @param name
	 */
	public DsUserTask(String name) {
		super(name);
	}

	@Override
	public ActionMsg act(ActionMsg msg) {
		// TODO 处理用户相关的任务，更新统计数据等
		return super.act(msg);
	}
	
	

}
