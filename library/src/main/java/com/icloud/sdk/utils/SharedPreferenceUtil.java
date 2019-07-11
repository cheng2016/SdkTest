package com.icloud.sdk.utils;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.icloud.sdk.utils.encry.RC4Http;
import com.icloud.sdk.utils.encry.TypeConvert;

public class SharedPreferenceUtil {
  private static String ACCESSTOKEN;
  
  private static String CURRENTDAY;
  
  private static final String IS_FIRST = "isFirst";
  
  private static String KEY_IMEI = "imei";
  
  private static String KEY_MAC = "macaddr";
  
  private static String NikeName;
  
  private static String PLAYERID = "playerId";
  
  private static final String USER_TYPE = "type";
  
  private static Application application;
  
  static  {
    ACCESSTOKEN = "accessToken";
    CURRENTDAY = "CURRENTDAY";
    NikeName = "NikeName";
  }
  
  public static String getAccessToken() { return getPreference().getString(ACCESSTOKEN, ""); }
  
  public static String getAccount() {
    String str2 = getPreference().getString("account", null);
    str1 = str2;
    if (str2 != null)
      try {
        byte[] arrayOfByte = TypeConvert.hexStr2ByteArr(str2);
        RC4Http.RC4Base(arrayOfByte, 0, arrayOfByte.length);
        return new String(arrayOfByte);
      } catch (Exception str1) {
        str1.printStackTrace();
        return str2;
      }  
    return str1;
  }
  
  public static String getCurrentDay() { return getPreference().getString(CURRENTDAY, "time"); }
  
  public static boolean getFirstState() { return getPreference().getBoolean("isFirst", true); }
  
  public static String getImei() { return getPreference().getString(KEY_IMEI, ""); }
  
  public static String getNikeName() { return getPreference().getString(NikeName, "name"); }
  
  public static String getPlayerId() { return getPreference().getString(PLAYERID, ""); }
  
  public static SharedPreferences getPreference() { return PreferenceManager.getDefaultSharedPreferences(application); }
  
  public static String getPsd() {
    String str2 = getPreference().getString("psd", null);
    String str1 = str2;
    if (str2 != null) {
      byte[] arrayOfByte = TypeConvert.hexStr2ByteArr(str2);
      RC4Http.RC4Base(arrayOfByte, 0, arrayOfByte.length);
      str1 = new String(arrayOfByte);
    } 
    return str1;
  }
  
  public static int getUserType() { return getPreference().getInt("type", 0); }
  
  public static String getmac() { return getPreference().getString(KEY_MAC, ""); }
  
  public static void init(Application paramApplication) { application = paramApplication; }
  
  public static void saveAccount(String paramString) {
    byte[] arrayOfByte = paramString.getBytes();
    RC4Http.RC4Base(arrayOfByte, 0, arrayOfByte.length);
    String str = TypeConvert.toHexString(arrayOfByte);
    getPreference().edit().putString("account", str).commit();
  }
  
  public static void saveFirstState(boolean paramBoolean) { getPreference().edit().putBoolean("isFirst", paramBoolean).commit(); }
  
  public static void saveNikeName(String paramString) { getPreference().edit().putString(NikeName, paramString).commit(); }
  
  public static void savePsd(String paramString) {
    byte[] arrayOfByte = paramString.getBytes();
    RC4Http.RC4Base(arrayOfByte, 0, paramString.length());
    paramString = TypeConvert.toHexString(arrayOfByte);
    getPreference().edit().putString("psd", paramString).commit();
  }
  
  public static void setAccessToken(String paramString) { getPreference().edit().putString(ACCESSTOKEN, paramString).commit(); }
  
  public static void setCurrentDay(String paramString) { getPreference().edit().putString(CURRENTDAY, paramString).commit(); }
  
  public static void setImei(String paramString) { getPreference().edit().putString(KEY_IMEI, paramString).commit(); }
  
  public static void setMac(String paramString) { getPreference().edit().putString(KEY_MAC, paramString).commit(); }
  
  public static void setPlayerId(String paramString) { getPreference().edit().putString(PLAYERID, paramString).commit(); }
  
  public static void setUserType(int paramInt) { getPreference().edit().putInt("type", 0).commit(); }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\icloud\sd\\utils\SharedPreferenceUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */