package com.ndilworth.superclickstar;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Circle {
	private float mRadius;
	private Paint mPaint = new Paint();
	
	private float mX;
	private float mY;
	
	public Circle() {
		mRadius = 100;
		mPaint.setColor(Color.RED);
	}
	
	public void doDraw(Canvas canvas, float x, float y)
	{
		mX = x / 2;
		mY = y / 2;
		canvas.drawCircle(mX, mY, mRadius, mPaint);
	}
}
