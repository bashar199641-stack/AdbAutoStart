package com.adbmanager.autostart;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import java.io.DataOutputStream;

public class AdbService extends Service {

    private static final String TAG = "AdbAutoStart";
    private static final String CHANNEL_ID = "adb_channel";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();

        // Foreground notification (مطلوب لـ Android 8+)
        Notification notification = new Notification.Builder(this, CHANNEL_ID)
                .setContentTitle("ADB AutoStart")
                .setContentText("ADB WiFi مفعّل على منفذ 5555")
                .setSmallIcon(android.R.drawable.ic_menu_manage)
                .build();

        startForeground(1, notification);

        // تشغيل tcpip 5555 في thread منفصل
        new Thread(() -> {
            try {
                Thread.sleep(3000); // انتظار 3 ثواني بعد الإقلاع
                enableAdbWifi();
            } catch (InterruptedException e) {
                Log.e(TAG, "Thread interrupted", e);
            }
            stopSelf();
        }).start();

        return START_NOT_STICKY;
    }

    private void enableAdbWifi() {
        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("setprop service.adb.tcp.port 5555\n");
            os.writeBytes("stop adbd\n");
            os.writeBytes("start adbd\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
            Log.d(TAG, "ADB WiFi enabled on port 5555");
        } catch (Exception e) {
            // بدون Root - نحاول طريقة أخرى
            try {
                Runtime.getRuntime().exec(new String[]{
                    "sh", "-c", "setprop service.adb.tcp.port 5555"
                });
                Runtime.getRuntime().exec(new String[]{
                    "sh", "-c", "stop adbd && start adbd"
                });
                Log.d(TAG, "ADB WiFi attempted without root");
            } catch (Exception e2) {
                Log.e(TAG, "Failed to enable ADB WiFi", e2);
            }
        }
    }

    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "ADB AutoStart",
                NotificationManager.IMPORTANCE_LOW);
        NotificationManager manager = getSystemService(NotificationManager.class);
        if (manager != null) manager.createNotificationChannel(channel);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
