package xr.android.savelogindemo.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;

public class UserInfo {

	// �����û��������� ����ɹ�������
	public static boolean saveUserInfo(Context context, String username, String password) {

		try {
			// ���û���������ϲ�Ϊ�ַ���
			String userinfo = username + "##" + password;
			// �õ������˽��Ŀ¼
			String path = context.getFilesDir().getPath();

			// �����ļ�
			File file = new File(path, "userinfo.txt");
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			// ���û���������д���ļ�
			fileOutputStream.write(userinfo.getBytes());
			fileOutputStream.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	// �õ��û���������
	public static Map<String, String> getUserInfo(Context context) {

		try {
			// �õ�˽��Ŀ¼
			String path = context.getFilesDir().getPath();
			File file = new File(path, "userinfo.txt");

			// �����е��û����������ҵ�
			FileInputStream fileInputStream = new FileInputStream(file);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
			String readLine = bufferedReader.readLine();
			// �����е��ַ����ԡ�##���и�
			String[] split = readLine.split("##");
			// ��ȡ�����û�����������뼯����
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("username", split[0]);
			hashMap.put("password", split[1]);
			bufferedReader.close();
			fileInputStream.close();
			return hashMap;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
