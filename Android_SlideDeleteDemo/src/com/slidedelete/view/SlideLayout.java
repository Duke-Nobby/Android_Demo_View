package com.slidedelete.view;

import com.slidedelete.utils.SlideLayoutManager;

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
	private float downX, downY;

	enum SlideState {
		Open, Close;
	}

	// 当前默认是关闭状态
	private SlideState currentState = SlideState.Close;

	private OnSlideStateChangeListener listener;

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
		// 如果当前有打开的，则下面的逻辑不能执行
		if (!SlideLayoutManager.getInstance().isShouldSlide(this)) {
			requestDisallowInterceptTouchEvent(true);
			return true;
		}

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downX = event.getX();
			downY = event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			float moveX = event.getX();
			float moveY = event.getY();
			float offsetX = moveX - downX;
			float offsetY = moveY - downY;
			if (Math.abs(offsetX) > Math.abs(offsetY)) {
				// 表示移动是偏向于水平方向，那么应该SlideLayout应该处理，请求ListView不要拦截
				requestDisallowInterceptTouchEvent(true);
			}
			// 更新downX，downY
			downX = moveX;
			downY = moveY;
			break;
		case MotionEvent.ACTION_UP:

			break;
		}
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
		boolean result = viewDragHelper.shouldInterceptTouchEvent(ev);

		// 如果当前有打开的，则需要直接拦截，交给onTouch处理
		if (!SlideLayoutManager.getInstance().isShouldSlide(this)) {
			// 先关闭已经打开的layout
			SlideLayoutManager.getInstance().closeCurrentLayout();
			result = true;
		}

		return result;
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

			// 判断开和关闭的逻辑
			if (itemView.getLeft() == 0 && currentState != SlideState.Close) {
				// 说明应该将state更改为关闭
				currentState = SlideState.Close;

				// 回调接口关闭的方法
				if (listener != null) {
					listener.onClose();
				}

				// 说明当前的SwipeLayout已经关闭，需要让Manager清空
				SlideLayoutManager.getInstance().clearCurrentLayout();
			} else if (itemView.getLeft() == -deleteWidth && currentState != SlideState.Open) {
				// 说明应该将state更改为开
				currentState = SlideState.Open;

				// 回调接口打开的方法
				if (listener != null) {
					listener.onOpen();
				}
				// 当前的Swipelayout已经打开，需要让Manager记录
				SlideLayoutManager.getInstance().setSlideLayout(SlideLayout.this);
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

	public void setOnSlideStateChangeListener(OnSlideStateChangeListener listener) {
		this.listener = listener;
	}

	/**
	 * @ClassName: OnSlideStateChangeListener
	 * @Description:监听接口
	 * @author: iamxiarui@foxmail.com
	 * @date: 2016年5月19日 下午6:57:11
	 */
	public interface OnSlideStateChangeListener {
		void onOpen();

		void onClose();
	}

}
