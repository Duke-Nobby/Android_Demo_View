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

		// ���¼�������
		loginButton.setOnClickListener(this);

		// �õ��û���������
		Map<String, String> map = UserInfo.getUserInfo(MainActivity.this);
		if (map != null) {
			String username = map.get("username");
			String password = map.get("password");
			userText.setText(username);// �����û���
			passText.setText(password);
			remeBox.setChecked(true);// ���ø�ѡ��ѡ��״̬
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
		// �õ��û��������� ��ȥ���ո�
		String username = userText.getText().toString().trim();
		String password = passText.getText().toString().trim();
		// �鿴��ѡ���Ƿ�ѡ��
		boolean isrem = remeBox.isChecked();
		// ����û���������Ϊ��
		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
			Toast.makeText(MainActivity.this, "�û������벻��Ϊ��", Toast.LENGTH_SHORT).show();
			return;
		}
		// ��Ϊ�յ�ʱ�� �����ѡ��ѡ��
		if (isrem) {
			// �Ƿ񱣴�ɹ�
			boolean result = UserInfo.saveUserInfo(MainActivity.this, username, password);
			if (result) {
				Toast.makeText(MainActivity.this, "�û������뱣��ɹ�", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(MainActivity.this, "�û������뱣��ʧ��", Toast.LENGTH_SHORT).show();
			}

		} else {
			Toast.makeText(MainActivity.this, "���豣��", Toast.LENGTH_SHORT).show();
		}
	}
}
