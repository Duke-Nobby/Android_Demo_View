package xr.listview.adapter;

/*
 * Adapter���ʹ��  
 * 
 * һ��ʹ��ListAdapter���������    
 * 
 * 
 *�̳����� ��ʵ������ķ���
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

	// ����list��Ҫ�������б���Ŀ ����ʵ��
	@Override
	public int getCount() {
		return 20;
	}

	// ����postion��ȡlistview�б��� ÿ��Ŀ��Ӧ��Bean �����װ�����ݣ��÷�����Ӱ�����ݵ�չʾ�������Ȳ�ʵ�֡�
	@Override
	public Object getItem(int position) {
		return null;
	}

	// getItemId:������ȡ�б� ĳ����Ŀ��ĳ�е�id���÷�����Ӱ�����ݵ�չʾ�������Ȳ�ʵ��
	@Override
	public long getItemId(int position) {
		return 0;
	}

	// �����б���Ŀ����ʾ������ ����ʵ�� �б���ÿ��ʾһ����Ŀ ����Ҫ����һ�������ķ���
	// ����ռ�ô����ڴ棬���ã�����ʹ��convertView�������
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// һ����Ҫ�����Ķ��� ������������ʾ����Ŀ �� �� 1
		TextView textView = null;

		// ���� convertView:ת�任�㡣����������ʹ�ù���view�����ظ�ʹ��, ʹ��ǰҪif �жϡ�
		if (convertView != null) {
			// �����Ϊ�� ֱ��ǿת����ʹ��
			textView = (TextView) convertView;
		} else {
			// ���Ϊ�����´���
			textView = new TextView(context);
		}

		textView.setText("�� " + position + "��");
		textView.setTextSize(30);

		return textView;
	}

}
