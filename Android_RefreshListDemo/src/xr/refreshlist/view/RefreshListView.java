package xr.refreshlist.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import xr.refreshlist.R;

/**
 * @ClassName: RefreshListView
 * @Description:��������ˢ�µ�ListView
 * @author: iamxiarui@foxmail.com
 * @date: 2016��5��7�� ����3:08:26
 */
public class RefreshListView extends ListView {

	private View headerView;
	private float downHeadY;// ����λ��
	private float moveHeadY;// �����ƶ�����
	private int headerViewHeight;// ˢ��ͷ�ĸ߶�
	private int paddingTop;// ˢ��ͷ��padding

	public static final int PULL_TO_REFRESH = 0;// ����ˢ��
	public static final int RELEASE_REFRESH = 1;// �ͷ�ˢ��
	public static final int REFRESHING = 2; // ˢ����
	private int currentState = PULL_TO_REFRESH; // ��ǰˢ��ģʽ
	private RotateAnimation pulldownRotate;
	private RotateAnimation pullupRotate;
	private ImageView arrowImage;
	private TextView refreshText, refreshtimeText;
	private ProgressBar refreshProgress;

	public RefreshListView(Context context) {
		super(context);
		initView();
	}

	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView();
	}

	public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		initView();
	}

	/**
	 * @Title: initView
	 * @Description:��ʼ��View
	 * @return: void
	 */
	private void initView() {
		initHeaderView();
		initHeaderAnimation();
		initFooterView();
	}

	/**
	 * @Title: initHeaderView
	 * @Description:��ʼ��List��ͷ�ļ�
	 * @return: void
	 */
	private void initHeaderView() {
		headerView = View.inflate(getContext(), R.layout.layout_list_header, null);
		arrowImage = (ImageView) headerView.findViewById(R.id.iv_arrow);
		refreshText = (TextView) headerView.findViewById(R.id.tv_refresh);
		refreshtimeText = (TextView) headerView.findViewById(R.id.tv_refreshtime);
		refreshProgress = (ProgressBar) headerView.findViewById(R.id.pb_refresh);

		// �������õĸù�������ؼ��ĸ߶�
		headerView.measure(0, 0);
		headerViewHeight = headerView.getMeasuredHeight();
		// ����Ϊ��ֵ���൱������
		headerView.setPadding(0, -headerViewHeight, 0, 0);
		addHeaderView(headerView);
	}

	/**
	 * @Title: initFooterView
	 * @Description:��ʼ��List�Ľ��ļ�
	 * @return: void
	 */
	private void initFooterView() {

	}

	/**
	 * @Title: onTouchEvent
	 * @Description:�����¼�
	 * @return: boolean
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// �õ�������ĸ߶�
			downHeadY = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			// �õ���������
			moveHeadY = ev.getY();

			// ����ƫ����
			float offset = moveHeadY - downHeadY;

			// ��������� �� �Ǵӵ�һ��λ�ÿ�ʼ
			if (offset >= 0 && getFirstVisiblePosition() == 0) {
				// padding�Ĵ�С���ݸ߶Ⱥ�����ƫ��������
				paddingTop = (int) (-headerViewHeight + offset);
				// ��������paddind
				headerView.setPadding(0, paddingTop, 0, 0);
			}

			// �л�״̬
			if (paddingTop >= 0 && currentState != RELEASE_REFRESH) { // ˢ��ͷ��ȫ��ʾ
				// �ͷ�ˢ��״̬
				currentState = RELEASE_REFRESH;
				updateHeaderView();

			} else if (paddingTop < 0 && currentState != PULL_TO_REFRESH) {// ˢ��ͷ����ȫ��ʾ
				// ����ˢ��״̬
				currentState = PULL_TO_REFRESH;
				updateHeaderView();
			}

			break;
		case MotionEvent.ACTION_UP:
			// �����������̵�����״̬
			if (currentState == PULL_TO_REFRESH) {
				// ͷ������ȫ��ʾ, �ָ�ԭ��
				headerView.setPadding(0, -headerViewHeight, 0, 0);
			} else if (currentState == RELEASE_REFRESH) {
				// ͷ�� ��ȫ��ʾ, ִ������ˢ��
				headerView.setPadding(0, 0, 0, 0);
				currentState = REFRESHING;
				updateHeaderView();
			}

			break;
		}
		return true;
	}

	/**
	 * @Title: updateHeaderView
	 * @Description:����ˢ�µ�ʱ�����ͷ��View
	 * @return: void
	 */
	private void updateHeaderView() {
		switch (currentState) {
		case PULL_TO_REFRESH:

			// ��������
			arrowImage.startAnimation(pulldownRotate);
			refreshText.setText("����ˢ��");
			break;
		case RELEASE_REFRESH:
			// ��������
			arrowImage.startAnimation(pullupRotate);
			refreshText.setText("�ͷ�ˢ��");
			break;
		case REFRESHING:
			// ˢ����
			arrowImage.clearAnimation();
			arrowImage.setVisibility(View.INVISIBLE);
			refreshProgress.setVisibility(View.VISIBLE);
			refreshText.setText("����ˢ����...");

			break;
		}
	}

	/**
	 * @Title: headerAnimation
	 * @Description:����ˢ��ͷ��View����
	 * @return: void
	 */
	private void initHeaderAnimation() {

		// ��������
		pullupRotate = new RotateAnimation(0f, -180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		// ����ִ��ʱ��
		pullupRotate.setDuration(300);
		// ����ͣ���ڽ���λ��
		pullupRotate.setFillAfter(true);

		// ��������
		pulldownRotate = new RotateAnimation(-180f, -360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		// ����ִ��ʱ��
		pulldownRotate.setDuration(300);
		// ����ͣ���ڽ���λ��
		pulldownRotate.setFillAfter(true);

	}

}
