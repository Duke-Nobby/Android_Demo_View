package xr.customswitch.view;

import android.R.bool;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @ClassName: CustomSwitchView
 * @Description:自定义控件 继承View
 * @author: iamxiarui@foxmail.com
 * @date: 2016年5月5日 下午6:51:49
 */
public class CustomSwitchView extends View {

	private Bitmap switchBackgroupBitmap;
	private Bitmap switchForegroupBitmap;
	private Paint paint;
	private boolean isSwitchState = true;
	private boolean isTouchState = false;
	private float currentPosition;// 当前开关位置
	private int maxPosition;// 开关滑动最大位置
	private OnSwitchStateUpdateListener onSwitchStateUpdateListener;

	/* 注意必须实现四个构造函数 */

	/**
	 * @Description:用于代码创建控件
	 */
	public CustomSwitchView(Context context) {
		super(context);
		initView();
	}

	/**
	 * @Description:用于在XML中使用，可以指定自定义属性
	 */
	public CustomSwitchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	/**
	 * @Description:用于在XML中使用，可以指定自定义属性，并指定样式
	 */
	public CustomSwitchView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView();
	}

	/**
	 * @Description:用于在XML中使用，可以指定自定义属性，并指定样式及其资源
	 */
	public CustomSwitchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		initView();
	}

	/**
	 * @Title: initView
	 * @Description:初始化View
	 * @return: void
	 */
	private void initView() {
		paint = new Paint();
	}

	/**
	 * @Title: setBackgroundPic
	 * @Description:设置背景图
	 * @return: void
	 */
	public void setBackgroundPic(int switchBackground) {
		switchBackgroupBitmap = BitmapFactory.decodeResource(getResources(), switchBackground);
	}

	/**
	 * @Title: setForegroundPic
	 * @Description:设置前景图
	 * @return: void
	 */
	public void setForegroundPic(int switchForeground) {
		switchForegroupBitmap = BitmapFactory.decodeResource(getResources(), switchForeground);
	}

	/**
	 * @Title: setSwitchState
	 * @Description:指定开关状态
	 * @return: void
	 */
	public void setSwitchState(boolean isSwitchState) {
		this.isSwitchState = isSwitchState;
	}

	/**
	 * @Title: onMeasure
	 * @Description:测量出自定义控件的长宽
	 * @return: void
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(switchBackgroupBitmap.getWidth(), switchBackgroupBitmap.getHeight());
	}

	/**
	 * @Title: onDraw
	 * @Description:绘制控件
	 * @return: void
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		// 先绘制背景
		canvas.drawBitmap(switchBackgroupBitmap, 0, 0, paint);

		// 如果处于触摸状态
		if (isTouchState) {
			// 触摸位置在开关的中间位置
			float movePosition = currentPosition - switchForegroupBitmap.getWidth() / 2.0f;
			maxPosition = switchBackgroupBitmap.getWidth() - switchForegroupBitmap.getWidth();
			// 限定开关滑动范围
			if (movePosition < 0) {
				movePosition = 0;
			} else if (movePosition > maxPosition) {
				movePosition = maxPosition;
			}
			// 绘制开关
			canvas.drawBitmap(switchForegroupBitmap, movePosition, 0, paint);
		} else {
			// 绘制开关
			if (isSwitchState) {
				maxPosition = switchBackgroupBitmap.getWidth() - switchForegroupBitmap.getWidth();
				canvas.drawBitmap(switchForegroupBitmap, maxPosition, 0, paint);
			} else {
				canvas.drawBitmap(switchForegroupBitmap, 0, 0, paint);
			}
		}
	}

	/**
	 * @Title: onTouchEvent
	 * @Description:触摸事件
	 * @return: void
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 处于触摸状态
			isTouchState = true;
			currentPosition = event.getX();
			break;

		case MotionEvent.ACTION_MOVE:
			currentPosition = event.getX();
			break;

		case MotionEvent.ACTION_UP:
			// 触摸状态结束
			isTouchState = false;
			currentPosition = event.getX();
			// 中间标志位置
			float centerPosition = switchBackgroupBitmap.getWidth() / 2.0f;

			// 如果开关当前位置大于背景位置的一半 显示关 否则显示开
			boolean currentState = currentPosition > centerPosition;

			// 如果当然状态不相同且绑定了监听对象 则执行监听方法
			if (currentState != isSwitchState && onSwitchStateUpdateListener != null) {
				onSwitchStateUpdateListener.onStateUpdate(currentState);
			}

			isSwitchState = currentState;
			break;
		}

		// 重新调用onDraw方法，不断重绘界面
		invalidate();
		return true;
	}

	/**
	 * @ClassName: OnSwitchStateUpdateListener
	 * @Description:添加事件状态监听接口对象
	 * @author: iamxiarui@foxmail.com
	 * @date: 2016年5月5日 下午9:33:35
	 */
	public interface OnSwitchStateUpdateListener {
		// 状态回调, 把当前状态传出去
		void onStateUpdate(boolean state);
	}

	/**
	 * @Title: setOnSwitchStateUpdateListener
	 * @Description:状态监听方法
	 * @return: void
	 */
	public void setOnSwitchStateUpdateListener(OnSwitchStateUpdateListener onSwitchStateUpdateListener) {
		this.onSwitchStateUpdateListener = onSwitchStateUpdateListener;
	}

}
