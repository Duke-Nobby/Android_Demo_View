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

	// 保存用户名和密码 保存成功返回真
	public static boolean saveUserInfo(Context context, String username, String password) {

		try {
			// 将用户名和密码合并为字符串
			String userinfo = username + "##" + password;
			// 得到软件的私有目录
			String path = context.getFilesDir().getPath();

			// 创建文件
			File file = new File(path, "userinfo.txt");
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			// 将用户名和密码写入文件
			fileOutputStream.write(userinfo.getBytes());
			fileOutputStream.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	// 得到用户名和密码
	public static Map<String, String> getUserInfo(Context context) {

		try {
			// 得到私有目录
			String path = context.getFilesDir().getPath();
			File file = new File(path, "userinfo.txt");

			// 将其中的用户名和密码找到
			FileInputStream fileInputStream = new FileInputStream(file);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
			String readLine = bufferedReader.readLine();
			// 将其中的字符串以“##”切割
			String[] split = readLine.split("##");
			// 将取出的用户名和密码存入集合中
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
