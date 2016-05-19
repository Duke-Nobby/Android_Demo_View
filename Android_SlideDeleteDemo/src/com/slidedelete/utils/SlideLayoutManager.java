package com.slidedelete.utils;

import com.slidedelete.view.SlideLayout;

/**
 * @ClassName: SwipeLayoutManager
 * @Description:保证打开删除框的条目只有一个 单例设计模式
 * @author: iamxiarui@foxmail.com
 * @date: 2016年5月19日 下午6:47:39
 */
public class SlideLayoutManager {

	// 记录当前打开的条目
	private SlideLayout currentLayout;
	private static SlideLayoutManager mInstance = new SlideLayoutManager();

	// 空的构造函数
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
	 * @Description:清空当前所记录的已经打开的条目
	 * @return: void
	 */
	public void clearCurrentLayout() {
		currentLayout = null;
	}

	/**
	 * @Title: closeCurrentLayout
	 * @Description:关闭当前已经打开的条目
	 * @return: void
	 */
	public void closeCurrentLayout() {
		if (currentLayout != null) {
			currentLayout.closeDeleteView();
		}
	}

	/**
	 * @Title: isShouldSlide
	 * @Description:用来判断当前是否应该滑动 如果没有打开的则可以滑动 如果已经有打开的 判断当前打开的是否是已经打开的条目
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
