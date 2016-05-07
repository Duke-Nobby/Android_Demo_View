package xr.refreshlist.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @ClassName: RefreshListAdapter
 * @Description:下拉刷新列表适配器
 * @author: iamxiarui@foxmail.com
 * @date: 2016年5月7日 下午3:16:28
 */
public class RefreshListAdapter extends BaseAdapter {

	private ArrayList<String> dataList;

	public RefreshListAdapter(ArrayList<String> dataList) {
		this.dataList = dataList;
	}

	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		TextView listText = new TextView(parent.getContext());

		listText.setText(dataList.get(position));
		listText.setTextSize(25);

		return listText;
	}

}
