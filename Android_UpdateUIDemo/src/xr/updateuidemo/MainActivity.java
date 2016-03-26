package xr.updateuidemo;

import android.app.Activity;
import android.os.Bundle;
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
					Thread.sleep(40);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				// ����UI
				uiText.setText("���߳��и���");
			}
		}).start();
	}

}
