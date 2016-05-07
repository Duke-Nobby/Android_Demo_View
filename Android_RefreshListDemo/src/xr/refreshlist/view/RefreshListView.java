package xr.refreshlist.view;

import java.text.SimpleDateFormat;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
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
public class RefreshListView extends ListView implements OnScrollListener {

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
	private OnRefreshListener onRefreshListener;
	private View footerView;
	private int footerViewHeight;
	private boolean isLoadingMore; // 正在加载状态

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

		// 添加滚动监听
		setOnScrollListener(this);
	}

	/**
	 * @Title: initHeaderView
	 * @Description:初始化List的头布局
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
	 * @Description:初始化List的底布局
	 * @return: void
	 */
	private void initFooterView() {
		footerView = View.inflate(getContext(), R.layout.layout_list_footer, null);

		// 按照设置的该规则测量控件的高度
		footerView.measure(0, 0);
		footerViewHeight = footerView.getMeasuredHeight();
		// 设置为负值，相当于隐藏
		footerView.setPadding(0, -footerViewHeight, 0, 0);
		addFooterView(footerView);

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
			// 如果正在刷新不做处理
			if (currentState == REFRESHING) {
				return super.onTouchEvent(ev);
			}

			// 下拉偏移量
			float offset = moveHeadY - downHeadY;

			// 如果是下拉 且 是从第一个位置开始
			if (offset > 0 && getFirstVisiblePosition() == 0) {
				// padding的大小根据高度和下拉偏移量决定
				paddingTop = (int) (-headerViewHeight + offset);
				// 重新设置paddind
				headerView.setPadding(0, paddingTop, 0, 0);

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

				return true; // 当前事件被我们处理并消费
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
		// 必须返回父类方法 因为存在两个触摸事件
		return super.onTouchEvent(ev);
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
			// 箭头不可见
			arrowImage.setVisibility(View.INVISIBLE);
			// 进度条可见
			refreshProgress.setVisibility(View.VISIBLE);
			refreshText.setText("正在刷新中...");

			// 如果监听者绑定了对象
			if (onRefreshListener != null) {
				onRefreshListener.onRefresh();
			}

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

	/**
	 * @ClassName: OnRefreshListener
	 * @Description:刷新监听者的接口
	 * @author: iamxiarui@foxmail.com
	 * @date: 2016年5月7日 下午7:51:09
	 */
	public interface OnRefreshListener {
		// 下拉刷新
		void onRefresh();

		// 加载更多
		void onLoadMore();
	}

	/**
	 * @Title: setOnRefreshListener
	 * @Description:刷新监听事件
	 * @return: void
	 */
	public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
		this.onRefreshListener = onRefreshListener;
	}

	/**
	 * @Title: onRefreshComplete
	 * @Description:刷新完成恢复界面
	 * @return: void
	 */
	public void onRefreshComplete() {
		// 如果正在加载更多
		if (isLoadingMore) {
			footerView.setPadding(0, -footerViewHeight, 0, 0);
			isLoadingMore = false;

		} else {
			// 下拉刷新
			currentState = PULL_TO_REFRESH;
			refreshText.setText("下拉刷新"); // 切换文本
			headerView.setPadding(0, -headerViewHeight, 0, 0);// 隐藏头布局
			refreshProgress.setVisibility(View.INVISIBLE);
			arrowImage.setVisibility(View.VISIBLE);
			refreshtimeText.setText("最后刷新时间: " + getTime());
		}
	}

	/**
	 * @Title: getTime
	 * @Description:得到系统时间
	 * @return: String
	 */
	private String getTime() {
		long currentTimeMillis = System.currentTimeMillis();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(currentTimeMillis);
	}

	/**
	 * @Title: onScrollStateChanged
	 * @Description:列表滚动监听
	 * @return: void
	 */
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// 已经在加载更多
		if (isLoadingMore) {
			return;
		}
		// 最新状态是空闲状态, 并且当前界面显示了所有数据的最后一条. 加载更多
		if (scrollState == SCROLL_STATE_IDLE && getLastVisiblePosition() >= (getCount() - 1)) {
			// 正在加载
			isLoadingMore = true;
			// 设置底布局显示
			footerView.setPadding(0, 0, 0, 0);
			// 跳转到最后一条, 使其显示出加载更多
			setSelection(getCount());

			if (onRefreshListener != null) {
				onRefreshListener.onLoadMore();
			}
		}

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

	}

}
