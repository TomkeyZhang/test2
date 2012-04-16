package com.lib.service;
/* Zelon Control Expansive TextView */
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.TextView;

public class ETextView extends FrameLayout{
	private static final String TAG = "ETextView";  
	private static final int STATE_STOP = 0;  
	private static final int STATE_MOVE = 1; 
	private int mState;
	private int mPreviousx = 0;
	private int mPreviousy = 0;
		
	// Initial the view.
	public ETextView(Context context, AttributeSet attribute) {
		this(context, attribute, 0);
	}

	// Initial the view.
	public ETextView(Context context, AttributeSet attribute, int style) {
		super(context, attribute, style);
		mState = STATE_STOP;
	}
		
	// On touch Event.
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		final int iAction = event.getAction();
		final int iCurrentx = (int)event.getX();
		final int iCurrenty = (int)event.getY();
		switch(iAction)
		{
		case MotionEvent.ACTION_DOWN:
			Log.e(TAG,"ACTION_DOWN");
			mState = STATE_MOVE;
			mPreviousx = iCurrentx;
			mPreviousy = iCurrenty;
			break;
		case MotionEvent.ACTION_MOVE:
			Log.e(TAG, "ACTION_MOVE");
			mState = STATE_MOVE;
			int iDeltx = iCurrentx - mPreviousx;
			int iDelty = iCurrenty - mPreviousy;
			final int iLeft = getLeft();
			final int iTop = getTop();
			if(iDeltx != 0 || iDelty != 0)
				layout(iLeft + iDeltx, 
						iTop + iDelty, 
						iLeft + iDeltx + getWidth(), 
						iTop + iDelty + getHeight());
			mPreviousx = iCurrentx - iDeltx;
			mPreviousy = iCurrenty - iDelty;
			break;
		case MotionEvent.ACTION_UP:
			Log.e(TAG,"ACTION_UP");
			mState = STATE_STOP;
			break;
		case MotionEvent.ACTION_CANCEL:
			Log.e(TAG, "ACTION_CANCEL");
			mState = STATE_STOP;
			break;
		}
		return true;
	}
}
