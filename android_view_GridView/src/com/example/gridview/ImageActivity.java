package com.example.gridview;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class ImageActivity extends Activity implements OnSeekBarChangeListener{

	private ImageView imageview;
	private SeekBar seekbar1,seekbar2;
	private TextView text1,text2;
	private int[] image = new int[] { R.drawable.pro1, R.drawable.pro2, R.drawable.pro3, R.drawable.pro4,
			R.drawable.pro5, R.drawable.pro6, R.drawable.pro7, R.drawable.pro8, R.drawable.pro9, R.drawable.pro10,
			R.drawable.pro11, R.drawable.pro12};
	private int minWidth = 80;
	private Matrix ma = new Matrix();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.image);
		Intent intent = getIntent();
		String str = intent.getStringExtra("image");
		int i = Integer.parseInt(str);
		imageview = (ImageView)findViewById(R.id.image);
		imageview.setImageResource(image [i]);
		seekbar1 = (SeekBar)findViewById(R.id.seekbar1);
		seekbar2 = (SeekBar)findViewById(R.id.seekbar2);
		text1 = (TextView)findViewById(R.id.text1);
		text2 = (TextView)findViewById(R.id.text2);
		seekbar1.setOnSeekBarChangeListener(this);
		seekbar2.setOnSeekBarChangeListener(this);

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		seekbar1.setMax(dm.widthPixels-minWidth);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		// TODO Auto-generated method stub
		if(seekBar.getId()==R.id.seekbar1){
			int newWidth = progress+minWidth;
			int newHeigth = (int)(newWidth);  
			imageview.setLayoutParams(new LinearLayout.LayoutParams(newWidth, newHeigth));
		}
		else if(seekBar.getId()==R.id.seekbar2){
			Intent intent = getIntent();
			String str = intent.getStringExtra("image");
			int i = Integer.parseInt(str);
			Bitmap bitmap = ((BitmapDrawable)(getResources().getDrawable(image [i]))).getBitmap();
			ma.setRotate(progress);  //设置翻转的角度
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),bitmap.getHeight(),ma,true);
			imageview.setImageBitmap(bitmap);

		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

}

