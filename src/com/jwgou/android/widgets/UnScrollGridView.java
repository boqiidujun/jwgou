package com.jwgou.android.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

public class UnScrollGridView extends GridView {

	public UnScrollGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = View.MeasureSpec.makeMeasureSpec(0x1fffffff, 0x80000000);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
	
}
