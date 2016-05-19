package com.slidedelete.activity;

import java.util.ArrayList;

import com.slidedelete.adapter.SlideAdapter;
import com.slidedelete.utils.SlideLayoutManager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

/**
 * @ClassName: MainActivity
 * @Description:列表滑动删除条目的实现
 * @author: iamxiarui@foxmail.com
 * @date: 2016年5月19日 下午4:11:33
 */
public class MainActivity extends Activity {

	private ListView mainListView;
	private ArrayList<String> list = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();

		initData();

		initAdapter();

	}

	/**
	 * @Title: initView
	 * @Description:初始化View
	 * @return: void
	 */
	public void initView() {
		mainListView = (ListView) findViewById(R.id.lv_main);
	}

	/**
	 * @Title: initData
	 * @Description:初始化数据
	 * @return: void
	 */
	public void initData() {
		for (int i = 0; i < 30; i++) {
			list.add("name - " + i);
		}

		// 设置滚动监听 滚动时关闭删除框
		mainListView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
					// 如果垂直滑动，则需要关闭已经打开的删除框
					SlideLayoutManager.getInstance().closeCurrentLayout();
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			}
		});
	}

	/**
	 * @Title: initAdapter
	 * @Description:绑定适配器
	 * @return: void
	 */
	public void initAdapter() {
		mainListView.setAdapter(new SlideAdapter(this, list));
	}

}
