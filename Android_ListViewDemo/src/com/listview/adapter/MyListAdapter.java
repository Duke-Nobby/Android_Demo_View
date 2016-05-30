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
 * @Description:�Զ���������ʽ��������
 * @author: iamxiarui@foxmail.com
 * @date: 2016��5��30�� ����2:51:00
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
	 * @Description:������ʽ
	 * @return: ��ʽ����Ŀ
	 */
	@Override
	public int getViewTypeCount() {
		return 2;
	}

	/**
	 * @Title: getItemViewType
	 * @Description:ÿ��item����ʽ
	 * @param position��itemλ��
	 * @return: ��ʽ
	 */
	@Override
	public int getItemViewType(int position) {
		// Ϊ�˷�����������Ǹ�����ż����������ʽ��ע���0��ʼ��
		if (position % 2 != 0) {
			// �������0
			return 0;
		} else {
			// ż�����0
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
		// �����������
		if (getItemViewType(position) == 0) {
			TextView itemText = (TextView) view.findViewById(R.id.tv_item);
			itemText.setText(list.get(position));
			itemText.setTextColor(Color.BLUE);
		}
		// �����ż����
		else if (getItemViewType(position) == 1) {
			TextView itemText = (TextView) view.findViewById(R.id.tv_item);
			itemText.setText(list.get(position));
			itemText.setTextColor(Color.GRAY);
		}
		return view;
	}

}
