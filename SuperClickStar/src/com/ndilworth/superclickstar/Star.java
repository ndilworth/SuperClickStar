package com.ndilworth.superclickstar;

import java.util.Random;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Star {
	public float mX;
	public float mY;
	private int mSpeedX;
	private int mSpeedY;
	private int mType;
	private int mClicks;
	
	private Bitmap mBitmap;
	
	public Star(Resources res, int x, int y, int type) {
		Random rand = new Random();
		mType = type;
		switch (type) {
			case 3:
				mBitmap = BitmapFactory.decodeResource(res, R.drawable.mediumredstar);
				break;
			case 2:
				mBitmap = BitmapFactory.decodeResource(res, R.drawable.mediumyellowstar);
				break;
			case 1:
				mBitmap = BitmapFactory.decodeResource(res, R.drawable.mediumbluestar);
				break;
			default:
				mBitmap = BitmapFactory.decodeResource(res, R.drawable.mediumbluestar);
				break;
		}
		mX = x - mBitmap.getWidth() / 2;
		mY = y - mBitmap.getHeight() / 2;
		mSpeedX = rand.nextInt(7);
		if (mSpeedX == 0)
			mSpeedX = 1;
		mSpeedY = rand.nextInt(7);
		if (mSpeedY == 0)
			mSpeedY = 1;
	}
	
	public void animate(long elapsedTime) {
		mX += mSpeedX * (elapsedTime / 20f);
		mY += mSpeedY * (elapsedTime / 20f);
		checkBorders();
	}
	
	public void doDraw(Canvas canvas) {
		canvas.drawBitmap(mBitmap, mX, mY, null);
	}
	
	private void checkBorders() {
		if (mX <= 0) {
			mSpeedX = -mSpeedX;
			mX = 0;
		} else if (mX + mBitmap.getWidth() >= Panel.mWidth) {
			mSpeedX = -mSpeedX;
			mX = Panel.mWidth - mBitmap.getWidth();
		}
		if (mY <= 0) {
			mSpeedY = -mSpeedY;
			mY = 0;			
		} else if (mY + mBitmap.getHeight() >= Panel.mHeight) {
			mSpeedY = -mSpeedY;
			mY = Panel.mHeight - mBitmap.getHeight();
		}
	}
}
