package xr.customswitch.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import xr.customswitch.view.CustomSwitchView.OnSwitchStateUpdateListener;

/**
 * @ClassName: MainActivity
 * @Description:自定义开关按钮的实现
 * @author: iamxiarui@foxmail.com
 * @date: 2016年5月5日 下午6:49:54
 */
public class MainActivity extends Activity {

	private CustomSwitchView buttonCSView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		buttonCSView = (CustomSwitchView) findViewById(R.id.csv_button);

		/*
		 * 在没有自定义属性之前，可以通过方法来设置相关属性
		 * 
		 * // 设置背景图 buttonCSView.setBackgroundPic(R.drawable.switch_background);
		 * 
		 * // 设置前景图 buttonCSView.setForegroundPic(R.drawable.switch_foreground);
		 * 
		 * // 设置默认状态 buttonCSView.setSwitchState(true);
		 */

		// 绑定监听事件
		buttonCSView.setOnSwitchStateUpdateListener(new OnSwitchStateUpdateListener() {

			@Override
			public void onStateUpdate(boolean state) {
				if (state) {
					Toast.makeText(MainActivity.this, "打开", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(MainActivity.this, "关闭", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
