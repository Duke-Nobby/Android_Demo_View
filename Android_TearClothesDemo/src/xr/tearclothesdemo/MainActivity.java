package xr.tearclothesdemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

/**
 * @ClassName: MainActivity
 * @Description: 撕衣服Demo
 * @author: iamxiarui@foxmail.com
 * @date: 2016年5月4日 上午11:28:59
 */
public class MainActivity extends Activity {
	private ImageView afterImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		afterImage = (ImageView) findViewById(R.id.image_after);

		// 将原图转化为Bitmap对象
		Bitmap preBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_pre);

		// 创建模板
		final Bitmap afterBitmap = Bitmap.createBitmap(preBitmap.getWidth(), preBitmap.getHeight(),
				preBitmap.getConfig());

		// 创建画布
		Canvas canvas = new Canvas(afterBitmap);

		// 创建画笔
		Paint paint = new Paint();

		// 开始画图
		canvas.drawBitmap(preBitmap, new Matrix(), paint);

		afterImage.setImageBitmap(afterBitmap);

		// 触摸事件
		afterImage.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
				// 移动过程中
				case MotionEvent.ACTION_MOVE:

					// 范围加大
					for (int i = 0; i < 40; i++) {
						for (int j = 0; j < 40; j++) {
							// 画圆
							if (Math.sqrt(i * i + j * j) < 20) {
								try {
									// 将触摸区域变成透明
									afterBitmap.setPixel((int) event.getX() + i, (int) event.getY() + j,
											Color.TRANSPARENT);
								} catch (Exception e) {
								}
							}
						}
					}
					// 设置图片
					afterImage.setImageBitmap(afterBitmap);
					break;
				}
				return true;
			}
		});
	}
}
