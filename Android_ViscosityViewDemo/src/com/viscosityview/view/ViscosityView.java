package com.viscosityview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

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
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);// 设置抗锯齿
		paint.setColor(Color.RED);
	}

	private float dragRadius = 20f;// 拖拽圆的半径
	private float stickyRadius = 20f;// 固定圆的半径
	private PointF dragCenter = new PointF(150f, 300f);// 拖拽圆的圆心
	private PointF stickyCenter = new PointF(300f, 300f);// 固定圆的圆心

	// 固定圆坐标
	private PointF[] stickyPoint = { new PointF(300f, 280f), new PointF(300f, 320f) };
	// 拖拽圆坐标
	private PointF[] dragPoint = { new PointF(150f, 280f), new PointF(150f, 320f) };

	// 控制点坐标
	private PointF controlPoint = new PointF(225f, 300f);

	/**
	 * @Title: onDraw
	 * @Description:绘制View
	 * @param canvas
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 先绘制两个圆
		canvas.drawCircle(dragCenter.x, dragCenter.y, dragRadius, paint);
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

}
