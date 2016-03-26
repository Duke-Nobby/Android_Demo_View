package xr.updateuidemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

/**
 * @ClassName: MainActivity
 * @Description: ��ƻ��Ƶļ򵥲��� ���߳��и���UI
 * @author iamxiarui@foxmail.com
 * @date 2016��3��26�� ����10:39:06
 * 
 */
public class MainActivity extends Activity {
	private TextView uiText;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		uiText = (TextView) findViewById(R.id.uiText);

		// �������߳�
		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					// �����߳�˯��40ms (����С��) ʹ�����̵߳�Activity��û����ȫ�ļ��ؽ��� ���ɸ���UI
					// ��ƻ��� ��Activity��ȫ��ʾ������ʱ�� �Ż�ȥ����ܲ��ܸ���UI
					// SurfaceView �� Progress�ؼ�Ҳ�������߳��и���UI
					Thread.sleep(40);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				// ���ַ�ʽ Ҳ�ܸ���UI ���۵�ǰ�߳��Ƿ������߳� ���������߳���ִ��
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// ����UI
						uiText.setText("���߳�runOnUiThread�и���");
					}
				});

				// ���ַ�ʽ Ҳ�ܸ��� Handler ֱ�� post�����߳�
				handler.post(new Runnable() {

					@Override
					public void run() {
						// ����UI
						uiText.setText("���߳�post�и���");
					}
				});
			}
		}).start();
	}

}
