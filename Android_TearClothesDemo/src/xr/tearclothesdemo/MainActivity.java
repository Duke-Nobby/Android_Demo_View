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

public class MainActivity extends Activity {
	private ImageView afterImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		afterImage = (ImageView) findViewById(R.id.image_after);

		Bitmap preBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_pre);

		final Bitmap afterBitmap = Bitmap.createBitmap(preBitmap.getWidth(), preBitmap.getHeight(),
				preBitmap.getConfig());

		Canvas canvas = new Canvas(afterBitmap);

		Paint paint = new Paint();

		canvas.drawBitmap(preBitmap, new Matrix(), paint);

		afterImage.setImageBitmap(afterBitmap);

		afterImage.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
				case MotionEvent.ACTION_MOVE:

					for (int i = 0; i < 40; i++) {
						for (int j = 0; j < 40; j++) {
							if (Math.sqrt(i * i + j * j) < 20) {
								try {
									afterBitmap.setPixel((int) event.getX() + i, (int) event.getY() + j,
											Color.TRANSPARENT);
								} catch (Exception e) {
								}
							}
						}
					}
					afterImage.setImageBitmap(afterBitmap);
					break;
				}
				return true;
			}
		});
	}
}
