package com.slidedelete.utils;

import com.slidedelete.view.SlideLayout;

/**
 * @ClassName: SwipeLayoutManager
 * @Description:��֤��ɾ�������Ŀֻ��һ�� �������ģʽ
 * @author: iamxiarui@foxmail.com
 * @date: 2016��5��19�� ����6:47:39
 */
public class SlideLayoutManager {

	// ��¼��ǰ�򿪵���Ŀ
	private SlideLayout currentLayout;
	private static SlideLayoutManager mInstance = new SlideLayoutManager();

	// �յĹ��캯��
	private SlideLayoutManager() {
	}


	public static SlideLayoutManager getInstance() {
		return mInstance;
	}

	public void setSlideLayout(SlideLayout layout) {
		this.currentLayout = layout;
	}

	/**
	 * @Title: clearCurrentLayout
	 * @Description:��յ�ǰ����¼���Ѿ��򿪵���Ŀ
	 * @return: void
	 */
	public void clearCurrentLayout() {
		currentLayout = null;
	}

	/**
	 * @Title: closeCurrentLayout
	 * @Description:�رյ�ǰ�Ѿ��򿪵���Ŀ
	 * @return: void
	 */
	public void closeCurrentLayout() {
		if (currentLayout != null) {
			currentLayout.closeDeleteView();
		}
	}

	/**
	 * @Title: isShouldSlide
	 * @Description:�����жϵ�ǰ�Ƿ�Ӧ�û��� ���û�д򿪵�����Ի��� ����Ѿ��д򿪵� �жϵ�ǰ�򿪵��Ƿ����Ѿ��򿪵���Ŀ
	 * @param slideLayout
	 */
	public boolean isShouldSlide(SlideLayout slideLayout) {
		if (currentLayout == null) {
			return true;
		} else {
			return currentLayout == slideLayout;
		}
	}
}
