package com.lib.service;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
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

    private MoveableToast(Context paramContext, int height, int width, int x,
            int y, boolean isMoveable) {
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
        WindowManager localWindowManager = (WindowManager) getContext()
                .getSystemService("window");
        this.windowManager = localWindowManager;
        localLayoutParams.height = height;
        localLayoutParams.width = width;
        localLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        localLayoutParams.alpha = 1.0f;
        localLayoutParams.format = 1;
        localLayoutParams.flags = 296;
    }
    public static MoveableToast create(Context paramContext, View paramView,
            int width, int height, int x, int y, boolean isMoveable) {
        MoveableToast localClickableToast = new MoveableToast(paramContext,
                width, height, x, y, isMoveable);
        localClickableToast.addView(paramView);
        return localClickableToast;
    }

    private WindowManager.LayoutParams getWmParams() {
        localLayoutParams.x = x;
        localLayoutParams.y = y;
        return localLayoutParams;
    }

    public void show() {
        Log.d("zqt", "show()" + isShown);
        if (!isShown) {
            Log.d("zqt", "handler.post");
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Log.d("zqt", "show toast!");
                    WindowManager.LayoutParams localLayoutParams = getWmParams();
                    localLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
                    windowManager
                            .addView(MoveableToast.this, localLayoutParams);
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
            windowManager.updateViewLayout(this, getWmParams());
            mPreviousx = iCurrentx - iDeltx;
            mPreviousy = iCurrenty - iDelty;
            break;
        case MotionEvent.ACTION_UP:
            // Log.d(TAG, "ACTION_UP");
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