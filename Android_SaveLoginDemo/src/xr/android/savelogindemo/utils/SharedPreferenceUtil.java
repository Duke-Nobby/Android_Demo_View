package xr.android.savelogindemo.utils;

/*
 * 利用SharedPreference对象保存信息
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
			// 利用当前的Context对象 创建一个SharedPreference对象
			// 第一个参数是保存信息文件的名称 第二个参数是文件的模式
			SharedPreferences sharedPreferences = context.getSharedPreferences("SharedInfo", Context.MODE_PRIVATE);

			// 利用SharedPreference对象创建一个Editor对象
			Editor edit = sharedPreferences.edit();

			// 往Editor中添加内容
			edit.putString("username", username);
			edit.putString("password", password);

			// 提交editor对象
			edit.commit();

			return true;
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return false;
	}

	public static Map<String, String> SharedgetInfo(Context context) {

		try {

			// 创建SharedPreference对象
			SharedPreferences sharedPreferences = context.getSharedPreferences("SharedInfo", Context.MODE_PRIVATE);

			// 获取对象中的信息
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
