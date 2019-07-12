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

  public static Map<String, String> collectDeviceInfo(Context paramContext) {
    return null;
  }

    public static ClientDeviceInfo getInstance(){
      if (instance == null)
        instance = new ClientDeviceInfo();
      return instance;
    }

    private static String getSign (Context paramContext){
      PackageManager packageManager = paramContext.getPackageManager();
      try {
        return new String((packageManager.getPackageInfo(paramContext.getPackageName(), 1)).signatures[0].toByteArray());
      } catch (android.content.pm.PackageManager.NameNotFoundException e) {
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      }
      return "";
    }

    public void getDeviceModel () {
      this.system = Build.VERSION.SDK_INT + "";
      this.nickname = Build.MODEL;
    }

    public void getGameName (Context paramContext){
      this.gameName = paramContext.getPackageName();
    }

    public void getMacAddress (Context paramContext){
      WifiManager wifiManager = (WifiManager) paramContext.getSystemService("wifi");
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

    public void getMachineId(Context mContext) {
        if (!DUtils.checkPermission(mContext, Manifest.permission.READ_PHONE_STATE)) {
            return;
        }
        TelephonyManager telephonyManager = (TelephonyManager) mContext
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            imsi = telephonyManager.getSubscriberId();
            imei = telephonyManager.getDeviceId();
        }

        if (imei == null) {
            imei = SharedPreferenceUtil.getImei();
        } else {
            SharedPreferenceUtil.setImei(imei);
        }

        if (imsi == null) {
            imsi = "";
        }
    }

    public void init (Application paramApplication){
      getMachineId(paramApplication);
      getMacAddress(paramApplication);
      getDeviceModel();
      getGameName(paramApplication);
    }

    public String toString() {
        return "ClientDeviceInfo [brandid=" + ", version="
                + version + ", mac=" + mac + ", imei=" + imei + ", imsi=" + imsi + ", unionid=" +
                ", system=" + system + ", province=" + "" + ", area=" + "" + ", nickname=" + nickname + "]";
    }
}

/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\icloud\sdk\model\ClientDeviceInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */
