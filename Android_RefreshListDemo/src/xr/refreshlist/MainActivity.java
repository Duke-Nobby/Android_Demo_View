package xr.refreshlist;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import xr.refreshlist.adapter.RefreshListAdapter;
import xr.refreshlist.view.RefreshListView;
import xr.refreshlist.view.RefreshListView.OnRefreshListener;

/**
 * @ClassName: MainActivity
 * @Description:下拉刷新列表的实现
 * @author: iamxiarui@foxmail.com
 * @date: 2016年5月7日 下午3:50:06
 */
public class MainActivity extends Activity {

	private RefreshListView refreshListView;
	private ArrayList<String> dataList;
	private RefreshListAdapter RefreshListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		refreshListView = (RefreshListView) findViewById(R.id.refresh_list);

		dataList = new ArrayList<String>();

		for (int i = 0; i < 30; i++) {
			dataList.add("这是初始化数据 " + i);
		}

		RefreshListAdapter = new RefreshListAdapter(dataList);

		// 绑定事件监听
		refreshListView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						dataList.add(0, "这是刷新出来的数据");

						runOnUiThread(new Runnable() {
							public void run() {
								RefreshListAdapter.notifyDataSetChanged();
								refreshListView.onRefreshComplete();
							}
						});

					}
				}).start();

			}

			@Override
			public void onLoadMore() {

			}
		});

		// 绑定适配器
		refreshListView.setAdapter(RefreshListAdapter);
	}

}
