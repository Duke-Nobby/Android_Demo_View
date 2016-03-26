package xr.updateuidemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
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
	private Handler handler;

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
					// 审计机制 当Activity完全显示出来的时候 才会去审计能不能更新UI
					// SurfaceView 和 Progress控件也能在子线程中更新UI
					Thread.sleep(40);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				// 这种方式 也能更新UI 无论当前线程是否是主线程 都将在主线程中执行
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// 更新UI
						uiText.setText("子线程runOnUiThread中更新");
					}
				});

				// 这种方式 也能更新 Handler 直接 post到主线程
				handler.post(new Runnable() {

					@Override
					public void run() {
						// 更新UI
						uiText.setText("子线程post中更新");
					}
				});
			}
		}).start();
	}

}
