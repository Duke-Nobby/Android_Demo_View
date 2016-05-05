package xr.customswitch.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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

		buttonCSView.setBackgroundPic(R.drawable.switch_background);

		buttonCSView.setForegroundPic(R.drawable.switch_foreground);
		
		buttonCSView.setSwitchState(false);
	}

}
