package com.jwgou.android.widgets;

import com.jwgou.android.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class TitleButton extends FrameLayout {

	private TextView mCountTextView;
	private ImageView mImageView;
	private TextView mTextView;
	public TitleButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.title_button, this);
		setClickable(true);
		setFocusable(true);
		setFocusableInTouchMode(true);
		mTextView = (TextView)findViewById(R.id.text);
		mImageView = (ImageView)findViewById(R.id.image);
		mCountTextView = (TextView)findViewById(R.id.count_text);
	}
	
	public void setCount(int count){
		mCountTextView.setText(Integer.toString(count));
		if(count > 0){
			mCountTextView.setVisibility(View.VISIBLE);
			return;
		}
		mCountTextView.setVisibility(View.GONE);
	}

	public void setText(String text){
		if(text == null){
			mTextView.setVisibility(View.GONE);
			return;
		}
		mTextView.setText(text);
		mTextView.setVisibility(View.VISIBLE);
	}
	
	public void setIcon(int resId){
		if(resId >= 0){
			mImageView.setImageResource(resId);
			mImageView.setVisibility(View.VISIBLE);
			return;
		}
		mImageView.setVisibility(View.GONE);
	}
	
	public TitleButton(Context context) {
		super(context);
		init();
	}

	public TitleButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

}
