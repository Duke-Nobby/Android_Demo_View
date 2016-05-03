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
 * @Description: ����ͼƬ
 * @author: iamxiarui@foxmail.com
 * @date: 2016��5��3�� ����7:59:16
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

		// ��ʼ��View
		InitView();
		// �õ���Ļ�ĳߴ�
		getScreenSize();
	}

	@Override
	public void onClick(View v) {

		Bitmap bitmap = getImageBitmap();
		// ��ͼƬ��ʾ��ImageView��
		loadImage.setImageBitmap(bitmap);

	}

	/**
	 * @Title: InitView
	 * @Description:��ʼ��View
	 * @return: void
	 */
	private void InitView() {
		loadButton = (Button) findViewById(R.id.button_load);
		loadImage = (ImageView) findViewById(R.id.image_load);
		loadButton.setOnClickListener(this);
	}

	/**
	 * @Title: getScreenSize
	 * @Description:�õ��ֻ���Ļ�ĳߴ�
	 * @return: void
	 */
	@SuppressWarnings("deprecation")
	private void getScreenSize() {
		// ���WindowManagerʵ��
		WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
		screenWidth = wm.getDefaultDisplay().getWidth();
		screenHeight = wm.getDefaultDisplay().getHeight();

		System.out.println("�ֻ��ĳ���Ϊ �� " + screenWidth + " - " + screenHeight);
	}

	/**
	 * @Title: getImageBitmap
	 * @Description:�õ�ͼƬ��Bitmap����
	 * @return: Bitmap
	 */
	private Bitmap getImageBitmap() {
		// �õ�ͼƬ�ĳ���
		BitmapFactory.Options options = new Options();

		// ����null ��ȥ��������λͼ �����ܷ���ͼƬ����Ϣ(��͸�)
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(getResources(), R.drawable.image_test, options);

		int imageWidth = options.outWidth;
		int imageHeight = options.outHeight;

		System.out.println("ͼƬ�ĳ���Ϊ �� " + imageWidth + " - " + imageHeight);

		// ����Ĭ�ϵ����ű�
		int scale = 1;
		// ���㳤�����ű�
		int scaleX = imageWidth / screenWidth;
		int scaleY = imageHeight / screenHeight;

		// �Ǹ�����������ĸ�����
		if (scaleX > scaleY && scaleX > scale) {
			scale = scaleX;
		}
		if (scaleY > scaleX && scaleY > scale) {
			scale = scaleY;
		}

		System.out.println("���ű�Ϊ �� " + scale);

		// ��������λͼ
		options.inJustDecodeBounds = false;
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_test, options);
		return bitmap;
	}

}
