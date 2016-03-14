package xr.android.savelogindemo;

import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import xr.android.savelogindemo.utils.UserInfo;

public class MainActivity extends Activity implements android.view.View.OnClickListener {
	private Button loginButton;
	private CheckBox remeBox;
	private EditText userText, passText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		userText = (EditText) findViewById(R.id.userText);
		passText = (EditText) findViewById(R.id.passText);
		loginButton = (Button) findViewById(R.id.loginButton);
		remeBox = (CheckBox) findViewById(R.id.remeBox);

		// 绑定事件监听器
		loginButton.setOnClickListener(this);

		// 得到用户名和密码
		Map<String, String> map = UserInfo.getUserInfo(MainActivity.this);
		if (map != null) {
			String username = map.get("username");
			String password = map.get("password");
			userText.setText(username);// 设置用户名
			passText.setText(password);
			remeBox.setChecked(true);// 设置复选框选中状态
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loginButton:
			Login();
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, OtherActivity.class);
			MainActivity.this.startActivity(intent);
			break;
		default:
			break;
		}

	}

	public void Login() {
		// 得到用户名和密码 并去掉空格
		String username = userText.getText().toString().trim();
		String password = passText.getText().toString().trim();
		// 查看复选框是否被选中
		boolean isrem = remeBox.isChecked();
		// 如果用户名和密码为空
		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
			Toast.makeText(MainActivity.this, "用户名密码不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		// 不为空的时候 如果复选框被选中
		if (isrem) {
			// 是否保存成功
			boolean result = UserInfo.saveUserInfo(MainActivity.this, username, password);
			if (result) {
				Toast.makeText(MainActivity.this, "用户名密码保存成功", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(MainActivity.this, "用户名密码保存失败", Toast.LENGTH_SHORT).show();
			}

		} else {
			Toast.makeText(MainActivity.this, "无需保存", Toast.LENGTH_SHORT).show();
		}
	}
}
