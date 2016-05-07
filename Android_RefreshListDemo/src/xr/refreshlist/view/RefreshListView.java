package xr.refreshlist.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;
import xr.refreshlist.R;

/**
 * @ClassName: RefreshListView
 * @Description:带有下拉刷新的ListView
 * @author: iamxiarui@foxmail.com
 * @date: 2016年5月7日 下午3:08:26
 */
public class RefreshListView extends ListView {

	private View headerView;

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
		initFooterView();
	}

	/**
	 * @Title: initHeaderView
	 * @Description:初始化List的头文件
	 * @return: void
	 */
	private void initHeaderView() {
		headerView = View.inflate(getContext(), R.layout.layout_list_header, null);
		// 按照设置的该规则测量控件的高度
		headerView.measure(0, 0);
		int headerViewHeight = headerView.getMeasuredHeight();
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

}
