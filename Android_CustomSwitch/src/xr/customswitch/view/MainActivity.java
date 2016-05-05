package xr.customswitch.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import xr.customswitch.view.CustomSwitchView.OnSwitchStateUpdateListener;

/**
 * @ClassName: MainActivity
 * @Description:�Զ��忪�ذ�ť��ʵ��
 * @author: iamxiarui@foxmail.com
 * @date: 2016��5��5�� ����6:49:54
 */
public class MainActivity extends Activity {

	private CustomSwitchView buttonCSView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		buttonCSView = (CustomSwitchView) findViewById(R.id.csv_button);

		/*
		 * ��û���Զ�������֮ǰ������ͨ�������������������
		 * 
		 * // ���ñ���ͼ buttonCSView.setBackgroundPic(R.drawable.switch_background);
		 * 
		 * // ����ǰ��ͼ buttonCSView.setForegroundPic(R.drawable.switch_foreground);
		 * 
		 * // ����Ĭ��״̬ buttonCSView.setSwitchState(true);
		 */

		// �󶨼����¼�
		buttonCSView.setOnSwitchStateUpdateListener(new OnSwitchStateUpdateListener() {

			@Override
			public void onStateUpdate(boolean state) {
				if (state) {
					Toast.makeText(MainActivity.this, "��", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(MainActivity.this, "�ر�", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
