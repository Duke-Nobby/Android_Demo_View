package com.example.imageset;



import android.app.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	private Button button1,button2;
	private ImageView imageview;
	//方法返回标志位
	private static final int imasel = 1;     //选择图片
	private static final int imacut = 2;     //裁剪图片·

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		button1 = (Button)findViewById(R.id.button1);
		button2 = (Button)findViewById(R.id.button2);
		imageview = (ImageView)findViewById(R.id.imageview);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
	}

@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==RESULT_OK){
			//指定照片按照手机屏幕的大小显示
			if(requestCode==imasel){
				Uri uri = data.getData();   //获得图片的路径
				int width = getWindowManager().getDefaultDisplay().getWidth(); //获得屏幕的宽度与高度
				int height = getWindowManager().getDefaultDisplay().getHeight()/2; 
				try {
					BitmapFactory.Options factory = new BitmapFactory.Options();
					factory.inJustDecodeBounds= true;
					Bitmap bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, factory);
					//对图片高度宽度进行手机屏幕的适配
					int  Width = (int)Math.ceil(factory.outWidth/(float)width);
					int  Height = (int)Math.ceil(factory.outHeight/(float)height);  //如果大于1则表示高宽度大于手机屏幕的高宽度
					if(Width>1||Height>1){
						if(Width>Height){
							factory.inSampleSize = Width;
						}else {
							factory.inSampleSize = Height;
						}
					}
					factory.inJustDecodeBounds = false;
					//对图片进行适屏操作
					bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, factory);
					imageview.setImageBitmap(bmp);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}else if(requestCode==imacut){    //裁剪照片
				Bitmap bmp = data.getParcelableExtra("data");
				imageview.setImageBitmap(bmp);
			}
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.button1){
			Intent intent1 = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent1, imasel);
		}
		else if(v.getId()==R.id.button2) {
			Intent intent2 = getImageClipIntent();
			startActivityForResult(intent2, imacut);
		}
	}


	private Intent getImageClipIntent() {
		// TODO Auto-generated method stub
		Intent intent = new Intent (Intent.ACTION_GET_CONTENT,null);
		//设置图片的相关属性
		intent.setType("image/*");    //获取任意图片类型
		intent.putExtra("crop", "true");    //滑动选中图片区域
		intent.putExtra("aspectX", 1);      //裁剪区域的比例  1:1
		intent.putExtra("aspectY", 1);      
		intent.putExtra("outputX", 80);    //保存图片的大小
		intent.putExtra("outputY", 80); 
		intent.putExtra("return-data", true);
		return intent;
	}



	//点击菜单 显示各种选项的方法
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0,1,1,R.string.exit);
		menu.add(0,2,2,R.string.about);
		return super.onCreateOptionsMenu(menu);
	}
	//点击菜单选项中的某一项 做出的反应的方法
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId()==1)
		{
			finish();
		}else if(item.getItemId()==2){
			Toast.makeText(this, "版本号 1.0.1", Toast.LENGTH_SHORT).show();
		}
		return super.onOptionsItemSelected(item);
	}

}
