package com.headparallax.view;

import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ListView;

public class ParallaxListView extends ListView {
	private ImageView headerImage;
	// 最初的高度
	private int initHeight;
	// 图片下拉最大高度
	private int maxHeight;

	public ParallaxListView(Context context) {
		super(context);

	}

	public ParallaxListView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public ParallaxListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

	}

	public ParallaxListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);

	}

	/**
	 * @Title: setParallaxImageView
	 * @Description:设置图片相关属性
	 * @param headerImage
	 * @return: void
	 */
	public void setParallaxImageView(final ImageView headerImage) {
		this.headerImage = headerImage;

		// 设定图片最大高度
		headerImage.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				// 先移除所有监听
				headerImage.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				// 初识高度
				initHeight = headerImage.getHeight();
				// 绘制的高度
				int drawHeight = headerImage.getDrawable().getIntrinsicHeight();
				maxHeight = initHeight > drawHeight ? initHeight * 2 : drawHeight;
			}
		});
	}

	/**
	 * @Title: overScrollBy
	 * @Description: 在ListView滑动到头的时候执行，可以获取到继续滑动的距离和方向
	 * @param deltaX：继续滑动的X的距离，负值表示顶部到头，正值表示底部到头
	 * @param deltaY：继续滑动的Y的距离，负值表示顶部到头，正值表示底部到头
	 * @param scrollX：继续滚动的X的距离
	 * @param scrollY：继续滚动的Y的距离
	 * @param scrollRangeX：继续滚动的X的距离范围
	 * @param scrollRangeY：继续滚动的Y的距离范围
	 * @param maxOverScrollX：X方向最大可以滚动的距离
	 * @param maxOverScrollY：Y方向最大可以滚动的距离
	 * @param isTouchEvent：true是手指拖动滑动，false表示fling靠惯性滑动
	 * @return
	 */
	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY,
			int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
		// 表示顶部到头，并且是手动拖动到头的情况 需要不断的增加ImageView的高度
		if (deltaY < 0 && isTouchEvent) {
			// 如果图片不为空
			if (headerImage != null) {
				// 新的高度为原来的高度加上移动的值的三分之一（移动的值为负值）
				int newHeight = headerImage.getHeight() + (-deltaY / 3);
				// 规定范围
				if (newHeight > maxHeight) {
					newHeight = maxHeight;
				}
				// 重新设置图片高度
				headerImage.getLayoutParams().height = newHeight;
				// 使ImageView的布局参数生效
				headerImage.requestLayout();
			}
		}
		return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX,
				maxOverScrollY, isTouchEvent);
	}

	/**
	 * @Title: onTouchEvent
	 * @Description:触摸事件
	 * @param ev
	 * @return
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_UP) {
			// 需要将ImageView的高度缓慢恢复到最初高度
			ValueAnimator animator = ValueAnimator.ofInt(headerImage.getHeight(), initHeight);
			animator.addUpdateListener(new AnimatorUpdateListener() {
				@Override
				public void onAnimationUpdate(ValueAnimator animator) {
					// 获取动画的值，设置给imageview
					int animatedValue = (Integer) animator.getAnimatedValue();
					// 重新设置图片高度
					headerImage.getLayoutParams().height = animatedValue;
					// 使ImageView的布局参数生效
					headerImage.requestLayout();
				}
			});
			// 弹性的插值器
			animator.setInterpolator(new OvershootInterpolator());
			animator.setDuration(500);
			animator.start();
		}
		return super.onTouchEvent(ev);
	}

}
