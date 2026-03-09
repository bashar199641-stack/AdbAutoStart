# ADB AutoStart APK

## ماذا يفعل هذا التطبيق؟
يشغّل ADB WiFi على منفذ 5555 تلقائياً عند كل إقلاع للجهاز.
بعد تثبيته لا تحتاج USB أبداً لإعادة تفعيل ADB.

## طريقة البناء على Replit:

1. افتح https://replit.com وسجّل دخول
2. اضغط Create Repl
3. اختر "Import from ZIP"
4. ارفع ملف AdbAutoStart.zip
5. في الـ Shell اكتب:
   ./gradlew assembleDebug
6. الـ APK سيكون في:
   app/build/outputs/apk/debug/app-debug.apk

## طريقة التثبيت على الأجهزة:
من برنامج ADB Manager، في حقل CMD اكتب:
   install "مسار/app-debug.apk"

أو من CMD على الكمبيوتر:
   adb install app-debug.apk

## ملاحظة:
- يعمل بدون Root على Android 10+
- بعد التثبيت افتح التطبيق مرة واحدة فقط
- بعدها يعمل تلقائياً في الخلفية عند كل إقلاع
