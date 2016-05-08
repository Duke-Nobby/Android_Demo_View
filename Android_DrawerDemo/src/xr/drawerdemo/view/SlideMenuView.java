package xr.drawerdemo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @ClassName: SlideMenuView
 * @Description:�Զ��廬������˵�
 * @author: iamxiarui@foxmail.com
 * @date: 2016��5��8�� ����10:35:23
 */
public class SlideMenuView extends ViewGroup {

	// ����ʵ���乹�캯��
	public SlideMenuView(Context context) {
		super(context);
	}

	public SlideMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SlideMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	@SuppressLint("NewApi")
	public SlideMenuView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	/**
	 * @Title: onMeasure
	 * @Description:���ƽ����һ�� ����
	 * @param widthMeasureSpec�����
	 * @param heightMeasureSpec���߶�
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		// ������һ��View �˵�����Ŀ��
		View leftMenu = getChildAt(0);
		leftMenu.measure(leftMenu.getLayoutParams().width, heightMeasureSpec);

		// �����ڶ���View ������Ŀ��
		View rightMenu = getChildAt(1);
		rightMenu.measure(widthMeasureSpec, heightMeasureSpec);

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	/**
	 * @Title: onLayout
	 * @Description:���ƽ���ڶ��� �ڷ�
	 * @param changed:��ǰ�ؼ��ߴ�/λ���Ƿ����˱仯
	 * @param l:��߾�
	 * @param t:���߾�
	 * @param r:�ұ߽�
	 * @param b:�±߽�
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		// �˵�����λ��
		View leftMenu = getChildAt(0);
		leftMenu.layout(-leftMenu.getMeasuredWidth(), 0, 0, b);

		// ���������λ��
		getChildAt(1).layout(l, t, r, b);

	}

}
