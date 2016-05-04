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
 * @Description: ������ƵDemo
 * @author: iamxiarui@foxmail.com
 * @date: 2016��5��4�� ����6:35:26
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

		// ����һ��SurfaceHolder����
		SurfaceHolder holder = videoSurface.getHolder();

		// ���һ��Callback
		holder.addCallback(new Callback() {

			// Surface���ٵ�ʱ��
			public void surfaceDestroyed(SurfaceHolder holder) {
				// ������ٵ�ʱ�� ��Ϊ�� ������Ƶ���ڲ���
				if (videoPlayer != null && videoPlayer.isPlaying()) {
					// ��¼��ǰλ��
					currentPosition = videoPlayer.getCurrentPosition();
					videoPlayer.stop();
				}
			}

			// Surface������ʱ��
			public void surfaceCreated(SurfaceHolder holder) {
				try {
					videoPlayer = new MediaPlayer();

					videoPlayer.setDataSource(Environment.getExternalStorageDirectory().getPath() + "//test.MOV");

					videoPlayer.setDisplay(holder);

					videoPlayer.prepare();

					// ׼����ɼ����¼�
					videoPlayer.setOnPreparedListener(new OnPreparedListener() {

						@Override
						public void onPrepared(MediaPlayer mp) {

							videoPlayer.start();
							// �ӵ�ǰλ�ÿ�ʼ
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
