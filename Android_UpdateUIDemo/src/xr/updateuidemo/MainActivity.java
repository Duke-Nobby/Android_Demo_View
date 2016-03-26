package xr.updateuidemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * @ClassName: MainActivity
 * @Description: 审计机制的简单测试 子线程中更新UI
 * @author iamxiarui@foxmail.com
 * @date 2016年3月26日 上午10:39:06
 * 
 */
public class MainActivity extends Activity {
	private TextView uiText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		uiText = (TextView) findViewById(R.id.uiText);

		// 创建子线程
		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					// 让子线程睡眠40ms (或者小于) 使得主线程的Activity并没有完全的加载进来 即可更新UI
					Thread.sleep(40);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				// 更新UI
				uiText.setText("子线程中更新");
			}
		}).start();
	}

}
