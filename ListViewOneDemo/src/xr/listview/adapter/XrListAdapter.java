package xr.listview.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class XrListAdapter extends BaseAdapter {

	private Context context;

	public XrListAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return 20;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		TextView textView = null;
		if (convertView != null) {
			textView = (TextView) convertView;
		} else {
			textView = new TextView(context);
		}

		textView.setText("µÚ " + position + "Ìõ");
		textView.setTextSize(30);

		return textView;
	}

}
