package xr.fragmentdemo;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @ClassName: MainActivity
 * @Description:动态加载Fragment模仿微信界面
 * @author iamxiarui@foxmail.com
 * @date 2016年4月16日 下午8:31:35
 * 
 */
public class MainActivity extends Activity implements OnClickListener {
	private Button talkButton, personButton, findButton, meButton;
	private FragmentTransaction beginTransaction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		InitView();

	}

	public void InitView() {
		talkButton = (Button) findViewById(R.id.talk_button);
		personButton = (Button) findViewById(R.id.person_button);
		findButton = (Button) findViewById(R.id.find_button);
		meButton = (Button) findViewById(R.id.me_button);

		talkButton.setOnClickListener(this);
		personButton.setOnClickListener(this);
		findButton.setOnClickListener(this);
		meButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// 获得Fragment管理者
		FragmentManager fragmentManager = getFragmentManager();

		// 开启一个事务
		beginTransaction = fragmentManager.beginTransaction();

		switch (v.getId()) {
		case R.id.talk_button:
			beginTransaction.replace(R.id.fragment_layout, new FragmentOne());
			break;

		case R.id.person_button:
			beginTransaction.replace(R.id.fragment_layout, new FragmentTwo());
			break;

		case R.id.find_button:
			beginTransaction.replace(R.id.fragment_layout, new FragmentThree());
			break;

		case R.id.me_button:
			beginTransaction.replace(R.id.fragment_layout, new FragmentFour());
			break;

		}

		// 一定要记得提交事务
		beginTransaction.commit();

	}

}
