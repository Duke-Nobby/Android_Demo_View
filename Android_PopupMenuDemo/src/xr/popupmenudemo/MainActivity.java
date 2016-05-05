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
 * @Description: ����ѡ���Demo
 * @author: iamxiarui@foxmail.com
 * @date: 2016��5��5�� ����12:01:20
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
	 * @Description:��ʾ����ѡ���
	 * @return: void
	 */
	private void showPopMenu() {

		initListView();
		popupWindow = new PopupWindow(listView, inputEText.getWidth(), 800);

		// ���ò˵��ⲿ���Դ���
		popupWindow.setOutsideTouchable(true);

		// �ⲿ����¼�
		popupWindow.setBackgroundDrawable(new BitmapDrawable());

		// ���ÿɻ�ȡ����
		popupWindow.setFocusable(true);

		// ��ʾ��ָ���ؼ���
		popupWindow.showAsDropDown(inputEText, 0, 0);
	}

	/**
	 * @Title: initListView
	 * @Description:��ʼ��ListView
	 * @return: void
	 */
	private void initListView() {
		listView = new ListView(this);

		listView.setDividerHeight(0);
		listView.setBackgroundResource(R.drawable.listview_background);
		listView.setOnItemClickListener(this);

		listData = new ArrayList<String>();
		// ����һЩ����
		for (int i = 0; i < 30; i++) {
			listData.add((10000 + i) + "");
		}

		listView.setAdapter(new MyAdapter(listData));

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		String string = listData.get(position);
		inputEText.setText(string); // �����ı�

		popupWindow.dismiss(); // ��ʧ��
	}

}
