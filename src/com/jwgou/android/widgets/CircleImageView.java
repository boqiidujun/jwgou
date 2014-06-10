package com.jwgou.android.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

public class CircleImageView extends MaskedImage {

	private int cornerRadius;
	public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public CircleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CircleImageView(Context context) {
		super(context);
		init();
	}

	private void init() {
		cornerRadius = (int)(100.0f * getContext().getApplicationContext().getResources().getDisplayMetrics().density);
	}

	public int dipToPx(Context context, int i){
		float f = (float)i;
		float f1 = context.getResources().getDisplayMetrics().density;
		return roundUp(f*f1);
	}
	
	private int roundUp(float f) {
		return (int)(0.5f+f);
	}

	@Override
	public Bitmap createMask() {
		int i = getWidth();
		int j = getHeight();
		Bitmap bitmap = Bitmap.createBitmap(i, j, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint(1);
		paint.setColor(0xff424242);
		paint.setAntiAlias(true);
		Context context = getContext();
		int k = cornerRadius;
		int l = dipToPx(context, k);
		float f = (float)getWidth();
		float f1 = (float)getHeight();
		RectF rectf = new RectF(0, 0, f, f1);
		float f2 = (float)l;
		float f3 = (float)l;
		canvas.drawRoundRect(rectf, f2, f3, paint);
		return bitmap;
	}

}
