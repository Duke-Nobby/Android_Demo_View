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
 * @Description:自定义粘性控件
 * @author: iamxiarui@foxmail.com
 * @date: 2016年5月19日 下午8:10:24
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
	 * @Description:初始化View
	 * @return: void
	 */
	private void initView() {
		// 设置抗锯齿
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.RED);
	}

	// 拖拽圆的半径
	private float dragRadius = 30f;
	// 固定圆的半径
	private float stickyRadius = 30f;
	// 拖拽圆的圆心
	private PointF dragCenter = new PointF(400f, 600f);
	// 固定圆的圆心
	private PointF stickyCenter = new PointF(400f, 600f);

	// 拖拽圆坐标
	private PointF[] dragPoint = { new PointF(400f, 570f), new PointF(400f, 630f) };
	// 固定圆坐标
	private PointF[] stickyPoint = { new PointF(400f, 570f), new PointF(400f, 630f) };

	// 控制点坐标
	private PointF controlPoint = new PointF(400f, 600f);

	// 斜率
	private double slope;
	// 规定最大距离
	private final float maxDistance = 200;
	// 默认拖拽没有超出圆圈范围
	private boolean isDragOutOfRange = false;

	/**
	 * @Title: onDraw
	 * @Description:绘制View
	 * @param canvas
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// 让整体画布往上偏移一段距离 使得点击处在拖拽圆圆心
		canvas.translate(0, -Utils.getStatusBarHeight(getResources()));

		// 动态得到固定圆半径
		stickyRadius = getStickyRadius();

		// 得到X和Y方向上的偏移量 求出斜率
		float xOffset = dragCenter.x - stickyCenter.x;
		float yOffset = dragCenter.y - stickyCenter.y;
		if (xOffset != 0) {
			slope = yOffset / xOffset;
		}
		// 根据dragCenter动态求出dragPoint和stickyPoint
		// dragPoint: 两圆圆心连线的垂线与拖拽圆的交点
		dragPoint = GeometryUtil.getIntersectionPoints(dragCenter, dragRadius, slope);
		// stickyPoint:两圆圆心连线的垂线与固定圆的交点
		stickyPoint = GeometryUtil.getIntersectionPoints(stickyCenter, stickyRadius, slope);

		// 动态计算控制点
		controlPoint = GeometryUtil.getPointByPercent(dragCenter, stickyCenter, 0.618f);

		// 先绘制两个圆
		canvas.drawCircle(dragCenter.x, dragCenter.y, dragRadius, paint);
		if (!isDragOutOfRange) {
			canvas.drawCircle(stickyCenter.x, stickyCenter.y, stickyRadius, paint);
			Path path = new Path();
			// 设置起点
			path.moveTo(stickyPoint[0].x, stickyPoint[0].y);
			// 绘制上面的贝塞尔曲线
			path.quadTo(controlPoint.x, controlPoint.y, dragPoint[0].x, dragPoint[0].y);
			// 连接线
			path.lineTo(dragPoint[1].x, dragPoint[1].y);
			// 绘制下面的贝塞尔曲线
			path.quadTo(controlPoint.x, controlPoint.y, stickyPoint[1].x, stickyPoint[1].y);
			// 关闭路径 默认闭合
			path.close();
			// 绘制曲线
			canvas.drawPath(path, paint);
		}

		// 绘制圈圈，以固定圆圆心为圆心，最大距离80为半径
		paint.setStyle(Style.STROKE);// 设置只有边线
		canvas.drawCircle(stickyCenter.x, stickyCenter.y, maxDistance, paint);
		paint.setStyle(Style.FILL);

	}

	/**
	 * @Title: getStickyRadius
	 * @Description:动态的到固定圆半径
	 */
	private float getStickyRadius() {
		float radius;
		// 求出2个圆心之间的距离
		float centerDistance = GeometryUtil.getDistanceBetween2Points(dragCenter, stickyCenter);
		// 圆心距离占总距离的百分比
		float fraction = centerDistance / maxDistance;
		radius = GeometryUtil.evaluateValue(fraction, 30f, 5f);
		return radius;
	}

	/**
	 * @Title: onTouchEvent
	 * @Description:点击事件
	 * @param event
	 * @return
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			isDragOutOfRange = false;
			// 动态设置圆心
			dragCenter.set(event.getRawX(), event.getRawY());
			break;
		case MotionEvent.ACTION_MOVE:
			dragCenter.set(event.getRawX(), event.getRawY());
			// 如果两点之间具体大于最大距离
			if (GeometryUtil.getDistanceBetween2Points(dragCenter, stickyCenter) > maxDistance) {
				// 超出范围，应该断掉，不再绘制贝塞尔曲线的部分
				isDragOutOfRange = true;
			}
			break;
		case MotionEvent.ACTION_UP:
			if (GeometryUtil.getDistanceBetween2Points(dragCenter, stickyCenter) > maxDistance) {
				disappearAnimation();
			} else {
				if (isDragOutOfRange) {
					// 消失动画
					disappearAnimation();
				} else {
					// 返回动画
					backAnimation();
				}
			}
			break;
		}
		// 重绘View
		invalidate();
		return true;
	}

	/**
	 * @Title: disappearAnimation
	 * @Description:超出范围 消失动画
	 * @return: void
	 */
	private void disappearAnimation() {
		dragCenter.set(0, 0);
	}

	/**
	 * @Title: backAnimation
	 * @Description:没有超出范围 返回动画
	 * @return: void
	 */
	private void backAnimation() {
		// 以动画的形式回去
		ValueAnimator valueAnimator = ObjectAnimator.ofFloat(1);
		final PointF startPointF = new PointF(dragCenter.x, dragCenter.y);
		valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animator) {
				// d动画执行的百分比
				float animatedFraction = animator.getAnimatedFraction();
				PointF pointF = GeometryUtil.getPointByPercent(startPointF, stickyCenter, animatedFraction);
				dragCenter.set(pointF);
				invalidate();
			}
		});
		// 设置动画 与 插值器
		valueAnimator.setDuration(500);
		valueAnimator.setInterpolator(new OvershootInterpolator(3));
		valueAnimator.start();
	}

}
