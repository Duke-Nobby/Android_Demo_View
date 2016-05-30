package com.listview.adapter;

import java.util.ArrayList;

import com.listview.activity.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @ClassName: HolderAdapter
 * @Description:主要介绍ViewHolder的使用
 * @author: iamxiarui@foxmail.com
 * @date: 2016年5月30日 下午3:24:58
 */
public class HolderAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<String> list;

	public HolderAdapter(Context context, ArrayList<String> list) {
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
	 * @ClassName: ViewHolder
	 * @Description:定义ViewHolder类
	 * @author: iamxiarui@foxmail.com
	 * @date: 2016年5月30日 下午3:28:05
	 */
	private static class ViewHolder {
		TextView itemText;

		// 构造函数中就初始化View
		public ViewHolder(View convertView) {
			itemText = (TextView) convertView.findViewById(R.id.tv_item);
		}

		// 得到一个ViewHolder
		public static ViewHolder getViewHolder(View convertView) {
			ViewHolder viewHolder = (ViewHolder) convertView.getTag();
			if (viewHolder == null) {
				viewHolder = new ViewHolder(convertView);
				convertView.setTag(viewHolder);
			}
			return viewHolder;
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_list, null);
		}
		// 得到一个ViewHolder
		viewHolder = ViewHolder.getViewHolder(convertView);
		viewHolder.itemText.setText(list.get(position));

		return convertView;
	}
	
}
