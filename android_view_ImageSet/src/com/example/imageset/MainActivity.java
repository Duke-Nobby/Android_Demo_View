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
	//�������ر�־λ
	private static final int imasel = 1;     //ѡ��ͼƬ
	private static final int imacut = 2;     //�ü�ͼƬ��

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
			//ָ����Ƭ�����ֻ���Ļ�Ĵ�С��ʾ
			if(requestCode==imasel){
				Uri uri = data.getData();   //���ͼƬ��·��
				int width = getWindowManager().getDefaultDisplay().getWidth(); //�����Ļ�Ŀ����߶�
				int height = getWindowManager().getDefaultDisplay().getHeight()/2; 
				try {
					BitmapFactory.Options factory = new BitmapFactory.Options();
					factory.inJustDecodeBounds= true;
					Bitmap bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, factory);
					//��ͼƬ�߶ȿ�Ƚ����ֻ���Ļ������
					int  Width = (int)Math.ceil(factory.outWidth/(float)width);
					int  Height = (int)Math.ceil(factory.outHeight/(float)height);  //�������1���ʾ�߿�ȴ����ֻ���Ļ�ĸ߿��
					if(Width>1||Height>1){
						if(Width>Height){
							factory.inSampleSize = Width;
						}else {
							factory.inSampleSize = Height;
						}
					}
					factory.inJustDecodeBounds = false;
					//��ͼƬ������������
					bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, factory);
					imageview.setImageBitmap(bmp);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}else if(requestCode==imacut){    //�ü���Ƭ
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
		//����ͼƬ���������
		intent.setType("image/*");    //��ȡ����ͼƬ����
		intent.putExtra("crop", "true");    //����ѡ��ͼƬ����
		intent.putExtra("aspectX", 1);      //�ü�����ı���  1:1
		intent.putExtra("aspectY", 1);      
		intent.putExtra("outputX", 80);    //����ͼƬ�Ĵ�С
		intent.putExtra("outputY", 80); 
		intent.putExtra("return-data", true);
		return intent;
	}



	//����˵� ��ʾ����ѡ��ķ���
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0,1,1,R.string.exit);
		menu.add(0,2,2,R.string.about);
		return super.onCreateOptionsMenu(menu);
	}
	//����˵�ѡ���е�ĳһ�� �����ķ�Ӧ�ķ���
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId()==1)
		{
			finish();
		}else if(item.getItemId()==2){
			Toast.makeText(this, "�汾�� 1.0.1", Toast.LENGTH_SHORT).show();
		}
		return super.onOptionsItemSelected(item);
	}

}
