package com.adbmanager.autostart;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.graphics.Color;
import android.view.Gravity;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setBackgroundColor(Color.parseColor("#1E1E2E"));

        TextView title = new TextView(this);
        title.setText("ADB AutoStart");
        title.setTextSize(24f);
        title.setTextColor(Color.WHITE);
        title.setGravity(Gravity.CENTER);

        TextView status = new TextView(this);
        status.setText("✅ التطبيق مفعّل\nسيتم تشغيل ADB WiFi\nتلقائياً عند كل إقلاع\n\nالمنفذ: 5555");
        status.setTextSize(16f);
        status.setTextColor(Color.parseColor("#80DC8A"));
        status.setGravity(Gravity.CENTER);
        status.setPadding(40, 40, 40, 40);

        layout.addView(title);
        layout.addView(status);
        setContentView(layout);

        // تشغيل الـ Service فوراً عند فتح التطبيق للمرة الأولى
        startForegroundService(new android.content.Intent(this, AdbService.class));
    }
}
