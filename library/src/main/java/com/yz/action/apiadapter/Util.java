package com.yz.action.apiadapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

public class Util {
  public static String getAppName(Context paramContext) {
    try {
      PackageManager packageManager = paramContext.getPackageManager();
      return packageManager.getApplicationLabel(packageManager.getApplicationInfo(paramContext.getPackageName(), 128)).toString();
    } catch (android.content.pm.PackageManager.NameNotFoundException paramContext) {
      paramContext.printStackTrace();
      return null;
    } 
  }
  
  public static String getMetaDataValue(Context paramContext, String paramString) {
    String str = null;
    PackageManager packageManager = paramContext.getPackageManager();
    try {
      ApplicationInfo applicationInfo = packageManager.getApplicationInfo(paramContext.getPackageName(), 128);
      paramContext = str;
      if (applicationInfo != null) {
        paramContext = str;
        if (applicationInfo.metaData != null)
          Object object = applicationInfo.metaData.get(paramString); 
      } 
    } catch (Exception paramContext) {
      paramContext.printStackTrace();
      paramContext = str;
    } 
    str = paramContext;
    if (paramContext == null) {
      Log.e("MetaDataValue", "请查看重要错误日志:Manifests中是否配置 " + paramString + " ?");
      str = new String();
    } 
    return str.toString();
  }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\yz\action\apiadapter\Util.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */