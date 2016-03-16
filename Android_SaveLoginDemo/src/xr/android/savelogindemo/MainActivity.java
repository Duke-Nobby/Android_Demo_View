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

		// ���¼�������
		loginButton.setOnClickListener(this);

		// �õ��û���������
		// Map<String, String> map = UserInfo.getUserInfo(MainActivity.this);
		Map<String, String> map = UserInfo2.getInfo(MainActivity.this);
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
		// �õ��û��������� ��ȥ���ո�
		String username = userText.getText().toString().trim();
		String password = passText.getText().toString().trim();
		// �鿴��ѡ���Ƿ�ѡ��
		boolean isrem = remeBox.isChecked();
		// ����û���������Ϊ��
		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
			Toast.makeText(MainActivity.this, "�û������벻��Ϊ��", Toast.LENGTH_SHORT).show();
			return false;
		}
		// ��Ϊ�յ�ʱ�� �����ѡ��ѡ��
		if (isrem) {

			// �ж�SD���Ƿ����
			if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				Toast.makeText(MainActivity.this, "SD�������ڣ�����", Toast.LENGTH_SHORT).show();
				return false;
			}

			// ��ȡSD�����ļ�Ŀ¼����
			File sdfile = Environment.getExternalStorageDirectory();

			// ��ȡ�ļ�Ŀ¼����Ŀ��ÿռ�
			long usableSpace = sdfile.getUsableSpace();

			// ��ȡʣ��ռ�
			String formatFileSize = Formatter.formatFileSize(MainActivity.this, usableSpace);
			if (usableSpace < 1024 * 1024 * 200) {
				Toast.makeText(MainActivity.this, "SD���ռ䲻�㣬ʣ��ռ�Ϊ+" + formatFileSize, Toast.LENGTH_SHORT).show();
				return false;
			}

			// �Ƿ񱣴�ɹ�
			// boolean result = UserInfo.saveUserInfo(MainActivity.this,
			// username, password);
			boolean result = UserInfo2.saveInfo(MainActivity.this, username, password);
			if (result) {
				Toast.makeText(MainActivity.this, "�û������뱣��ɹ�", Toast.LENGTH_SHORT).show();
				return true;
			} else {
				Toast.makeText(MainActivity.this, "�û������뱣��ʧ��", Toast.LENGTH_SHORT).show();
				return false;
			}

		} else {
			Toast.makeText(MainActivity.this, "���豣��", Toast.LENGTH_SHORT).show();
			return false;
		}
	}
}
