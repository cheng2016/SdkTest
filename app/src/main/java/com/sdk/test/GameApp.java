package com.sdk.test;

import android.app.Application;
import android.os.StrictMode;
import android.widget.Toast;
import com.icloud.sdk.YZSDK;
import com.icloud.sdk.listener.CallbackListener;
import com.icloud.sdk.listener.ResultCode;

public class GameApp extends Application {
  public static void startStrictModeThreadPolicy() { StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder()).detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build()); }
  
  public static void startStrictModeVmPolicy() { StrictMode.setVmPolicy((new StrictMode.VmPolicy.Builder()).detectActivityLeaks().detectLeakedClosableObjects().detectLeakedSqlLiteObjects().penaltyLog().build()); }
  
  public void onCreate() {
    super.onCreate();
    YZSDK.instance().initApp(this, new CallbackListener() {
          public void onResult(ResultCode param1ResultCode, String param1String1, String param1String2) {
            if (param1ResultCode != ResultCode.SUCCESS)
              Toast.makeText(GameApp.this, param1String1, 0).show(); 
          }
        });
  }
}
