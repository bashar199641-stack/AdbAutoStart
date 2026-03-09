package com.adbmanager.autostart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_BOOT_COMPLETED.equals(action) ||
            "android.intent.action.QUICKBOOT_POWERON".equals(action)) {
            // تشغيل الـ Service عند الإقلاع
            Intent serviceIntent = new Intent(context, AdbService.class);
            context.startForegroundService(serviceIntent);
        }
    }
}
