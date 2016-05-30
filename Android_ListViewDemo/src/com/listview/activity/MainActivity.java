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
 * @Description:ListView的拓展
 * @author: iamxiarui@foxmail.com
 * @date: 2016年5月30日 下午2:15:46
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
	 * @Description:初始化View
	 * @return: void
	 */
	private void initView() {
		mainListView = (ListView) findViewById(R.id.lv_main);
	}

	/**
	 * @Title: initData
	 * @Description:初始化列表数据
	 * @return: void
	 */
	private void initData() {
		listData = new ArrayList<String>();
		for (int i = 0; i < 30; i++) {
			listData.add("这是第 " + i + " 条数据");
		}
	}

	/**
	 * @Title: initAdapter
	 * @Description:绑定适配器，并更改自带主题样式
	 * @return: void
	 */
	private void initAdapter() {

		// 更换系统默认ListView主题的样式
//		mainListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData) {
//			@Override
//			public View getView(int position, View convertView, ViewGroup parent) {
//				// 这里我们知道，item中只有一个TextView，可以直接写
//				TextView textView = (TextView) super.getView(position, convertView, parent);
//				textView.setTextColor(Color.BLUE);
//				textView.setTextSize(36);
//				return textView;
//			}
//		});

		// 多样式ListView
		// mainListView.setAdapter(new MyListAdapter(this, listData));

		// ViewHolder的使用
		mainListView.setAdapter(new HolderAdapter(this, listData));
	}
}
