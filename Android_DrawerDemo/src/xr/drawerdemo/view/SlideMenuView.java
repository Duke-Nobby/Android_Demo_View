package xr.drawerdemo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

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

	private static final int MAIN_MODE = 0;
	private static final int MENU_MODE = 1;
	private int currentMode = MAIN_MODE;

	private Scroller scroller;
	private float downStartY;

	// 必须实现其构造函数
	public SlideMenuView(Context context) {
		super(context);
		initView();
	}

	public SlideMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public SlideMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView();
	}

	@SuppressLint("NewApi")
	public SlideMenuView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		initView();
	}

	/**
	 * @Title: initVie初始化View
	 * @return: void
	 */
	private void initView() {
		scroller = new Scroller(getContext());
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
				currentMode = MENU_MODE;
				showMenuView();
			} else {
				// 否则返回主菜单
				currentMode = MAIN_MODE;
				showMenuView();
			}

			break;
		}
		return true;
	}

	/**
	 * @Title: onInterceptTouchEvent
	 * @Description:触摸拦截事件
	 * @param ev
	 * @return
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downStartX = ev.getX();
			downStartY = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			// 分别得到移动的X，Y偏移量
			float xOffset = Math.abs(ev.getX() - downStartX);
			float yOffset = Math.abs(ev.getY() - downStartY);

			// 水平方向超出一定距离时,才拦截
			if (xOffset > yOffset && xOffset > 5) {
				// 拦截此次触摸事件, 界面的滚动
				return true;
			}

			break;

		}
		return super.onInterceptTouchEvent(ev);
	}

	/**
	 * @Title: showMenuView
	 * @Description:根据滑动距离显示菜单
	 * @return: void
	 */
	private void showMenuView() {

		int startX = getScrollX();
		// 将要发生水平的变化量
		int distanceX = 0;

		if (currentMode == MAIN_MODE) {
			// 水平变化量为主界面移动的距离
			distanceX = 0 - startX;
		} else if (currentMode == MENU_MODE) {
			// 水平变化量为菜单界面还没有显示的距离
			distanceX = -getChildAt(0).getMeasuredWidth() - startX;
		}

		// 平滑数据的模拟
		int duration = Math.abs(distanceX * 2);
		// 开始平滑
		scroller.startScroll(startX, 0, distanceX, 0, duration);
		// 重绘界面
		invalidate();

	}

	/**
	 * @Title: computeScroll
	 * @Description:不断获取模拟数据维持动画的继续
	 */
	@Override
	public void computeScroll() {
		super.computeScroll();
		if (scroller.computeScrollOffset()) { // 直到duration事件以后, 结束
			// true, 动画还没有结束
			// 获取当前模拟的数据, 也就是要滚动到的位置
			int currX = scroller.getCurrX();
			// 直接滑动过去
			scrollTo(currX, 0);
			// 重绘界面
			invalidate();
		}
	}

	/**
	 * @Title: switchState
	 * @Description:根据状态 执行点击事件
	 * @return: void
	 */
	public void switchState() {
		if (currentMode == MAIN_MODE) {
			currentMode = MENU_MODE;
			showMenuView();
		} else {
			currentMode = MAIN_MODE;
			showMenuView();
		}
	}

	public int getCurrentState() {
		return currentMode;
	}

}
