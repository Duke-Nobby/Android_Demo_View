package xr.listview.activity;
/*
 * ListView的使用 和 注意事项
 * 
 * 
 * */

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import xr.listview.adapter.XrListAdapter;

public class MainActivity extends Activity {

	private ListView listView;
	private Context thisContext = MainActivity.this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView = (ListView) findViewById(R.id.listView);

		// 创建一个List适配器对象
		XrListAdapter listAdapter = new XrListAdapter(thisContext);

		// 让List绑定适配器
		listView.setAdapter(listAdapter);

	}

}
