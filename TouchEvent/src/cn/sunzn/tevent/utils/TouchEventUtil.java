package cn.sunzn.tevent.utils;

import android.view.MotionEvent;

/**  
 *  @ClassName: TouchEventUtil
 *  @Description:����¼�������
 *  @author: iamxiarui@foxmail.com
 *  @date: 2016��5��9�� ����9:53:51
 */
public class TouchEventUtil {
	
	public static String getTouchAction(int actionId) {
		String actionName = "Unknow:id=" + actionId;
		switch (actionId) {
		case MotionEvent.ACTION_DOWN:
			actionName = "ACTION_DOWN";
			break;
		case MotionEvent.ACTION_MOVE:
			actionName = "ACTION_MOVE";
			break;
		case MotionEvent.ACTION_UP:
			actionName = "ACTION_UP";
			break;
		case MotionEvent.ACTION_CANCEL:
			actionName = "ACTION_CANCEL";
			break;
		case MotionEvent.ACTION_OUTSIDE:
			actionName = "ACTION_OUTSIDE";
			break;
		}
		return actionName;
	}
	
}
