package com.icloud.sdk.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Locale;
import org.json.JSONObject;

public class DUtils {
  public static boolean checkPermission(Context paramContext, String paramString) {
    boolean bool = false;
    if (paramContext == null)
      return false; 
    if (Build.VERSION.SDK_INT >= 23) {
      try {
        int i = ((Integer)Class.forName("android.content.Context").getMethod("checkSelfPermission", new Class[] { String.class }).invoke(paramContext, new Object[] { paramString })).intValue();
        if (i == 0)
          return true; 
        bool = false;
      } catch (Throwable e) {
        bool = false;
      } 
      return bool;
    } 
    if (paramContext.getPackageManager().checkPermission(paramString, paramContext.getPackageName()) == PackageManager.PERMISSION_GRANTED)
      bool = true; 
    return bool;
  }
  
  public static String getDeviceInfo(Context paramContext) {
    try {
      JSONObject jSONObject = new JSONObject();
      TelephonyManager telephonyManager = (TelephonyManager)paramContext.getSystemService(Context.TELEPHONY_SERVICE);
      String str1 = null;
      if (checkPermission(paramContext, "android.permission.READ_PHONE_STATE"))
        str1 = telephonyManager.getDeviceId(); 
      String str3 = getMac(paramContext);
      jSONObject.put("mac", str3);
      String str2 = str1;
      if (TextUtils.isEmpty(str1))
        str2 = str3; 
      str1 = str2;
      if (TextUtils.isEmpty(str2))
        str1 = Settings.Secure.getString(paramContext.getContentResolver(), "android_id"); 
      jSONObject.put("device_id", str1);
      return jSONObject.toString();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public static String getMac(Context paramContext) {
    if (paramContext == null)
      return ""; 
    if (Build.VERSION.SDK_INT < 23)
      return getMacBySystemInterface(paramContext); 
    String str2 = getMacByJavaAPI();
    String str1 = str2;
    if (TextUtils.isEmpty(str2))
      str1 = getMacBySystemInterface(paramContext); 
    return str1;
  }
  
  @TargetApi(9)
  private static String getMacByJavaAPI() {
    byte b = 0;
    try {
      Enumeration enumeration = NetworkInterface.getNetworkInterfaces();
      while (enumeration.hasMoreElements()) {
        NetworkInterface networkInterface = (NetworkInterface)enumeration.nextElement();
        if ("wlan0".equals(networkInterface.getName()) || "eth0".equals(networkInterface.getName())) {
          byte[] arrayOfByte = networkInterface.getHardwareAddress();
          if (arrayOfByte != null) {
            if (arrayOfByte.length == 0)
              return null; 
            StringBuilder stringBuilder = new StringBuilder();
            int i = arrayOfByte.length;
            while (b < i) {
              stringBuilder.append(String.format("%02X:", new Object[] { Byte.valueOf(arrayOfByte[b]) }));
              b++;
            } 
            if (stringBuilder.length() > 0)
              stringBuilder.deleteCharAt(stringBuilder.length() - 1); 
            return stringBuilder.toString().toLowerCase(Locale.getDefault());
          } 
          break;
        } 
      } 
    } catch (Throwable throwable) {}
    return null;
  }
  
  private static String getMacBySystemInterface(Context paramContext) {
    if (paramContext == null)
      return ""; 
    try {
      WifiManager wifiManager = (WifiManager)paramContext.getSystemService(Context.WIFI_SERVICE);
      return checkPermission(paramContext, "android.permission.ACCESS_WIFI_STATE") ? wifiManager.getConnectionInfo().getMacAddress() : "";
    } catch (Throwable e) {
      return "";
    } 
  }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\icloud\sd\\utils\DUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */
