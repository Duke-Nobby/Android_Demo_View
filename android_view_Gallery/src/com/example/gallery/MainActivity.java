package com.example.gallery;



import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher.ViewFactory;

public class MainActivity extends Activity implements ViewFactory{

	private Gallery gallery;
	private int[] image = new int[] { R.drawable.ima1, R.drawable.ima2, R.drawable.ima3, R.drawable.ima4,
			R.drawable.ima5, R.drawable.ima6, R.drawable.ima7, R.drawable.ima8, R.drawable.ima9, R.drawable.ima10,
			R.drawable.ima11, R.drawable.ima12};
	private ImageSwitcher imageSwitcher;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		gallery = (Gallery)findViewById(R.id.gallery);
		imageSwitcher = (ImageSwitcher)findViewById(R.id.imageSwitcher);
		//		imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in)); //设置淡入淡出效果
		//		imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
		imageSwitcher.setFactory(this);
		BaseAdapter adapter = new BaseAdapter() {
			private ImageView imageView;
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				imageView = new ImageView (MainActivity.this);
				if(convertView == null){
					imageView.setScaleType(ImageView.ScaleType.FIT_XY);  //设置缩放方式
					TypedArray typedArray = obtainStyledAttributes(R.styleable.Gallery);
					imageView.setBackgroundResource(typedArray.getResourceId(R.styleable.Gallery_android_galleryItemBackground, 0));
					imageView.setPadding(5, 5, 5, 5);                   //设置内边距
				}else {
					imageView = (ImageView)convertView;

				}
				imageView.setImageResource(image[position]);
				return imageView;
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return image.length;
			}
		};
		gallery.setAdapter(adapter);
		gallery.setSelection(image.length-12);
		gallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				imageSwitcher.setImageResource(image[position]);
			}

		});
	}

	@Override
	public View makeView() {
		// TODO Auto-generated method stub
		ImageView imageView = new ImageView (MainActivity.this);
		imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		imageView.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		return imageView;
	}
}
