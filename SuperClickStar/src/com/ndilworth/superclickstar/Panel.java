package com.ndilworth.superclickstar;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Panel extends SurfaceView implements SurfaceHolder.Callback {

    private ViewThread mThread;
    private int mStarNumber = 0;
    private ArrayList<Star> mStars = new ArrayList<Star>();
    private Circle mCircle = new Circle();
    private int mScore = 0;
    
    public static float mWidth;
    public static float mHeight;
    
    private Paint mPaint = new Paint();
    private long mStartTime;
    private Handler mHandler = new Handler();
    
    public Panel(Context context) {
        super(context);
        getHolder().addCallback(this);
        mThread = new ViewThread(this);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(25);
        
        mStartTime = System.currentTimeMillis();
        mHandler.removeCallbacks(mCreateStar);
        mHandler.postDelayed(mCreateStar, 5000);
    }
    
    private Runnable mCreateStar = new Runnable() {
    	public void run() {
    		createStar();    		
    		mHandler.postDelayed(mCreateStar, 5000);
    	}
    };

    public void doDraw(long elapsed, Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        mCircle.doDraw(canvas, mWidth, mHeight);
        synchronized (mStars) {
        	for (Star star : mStars) {
        		star.doDraw(canvas);
        	}
        }
        canvas.drawText("FPS: " + Math.round(1000f / elapsed) + " Stars: " + mStarNumber + " Score: " + mScore, 10, 25, mPaint);
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mWidth = width;
        mHeight = height;
    }

    public void surfaceCreated(SurfaceHolder holder) {
        if (!mThread.isAlive()) {
            mThread = new ViewThread(this);
            mThread.setRunning(true);
            mThread.start();
        }
    }
    
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mThread.isAlive()) {
            mThread.setRunning(false);
        }
    }
    
    public void animate(long elapsedTime) {
    	synchronized(mStars) {
    		for (Star star : mStars) {
    			star.animate(elapsedTime);
    		}
    	}
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        synchronized(mStars) {
        	for(Star star : mStars) {
        		if(checkClick(star, event.getX(), event.getX())) {
        			starClicked();
        			mStars.remove(star);
        		}
        	}
        }
        return super.onTouchEvent(event);
    }
    
    private boolean checkClick(Star star, float x, float y) {
    	if(star.mX <= x + 35 && star.mX >= x - 35 && star.mY >= y - 35  && star.mY <= y + 35 ) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    private void createStar() {
    	Random rand = new Random();
    	synchronized(mStars) {
        	//Every 10 create red star
        	if (mStarNumber % 10 == 0 && mStarNumber > 11) {
        		mStars.add(new Star(getResources(), rand.nextInt((int)mWidth), rand.nextInt((int)mHeight), 3));
        	//Every 5 create yellow star
        	} else if (mStarNumber %5 == 0 && mStarNumber > 11) {
        		mStars.add(new Star(getResources(), rand.nextInt((int)mWidth), rand.nextInt((int)mHeight), 2));
        	//Every other create blue star
        	} else {
        		mStars.add(new Star(getResources(), rand.nextInt((int)mWidth), rand.nextInt((int)mHeight), 1));
        	}
        	mStarNumber = mStars.size();
        }
    }
    
    private void starClicked() {
    	mScore++;
    }
}