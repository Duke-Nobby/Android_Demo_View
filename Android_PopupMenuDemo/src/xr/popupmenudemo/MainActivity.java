package xr.popupmenudemo;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import xr.popmenudemo.R;

/**
 * @ClassName: MainActivity
 * @Description: 下拉选择框Demo
 * @author: iamxiarui@foxmail.com
 * @date: 2016年5月5日 下午12:01:20
 */
public class MainActivity extends Activity implements OnClickListener, OnItemClickListener {

	private EditText inputEText;
	private ImageButton menuButton;
	private ListView listView;
	private ArrayList<String> listData;
	static PopupWindow popupWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		inputEText = (EditText) findViewById(R.id.et_input);
		menuButton = (ImageButton) findViewById(R.id.ib_dropdown);
		menuButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		showPopMenu();
	}

	/**
	 * @Title: showPopMenu
	 * @Description:显示下拉选择框
	 * @return: void
	 */
	private void showPopMenu() {

		initListView();
		popupWindow = new PopupWindow(listView, inputEText.getWidth(), 800);

		// 设置菜单外部可以触摸
		popupWindow.setOutsideTouchable(true);

		// 外部点击事件
		popupWindow.setBackgroundDrawable(new BitmapDrawable());

		// 设置可获取焦点
		popupWindow.setFocusable(true);

		// 显示在指定控件下
		popupWindow.showAsDropDown(inputEText, 0, 0);
	}

	/**
	 * @Title: initListView
	 * @Description:初始化ListView
	 * @return: void
	 */
	private void initListView() {
		listView = new ListView(this);

		listView.setDividerHeight(0);
		listView.setBackgroundResource(R.drawable.listview_background);
		listView.setOnItemClickListener(this);

		listData = new ArrayList<String>();
		// 创建一些数据
		for (int i = 0; i < 30; i++) {
			listData.add((10000 + i) + "");
		}

		listView.setAdapter(new MyAdapter(listData));

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		String string = listData.get(position);
		inputEText.setText(string); // 设置文本

		popupWindow.dismiss(); // 消失了
	}

}
