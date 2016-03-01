package com.example.progressbar;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends Activity implements OnClickListener {
	private Button button1,button2;
	private ProgressBar pro;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_PROGRESS);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.main);
		button1 = (Button)findViewById(R.id.button1);
		button2 = (Button)findViewById(R.id.button2);
		pro = (ProgressBar)findViewById(R.id.pro);
		setProgress(3500);
		setProgressBarIndeterminate(true);
		setProgressBarVisibility(true);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.button1){
			pro.setProgress((int)(pro.getProgress()*2));
		}
		
		if(v.getId()==R.id.button2){
			pro.setProgress((int)(pro.getProgress()*0.5));
		}
	}

}
