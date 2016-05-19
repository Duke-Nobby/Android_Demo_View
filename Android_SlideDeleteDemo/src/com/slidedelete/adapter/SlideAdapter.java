package com.slidedelete.adapter;

import java.util.ArrayList;

import com.slidedelete.activity.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SlideAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<String> list;

	public SlideAdapter(Context context, ArrayList<String> list) {
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_list, null);
		}
		ViewHolder viewHolder = ViewHolder.getViewHolder(convertView);

		viewHolder.nameText.setText(list.get(position));

		return convertView;
	}

	static class ViewHolder {
		TextView nameText, deleteText;

		public ViewHolder(View convertView) {
			nameText = (TextView) convertView.findViewById(R.id.tv_name);
			deleteText = (TextView) convertView.findViewById(R.id.tv_delete);
		}

		public static ViewHolder getViewHolder(View convertView) {
			ViewHolder viewHolder = (ViewHolder) convertView.getTag();
			if (viewHolder == null) {
				viewHolder = new ViewHolder(convertView);
				convertView.setTag(viewHolder);
			}
			return viewHolder;
		}
	}

}
