package com.viscosityview.view;

import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
import com.viscosityview.utils.GeometryUtil;
import com.viscosityview.utils.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

/**
 * @ClassName: ViscosityView
 * @Description:�Զ���ճ�Կؼ�
 * @author: iamxiarui@foxmail.com
 * @date: 2016��5��19�� ����8:10:24
 */
public class ViscosityView extends View {
	private Paint paint;

	public ViscosityView(Context context) {
		super(context);
		initView();
	}

	public ViscosityView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public ViscosityView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView();
	}

	public ViscosityView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		initView();
	}

	/**
	 * @Title: initView
	 * @Description:��ʼ��View
	 * @return: void
	 */
	private void initView() {
		// ���ÿ����
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.RED);
	}

	// ��קԲ�İ뾶
	private float dragRadius = 30f;
	// �̶�Բ�İ뾶
	private float stickyRadius = 30f;
	// ��קԲ��Բ��
	private PointF dragCenter = new PointF(400f, 600f);
	// �̶�Բ��Բ��
	private PointF stickyCenter = new PointF(400f, 600f);

	// ��קԲ����
	private PointF[] dragPoint = { new PointF(400f, 570f), new PointF(400f, 630f) };
	// �̶�Բ����
	private PointF[] stickyPoint = { new PointF(400f, 570f), new PointF(400f, 630f) };

	// ���Ƶ�����
	private PointF controlPoint = new PointF(400f, 600f);

	// б��
	private double slope;
	// �涨������
	private final float maxDistance = 200;
	// Ĭ����קû�г���ԲȦ��Χ
	private boolean isDragOutOfRange = false;

	/**
	 * @Title: onDraw
	 * @Description:����View
	 * @param canvas
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// �����廭������ƫ��һ�ξ��� ʹ�õ��������קԲԲ��
		canvas.translate(0, -Utils.getStatusBarHeight(getResources()));

		// ��̬�õ��̶�Բ�뾶
		stickyRadius = getStickyRadius();

		// �õ�X��Y�����ϵ�ƫ���� ���б��
		float xOffset = dragCenter.x - stickyCenter.x;
		float yOffset = dragCenter.y - stickyCenter.y;
		if (xOffset != 0) {
			slope = yOffset / xOffset;
		}
		// ����dragCenter��̬���dragPoint��stickyPoint
		// dragPoint: ��ԲԲ�����ߵĴ�������קԲ�Ľ���
		dragPoint = GeometryUtil.getIntersectionPoints(dragCenter, dragRadius, slope);
		// stickyPoint:��ԲԲ�����ߵĴ�����̶�Բ�Ľ���
		stickyPoint = GeometryUtil.getIntersectionPoints(stickyCenter, stickyRadius, slope);

		// ��̬������Ƶ�
		controlPoint = GeometryUtil.getPointByPercent(dragCenter, stickyCenter, 0.618f);

		// �Ȼ�������Բ
		canvas.drawCircle(dragCenter.x, dragCenter.y, dragRadius, paint);
		if (!isDragOutOfRange) {
			canvas.drawCircle(stickyCenter.x, stickyCenter.y, stickyRadius, paint);
			Path path = new Path();
			// �������
			path.moveTo(stickyPoint[0].x, stickyPoint[0].y);
			// ��������ı���������
			path.quadTo(controlPoint.x, controlPoint.y, dragPoint[0].x, dragPoint[0].y);
			// ������
			path.lineTo(dragPoint[1].x, dragPoint[1].y);
			// ��������ı���������
			path.quadTo(controlPoint.x, controlPoint.y, stickyPoint[1].x, stickyPoint[1].y);
			// �ر�·�� Ĭ�ϱպ�
			path.close();
			// ��������
			canvas.drawPath(path, paint);
		}

		// ����ȦȦ���Թ̶�ԲԲ��ΪԲ�ģ�������80Ϊ�뾶
		paint.setStyle(Style.STROKE);// ����ֻ�б���
		canvas.drawCircle(stickyCenter.x, stickyCenter.y, maxDistance, paint);
		paint.setStyle(Style.FILL);

	}

	/**
	 * @Title: getStickyRadius
	 * @Description:��̬�ĵ��̶�Բ�뾶
	 */
	private float getStickyRadius() {
		float radius;
		// ���2��Բ��֮��ľ���
		float centerDistance = GeometryUtil.getDistanceBetween2Points(dragCenter, stickyCenter);
		// Բ�ľ���ռ�ܾ���İٷֱ�
		float fraction = centerDistance / maxDistance;
		radius = GeometryUtil.evaluateValue(fraction, 30f, 5f);
		return radius;
	}

	/**
	 * @Title: onTouchEvent
	 * @Description:����¼�
	 * @param event
	 * @return
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			isDragOutOfRange = false;
			// ��̬����Բ��
			dragCenter.set(event.getRawX(), event.getRawY());
			break;
		case MotionEvent.ACTION_MOVE:
			dragCenter.set(event.getRawX(), event.getRawY());
			// �������֮��������������
			if (GeometryUtil.getDistanceBetween2Points(dragCenter, stickyCenter) > maxDistance) {
				// ������Χ��Ӧ�öϵ������ٻ��Ʊ��������ߵĲ���
				isDragOutOfRange = true;
			}
			break;
		case MotionEvent.ACTION_UP:
			if (GeometryUtil.getDistanceBetween2Points(dragCenter, stickyCenter) > maxDistance) {
				disappearAnimation();
			} else {
				if (isDragOutOfRange) {
					// ��ʧ����
					disappearAnimation();
				} else {
					// ���ض���
					backAnimation();
				}
			}
			break;
		}
		// �ػ�View
		invalidate();
		return true;
	}

	/**
	 * @Title: disappearAnimation
	 * @Description:������Χ ��ʧ����
	 * @return: void
	 */
	private void disappearAnimation() {
		dragCenter.set(0, 0);
	}

	/**
	 * @Title: backAnimation
	 * @Description:û�г�����Χ ���ض���
	 * @return: void
	 */
	private void backAnimation() {
		// �Զ�������ʽ��ȥ
		ValueAnimator valueAnimator = ObjectAnimator.ofFloat(1);
		final PointF startPointF = new PointF(dragCenter.x, dragCenter.y);
		valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animator) {
				// d����ִ�еİٷֱ�
				float animatedFraction = animator.getAnimatedFraction();
				PointF pointF = GeometryUtil.getPointByPercent(startPointF, stickyCenter, animatedFraction);
				dragCenter.set(pointF);
				invalidate();
			}
		});
		// ���ö��� �� ��ֵ��
		valueAnimator.setDuration(500);
		valueAnimator.setInterpolator(new OvershootInterpolator(3));
		valueAnimator.start();
	}

}
