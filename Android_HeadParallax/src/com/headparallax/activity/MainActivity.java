package com.headparallax.activity;

import com.headparallax.R;
import com.headparallax.view.ParallaxListView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private ParallaxListView mainListView;
	private String[] indexArr = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
			"R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();

		initAdapter();
	}

	/**
	 * @Title: initView
	 * @Description:��ʼ��View
	 * @return: void
	 */
	private void initView() {
		mainListView = (ParallaxListView) findViewById(R.id.plv_main);
		// ���ض�����Ӱ
		mainListView.setOverScrollMode(ListView.OVER_SCROLL_NEVER);

		// ����б�ͷ����
		View headerView = View.inflate(this, R.layout.list_header, null);
		ImageView imageView = (ImageView) headerView.findViewById(R.id.iv_head);
		mainListView.setParallaxImageView(imageView);
		mainListView.addHeaderView(headerView);
	}

	/**
	 * @Title: initAdapter
	 * @Description:���������
	 * @return: void
	 */
	private void initAdapter() {
		mainListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, indexArr) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				TextView textView = (TextView) super.getView(position, convertView, parent);
				// �����ı�����
				textView.setGravity(Gravity.CENTER);
				return textView;
			}
		});
	}

}
