package xr.loadbigimage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

/**
 * @ClassName: MainActivity
 * @Description: 缩放图片
 * @author: iamxiarui@foxmail.com
 * @date: 2016年5月3日 下午7:59:16
 */
public class MainActivity extends Activity implements OnClickListener {

	private Button loadButton;
	private ImageView loadImage;
	private int screenWidth;
	private int screenHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 初始化View
		InitView();
		// 得到屏幕的尺寸
		getScreenSize();
	}

	@Override
	public void onClick(View v) {

		Bitmap bitmap = getImageBitmap();
		// 将图片显示在ImageView上
		loadImage.setImageBitmap(bitmap);

	}

	/**
	 * @Title: InitView
	 * @Description:初始化View
	 * @return: void
	 */
	private void InitView() {
		loadButton = (Button) findViewById(R.id.button_load);
		loadImage = (ImageView) findViewById(R.id.image_load);
		loadButton.setOnClickListener(this);
	}

	/**
	 * @Title: getScreenSize
	 * @Description:得到手机屏幕的尺寸
	 * @return: void
	 */
	@SuppressWarnings("deprecation")
	private void getScreenSize() {
		// 获得WindowManager实例
		WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
		screenWidth = wm.getDefaultDisplay().getWidth();
		screenHeight = wm.getDefaultDisplay().getHeight();

		System.out.println("手机的长宽为 ： " + screenWidth + " - " + screenHeight);
	}

	/**
	 * @Title: getImageBitmap
	 * @Description:得到图片的Bitmap对象
	 * @return: Bitmap
	 */
	private Bitmap getImageBitmap() {
		// 得到图片的长宽
		BitmapFactory.Options options = new Options();

		// 返回null 不去真正解析位图 但是能返回图片的信息(宽和高)
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(getResources(), R.drawable.image_test, options);

		int imageWidth = options.outWidth;
		int imageHeight = options.outHeight;

		System.out.println("图片的长宽为 ： " + imageWidth + " - " + imageHeight);

		// 设置默认的缩放比
		int scale = 1;
		// 计算长宽缩放比
		int scaleX = imageWidth / screenWidth;
		int scaleY = imageHeight / screenHeight;

		// 那个比例大就用哪个比例
		if (scaleX > scaleY && scaleX > scale) {
			scale = scaleX;
		}
		if (scaleY > scaleX && scaleY > scale) {
			scale = scaleY;
		}

		System.out.println("缩放比为 ： " + scale);

		// 真正解析位图
		options.inJustDecodeBounds = false;
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_test, options);
		return bitmap;
	}

}
