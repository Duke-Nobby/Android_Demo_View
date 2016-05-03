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
 * @Description: 画画板案例
 * @author: iamxiarui@foxmail.com
 * @date: 2016年5月3日 下午10:22:33
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
	 * @Description:初始化View
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
	 * @Description:创建并初始化画板对象
	 * @return: void
	 */
	private void createDraw() {
		// 将原始背景转换成Bitmap对象
		initBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background);
		// 并在原始背景的基础上创建模板
		modelBitmap = Bitmap.createBitmap(initBitmap.getWidth(), initBitmap.getHeight(), initBitmap.getConfig());
		// 创建画布
		canvas = new Canvas(modelBitmap);
		// 创建画笔
		paint = new Paint();
		// 开始画画
		canvas.drawBitmap(initBitmap, new Matrix(), paint);

		// 将模板显示在ImageView上
		drawImage.setImageBitmap(modelBitmap);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_color:
			// 设置画笔颜色
			paint.setColor(Color.WHITE);
			break;

		case R.id.button_bold:
			// 设置画笔的宽度
			paint.setStrokeWidth(15);
			break;

		case R.id.button_save:
			try {
				// 保存到SD卡中
				File file = new File(Environment.getExternalStorageDirectory().getPath(), "test.png");
				FileOutputStream fos = new FileOutputStream(file);
				// 第一个参数是压缩格式 第二个是压缩质量 PNG则忽视压缩质量 第三个参数为 输出流
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

		// 初始化起点坐标
		int startX = 0;
		int startY = 0;

		// 得到触摸事件
		int action = event.getAction();

		switch (action) {
		// 按下事件
		case MotionEvent.ACTION_DOWN:
			// 获取按下坐标
			startX = (int) event.getX();
			startY = (int) event.getY();
			break;
		// 移动事件
		case MotionEvent.ACTION_MOVE:
			// 获取停止的坐标
			int stopX = (int) event.getX();
			int stopY = (int) event.getY();

			// 画线
			canvas.drawLine(startX, startY, stopX, stopY, paint);

			// 更新一下起点坐标
			startX = stopX;
			startY = stopY;

			// 更新UI
			drawImage.setImageBitmap(modelBitmap);
			break;
		}

		// 一定要返回true,才能一直监听
		return true;
	}

}
