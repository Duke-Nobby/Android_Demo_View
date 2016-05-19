package com.slidedelete.view;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * @ClassName: SlideLayout
 * @Description:自定义滑动布局
 * @author: iamxiarui@foxmail.com
 * @date: 2016年5月19日 下午2:57:53
 */
public class SlideLayout extends FrameLayout {

	private View itemView;
	private View deleteView;
	private int itemWidth, itemHeight, deleteWidth, deleteHeight;
	private ViewDragHelper viewDragHelper;

	public SlideLayout(Context context) {
		super(context);
		initView();

	}

	public SlideLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();

	}

	public SlideLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView();

	}

	public SlideLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		initView();
	}

	/**
	 * @Title: initView
	 * @Description:初始化View
	 * @return: void
	 */
	private void initView() {
		viewDragHelper = ViewDragHelper.create(this, callback);
	}

	/**
	 * @Title: onFinishInflate
	 * @Description:得到子View
	 */
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		itemView = getChildAt(0);
		deleteView = getChildAt(1);
	}

	/**
	 * @Title: onSizeChanged
	 * @Description:测量对应View的宽高
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		itemWidth = itemView.getMeasuredWidth();
		itemHeight = itemView.getMeasuredHeight();
		deleteWidth = deleteView.getMeasuredWidth();
		deleteHeight = deleteView.getMeasuredHeight();
	}

	/**
	 * @Title: onLayout
	 * @Description:摆放子View的位置
	 */
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		itemView.layout(0, 0, itemWidth, itemHeight);
		deleteView.layout(itemView.getRight(), 0, itemView.getRight() + deleteWidth, deleteHeight);
	}

	/**
	 * @Title: onTouchEvent
	 * @Description:触摸事件
	 * @param event
	 * @return
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		viewDragHelper.processTouchEvent(event);
		return true;
	}

	/**
	 * @Title: onInterceptTouchEvent
	 * @Description:事件拦截
	 * @param ev
	 * @return
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return viewDragHelper.shouldInterceptTouchEvent(ev);
	}

	// 定义回调对象
	private ViewDragHelper.Callback callback = new Callback() {

		@Override
		public boolean tryCaptureView(View child, int pointerId) {
			return child == itemView || child == deleteView;
		}

		public int getViewHorizontalDragRange(View child) {
			return deleteWidth;
		};

		/**
		 * @Title: clampViewPositionHorizontal
		 * @Description:限定滑动范围
		 */
		public int clampViewPositionHorizontal(View child, int left, int dx) {
			if (child == itemView) {
				if (left > 0) {
					left = 0;
				}
				if (left < -deleteWidth) {
					left = -deleteWidth;
				}
			} else if (child == deleteView) {
				if (left > itemWidth) {
					left = itemWidth;
				}
				if (left < (itemWidth - deleteWidth)) {
					left = itemWidth - deleteWidth;
				}
			}
			return left;
		};

		/**
		 * @Title: onViewPositionChanged
		 * @Description:设置滑动位置
		 */
		public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
			super.onViewPositionChanged(changedView, left, top, dx, dy);
			if (changedView == itemView) {
				deleteView.layout(deleteView.getLeft() + dx, deleteView.getTop() + dy, deleteView.getRight() + dx,
						deleteView.getBottom() + dy);
			} else if (changedView == deleteView) {
				itemView.layout(itemView.getLeft() + dx, itemView.getTop() + dy, itemView.getRight() + dx,
						itemView.getBottom() + dy);
			}
		};

		public void onViewReleased(View releasedChild, float xvel, float yvel) {
			super.onViewReleased(releasedChild, xvel, yvel);
			if (itemView.getLeft() < -deleteWidth / 2) {
				openDeleteView();
			} else {
				closeDeleteView();
			}
		}

	};

	/**
	 * @Title: openDeleteView
	 * @Description:打开删除View
	 * @return: void
	 */
	public void openDeleteView() {
		viewDragHelper.smoothSlideViewTo(itemView, -deleteWidth, itemView.getTop());
		ViewCompat.postInvalidateOnAnimation(SlideLayout.this);
	};

	/**
	 * @Title: openDeleteView
	 * @Description:关闭删除View
	 * @return: void
	 */
	public void closeDeleteView() {
		viewDragHelper.smoothSlideViewTo(itemView, 0, itemView.getTop());
		ViewCompat.postInvalidateOnAnimation(SlideLayout.this);
	};

	/**
	 * @Title: computeScroll
	 * @Description:继续执行动画
	 */
	public void computeScroll() {
		if (viewDragHelper.continueSettling(true)) {
			ViewCompat.postInvalidateOnAnimation(SlideLayout.this);
		}
	}

}
