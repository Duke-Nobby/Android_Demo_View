package xr.android.savelogindemo.utils;

/*
 * ����SharedPreference���󱣴���Ϣ
 * 
 * 
 * */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferenceUtil {

	public static boolean SharedsaveInfo(Context context, String username, String password) {

		try {
			// ���õ�ǰ��Context���� ����һ��SharedPreference����
			// ��һ�������Ǳ�����Ϣ�ļ������� �ڶ����������ļ���ģʽ
			SharedPreferences sharedPreferences = context.getSharedPreferences("SharedInfo", Context.MODE_PRIVATE);

			// ����SharedPreference���󴴽�һ��Editor����
			Editor edit = sharedPreferences.edit();

			// ��Editor���������
			edit.putString("username", username);
			edit.putString("password", password);

			// �ύeditor����
			edit.commit();

			return true;
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return false;
	}

	public static Map<String, String> SharedgetInfo(Context context) {

		try {

			// ����SharedPreference����
			SharedPreferences sharedPreferences = context.getSharedPreferences("SharedInfo", Context.MODE_PRIVATE);

			// ��ȡ�����е���Ϣ
			String username = sharedPreferences.getString("username", "");
			String password = sharedPreferences.getString("password", "");

			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("username", username);
			hashMap.put("password", password);

			return hashMap;
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return null;
	}

}
