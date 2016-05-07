package xr.refreshlist.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;
import xr.refreshlist.R;

/**
 * @ClassName: RefreshListView
 * @Description:��������ˢ�µ�ListView
 * @author: iamxiarui@foxmail.com
 * @date: 2016��5��7�� ����3:08:26
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
	 * @Description:��ʼ��View
	 * @return: void
	 */
	private void initView() {
		initHeaderView();
		initFooterView();
	}

	/**
	 * @Title: initHeaderView
	 * @Description:��ʼ��List��ͷ�ļ�
	 * @return: void
	 */
	private void initHeaderView() {
		headerView = View.inflate(getContext(), R.layout.layout_list_header, null);
		// �������õĸù�������ؼ��ĸ߶�
		headerView.measure(0, 0);
		int headerViewHeight = headerView.getMeasuredHeight();
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

}
