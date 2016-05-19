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
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);// ���ÿ����
		paint.setColor(Color.RED);
	}

	private float dragRadius = 20f;// ��קԲ�İ뾶
	private float stickyRadius = 20f;// �̶�Բ�İ뾶
	private PointF dragCenter = new PointF(150f, 300f);// ��קԲ��Բ��
	private PointF stickyCenter = new PointF(300f, 300f);// �̶�Բ��Բ��

	// �̶�Բ����
	private PointF[] stickyPoint = { new PointF(300f, 280f), new PointF(300f, 320f) };
	// ��קԲ����
	private PointF[] dragPoint = { new PointF(150f, 280f), new PointF(150f, 320f) };

	// ���Ƶ�����
	private PointF controlPoint = new PointF(225f, 300f);

	/**
	 * @Title: onDraw
	 * @Description:����View
	 * @param canvas
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// �Ȼ�������Բ
		canvas.drawCircle(dragCenter.x, dragCenter.y, dragRadius, paint);
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

}
