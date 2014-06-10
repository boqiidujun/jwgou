package com.jwgou.android.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RotateImageView extends ImageView {

	private static final int ANIMATION_SPEED = 30;
	private int mCurrentDegree = 0;
	private volatile boolean mIsStart = false;
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			invalidate();
		}
		
	};
	public RotateImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public void start(){
		if(!mIsStart){
			mIsStart = true;
			mCurrentDegree = 0;
			invalidate();
		}
	}
	public void stop(){
		mIsStart = false;
		invalidate();
	}
	@Override
	protected void onDraw(Canvas canvas) {
		if((mIsStart) || (mCurrentDegree != 0)){
			Drawable drawable = getDrawable();
			if(drawable == null)
				return;
			Rect bounds = drawable.getBounds();
			int w = bounds.right - bounds.left;
			int h = bounds.bottom - bounds.top;
			if((w != 0) && (h != 0)){
				mCurrentDegree = ((mCurrentDegree + 10) % 360);
				int left = getPaddingLeft();
				int top = getPaddingTop();
				int right = getPaddingRight();
				int bottom = getPaddingBottom();
				int width = (getWidth() - left) - right;
				int height = getHeight() - top - bottom;
				int saveCount = canvas.getSaveCount();
				canvas.translate((float)((width / 2) + left), (float)((height / 2) + top));
				canvas.rotate((float)mCurrentDegree);
				canvas.translate((float)(-w / 2), (float)(-h / 2));
				drawable.draw(canvas);
				canvas.restoreToCount(saveCount);
				mHandler.removeMessages(0);
				if(mIsStart){
					mHandler.sendEmptyMessageDelayed(0, ANIMATION_SPEED);
					return;
				}
				mHandler.sendEmptyMessageDelayed(0, 3);
			}
		}
		super.onDraw(canvas);
	}

}
