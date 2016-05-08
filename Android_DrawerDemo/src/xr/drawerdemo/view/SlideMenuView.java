package xr.drawerdemo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * @ClassName: SlideMenuView
 * @Description:�Զ��廬������˵�
 * @author: iamxiarui@foxmail.com
 * @date: 2016��5��8�� ����10:35:23
 */
public class SlideMenuView extends ViewGroup {

	private float downStartX;
	private float moveEndX;
	private int scrollX;

	private static final int MAIN_MODE = 0;
	private static final int MENU_MODE = 1;
	private int currentMode = MAIN_MODE;

	private Scroller scroller;
	private float downStartY;

	// ����ʵ���乹�캯��
	public SlideMenuView(Context context) {
		super(context);
		initView();
	}

	public SlideMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public SlideMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView();
	}

	@SuppressLint("NewApi")
	public SlideMenuView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		initView();
	}

	/**
	 * @Title: initVie��ʼ��View
	 * @return: void
	 */
	private void initView() {
		scroller = new Scroller(getContext());
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

	/**
	 * @Title: onTouchEvent
	 * @Description:���������¼�
	 * @param event���¼�����
	 * @return
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// ����λ�õ�Xֵ
			downStartX = event.getX();

			break;
		case MotionEvent.ACTION_MOVE:
			// �ƶ�����λ�õ�Xֵ
			moveEndX = event.getX();

			// ���ߵĲ�ֵΪ��������
			scrollX = (int) (downStartX - moveEndX);

			// ��ǰ���㽫Ҫ�ƶ�����
			int willScrollX = getScrollX() + scrollX;

			// ���û����߽�
			if (willScrollX < -getChildAt(0).getMeasuredWidth()) {
				// �������������߽� ֱ�ӻ�������߽�
				scrollTo(-getChildAt(0).getMeasuredWidth(), 0);
			} else if (willScrollX > 0) {
				// ������������ұ߽� ֱ�ӻ������ұ߽�
				scrollTo(0, 0);
			} else {
				// ���û�г����߽� ֱ�����ù���
				scrollBy(scrollX, 0);
			}

			// �ƶ�����λ����Ϊ��һ�λ�����ʼλ��
			downStartX = moveEndX;

			break;
		case MotionEvent.ACTION_UP:

			// ��߲˵������м�λ��
			int leftCenter = (int) (-getChildAt(0).getMeasuredWidth() / 2.0f);

			// ��������ľ�����ڲ˵���һ�� ֱ����ʾ�˵�
			if (getScrollX() < leftCenter) {
				currentMode = MENU_MODE;
				showMenuView();
			} else {
				// ���򷵻����˵�
				currentMode = MAIN_MODE;
				showMenuView();
			}

			break;
		}
		return true;
	}

	/**
	 * @Title: onInterceptTouchEvent
	 * @Description:���������¼�
	 * @param ev
	 * @return
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downStartX = ev.getX();
			downStartY = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			// �ֱ�õ��ƶ���X��Yƫ����
			float xOffset = Math.abs(ev.getX() - downStartX);
			float yOffset = Math.abs(ev.getY() - downStartY);

			// ˮƽ���򳬳�һ������ʱ,������
			if (xOffset > yOffset && xOffset > 5) {
				// ���ش˴δ����¼�, ����Ĺ���
				return true;
			}

			break;

		}
		return super.onInterceptTouchEvent(ev);
	}

	/**
	 * @Title: showMenuView
	 * @Description:���ݻ���������ʾ�˵�
	 * @return: void
	 */
	private void showMenuView() {

		int startX = getScrollX();
		// ��Ҫ����ˮƽ�ı仯��
		int distanceX = 0;

		if (currentMode == MAIN_MODE) {
			// ˮƽ�仯��Ϊ�������ƶ��ľ���
			distanceX = 0 - startX;
		} else if (currentMode == MENU_MODE) {
			// ˮƽ�仯��Ϊ�˵����滹û����ʾ�ľ���
			distanceX = -getChildAt(0).getMeasuredWidth() - startX;
		}

		// ƽ�����ݵ�ģ��
		int duration = Math.abs(distanceX * 2);
		// ��ʼƽ��
		scroller.startScroll(startX, 0, distanceX, 0, duration);
		// �ػ����
		invalidate();

	}

	/**
	 * @Title: computeScroll
	 * @Description:���ϻ�ȡģ������ά�ֶ����ļ���
	 */
	@Override
	public void computeScroll() {
		super.computeScroll();
		if (scroller.computeScrollOffset()) { // ֱ��duration�¼��Ժ�, ����
			// true, ������û�н���
			// ��ȡ��ǰģ�������, Ҳ����Ҫ��������λ��
			int currX = scroller.getCurrX();
			// ֱ�ӻ�����ȥ
			scrollTo(currX, 0);
			// �ػ����
			invalidate();
		}
	}

	/**
	 * @Title: switchState
	 * @Description:����״̬ ִ�е���¼�
	 * @return: void
	 */
	public void switchState() {
		if (currentMode == MAIN_MODE) {
			currentMode = MENU_MODE;
			showMenuView();
		} else {
			currentMode = MAIN_MODE;
			showMenuView();
		}
	}

	public int getCurrentState() {
		return currentMode;
	}

}
