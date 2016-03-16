package xr.android.savelogindemo.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;

public class UserInfo2 {

	public static boolean saveInfo(Context context, String username, String password) {

		String info = username + "##" + password;
		FileOutputStream fos;
		try {
			fos = context.openFileOutput("userinfo2.txt", Context.MODE_PRIVATE);
			fos.write(info.getBytes());
			fos.close();
			return true;
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return false;
	}

	public static Map<String, String> getInfo(Context context) {

		try {
			FileInputStream openFileInput = context.openFileInput("userinfo2.txt");

			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(openFileInput));
			String readLine = bufferedReader.readLine();
			// 将其中的字符串以“##”切割
			String[] split = readLine.split("##");
			// 将取出的用户名和密码存入集合中
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("username", split[0]);
			hashMap.put("password", split[1]);
			bufferedReader.close();
			openFileInput.close();
			return hashMap;
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return null;
	}
}
