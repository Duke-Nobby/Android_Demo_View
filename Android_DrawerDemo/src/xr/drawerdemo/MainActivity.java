package xr.drawerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import xr.drawerdemo.view.SlideMenuView;

public class MainActivity extends Activity {
	private SlideMenuView smView;
	private ImageButton backButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		smView = (SlideMenuView) findViewById(R.id.smv);
		backButton = (ImageButton) findViewById(R.id.ib_back);

		// 添加按钮点击事件
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				smView.switchState();
			}
		});
	}

}
