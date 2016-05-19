package com.headparallax.activity;

import com.headparallax.R;
import com.headparallax.view.ParallaxListView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private ParallaxListView mainList;
	private String[] indexArr = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
			"R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mainList = (ParallaxListView) findViewById(R.id.plv_main);

		View headerView = View.inflate(this, R.layout.list_header, null);
		ImageView imageView = (ImageView) headerView.findViewById(R.id.iv_head);
		mainList.setParallaxImageView(imageView);
		mainList.addHeaderView(headerView);

		mainList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, indexArr));
	}

}
