package xr.listview.adapter;

/*
 * Adapter类的使用  
 * 
 * 一般使用ListAdapter下面的子类    
 * 
 * 
 *继承子类 并实现子类的方法
 * */
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class XrListAdapter extends BaseAdapter {

	private Context context;

	public XrListAdapter(Context context) {
		this.context = context;
	}

	// 返回list需要创建的列表条目 必须实现
	@Override
	public int getCount() {
		return 20;
	}

	// 根据postion获取listview列表上 每条目对应的Bean 对象封装的数据，该方法不影响数据的展示，可以先不实现。
	@Override
	public Object getItem(int position) {
		return null;
	}

	// getItemId:用来获取列表 某个条目的某行的id，该方法不影响数据的展示，可以先不实现
	@Override
	public long getItemId(int position) {
		return 0;
	}

	// 返回列表条目中显示的内容 必须实现 列表中每显示一个条目 就需要调用一次这样的方法
	// 这样占用大量内存，不好，所以使用convertView解决问题
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 一般需要创建的对象 是整个对象显示的条目 再 加 1
		TextView textView = null;

		// 参数 convertView:转变换算。可以让曾经使用过的view对象被重复使用, 使用前要if 判断。
		if (convertView != null) {
			// 如果不为空 直接强转继续使用
			textView = (TextView) convertView;
		} else {
			// 如果为空则新创建
			textView = new TextView(context);
		}

		textView.setText("第 " + position + "条");
		textView.setTextSize(30);

		return textView;
	}

}
