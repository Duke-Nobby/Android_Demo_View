package xr.listview.activity;

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

		XrListAdapter listAdapter = new XrListAdapter(thisContext);

		listView.setAdapter(listAdapter);

	}

}
