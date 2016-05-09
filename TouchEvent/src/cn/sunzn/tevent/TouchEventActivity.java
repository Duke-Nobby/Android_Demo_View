package cn.sunzn.tevent;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import cn.sunzn.tevent.utils.TouchEventUtil;

/**  
 *  @ClassName: TouchEventActivity
 *  @Description:�¼��ַ��������
 *  @author: iamxiarui@foxmail.com
 *  @date: 2016��5��9�� ����9:53:27
 */
public class TouchEventActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.w("sunzn", "TouchEventActivity | dispatchTouchEvent --> " + TouchEventUtil.getTouchAction(ev.getAction()));
		return super.dispatchTouchEvent(ev);
	}

	public boolean onTouchEvent(MotionEvent event) {
		Log.w("sunzn", "TouchEventActivity | onTouchEvent --> " + TouchEventUtil.getTouchAction(event.getAction()));
		return super.onTouchEvent(event);
	}

}