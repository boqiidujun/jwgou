package com.jwgou.android.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public abstract class MaskedImage extends ImageView {

	private static final Xfermode MASK_XFERMODE;
	public abstract Bitmap createMask();
	Paint paint = new Paint();
	public MaskedImage(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public MaskedImage(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MaskedImage(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Drawable drawable = getDrawable();
		if(drawable == null)
			return;
		paint.setFilterBitmap(false);
		float f = (float)getWidth();
		float f1 = (float)getHeight();
		Canvas canvas1 = canvas;
		float f2 = 0;
		int i = canvas1.saveLayerAlpha(0.0f, f2, f, f1, 0x0, 0x1f);
		int j = getWidth();
		int k = getHeight();
		drawable.setBounds(0, 0, j, k);
		drawable.draw(canvas);
		paint.setXfermode(MASK_XFERMODE);
		Bitmap bitmap = createMask();
		canvas.drawBitmap(bitmap, 0, 0, paint);
		canvas.restoreToCount(i);
	}

	static {
		MASK_XFERMODE = new PorterDuffXfermode(Mode.SRC_IN);
	}
}
