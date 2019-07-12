package com.yz.action.apiadapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

public class Util {
  public static String getAppName(Context paramContext) {
    try {
      PackageManager packageManager = paramContext.getPackageManager();
      return packageManager.getApplicationLabel(packageManager.getApplicationInfo(paramContext.getPackageName(), PackageManager.GET_META_DATA)).toString();
    } catch (android.content.pm.PackageManager.NameNotFoundException e) {
      e.printStackTrace();
      return null;
    } 
  }
  
    public static String getMetaDataValue(Context ctx, String name) {
        Object value = null;
        PackageManager packageManager = ctx.getPackageManager();

        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), 128);
            if (applicationInfo != null && applicationInfo.metaData != null) {
                value = applicationInfo.metaData.get(name);
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        if (value == null) {
            Log.e("MetaDataValue", "请查看重要错误日志:Manifests中是否配置 " + name + " ?");
            value = new String();
        }

        return value.toString();
    }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\yz\action\apiadapter\Util.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */
