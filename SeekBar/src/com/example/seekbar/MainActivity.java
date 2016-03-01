package com.example.seekbar;

import javax.crypto.SecretKey;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnSeekBarChangeListener,OnRatingBarChangeListener{
	private TextView text1,text2,text3;
	private SeekBar seek1,seek2;
	private RatingBar ratBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		text1 = (TextView)findViewById(R.id.text1);
		text2 = (TextView)findViewById(R.id.text2);
		text3 = (TextView)findViewById(R.id.text3);
		seek1 = (SeekBar)findViewById(R.id.seek1);
		seek2 = (SeekBar)findViewById(R.id.seek2);
		ratBar = (RatingBar)findViewById(R.id.ratBar);
		seek1.setOnSeekBarChangeListener(this);
		seek2.setOnSeekBarChangeListener(this);
		ratBar.setOnRatingBarChangeListener(this);
	}
	//滑动操作时触发的事件
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		// TODO Auto-generated method stub
		if(seekBar.getId()==R.id.seek1){
			text1.setText("seek1当前位置"+progress);
		}
		else if (seekBar.getId()==R.id.seek2){
			text2.setText("seek2当前位置"+progress);
		}
	}
	//开始滑动
	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		if(seekBar.getId()==R.id.seek1){
			text1.setText("seek1开始滑动了");
		}
		else if (seekBar.getId()==R.id.seek2){
			text2.setText("seek2开始滑动");
		}
	}
	//停止滑动
	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		if(seekBar.getId()==R.id.seek1){
			text1.setText("seek1停止滑动了");
		}
		else if (seekBar.getId()==R.id.seek2){
			text2.setText("seek2停止滑动了");
		}
	}
	
	@Override
	public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
		// TODO Auto-generated method stub
		text3.setText("当前评分为 : "+ rating);
	}

}
