package xr.drawerdemo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * @ClassName: SlideMenuView
 * @Description:自定义滑动抽屉菜单
 * @author: iamxiarui@foxmail.com
 * @date: 2016年5月8日 上午10:35:23
 */
public class SlideMenuView extends ViewGroup {

	private float downStartX;
	private float moveEndX;
	private int scrollX;

	// 必须实现其构造函数
	public SlideMenuView(Context context) {
		super(context);
	}

	public SlideMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SlideMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	@SuppressLint("NewApi")
	public SlideMenuView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	/**
	 * @Title: onMeasure
	 * @Description:绘制界面第一步 测量
	 * @param widthMeasureSpec：宽度
	 * @param heightMeasureSpec：高度
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		// 测量第一个View 菜单界面的宽高
		View leftMenu = getChildAt(0);
		leftMenu.measure(leftMenu.getLayoutParams().width, heightMeasureSpec);

		// 测量第二个View 主界面的宽高
		View rightMenu = getChildAt(1);
		rightMenu.measure(widthMeasureSpec, heightMeasureSpec);

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	/**
	 * @Title: onLayout
	 * @Description:绘制界面第二步 摆放
	 * @param changed:当前控件尺寸/位置是否发生了变化
	 * @param l:左边距
	 * @param t:顶边距
	 * @param r:右边界
	 * @param b:下边界
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		// 菜单所在位置
		View leftMenu = getChildAt(0);
		leftMenu.layout(-leftMenu.getMeasuredWidth(), 0, 0, b);

		// 主面板所在位置
		getChildAt(1).layout(l, t, r, b);

	}

	/**
	 * @Title: onTouchEvent
	 * @Description:滑动触摸事件
	 * @param event：事件类型
	 * @return
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 按下位置的X值
			downStartX = event.getX();

			break;
		case MotionEvent.ACTION_MOVE:
			// 移动结束位置的X值
			moveEndX = event.getX();

			// 两者的差值为滑动距离
			scrollX = (int) (downStartX - moveEndX);

			// 提前计算将要移动距离
			int willScrollX = getScrollX() + scrollX;

			// 设置滑动边界
			if (willScrollX < -getChildAt(0).getMeasuredWidth()) {
				// 如果滑动超过左边界 直接滑动到左边界
				scrollTo(-getChildAt(0).getMeasuredWidth(), 0);
			} else if (willScrollX > 0) {
				// 如果滑动超过右边界 直接滑动到右边界
				scrollTo(0, 0);
			} else {
				// 如果没有超过边界 直接设置滚动
				scrollBy(scrollX, 0);
			}

			// 移动结束位置作为下一次滑动开始位置
			downStartX = moveEndX;

			break;
		case MotionEvent.ACTION_UP:

			// 左边菜单栏的中间位置
			int leftCenter = (int) (-getChildAt(0).getMeasuredWidth() / 2.0f);

			// 如果滑动的距离大于菜单的一半 直接显示菜单
			if (getScrollX() < leftCenter) {
				scrollTo(-getChildAt(0).getMeasuredWidth(), 0);
			} else {
				// 否则返回主菜单
				scrollTo(0, 0);
			}

			break;
		}
		return true;
	}

}
