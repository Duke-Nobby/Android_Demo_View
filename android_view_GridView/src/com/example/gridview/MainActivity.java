package com.example.gridview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity implements OnItemSelectedListener, OnItemClickListener {

	private ImageView imageview;
	private GridView gridview;
	private int[] image = new int[] { R.drawable.pro1, R.drawable.pro2, R.drawable.pro3, R.drawable.pro4,
			R.drawable.pro5, R.drawable.pro6, R.drawable.pro7, R.drawable.pro8, R.drawable.pro9, R.drawable.pro10,
			R.drawable.pro11, R.drawable.pro12};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		imageview = (ImageView) findViewById(R.id.imageview);
		gridview = (GridView) findViewById(R.id.girdview);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(); // ����һ��list����
		for (int i = 0; i < image.length; i++) { // ͨ��forѭ����ͼƬ����map�У�����ӵ�list������
			Map<String, Object> pic = new HashMap<String, Object>();
			pic.put("image", image[i]);
			list.add(pic); // ��map������ӵ�list������
		}
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.pic, new String[] { "image" },
				new int[] { R.id.imageview });
		gridview.setAdapter(simpleAdapter);
		gridview.setOnItemClickListener(this);
		gridview.setOnItemSelectedListener(this);
		imageview.setImageResource(image[0]);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		imageview.setImageResource(image[position]);   
		Intent intent = new Intent();
		String str = Integer.toString(position);
		intent.putExtra("image", str);
		intent.setClass(MainActivity.this,ImageActivity.class);
		MainActivity.this.startActivity(intent);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		imageview.setImageResource(image[position]);
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

}
