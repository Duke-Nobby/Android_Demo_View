package cn.sunzn.tevent.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import cn.sunzn.tevent.utils.TouchEventUtil;

/**
 * @ClassName: TouchEventChilds
 * @Description:��View
 * @author: iamxiarui@foxmail.com
 * @date: 2016��5��9�� ����9:54:06
 */
public class TouchEventChilds extends LinearLayout {

	public TouchEventChilds(Context context) {
		super(context);
	}

	public TouchEventChilds(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.e("sunzn", "TouchEventChilds | dispatchTouchEvent --> " + TouchEventUtil.getTouchAction(ev.getAction()));
		// return super.dispatchTouchEvent(ev);
		return true;
	}

	public boolean onInterceptTouchEvent(MotionEvent ev) {
		Log.i("sunzn", "TouchEventChilds | onInterceptTouchEvent --> " + TouchEventUtil.getTouchAction(ev.getAction()));
		return super.onInterceptTouchEvent(ev);
	}

	public boolean onTouchEvent(MotionEvent ev) {
		Log.d("sunzn", "TouchEventChilds | onTouchEvent --> " + TouchEventUtil.getTouchAction(ev.getAction()));
		return super.onTouchEvent(ev);
	}

}
