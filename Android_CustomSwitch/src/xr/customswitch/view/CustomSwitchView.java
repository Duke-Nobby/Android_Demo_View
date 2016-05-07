package xr.customswitch.view;

import android.R.bool;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @ClassName: CustomSwitchView
 * @Description:�Զ���ؼ� �̳�View
 * @author: iamxiarui@foxmail.com
 * @date: 2016��5��5�� ����6:51:49
 */
public class CustomSwitchView extends View {

	private Bitmap switchBackgroupBitmap;
	private Bitmap switchForegroupBitmap;
	private Paint paint;

	private boolean isSwitchState = true;// ����״̬
	private boolean isTouchState = false;// ����״̬
	private float currentPosition;// ��ǰ����λ��
	private int maxPosition;// ���ػ������λ��

	private OnSwitchStateUpdateListener onSwitchStateUpdateListener;

	/* ע�����ʵ���ĸ����캯�� */

	/**
	 * @Description:���ڴ��봴���ؼ�
	 */
	public CustomSwitchView(Context context) {
		super(context);
		initView();
	}

	/**
	 * @Description:������XML��ʹ�ã�����ָ���Զ�������
	 */
	public CustomSwitchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();

		// ���������ռ�
		String namespace = "http://schemas.android.com/apk/res/xr.customswitch.view";

		// ͨ�������ռ� �� �������� �ҵ���Ӧ����Դ����
		int switchBackgroundResource = attrs.getAttributeResourceValue(namespace, "switch_background", -1);
		int switchForegroundResource = attrs.getAttributeResourceValue(namespace, "switch_foreground", -1);
		isSwitchState = attrs.getAttributeBooleanValue(namespace, "switch_state", false);

		// ����Դ�������õ���Ӧλ��
		setBackgroundPic(switchBackgroundResource);
		setForegroundPic(switchForegroundResource);
	}

	/**
	 * @Description:������XML��ʹ�ã�����ָ���Զ������ԣ���ָ����ʽ
	 */
	public CustomSwitchView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView();
	}

	/**
	 * @Description:������XML��ʹ�ã�����ָ���Զ������ԣ���ָ����ʽ������Դ
	 */
	public CustomSwitchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		initView();
	}

	/**
	 * @Title: initView
	 * @Description:��ʼ��View
	 * @return: void
	 */
	private void initView() {
		paint = new Paint();
	}

	/**
	 * @Title: setBackgroundPic
	 * @Description:���ñ���ͼ
	 * @return: void
	 */
	public void setBackgroundPic(int switchBackground) {
		switchBackgroupBitmap = BitmapFactory.decodeResource(getResources(), switchBackground);
	}

	/**
	 * @Title: setForegroundPic
	 * @Description:����ǰ��ͼ
	 * @return: void
	 */
	public void setForegroundPic(int switchForeground) {
		switchForegroupBitmap = BitmapFactory.decodeResource(getResources(), switchForeground);
	}

	/**
	 * @Title: setSwitchState
	 * @Description:ָ������״̬
	 * @return: void
	 */
	public void setSwitchState(boolean isSwitchState) {
		this.isSwitchState = isSwitchState;
	}

	/**
	 * @Title: onMeasure
	 * @Description:�������Զ���ؼ��ĳ���
	 * @return: void
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(switchBackgroupBitmap.getWidth(), switchBackgroupBitmap.getHeight());
	}

	/**
	 * @Title: onDraw
	 * @Description:���ƿؼ�
	 * @return: void
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		// �Ȼ��Ʊ���
		canvas.drawBitmap(switchBackgroupBitmap, 0, 0, paint);

		// ������ڴ���״̬
		if (isTouchState) {
			// ����λ���ڿ��ص��м�λ��
			float movePosition = currentPosition - switchForegroupBitmap.getWidth() / 2.0f;
			maxPosition = switchBackgroupBitmap.getWidth() - switchForegroupBitmap.getWidth();
			// �޶����ػ�����Χ ֻ���� 0 - maxPosition��Χ��
			if (movePosition < 0) {
				movePosition = 0;
			} else if (movePosition > maxPosition) {
				movePosition = maxPosition;
			}
			// ���ƿ���
			canvas.drawBitmap(switchForegroupBitmap, movePosition, 0, paint);
		}
		// ֱ�ӻ��ƿ���
		else {
			// ������棬ֱ�ӽ����ػ�����Ϊ����״̬
			if (isSwitchState) {
				maxPosition = switchBackgroupBitmap.getWidth() - switchForegroupBitmap.getWidth();
				canvas.drawBitmap(switchForegroupBitmap, maxPosition, 0, paint);
			} else {
				// ���򽫿�����Ϊ�ر�״̬
				canvas.drawBitmap(switchForegroupBitmap, 0, 0, paint);
			}
		}
	}

	/**
	 * @Title: onTouchEvent
	 * @Description:�����¼�
	 * @return: void
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// ���ڴ���״̬
			isTouchState = true;
			// �õ�λ������
			currentPosition = event.getX();
			break;

		case MotionEvent.ACTION_MOVE:
			currentPosition = event.getX();
			break;

		case MotionEvent.ACTION_UP:
			// ����״̬����
			isTouchState = false;
			currentPosition = event.getX();
			// �м��־λ��
			float centerPosition = switchBackgroupBitmap.getWidth() / 2.0f;

			// ������ص�ǰλ�ô��ڱ���λ�õ�һ�� ��ʾ�� ������ʾ��
			boolean currentState = currentPosition > centerPosition;

			// �����Ȼ״̬����ͬ�Ұ��˼������� ��ִ�м�������
			if (currentState != isSwitchState && onSwitchStateUpdateListener != null) {
				onSwitchStateUpdateListener.onStateUpdate(currentState);
			}
			// ��ǰ״̬��Ϊ����״̬
			isSwitchState = currentState;
			break;
		}

		// ���µ���onDraw�����������ػ����
		invalidate();
		return true;
	}

	/**
	 * @ClassName: OnSwitchStateUpdateListener
	 * @Description:����¼�״̬�����ӿڶ���
	 * @author: iamxiarui@foxmail.com
	 * @date: 2016��5��5�� ����9:33:35
	 */
	public interface OnSwitchStateUpdateListener {
		// ״̬�ص�, �ѵ�ǰ״̬����ȥ
		void onStateUpdate(boolean state);
	}

	/**
	 * @Title: setOnSwitchStateUpdateListener
	 * @Description:״̬��������
	 * @return: void
	 */
	public void setOnSwitchStateUpdateListener(OnSwitchStateUpdateListener onSwitchStateUpdateListener) {
		this.onSwitchStateUpdateListener = onSwitchStateUpdateListener;
	}

}
