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
 * @Description:�б���ɾ����Ŀ��ʵ��
 * @author: iamxiarui@foxmail.com
 * @date: 2016��5��19�� ����4:11:33
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
	 * @Description:��ʼ��View
	 * @return: void
	 */
	public void initView() {
		mainListView = (ListView) findViewById(R.id.lv_main);
	}

	/**
	 * @Title: initData
	 * @Description:��ʼ������
	 * @return: void
	 */
	public void initData() {
		for (int i = 0; i < 30; i++) {
			list.add("name - " + i);
		}

		// ���ù������� ����ʱ�ر�ɾ����
		mainListView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
					// �����ֱ����������Ҫ�ر��Ѿ��򿪵�ɾ����
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
	 * @Description:��������
	 * @return: void
	 */
	public void initAdapter() {
		mainListView.setAdapter(new SlideAdapter(this, list));
	}

}
