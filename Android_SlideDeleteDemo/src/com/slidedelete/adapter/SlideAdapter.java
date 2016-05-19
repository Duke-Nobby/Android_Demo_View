package com.slidedelete.adapter;

import java.util.ArrayList;

import com.slidedelete.activity.R;
import com.slidedelete.utils.SlideLayoutManager;
import com.slidedelete.view.SlideLayout;
import com.slidedelete.view.SlideLayout.OnSlideStateChangeListener;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @ClassName: SlideAdapter
 * @Description:�����б�������
 * @author: iamxiarui@foxmail.com
 * @date: 2016��5��19�� ����7:18:24
 */
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_list, null);
		}
		final ViewHolder viewHolder = ViewHolder.getViewHolder(convertView);

		viewHolder.nameText.setText(list.get(position));

		// ���ɾ����Ŀ
		viewHolder.deleteText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ɾ����Ŀ
				list.remove(position);
				// ˢ��
				notifyDataSetChanged();
				// �رջ���
				SlideLayoutManager.getInstance().closeCurrentLayout();
			}
		});

		// ����ö���Ŀ
		viewHolder.topText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ��ɾ����ǰ��Ŀ
				list.remove(position);
				// �ڽ���ǰ��Ŀ���ӵ��б���
				list.add(0,viewHolder.nameText.getText().toString());
				// ˢ��
				notifyDataSetChanged();
				// �رջ���
				SlideLayoutManager.getInstance().closeCurrentLayout();
			}
		});

		return convertView;
	}

	/**
	 * @ClassName: ViewHolder
	 * @Description:����һ��ViewHolder
	 */

	static class ViewHolder {
		TextView nameText, deleteText, topText;

		public ViewHolder(View convertView) {
			nameText = (TextView) convertView.findViewById(R.id.tv_name);
			deleteText = (TextView) convertView.findViewById(R.id.tv_delete);
			topText = (TextView) convertView.findViewById(R.id.tv_top);
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
