package com.icloud.sdk.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class AppUtil {
  public static int getVersionCode(Context paramContext) {
    int i = 0;
    PackageManager packageManager = paramContext.getPackageManager();
    Context context = null;
    try {
      PackageInfo packageInfo = packageManager.getPackageInfo(paramContext.getApplicationContext().getPackageName(), 0);
    } catch (android.content.pm.PackageManager.NameNotFoundException paramContext) {
      paramContext.printStackTrace();
      paramContext = context;
    } 
    if (paramContext != null)
      i = paramContext.versionCode; 
    return i;
  }
  
  public static String getVersionName(Context paramContext) {
    String str = null;
    PackageManager packageManager = paramContext.getPackageManager();
    Context context = null;
    try {
      PackageInfo packageInfo = packageManager.getPackageInfo(paramContext.getApplicationContext().getPackageName(), 0);
    } catch (android.content.pm.PackageManager.NameNotFoundException paramContext) {
      paramContext.printStackTrace();
      paramContext = context;
    } 
    if (paramContext != null)
      str = paramContext.versionName; 
    return str;
  }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\icloud\sd\\utils\AppUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */