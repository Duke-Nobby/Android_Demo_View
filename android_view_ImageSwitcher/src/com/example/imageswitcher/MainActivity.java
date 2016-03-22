package com.example.imageswitcher;



import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher.ViewFactory;

public class MainActivity extends Activity implements OnClickListener,ViewFactory{

	private Button button1,button2;
	private int index = 0;
	private ImageSwitcher imageswitcher;
	private int [] image = new int[]{R.drawable.ima2,R.drawable.ima3,R.drawable.ima4};
	private ImageView imageview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		button1 = (Button)findViewById(R.id.button1);
		button2 = (Button)findViewById(R.id.button2);
		imageswitcher = (ImageSwitcher)findViewById(R.id.imageswitcher);
		imageswitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));  //设置淡入淡出动画
		imageswitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		imageswitcher.setFactory(this);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0,1,1,R.string.exit);
		menu.add(0,2,2,R.string.about);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId()==1)
		{
			finish();

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public View makeView() {
		// TODO Auto-generated method stub
		imageview = new ImageView(MainActivity.this);
		imageview.setScaleType(ImageView.ScaleType.FIT_XY);  //设置保持纵横比居中缩放图片
		imageview.setLayoutParams(new ImageSwitcher.LayoutParams(200,200));
		return imageview;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.button1){
			if(index>0){
				index--;
			}
			else  {
				index = image.length-1;
			}
		}
		else if(v.getId()==R.id.button2){
			if(index<image.length-1){
				index++;
			}
			else {
				index=0;
			}
		}
		imageswitcher.setImageResource(image[index]);  //显示图像
	}
}
