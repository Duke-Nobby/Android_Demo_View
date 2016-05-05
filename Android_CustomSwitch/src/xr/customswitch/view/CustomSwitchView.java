package xr.customswitch.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
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

		// 然后绘制开关
		if (isSwitchState) {
			int moveDistance = switchBackgroupBitmap.getWidth() - switchForegroupBitmap.getWidth();
			canvas.drawBitmap(switchForegroupBitmap, moveDistance, 0, paint);
		} else {
			canvas.drawBitmap(switchForegroupBitmap, 0, 0, paint);
		}
	}

}
