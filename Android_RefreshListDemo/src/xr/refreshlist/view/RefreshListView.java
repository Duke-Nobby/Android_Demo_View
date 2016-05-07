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
 * @Description:带有下拉刷新的ListView
 * @author: iamxiarui@foxmail.com
 * @date: 2016年5月7日 下午3:08:26
 */
public class RefreshListView extends ListView {

	private View headerView;
	private float downHeadY;// 下拉位置
	private float moveHeadY;// 下拉移动距离
	private int headerViewHeight;// 刷新头的高度
	private int paddingTop;// 刷新头的padding

	public static final int PULL_TO_REFRESH = 0;// 下拉刷新
	public static final int RELEASE_REFRESH = 1;// 释放刷新
	public static final int REFRESHING = 2; // 刷新中
	private int currentState = PULL_TO_REFRESH; // 当前刷新模式
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
	 * @Description:初始化View
	 * @return: void
	 */
	private void initView() {
		initHeaderView();
		initHeaderAnimation();
		initFooterView();
	}

	/**
	 * @Title: initHeaderView
	 * @Description:初始化List的头文件
	 * @return: void
	 */
	private void initHeaderView() {
		headerView = View.inflate(getContext(), R.layout.layout_list_header, null);
		arrowImage = (ImageView) headerView.findViewById(R.id.iv_arrow);
		refreshText = (TextView) headerView.findViewById(R.id.tv_refresh);
		refreshtimeText = (TextView) headerView.findViewById(R.id.tv_refreshtime);
		refreshProgress = (ProgressBar) headerView.findViewById(R.id.pb_refresh);

		// 按照设置的该规则测量控件的高度
		headerView.measure(0, 0);
		headerViewHeight = headerView.getMeasuredHeight();
		// 设置为负值，相当于隐藏
		headerView.setPadding(0, -headerViewHeight, 0, 0);
		addHeaderView(headerView);
	}

	/**
	 * @Title: initFooterView
	 * @Description:初始化List的脚文件
	 * @return: void
	 */
	private void initFooterView() {

	}

	/**
	 * @Title: onTouchEvent
	 * @Description:触摸事件
	 * @return: boolean
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 得到下拉点的高度
			downHeadY = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			// 得到下拉距离
			moveHeadY = ev.getY();

			// 下拉偏移量
			float offset = moveHeadY - downHeadY;

			// 如果是下拉 且 是从第一个位置开始
			if (offset >= 0 && getFirstVisiblePosition() == 0) {
				// padding的大小根据高度和下拉偏移量决定
				paddingTop = (int) (-headerViewHeight + offset);
				// 重新设置paddind
				headerView.setPadding(0, paddingTop, 0, 0);
			}

			// 切换状态
			if (paddingTop >= 0 && currentState != RELEASE_REFRESH) { // 刷新头完全显示
				// 释放刷新状态
				currentState = RELEASE_REFRESH;
				updateHeaderView();

			} else if (paddingTop < 0 && currentState != PULL_TO_REFRESH) {// 刷新头不完全显示
				// 下拉刷新状态
				currentState = PULL_TO_REFRESH;
				updateHeaderView();
			}

			break;
		case MotionEvent.ACTION_UP:
			// 根据下拉过程的设置状态
			if (currentState == PULL_TO_REFRESH) {
				// 头部不完全显示, 恢复原样
				headerView.setPadding(0, -headerViewHeight, 0, 0);
			} else if (currentState == RELEASE_REFRESH) {
				// 头部 完全显示, 执行正在刷新
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
	 * @Description:下拉刷新的时候更新头部View
	 * @return: void
	 */
	private void updateHeaderView() {
		switch (currentState) {
		case PULL_TO_REFRESH:

			// 上拉动画
			arrowImage.startAnimation(pulldownRotate);
			refreshText.setText("下拉刷新");
			break;
		case RELEASE_REFRESH:
			// 下拉动画
			arrowImage.startAnimation(pullupRotate);
			refreshText.setText("释放刷新");
			break;
		case REFRESHING:
			// 刷新中
			arrowImage.clearAnimation();
			arrowImage.setVisibility(View.INVISIBLE);
			refreshProgress.setVisibility(View.VISIBLE);
			refreshText.setText("正在刷新中...");

			break;
		}
	}

	/**
	 * @Title: headerAnimation
	 * @Description:下拉刷新头部View动画
	 * @return: void
	 */
	private void initHeaderAnimation() {

		// 下拉动画
		pullupRotate = new RotateAnimation(0f, -180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		// 动画执行时间
		pullupRotate.setDuration(300);
		// 动画停留在结束位置
		pullupRotate.setFillAfter(true);

		// 上拉动画
		pulldownRotate = new RotateAnimation(-180f, -360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		// 动画执行时间
		pulldownRotate.setDuration(300);
		// 动画停留在结束位置
		pulldownRotate.setFillAfter(true);

	}

}
