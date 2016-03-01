package com.example.autotext;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView text1,text2;
	private AutoCompleteTextView auto;
	private MultiAutoCompleteTextView mulauto;
	private String [] list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		text1 = (TextView)findViewById(R.id.text1);
		text2 = (TextView)findViewById(R.id.text2);
		auto = (AutoCompleteTextView)findViewById(R.id.auto);
		mulauto = (MultiAutoCompleteTextView)findViewById(R.id.mulauto);
		list = new String [] {"安庆","安庆师范","安庆师范学院","安庆师范大学"};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_dropdown_item_1line, list);
		auto.setAdapter(adapter);
		mulauto.setAdapter(adapter);
		mulauto.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
	}

}
