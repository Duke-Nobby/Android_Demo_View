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
 * @Description:�Զ��廬������
 * @author: iamxiarui@foxmail.com
 * @date: 2016��5��19�� ����2:57:53
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

	// ��ǰĬ���ǹر�״̬
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
	 * @Description:��ʼ��View
	 * @return: void
	 */
	private void initView() {
		viewDragHelper = ViewDragHelper.create(this, callback);
	}

	/**
	 * @Title: onFinishInflate
	 * @Description:�õ���View
	 */
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		itemView = getChildAt(0);
		deleteView = getChildAt(1);
	}

	/**
	 * @Title: onSizeChanged
	 * @Description:������ӦView�Ŀ��
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
	 * @Description:�ڷ���View��λ��
	 */
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		itemView.layout(0, 0, itemWidth, itemHeight);
		deleteView.layout(itemView.getRight(), 0, itemView.getRight() + deleteWidth, deleteHeight);
	}

	/**
	 * @Title: onTouchEvent
	 * @Description:�����¼�
	 * @param event
	 * @return
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// �����ǰ�д򿪵ģ���������߼�����ִ��
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
				// ��ʾ�ƶ���ƫ����ˮƽ������ôӦ��SlideLayoutӦ�ô�������ListView��Ҫ����
				requestDisallowInterceptTouchEvent(true);
			}
			// ����downX��downY
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
	 * @Description:�¼�����
	 * @param ev
	 * @return
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		boolean result = viewDragHelper.shouldInterceptTouchEvent(ev);

		// �����ǰ�д򿪵ģ�����Ҫֱ�����أ�����onTouch����
		if (!SlideLayoutManager.getInstance().isShouldSlide(this)) {
			// �ȹر��Ѿ��򿪵�layout
			SlideLayoutManager.getInstance().closeCurrentLayout();
			result = true;
		}

		return result;
	}

	// ����ص�����
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
		 * @Description:�޶�������Χ
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
		 * @Description:���û���λ��
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

			// �жϿ��͹رյ��߼�
			if (itemView.getLeft() == 0 && currentState != SlideState.Close) {
				// ˵��Ӧ�ý�state����Ϊ�ر�
				currentState = SlideState.Close;

				// �ص��ӿڹرյķ���
				if (listener != null) {
					listener.onClose();
				}

				// ˵����ǰ��SwipeLayout�Ѿ��رգ���Ҫ��Manager���
				SlideLayoutManager.getInstance().clearCurrentLayout();
			} else if (itemView.getLeft() == -deleteWidth && currentState != SlideState.Open) {
				// ˵��Ӧ�ý�state����Ϊ��
				currentState = SlideState.Open;

				// �ص��ӿڴ򿪵ķ���
				if (listener != null) {
					listener.onOpen();
				}
				// ��ǰ��Swipelayout�Ѿ��򿪣���Ҫ��Manager��¼
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
	 * @Description:��ɾ��View
	 * @return: void
	 */
	public void openDeleteView() {
		viewDragHelper.smoothSlideViewTo(itemView, -deleteWidth, itemView.getTop());
		ViewCompat.postInvalidateOnAnimation(SlideLayout.this);
	};

	/**
	 * @Title: openDeleteView
	 * @Description:�ر�ɾ��View
	 * @return: void
	 */
	public void closeDeleteView() {
		viewDragHelper.smoothSlideViewTo(itemView, 0, itemView.getTop());
		ViewCompat.postInvalidateOnAnimation(SlideLayout.this);
	};

	/**
	 * @Title: computeScroll
	 * @Description:����ִ�ж���
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
	 * @Description:�����ӿ�
	 * @author: iamxiarui@foxmail.com
	 * @date: 2016��5��19�� ����6:57:11
	 */
	public interface OnSlideStateChangeListener {
		void onOpen();

		void onClose();
	}

}
