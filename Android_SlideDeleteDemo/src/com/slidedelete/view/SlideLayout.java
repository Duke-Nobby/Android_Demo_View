package com.slidedelete.view;

import android.content.Context;
import android.util.AttributeSet;
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

}
