package com.listview.adapter;

import java.util.ArrayList;

import com.listview.activity.R;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @ClassName: MyListAdapter
 * @Description:自定义两种样式的适配器
 * @author: iamxiarui@foxmail.com
 * @date: 2016年5月30日 下午2:51:00
 */
public class MyListAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<String> list;

	public MyListAdapter(Context context, ArrayList<String> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {

		return list.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	/**
	 * @Title: getViewTypeCount
	 * @Description:两种样式
	 * @return: 样式的数目
	 */
	@Override
	public int getViewTypeCount() {
		return 2;
	}

	/**
	 * @Title: getItemViewType
	 * @Description:每个item的样式
	 * @param position：item位置
	 * @return: 样式
	 */
	@Override
	public int getItemViewType(int position) {
		// 为了方便起见，我们根据奇偶项来区别样式，注意从0开始数
		if (position % 2 != 0) {
			// 奇数项返回0
			return 0;
		} else {
			// 偶数项返回0
			return 1;
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_list, null);
		}
		view = convertView;
		// 如果是奇数项
		if (getItemViewType(position) == 0) {
			TextView itemText = (TextView) view.findViewById(R.id.tv_item);
			itemText.setText(list.get(position));
			itemText.setTextColor(Color.BLUE);
		}
		// 如果是偶数项
		else if (getItemViewType(position) == 1) {
			TextView itemText = (TextView) view.findViewById(R.id.tv_item);
			itemText.setText(list.get(position));
			itemText.setTextColor(Color.GRAY);
		}
		return view;
	}

}
