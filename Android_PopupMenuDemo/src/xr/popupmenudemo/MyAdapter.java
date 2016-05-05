package xr.popupmenudemo;

import java.util.ArrayList;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import xr.popmenudemo.R;

/**
 * @ClassName: MyAdapter
 * @Description:创建ListView的适配器
 * @author: iamxiarui@foxmail.com
 * @date: 2016年5月5日 下午12:11:04
 */
public class MyAdapter extends BaseAdapter {

	private ArrayList<String> listData;

	public MyAdapter(ArrayList<String> listData) {
		this.listData = listData;
	}

	@Override
	public int getCount() {
		return listData.size();
	}

	@Override
	public Object getItem(int position) {
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view;
		if (convertView == null) {
			view = View.inflate(parent.getContext(), R.layout.item_number, null);
		} else {
			view = convertView;
		}

		TextView numText = (TextView) view.findViewById(R.id.tv_number);
		numText.setText(listData.get(position));

		view.findViewById(R.id.ib_delete).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				listData.remove(position);
				notifyDataSetChanged();

				if (listData.size() == 0) {
					// 如果删除的是最后一条, 隐藏popupwindow
					MainActivity.popupWindow.dismiss();
				}
			}
		});
		return view;
	}

}
