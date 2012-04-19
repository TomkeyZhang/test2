package com.lib.service;

import com.lib.R;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

public class MoveableToast extends FrameLayout {
    private static final String TAG = "MoveableToast";
    private static final int STATE_STOP = 0;
    private static final int STATE_MOVE = 1;
    private int mState;
    private int mPreviousx = 0;
    private int mPreviousy = 0;
    protected WindowManager windowManager;
    private WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
    private int x;
    private int y;
    private boolean isShown = false;
    private boolean isMoveable = true;
    private Handler handler;
    private Display display;

    private MoveableToast(Context paramContext, int height, int width, int x, int y, boolean isMoveable) {
        super(paramContext);
        mState = STATE_STOP;
        handler = new Handler();
        this.x = x;
        this.y = y;
        this.isMoveable = isMoveable;
        init(height, width);
    }

    private void init(int height, int width) {
        setBackgroundColor(0);
        WindowManager localWindowManager = (WindowManager) getContext().getSystemService("window");
        this.windowManager = localWindowManager;
        localLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        localLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        localLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        localLayoutParams.alpha = 1.0f;
        localLayoutParams.format = 1;
        localLayoutParams.flags = 296;
        display = windowManager.getDefaultDisplay();
    }

    public static MoveableToast create(Context paramContext, View paramView, int width, int height, int x, int y,
            boolean isMoveable) {
        MoveableToast localClickableToast = new MoveableToast(paramContext, width, height, x, y, isMoveable);
        localClickableToast.addView(paramView);
        return localClickableToast;
    }

    private WindowManager.LayoutParams getWmParams() {
        Log.d("zqt", "localLayoutParams.x:" + localLayoutParams.x);
        Log.d("zqt", "localLayoutParams.y:" + localLayoutParams.y);
        localLayoutParams.x = x;
        localLayoutParams.y = y;
        return localLayoutParams;
    }

    public void show() {
        Log.d("zqt", "show()" + isShown);
        if (!isShown) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Log.d("zqt", "show toast!");
                    WindowManager.LayoutParams localLayoutParams = getWmParams();
                    localLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
                    windowManager.addView(MoveableToast.this, localLayoutParams);
                    isShown = true;
                }
            });
        }
    }

    public void hide() {
        if (isShown) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        windowManager.removeView(MoveableToast.this);
                    } catch (RuntimeException localRuntimeException2) {
                    }
                    isShown = false;
                }
            });
        }
    }

    public void updateWidth(int width) {
        localLayoutParams.width = localLayoutParams.width + width;
        windowManager.updateViewLayout(this, getWmParams());
    }

    @Override
    public void onAttachedToWindow() {
        WindowManager.LayoutParams param = getWmParams();
        try {
            windowManager.updateViewLayout(this, param);
        } catch (RuntimeException e) {
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isMoveable)
            return false;
        final int iAction = event.getAction();
        final int iCurrentx = (int) event.getX();
        final int iCurrenty = (int) event.getY();
        Log.d("zqt", "iCurrentx:" + iCurrentx);
        Log.d("zqt", "iCurrenty:" + iCurrenty);
        switch (iAction) {
        case MotionEvent.ACTION_DOWN:
            // Log.d(TAG, "ACTION_DOWN");
            mState = STATE_MOVE;
            mPreviousx = iCurrentx;
            mPreviousy = iCurrenty;
            break;
        case MotionEvent.ACTION_MOVE:
            // Log.d(TAG, "ACTION_MOVE");
            mState = STATE_MOVE;
            int iDeltx = iCurrentx - mPreviousx;
            int iDelty = iCurrenty - mPreviousy;
            x = x + iDeltx;
            y = y + iDelty;
            // switch (localLayoutParams.gravity) {
            // case Gravity.LEFT | Gravity.TOP:
            // if (x > localLayoutParams.height) {
            // localLayoutParams.gravity = Gravity.LEFT | Gravity.BOTTOM;
            // x = 0;
            // y = 0;
            // mPreviousx = 0;
            // mPreviousy = 0;
            // }else if(y > localLayoutParams.height) {
            // localLayoutParams.gravity = Gravity.RIGHT | Gravity.TOP;
            // x = 0;
            // y = 0;
            // mPreviousx = 0;
            // mPreviousy = 0;
            // }else{
            // mPreviousx = iCurrentx - iDeltx;
            // mPreviousy = iCurrenty - iDelty;
            // }
            // windowManager.updateViewLayout(this, getWmParams());
            // Log.d("zqt", "x:" + x);
            // Log.d("zqt", "y:" + y);
            // Log.d("zqt", "width:" + display.getWidth());
            // Log.d("zqt", "height:" + display.getHeight());
            // break;
            // case Gravity.RIGHT | Gravity.TOP:
            // Log.d("zqt", "Gravity.RIGHT | Gravity.TOP ");
            // Log.d("zqt", "x:" + x);
            // Log.d("zqt", "y:" + y);
            // Log.d("zqt", "width:" + display.getWidth());
            // Log.d("zqt", "height:" + display.getHeight());
            // break;
            // case Gravity.LEFT | Gravity.BOTTOM:
            // break;
            // case Gravity.RIGHT | Gravity.BOTTOM:
            // break;
            //
            // default:
            // break;
            // }
            mPreviousx = iCurrentx - iDeltx;
            mPreviousy = iCurrenty - iDelty;
            windowManager.updateViewLayout(this, getWmParams());
            break;
        case MotionEvent.ACTION_UP:
            mState = STATE_STOP;
            break;
        case MotionEvent.ACTION_CANCEL:
            // Log.d(TAG, "ACTION_CANCEL");
            mState = STATE_STOP;
            break;
        }
        return false;
    }
}