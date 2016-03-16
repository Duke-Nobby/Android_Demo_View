package xr.android.savelogindemo;

import java.io.File;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import xr.android.savelogindemo.utils.UserInfo;
import xr.android.savelogindemo.utils.UserInfo2;

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
		// Map<String, String> map = UserInfo.getUserInfo(MainActivity.this);
		Map<String, String> map = UserInfo2.getInfo(MainActivity.this);
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
			boolean skip = Login();
			if (skip) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, OtherActivity.class);
				MainActivity.this.startActivity(intent);
			}
			break;
		default:
			break;
		}

	}

	public boolean Login() {
		// 得到用户名和密码 并去掉空格
		String username = userText.getText().toString().trim();
		String password = passText.getText().toString().trim();
		// 查看复选框是否被选中
		boolean isrem = remeBox.isChecked();
		// 如果用户名和密码为空
		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
			Toast.makeText(MainActivity.this, "用户名密码不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		// 不为空的时候 如果复选框被选中
		if (isrem) {

			// 判断SD卡是否存在
			if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				Toast.makeText(MainActivity.this, "SD卡不存在，请检查", Toast.LENGTH_SHORT).show();
				return false;
			}

			// 获取SD卡的文件目录对象
			File sdfile = Environment.getExternalStorageDirectory();

			// 获取文件目录对象的可用空间
			long usableSpace = sdfile.getUsableSpace();

			// 获取剩余空间
			String formatFileSize = Formatter.formatFileSize(MainActivity.this, usableSpace);
			if (usableSpace < 1024 * 1024 * 200) {
				Toast.makeText(MainActivity.this, "SD卡空间不足，剩余空间为+" + formatFileSize, Toast.LENGTH_SHORT).show();
				return false;
			}

			// 是否保存成功
			// boolean result = UserInfo.saveUserInfo(MainActivity.this,
			// username, password);
			boolean result = UserInfo2.saveInfo(MainActivity.this, username, password);
			if (result) {
				Toast.makeText(MainActivity.this, "用户名密码保存成功", Toast.LENGTH_SHORT).show();
				return true;
			} else {
				Toast.makeText(MainActivity.this, "用户名密码保存失败", Toast.LENGTH_SHORT).show();
				return false;
			}

		} else {
			Toast.makeText(MainActivity.this, "无需保存", Toast.LENGTH_SHORT).show();
			return false;
		}
	}
}
