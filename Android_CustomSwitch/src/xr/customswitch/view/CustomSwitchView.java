package xr.customswitch.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
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
	private boolean isSwitchState = true;

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

		// Ȼ����ƿ���
		if (isSwitchState) {
			int moveDistance = switchBackgroupBitmap.getWidth() - switchForegroupBitmap.getWidth();
			canvas.drawBitmap(switchForegroupBitmap, moveDistance, 0, paint);
		} else {
			canvas.drawBitmap(switchForegroupBitmap, 0, 0, paint);
		}
	}

}
