package xr.playvideodemo;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

/**
 * @ClassName: MainActivity
 * @Description: 播放视频Demo
 * @author: iamxiarui@foxmail.com
 * @date: 2016年5月4日 下午6:35:26
 */
public class MainActivity extends Activity {
	private SurfaceView videoSurface;
	private MediaPlayer videoPlayer;
	private int currentPosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		videoSurface = (SurfaceView) findViewById(R.id.surface_video);

		// 创建一个SurfaceHolder对象
		SurfaceHolder holder = videoSurface.getHolder();

		// 添加一个Callback
		holder.addCallback(new Callback() {

			// Surface销毁的时候
			public void surfaceDestroyed(SurfaceHolder holder) {
				// 如果销毁的时候 不为空 或者视频正在播放
				if (videoPlayer != null && videoPlayer.isPlaying()) {
					// 记录当前位置
					currentPosition = videoPlayer.getCurrentPosition();
					videoPlayer.stop();
				}
			}

			// Surface创建的时候
			public void surfaceCreated(SurfaceHolder holder) {
				try {
					videoPlayer = new MediaPlayer();

					videoPlayer.setDataSource(Environment.getExternalStorageDirectory().getPath() + "//test.MOV");

					videoPlayer.setDisplay(holder);

					videoPlayer.prepare();

					// 准备完成监听事件
					videoPlayer.setOnPreparedListener(new OnPreparedListener() {

						@Override
						public void onPrepared(MediaPlayer mp) {

							videoPlayer.start();
							// 从当前位置开始
							videoPlayer.seekTo(currentPosition);
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

			}
		});

	}

}
