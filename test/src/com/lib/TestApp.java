package com.lib;

import com.lib.service.MoveableToast;
import com.lib.service.MoveableToastService;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.View;

public class TestApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // startService(new Intent(this, MoveableToastService.class));
        MoveableToastService.launchService(this,true);
        // view.setOnClickListener(new View.OnClickListener() {
        // @Override
        // public void onClick(View v) {
        // clickableToast.hide();
        // }
        // });
    }
}
