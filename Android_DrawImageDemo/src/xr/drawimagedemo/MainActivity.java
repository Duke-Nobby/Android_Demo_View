package xr.drawimagedemo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;

/**
 * @ClassName: MainActivity
 * @Description: �����尸��
 * @author: iamxiarui@foxmail.com
 * @date: 2016��5��3�� ����10:22:33
 */
public class MainActivity extends Activity implements OnClickListener, OnTouchListener {

	private Button colorButton, boldButton, saveButton;
	private ImageView drawImage;
	private Bitmap initBitmap, modelBitmap;
	private Canvas canvas;
	private Paint paint;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		InitView();

		createDraw();

	}

	/**
	 * @Title: InitView
	 * @Description:��ʼ��View
	 * @return: void
	 */
	private void InitView() {
		colorButton = (Button) findViewById(R.id.button_color);
		boldButton = (Button) findViewById(R.id.button_bold);
		saveButton = (Button) findViewById(R.id.button_save);
		drawImage = (ImageView) findViewById(R.id.image_draw);

		colorButton.setOnClickListener(this);
		boldButton.setOnClickListener(this);
		saveButton.setOnClickListener(this);
		drawImage.setOnTouchListener(this);
	}

	/**
	 * @Title: createDraw
	 * @Description:��������ʼ���������
	 * @return: void
	 */
	private void createDraw() {
		// ��ԭʼ����ת����Bitmap����
		initBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background);
		// ����ԭʼ�����Ļ����ϴ���ģ��
		modelBitmap = Bitmap.createBitmap(initBitmap.getWidth(), initBitmap.getHeight(), initBitmap.getConfig());
		// ��������
		canvas = new Canvas(modelBitmap);
		// ��������
		paint = new Paint();
		// ��ʼ����
		canvas.drawBitmap(initBitmap, new Matrix(), paint);

		// ��ģ����ʾ��ImageView��
		drawImage.setImageBitmap(modelBitmap);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_color:
			// ���û�����ɫ
			paint.setColor(Color.WHITE);
			break;

		case R.id.button_bold:
			// ���û��ʵĿ��
			paint.setStrokeWidth(15);
			break;

		case R.id.button_save:
			try {
				// ���浽SD����
				File file = new File(Environment.getExternalStorageDirectory().getPath(), "test.png");
				FileOutputStream fos = new FileOutputStream(file);
				// ��һ��������ѹ����ʽ �ڶ�����ѹ������ PNG�����ѹ������ ����������Ϊ �����
				modelBitmap.compress(CompressFormat.PNG, 100, fos);
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		// ��ʼ���������
		int startX = 0;
		int startY = 0;

		// �õ������¼�
		int action = event.getAction();

		switch (action) {
		// �����¼�
		case MotionEvent.ACTION_DOWN:
			// ��ȡ��������
			startX = (int) event.getX();
			startY = (int) event.getY();
			break;
		// �ƶ��¼�
		case MotionEvent.ACTION_MOVE:
			// ��ȡֹͣ������
			int stopX = (int) event.getX();
			int stopY = (int) event.getY();

			// ����
			canvas.drawLine(startX, startY, stopX, stopY, paint);

			// ����һ���������
			startX = stopX;
			startY = stopY;

			// ����UI
			drawImage.setImageBitmap(modelBitmap);
			break;
		}

		// һ��Ҫ����true,����һֱ����
		return true;
	}

}
