package com.listview.activity;

import java.util.ArrayList;

import com.listview.adapter.HolderAdapter;
import com.listview.adapter.MyListAdapter;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @ClassName: MainActivity
 * @Description:ListView����չ
 * @author: iamxiarui@foxmail.com
 * @date: 2016��5��30�� ����2:15:46
 */
public class MainActivity extends Activity {

	private ListView mainListView;
	private ArrayList<String> listData;

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
	private void initView() {
		mainListView = (ListView) findViewById(R.id.lv_main);
	}

	/**
	 * @Title: initData
	 * @Description:��ʼ���б�����
	 * @return: void
	 */
	private void initData() {
		listData = new ArrayList<String>();
		for (int i = 0; i < 30; i++) {
			listData.add("���ǵ� " + i + " ������");
		}
	}

	/**
	 * @Title: initAdapter
	 * @Description:�����������������Դ�������ʽ
	 * @return: void
	 */
	private void initAdapter() {

		// ����ϵͳĬ��ListView�������ʽ
//		mainListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData) {
//			@Override
//			public View getView(int position, View convertView, ViewGroup parent) {
//				// ��������֪����item��ֻ��һ��TextView������ֱ��д
//				TextView textView = (TextView) super.getView(position, convertView, parent);
//				textView.setTextColor(Color.BLUE);
//				textView.setTextSize(36);
//				return textView;
//			}
//		});

		// ����ʽListView
		// mainListView.setAdapter(new MyListAdapter(this, listData));

		// ViewHolder��ʹ��
		mainListView.setAdapter(new HolderAdapter(this, listData));
	}
}
