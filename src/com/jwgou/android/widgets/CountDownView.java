package com.jwgou.android.widgets;

import java.util.Date;

import com.jwgou.android.R;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class CountDownView extends TextView {

	private OnExpandChangeListener mListener;
	public boolean mIsExpand = false;
	private Date mEndDate;
	private CountDownTimer timer;

	public CountDownView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initCountDownView();
	}

	public CountDownView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initCountDownView();
	}

	public CountDownView(Context context) {
		super(context);
		initCountDownView();
	}

	public void setEndDate(Date paramDate) {
		this.mEndDate = paramDate;
		setText(null);
		if (!mIsExpand)
			return;

		processCountDown();
		
	}

	private void initCountDownView() {
		setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setText(null);
				if (mIsExpand) {
					collapse();
				} else {
					expand();
				}
				if(mListener != null){
					mListener.onChange(mIsExpand);
				}
			}
		});
		collapse();
	}

	public boolean isTimeOut() {
		return this.mEndDate.getTime() - System.currentTimeMillis() < 0;
	}

	public void collapse() {
		if(timer != null){
			timer.cancel();
			setText(null);
		}
		mIsExpand = false;
		setBackgroundResource(R.drawable.countdown_bg_collapse);
	}

	public void expand() {
		mIsExpand = true;
		setBackgroundResource(R.drawable.countdown_bg_expand);
		processCountDown();
	}

	private void processCountDown() {
		setBackgroundResource(R.drawable.countdown_bg_expand);
		if (mEndDate != null) {
			long times = this.mEndDate.getTime() - System.currentTimeMillis();
			if(timer != null){
				timer.cancel();
				setText(null);
			}
			timer = new CountDownTimer(times, 1000) {

				@Override
				public void onFinish() {
					if (mIsExpand)
						setText("活动已结束");
				}

				@Override
				public void onTick(long millisUntilFinished) {
					if (mIsExpand)
						calculate(millisUntilFinished);
				}
			};
			timer.start();
		}
	}

	private void calculate(long times) {
		long time = times / 1000;
		int hour = (int) (time / 3600);
		int minute = (int) ((time - hour * 3600) / 60);
		int second = (int) (time - hour * 3600 - minute * 60);
		setText(hour + ":" + (minute < 10 ? "0" + minute : minute) + ":"
				+ (second < 10 ? "0" + second : second));
	}

	public void setOnExpandChangeListener(
			OnExpandChangeListener paramOnExpandChangeListener) {
		this.mListener = paramOnExpandChangeListener;
	}

	public static abstract interface OnExpandChangeListener {
		public abstract void onChange(boolean paramBoolean);
	}

}
