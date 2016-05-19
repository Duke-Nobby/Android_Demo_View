package com.headparallax.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ListView;

public class ParallaxListView extends ListView {
	private ImageView headerImage;

	public ParallaxListView(Context context) {
		super(context);

	}

	public ParallaxListView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public ParallaxListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

	}

	public ParallaxListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);

	}

	public void setParallaxImageView(ImageView headerImage) {
		this.headerImage = headerImage;
	}

}
