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
 * @Description: ˺�·�Demo
 * @author: iamxiarui@foxmail.com
 * @date: 2016��5��4�� ����11:28:59
 */
public class MainActivity extends Activity {
	private ImageView afterImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		afterImage = (ImageView) findViewById(R.id.image_after);

		// ��ԭͼת��ΪBitmap����
		Bitmap preBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_pre);

		// ����ģ��
		final Bitmap afterBitmap = Bitmap.createBitmap(preBitmap.getWidth(), preBitmap.getHeight(),
				preBitmap.getConfig());

		// ��������
		Canvas canvas = new Canvas(afterBitmap);

		// ��������
		Paint paint = new Paint();

		// ��ʼ��ͼ
		canvas.drawBitmap(preBitmap, new Matrix(), paint);

		afterImage.setImageBitmap(afterBitmap);

		// �����¼�
		afterImage.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
				// �ƶ�������
				case MotionEvent.ACTION_MOVE:

					// ��Χ�Ӵ�
					for (int i = 0; i < 40; i++) {
						for (int j = 0; j < 40; j++) {
							// ��Բ
							if (Math.sqrt(i * i + j * j) < 20) {
								try {
									// ������������͸��
									afterBitmap.setPixel((int) event.getX() + i, (int) event.getY() + j,
											Color.TRANSPARENT);
								} catch (Exception e) {
								}
							}
						}
					}
					// ����ͼƬ
					afterImage.setImageBitmap(afterBitmap);
					break;
				}
				return true;
			}
		});
	}
}
