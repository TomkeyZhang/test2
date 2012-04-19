package com.lib.service;

import java.util.List;

import com.lib.R;

import android.app.ActivityManager;
import android.app.Service;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MoveableToastService extends Service {
    // private static final String LAYOUT_ID = "layout_id";
    // private static final String LEFT = "left";
    // private static final String TOP = "right";
    // private static final String WIDTH = "width";
    // private static final String HEIGHT = "height";
    private static final String MOVEABLE = "moveable";
    private MoveableToast moveableToast;
    private final IBinder binder = new MyBinder();
    private ActivityManager activityManager;

    /**
     * 
     * @param context Context对象
     * @param moveable 弹出框是否可以移动
     */
    public static void launchService(Context context, boolean moveable) {
        Intent intent = new Intent(context, MoveableToastService.class);
        // intent.putExtra(LAYOUT_ID, R.layout.toast_test);
        // intent.putExtra(LEFT, 20);
        // intent.putExtra(TOP, 30);
        intent.putExtra(MOVEABLE, moveable);
        // intent.putExtra(WIDTH, 100);
        // intent.putExtra(HEIGHT, 100);
        context.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceDisconnected(ComponentName name) {
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {

            }
        }, BIND_AUTO_CREATE);
    }

    public class MyBinder extends Binder {
        MoveableToastService getService() {
            return MoveableToastService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (moveableToast == null) {
            moveableToast = MoveableToast.create(this, View.inflate(this, R.layout.toast_test, null), 150, 200, 0, 0,
                    intent.getBooleanExtra(MOVEABLE, false));
            final Button expandBtn = (Button) moveableToast.findViewById(R.id.expand_btn);
            final LinearLayout expandLL = (LinearLayout) moveableToast.findViewById(R.id.expand_ll);
            expandBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (expandLL.getVisibility() == View.VISIBLE) {
                        expandBtn.setText("展开");
                        expandLL.setVisibility(View.GONE);
                    } else {
                        expandBtn.setText("隐藏");
                        expandLL.setVisibility(View.VISIBLE);
                    }
                }
            });
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        List<RunningTaskInfo> runningList = activityManager.getRunningTasks(1);
                        if ((runningList != null) && (runningList.size() > 0)) {
                            RunningTaskInfo taskinfo = runningList.get(0);
                            if (taskinfo.baseActivity.getPackageName().equals(getPackageName())) {
                                moveableToast.show();
                            } else {
                                moveableToast.hide();
                            }
                        }
                    }
                }
            }).start();
        }
        return binder;
    }

    @Override
    public void onCreate() {
        activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
    }
}
