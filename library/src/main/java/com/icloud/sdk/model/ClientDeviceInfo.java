package com.icloud.sdk.model;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import com.icloud.sdk.utils.DUtils;
import com.icloud.sdk.utils.SharedPreferenceUtil;
import java.util.Map;

public class ClientDeviceInfo {
  private static ClientDeviceInfo instance;
  
  public String gameName;
  
  public String imei = "";
  
  public String imsi = "";
  
  public String mac = "";
  
  public String nickname = "";
  
  public String system = "";
  
  public String version = "";
  
  public static Map<String, String> collectDeviceInfo(Context paramContext) { // Byte code:
    //   0: new java/util/HashMap
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: astore_3
    //   8: aload_0
    //   9: invokevirtual getPackageManager : ()Landroid/content/pm/PackageManager;
    //   12: aload_0
    //   13: invokevirtual getPackageName : ()Ljava/lang/String;
    //   16: iconst_1
    //   17: invokevirtual getPackageInfo : (Ljava/lang/String;I)Landroid/content/pm/PackageInfo;
    //   20: astore #4
    //   22: aload #4
    //   24: ifnull -> 111
    //   27: aload #4
    //   29: getfield versionName : Ljava/lang/String;
    //   32: ifnonnull -> 204
    //   35: ldc 'null'
    //   37: astore_0
    //   38: new java/lang/StringBuilder
    //   41: dup
    //   42: invokespecial <init> : ()V
    //   45: aload #4
    //   47: getfield versionCode : I
    //   50: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   53: ldc ''
    //   55: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   58: invokevirtual toString : ()Ljava/lang/String;
    //   61: astore #4
    //   63: aload_3
    //   64: ldc 'versionName'
    //   66: aload_0
    //   67: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   72: pop
    //   73: aload_3
    //   74: ldc 'versionCode'
    //   76: aload #4
    //   78: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   83: pop
    //   84: new java/lang/StringBuilder
    //   87: dup
    //   88: invokespecial <init> : ()V
    //   91: aload_0
    //   92: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   95: ldc '*'
    //   97: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   100: aload #4
    //   102: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   105: invokevirtual toString : ()Ljava/lang/String;
    //   108: invokestatic i : (Ljava/lang/Object;)V
    //   111: ldc android/os/Build
    //   113: invokevirtual getDeclaredFields : ()[Ljava/lang/reflect/Field;
    //   116: astore_0
    //   117: aload_0
    //   118: arraylength
    //   119: istore_2
    //   120: iconst_0
    //   121: istore_1
    //   122: iload_1
    //   123: iload_2
    //   124: if_icmpge -> 235
    //   127: aload_0
    //   128: iload_1
    //   129: aaload
    //   130: astore #4
    //   132: aload #4
    //   134: iconst_1
    //   135: invokevirtual setAccessible : (Z)V
    //   138: aload_3
    //   139: aload #4
    //   141: invokevirtual getName : ()Ljava/lang/String;
    //   144: aload #4
    //   146: aconst_null
    //   147: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   150: invokevirtual toString : ()Ljava/lang/String;
    //   153: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   158: pop
    //   159: new java/lang/StringBuilder
    //   162: dup
    //   163: invokespecial <init> : ()V
    //   166: aload #4
    //   168: invokevirtual getName : ()Ljava/lang/String;
    //   171: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   174: ldc '*'
    //   176: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   179: aload #4
    //   181: aconst_null
    //   182: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   185: invokevirtual toString : ()Ljava/lang/String;
    //   188: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   191: invokevirtual toString : ()Ljava/lang/String;
    //   194: invokestatic i : (Ljava/lang/Object;)V
    //   197: iload_1
    //   198: iconst_1
    //   199: iadd
    //   200: istore_1
    //   201: goto -> 122
    //   204: aload #4
    //   206: getfield versionName : Ljava/lang/String;
    //   209: astore_0
    //   210: goto -> 38
    //   213: astore_0
    //   214: ldc 'an error occured when collect package info'
    //   216: aload_0
    //   217: invokestatic e : (Ljava/lang/String;Ljava/lang/Exception;)V
    //   220: goto -> 111
    //   223: astore #4
    //   225: ldc 'an error occured when collect crash info'
    //   227: aload #4
    //   229: invokestatic e : (Ljava/lang/String;Ljava/lang/Exception;)V
    //   232: goto -> 197
    //   235: aload_3
    //   236: areturn
    // Exception table:
    //   from	to	target	type
    //   8	22	213	android/content/pm/PackageManager$NameNotFoundException
    //   27	35	213	android/content/pm/PackageManager$NameNotFoundException
    //   38	111	213	android/content/pm/PackageManager$NameNotFoundException
    //   132	197	223	java/lang/Exception
    //   204	210	213	android/content/pm/PackageManager$NameNotFoundException }
  
  public static ClientDeviceInfo getInstance() {
    if (instance == null)
      instance = new ClientDeviceInfo(); 
    return instance;
  }
  
  private static String getSign(Context paramContext) {
    PackageManager packageManager = paramContext.getPackageManager();
    try {
      return new String((packageManager.getPackageInfo(paramContext.getPackageName(), 1)).signatures[0].toByteArray());
    } catch (android.content.pm.PackageManager.NameNotFoundException paramContext) {
      paramContext.printStackTrace();
    } catch (Exception paramContext) {
      paramContext.printStackTrace();
    } 
    return "";
  }
  
  public void getDeviceModel() {
    this.system = Build.VERSION.SDK_INT + "";
    this.nickname = Build.MODEL;
  }
  
  public void getGameName(Context paramContext) { this.gameName = paramContext.getPackageName(); }
  
  public void getMacAddress(Context paramContext) {
    WifiManager wifiManager = (WifiManager)paramContext.getSystemService("wifi");
    if (wifiManager != null) {
      WifiInfo wifiInfo = wifiManager.getConnectionInfo();
      if (wifiInfo != null)
        this.mac = wifiInfo.getMacAddress(); 
    } 
    if (this.mac == null) {
      this.mac = SharedPreferenceUtil.getmac();
      return;
    } 
    SharedPreferenceUtil.setMac(this.mac);
  }
  
  public void getMachineId(Context paramContext) {
    if (DUtils.checkPermission(paramContext, "android.permission.READ_PHONE_STATE")) {
      TelephonyManager telephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
      if (telephonyManager != null) {
        this.imsi = telephonyManager.getSubscriberId();
        this.imei = telephonyManager.getDeviceId();
      } 
      if (this.imei == null) {
        this.imei = SharedPreferenceUtil.getImei();
      } else {
        SharedPreferenceUtil.setImei(this.imei);
      } 
      if (this.imsi == null) {
        this.imsi = "";
        return;
      } 
    } 
  }
  
  public void init(Application paramApplication) {
    getMachineId(paramApplication);
    getMacAddress(paramApplication);
    getDeviceModel();
    getGameName(paramApplication);
  }
  
  public String toString() { return "ClientDeviceInfo [brandid=, version=" + this.version + ", mac=" + this.mac + ", imei=" + this.imei + ", imsi=" + this.imsi + ", unionid=, system=" + this.system + ", province=, area=, nickname=" + this.nickname + "]"; }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\icloud\sdk\model\ClientDeviceInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */