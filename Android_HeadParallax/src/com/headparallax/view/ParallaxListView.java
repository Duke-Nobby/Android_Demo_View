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
	// ����ĸ߶�
	private int initHeight;
	// ͼƬ�������߶�
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
	 * @Description:����ͼƬ�������
	 * @param headerImage
	 * @return: void
	 */
	public void setParallaxImageView(final ImageView headerImage) {
		this.headerImage = headerImage;

		// �趨ͼƬ���߶�
		headerImage.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				// ���Ƴ����м���
				headerImage.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				// ��ʶ�߶�
				initHeight = headerImage.getHeight();
				// ���Ƶĸ߶�
				int drawHeight = headerImage.getDrawable().getIntrinsicHeight();
				maxHeight = initHeight > drawHeight ? initHeight * 2 : drawHeight;
			}
		});
	}

	/**
	 * @Title: overScrollBy
	 * @Description: ��ListView������ͷ��ʱ��ִ�У����Ի�ȡ�����������ľ���ͷ���
	 * @param deltaX������������X�ľ��룬��ֵ��ʾ������ͷ����ֵ��ʾ�ײ���ͷ
	 * @param deltaY������������Y�ľ��룬��ֵ��ʾ������ͷ����ֵ��ʾ�ײ���ͷ
	 * @param scrollX������������X�ľ���
	 * @param scrollY������������Y�ľ���
	 * @param scrollRangeX������������X�ľ��뷶Χ
	 * @param scrollRangeY������������Y�ľ��뷶Χ
	 * @param maxOverScrollX��X���������Թ����ľ���
	 * @param maxOverScrollY��Y���������Թ����ľ���
	 * @param isTouchEvent��true����ָ�϶�������false��ʾfling�����Ի���
	 * @return
	 */
	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY,
			int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
		// ��ʾ������ͷ���������ֶ��϶���ͷ����� ��Ҫ���ϵ�����ImageView�ĸ߶�
		if (deltaY < 0 && isTouchEvent) {
			// ���ͼƬ��Ϊ��
			if (headerImage != null) {
				// �µĸ߶�Ϊԭ���ĸ߶ȼ����ƶ���ֵ������֮һ���ƶ���ֵΪ��ֵ��
				int newHeight = headerImage.getHeight() + (-deltaY / 3);
				// �涨��Χ
				if (newHeight > maxHeight) {
					newHeight = maxHeight;
				}
				// ��������ͼƬ�߶�
				headerImage.getLayoutParams().height = newHeight;
				// ʹImageView�Ĳ��ֲ�����Ч
				headerImage.requestLayout();
			}
		}
		return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX,
				maxOverScrollY, isTouchEvent);
	}

	/**
	 * @Title: onTouchEvent
	 * @Description:�����¼�
	 * @param ev
	 * @return
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_UP) {
			// ��Ҫ��ImageView�ĸ߶Ȼ����ָ�������߶�
			ValueAnimator animator = ValueAnimator.ofInt(headerImage.getHeight(), initHeight);
			animator.addUpdateListener(new AnimatorUpdateListener() {
				@Override
				public void onAnimationUpdate(ValueAnimator animator) {
					// ��ȡ������ֵ�����ø�imageview
					int animatedValue = (Integer) animator.getAnimatedValue();
					// ��������ͼƬ�߶�
					headerImage.getLayoutParams().height = animatedValue;
					// ʹImageView�Ĳ��ֲ�����Ч
					headerImage.requestLayout();
				}
			});
			// ���ԵĲ�ֵ��
			animator.setInterpolator(new OvershootInterpolator());
			animator.setDuration(500);
			animator.start();
		}
		return super.onTouchEvent(ev);
	}

}
