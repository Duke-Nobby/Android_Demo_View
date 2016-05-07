package xr.refreshlist;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import xr.refreshlist.adapter.RefreshListAdapter;
import xr.refreshlist.view.RefreshListView;

/**  
 *  @ClassName: MainActivity
 *  @Description:下拉刷新列表的实现
 *  @author: iamxiarui@foxmail.com
 *  @date: 2016年5月7日 下午3:50:06
 */
public class MainActivity extends Activity {

	private RefreshListView refreshList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		refreshList = (RefreshListView) findViewById(R.id.refresh_list);

		ArrayList<String> dataList = new ArrayList<String>();

		for (int i = 0; i < 30; i++) {
			dataList.add("这是初始化数据 " + i);
		}
		refreshList.setAdapter(new RefreshListAdapter(dataList));
	}

}
