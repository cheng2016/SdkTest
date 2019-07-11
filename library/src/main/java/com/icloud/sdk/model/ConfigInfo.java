package com.icloud.sdk.model;

import android.content.Context;
import android.text.TextUtils;
import com.icloud.sdk.config.HttpConfig;
import com.icloud.sdk.listener.CallbackListener;
import com.icloud.sdk.listener.ResultCode;
import com.icloud.sdk.utils.FileUtils;
import com.icloud.sdk.utils.LogUtil;
import com.icloud.sdk.utils.Logger;
import com.icloud.sdk.utils.WeiXinUtil;

public class ConfigInfo {
  private static ConfigInfo instance;
  
  public String APP_KEY = "default";
  
  public String APP_SECRET = "default";
  
  public String CHANNEL_ID = "default";
  
  public String GAME_ID = "default";
  
  public String SERVER_URL = "default";
  
  public String WX_APP_ID = "default";
  
  public boolean isDebug = false;
  
  private String orderName = "";
  
  public static ConfigInfo getInstance() {
    if (instance == null)
      instance = new ConfigInfo(); 
    return instance;
  }
  
  public void init(Context paramContext, CallbackListener paramCallbackListener) {
    this.isDebug = Boolean.parseBoolean(FileUtils.getMetaDataValue(paramContext, "IS_DEBUG"));
    this.WX_APP_ID = FileUtils.getMetaDataValue(paramContext, "WX_APP_ID");
    this.APP_KEY = FileUtils.getMetaDataValue(paramContext, "APP_KEY");
    this.APP_SECRET = FileUtils.getMetaDataValue(paramContext, "APP_SECRET");
    this.SERVER_URL = FileUtils.getMetaDataValue(paramContext, "SERVER_URL");
    this.GAME_ID = FileUtils.getMetaDataValue(paramContext, "GAME_ID");
    this.CHANNEL_ID = FileUtils.getMetaDataValue(paramContext, "CHANNEL_ID");
    if (TextUtils.isEmpty(this.WX_APP_ID)) {
      paramCallbackListener.onResult(ResultCode.Fail, "WeChat app id is null", "");
      return;
    } 
    WeiXinUtil.instance().setAppId(this.WX_APP_ID);
    if (TextUtils.isEmpty(this.SERVER_URL) && paramCallbackListener != null)
      paramCallbackListener.onResult(ResultCode.Fail, "SERVER URL is null", ""); 
    if (TextUtils.isEmpty(this.APP_KEY) && paramCallbackListener != null)
      paramCallbackListener.onResult(ResultCode.Fail, "APP_KEY is null", ""); 
    if (TextUtils.isEmpty(this.APP_SECRET) && paramCallbackListener != null)
      paramCallbackListener.onResult(ResultCode.Fail, "APP_SECRET is null", ""); 
    setServerUrl(this.SERVER_URL);
    LogUtil.init(paramContext, this.isDebug);
    Logger.init(paramContext);
    LogUtil.e("initApp", "\nisDebug:\t" + this.isDebug + "\nWX_APP_ID:\t" + this.WX_APP_ID + "\nAPP_KEY:\t" + this.APP_KEY + "\nSERVER_URL:\t" + this.SERVER_URL + "\nAPP_SECRET:\t" + this.APP_SECRET + "\nGAME_ID:\t" + this.GAME_ID + "\nCHANNEL_ID:\t" + this.CHANNEL_ID);
  }
  
  public void setServerUrl(String paramString) {
    this.SERVER_URL = paramString;
    if (this.SERVER_URL.indexOf("http") < 0)
      this.SERVER_URL = "http://" + this.SERVER_URL; 
    HttpConfig.init(this.SERVER_URL);
  }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\icloud\sdk\model\ConfigInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */