package xr.fragmentdemo;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentOne extends Fragment {

	/*
	 * Title: onCreateView Description:一定要重写这个方法初始化view
	 * 
	 * @param inflater
	 * 
	 * @param container
	 * 
	 * @param savedInstanceState
	 * 
	 * @return
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_one, null);

		return view;
	}

}
