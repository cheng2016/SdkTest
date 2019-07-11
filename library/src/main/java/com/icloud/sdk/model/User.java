package com.icloud.sdk.model;

import android.text.TextUtils;
import com.icloud.sdk.utils.SharedPreferenceUtil;

public class User {
  private static User instance;
  
  private String accessToken;
  
  private String playerId;
  
  private String dealWith(String paramString) { return paramString.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"); }
  
  public static User getInstance() {
    if (instance == null)
      instance = new User(); 
    return instance;
  }
  
  public String getAccessToken() { return !TextUtils.isEmpty(this.accessToken) ? this.accessToken : SharedPreferenceUtil.getAccessToken(); }
  
  public String getPlayerId() { return this.playerId; }
  
  public boolean isPhone(String paramString) { return (paramString == null && paramString.length() != 11) ? false : paramString.matches("\\d+"); }
  
  public void logout() {
    this.playerId = null;
    this.accessToken = null;
    SharedPreferenceUtil.setAccessToken("");
  }
  
  public void upUserInfo(String paramString1, String paramString2, String paramString3, String paramString4) {
    this.playerId = paramString2;
    this.accessToken = paramString3;
    if (TextUtils.isEmpty(SharedPreferenceUtil.getImei()))
      SharedPreferenceUtil.setImei(paramString4); 
    SharedPreferenceUtil.setAccessToken(paramString3);
    if (TextUtils.isEmpty(paramString1)) {
      SharedPreferenceUtil.saveNikeName((ClientDeviceInfo.getInstance()).nickname);
      return;
    } 
    if (isPhone(paramString1)) {
      SharedPreferenceUtil.saveNikeName(dealWith(paramString1));
      return;
    } 
    SharedPreferenceUtil.saveNikeName(paramString1);
  }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\icloud\sdk\model\User.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */