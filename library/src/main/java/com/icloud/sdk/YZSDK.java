package com.icloud.sdk;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.icloud.sdk.adapter.base.Account;
import com.icloud.sdk.adapter.base.Pay;
import com.icloud.sdk.adapter.base.Sdk;
import com.icloud.sdk.config.HttpConfig;
import com.icloud.sdk.config.PayType;
import com.icloud.sdk.config.Platform;
import com.icloud.sdk.listener.CallbackListener;
import com.icloud.sdk.listener.ResultCode;
import com.icloud.sdk.model.ClientDeviceInfo;
import com.icloud.sdk.model.ConfigInfo;
import com.icloud.sdk.model.User;
import com.icloud.sdk.utils.FileUtils;
import com.icloud.sdk.utils.LogUtil;
import com.icloud.sdk.utils.SharedPreferenceUtil;
import com.icloud.sdk.utils.Util;
import com.icloud.sdk.view.AutoLoginPop;
import com.icloud.sdk.view.LoginPop;
import com.icloud.sdk.view.WebPaySelectPop;
import com.tencent.mm.opensdk.utils.Log;
import org.json.JSONObject;

public class YZSDK extends BaseYZSDK {
  private static YZSDK _instance;
  
  private boolean comLogin(Context paramContext, String paramString, CallbackListener paramCallbackListener, boolean paramBoolean) {
    if (TextUtils.isEmpty(paramString))
      str = "{}"; 
    if (paramContext == null || (paramContext instanceof Activity && ((Activity)paramContext).isFinishing())) {
      LogUtil.e("login", "Context is null or isFinishing ");
      if (paramCallbackListener != null)
        paramCallbackListener.onResult(ResultCode.Fail, "Context 为空", ""); 
      return false;
    } 
    if (!Util.isNetworkAvailable(paramContext)) {
      if (paramCallbackListener != null)
        paramCallbackListener.onResult(ResultCode.Fail, "网络连接失败", ""); 
      return false;
    } 
    try {
      JSONObject jSONObject = new JSONObject(paramString);
      jSONObject.put("gameId", (ConfigInfo.getInstance()).GAME_ID);
      jSONObject.put("channelId", (ConfigInfo.getInstance()).CHANNEL_ID);
      jSONObject.put("zoneId", 0);
      jSONObject.put("zoneName", Util.getAppName(paramContext));
      jSONObject.put("deviceKey", SharedPreferenceUtil.getImei());
      jSONObject.put("phoneModel", (ClientDeviceInfo.getInstance()).nickname);
      jSONObject.put("platform", Platform.Android);
      jSONObject.put("timeStamp", FileUtils.getSecondTimestamp());
      jSONObject.put("signType", "md5");
      paramString = jSONObject.toString();
    } catch (Exception e) {
      Log.e("comLogin", e.toString());
    } 
    if (paramBoolean) {
        return Account.getInstance().loginCheck(ctx, jsonStr, listener);
    } else {
        return Account.getInstance().login(ctx, jsonStr, listener);
    }
  }
  
  public static YZSDK instance() {
    if (_instance == null)
      _instance = new YZSDK(); 
    return _instance;
  }
  
  private boolean pay(Activity paramActivity, String paramString1, String paramString2, String paramString3, int paramInt, String paramString4, String paramString5, CallbackListener paramCallbackListener) {
    if (paramActivity == null || (paramActivity instanceof Activity && paramActivity.isFinishing())) {
      LogUtil.e("getOrder", "Context is null or isFinishing ");
      return false;
    } 
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("gameId", (ConfigInfo.getInstance()).GAME_ID);
      jSONObject.put("playerId", User.getInstance().getPlayerId());
      jSONObject.put("channelId", (ConfigInfo.getInstance()).CHANNEL_ID);
      jSONObject.put("zoneId", 0);
      jSONObject.put("zoneName", Util.getAppName(paramActivity));
      jSONObject.put("productId", paramString1);
      jSONObject.put("productName", paramString2);
      jSONObject.put("productCount", 1);
      jSONObject.put("productInfo", paramString3);
      jSONObject.put("productFee", paramInt);
      jSONObject.put("tradeMethod", paramString4);
      if (!TextUtils.isEmpty(paramString5))
        jSONObject.put("attach", paramString5); 
      jSONObject.put("timeStamp", FileUtils.getSecondTimestamp());
      jSONObject.put("signType", "md5");
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return Pay.getInstance().pay(paramActivity, jSONObject.toString(), paramCallbackListener);
  }
  
  private void serverInit(Context paramContext, CallbackListener paramCallbackListener) {
    if (!Util.isNetworkAvailable(paramContext)) {
      if (paramCallbackListener != null)
        paramCallbackListener.onResult(ResultCode.Fail, "网络连接失败", ""); 
      return;
    } 
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("gameId", (ConfigInfo.getInstance()).GAME_ID);
      jSONObject.put("channelId", (ConfigInfo.getInstance()).CHANNEL_ID);
      jSONObject.put("deviceKey", SharedPreferenceUtil.getImei());
      jSONObject.put("phoneModel", (ClientDeviceInfo.getInstance()).nickname);
      jSONObject.put("platform", Platform.Android);
      jSONObject.put("timeStamp", FileUtils.getSecondTimestamp());
      jSONObject.put("signType", "md5");
    } catch (Exception exception) {}
    Sdk.getInstance().serverInit(paramContext, jSONObject.toString(), paramCallbackListener);
  }
  
  public boolean aliPay(Activity paramActivity, String paramString1, String paramString2, String paramString3, int paramInt, String paramString4, CallbackListener paramCallbackListener) {
    Pay.getInstance().setCurrentPayType(PayType.AliPay);
    return pay(paramActivity, paramString1, paramString2, paramString3, paramInt, PayType.AliPay, paramString4, paramCallbackListener);
  }
  
  public boolean autoPay(Activity paramActivity, String paramString1, String paramString2, String paramString3, int paramInt, String paramString4, CallbackListener paramCallbackListener) {
    (new WebPaySelectPop(paramActivity, paramString1, paramString2, paramString3, paramInt, paramString4, paramCallbackListener)).show();
    return true;
  }
  
  public boolean bindPhone(Context paramContext, String paramString1, String paramString2, CallbackListener paramCallbackListener) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("gameId", (ConfigInfo.getInstance()).GAME_ID);
      jSONObject.put("playerId", User.getInstance().getPlayerId());
      jSONObject.put("accessToken", User.getInstance().getAccessToken());
      jSONObject.put("phone", paramString1);
      jSONObject.put("phoneCode", paramString2);
      jSONObject.put("timeStamp", FileUtils.getSecondTimestamp());
      jSONObject.put("signType", "md5");
    } catch (Exception e) {}
    return Account.getInstance().bindPhone(paramContext, jSONObject.toString(), paramCallbackListener);
  }
  
  public boolean changePwd(Context paramContext, String paramString1, String paramString2, String paramString3, CallbackListener paramCallbackListener) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("gameId", (ConfigInfo.getInstance()).GAME_ID);
      jSONObject.put("account", paramString1);
      jSONObject.put("oldPassword", paramString2);
      jSONObject.put("newPassword", paramString3);
      jSONObject.put("timeStamp", FileUtils.getSecondTimestamp());
      jSONObject.put("signType", "md5");
    } catch (Exception e) {}
    return Account.getInstance().changePwd(paramContext, jSONObject.toString(), paramCallbackListener);
  }
  
  public boolean checkLogin(Context paramContext, String paramString1, String paramString2, Account.LoginType paramLoginType, CallbackListener paramCallbackListener) {
    JSONObject jSONObject = new JSONObject();
    try {
      if (!TextUtils.isEmpty(paramString1))
        jSONObject.put("account", paramString1); 
      if (!TextUtils.isEmpty(paramString2))
        jSONObject.put("password", paramString2); 
      jSONObject.put("loginType", paramLoginType);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return comLogin(paramContext, jSONObject.toString(), paramCallbackListener, true);
  }
  
  public void exitSDK(Context paramContext, CallbackListener paramCallbackListener) {
    if (paramCallbackListener != null)
      paramCallbackListener.onResult(ResultCode.SUCCESS, "", ""); 
  }
  
  public boolean fastLogin(Context paramContext, CallbackListener paramCallbackListener) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("accessToken", SharedPreferenceUtil.getAccessToken());
      jSONObject.put("loginType", Account.LoginType.quick);
    } catch (Exception exception) {}
    return comLogin(paramContext, jSONObject.toString(), paramCallbackListener, false);
  }
  
  public void init(Activity paramActivity, CallbackListener paramCallbackListener) {
    super.init(paramActivity, paramCallbackListener);
    serverInit(paramActivity, paramCallbackListener);
  }
  
  public void initApp(Application paramApplication, CallbackListener paramCallbackListener) {
    super.initApp(paramApplication, paramCallbackListener);
    HttpConfig.init((ConfigInfo.getInstance()).SERVER_URL);
    paramCallbackListener.onResult(ResultCode.SUCCESS, "", "");
  }
  
  public void login(Context paramContext, CallbackListener paramCallbackListener) {
    if (TextUtils.isEmpty(SharedPreferenceUtil.getAccessToken())) {
      (new LoginPop(paramContext, paramCallbackListener)).show();
      return;
    } 
    (new AutoLoginPop(paramContext, paramCallbackListener)).show();
  }
  
  public boolean login(Context paramContext, String paramString1, String paramString2, Account.LoginType paramLoginType, CallbackListener paramCallbackListener) {
    JSONObject jSONObject = new JSONObject();
    try {
      if (!TextUtils.isEmpty(paramString1))
        jSONObject.put("account", paramString1); 
      if (!TextUtils.isEmpty(paramString2))
        jSONObject.put("password", paramString2); 
      jSONObject.put("loginType", paramLoginType);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return comLogin(paramContext, jSONObject.toString(), paramCallbackListener, false);
  }
  
  public void logout(Context paramContext) { Account.getInstance().logout(paramContext); }
  
  public boolean notice(Context paramContext, CallbackListener paramCallbackListener) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("gameId", (ConfigInfo.getInstance()).GAME_ID);
      jSONObject.put("channelId", (ConfigInfo.getInstance()).CHANNEL_ID);
      jSONObject.put("zoneId", 0);
      jSONObject.put("timeStamp", FileUtils.getSecondTimestamp());
      jSONObject.put("signType", "md5");
    } catch (Exception exception) {}
    return Account.getInstance().notice(paramContext, jSONObject, paramCallbackListener);
  }
  
  public boolean regist(Context paramContext, String paramString1, String paramString2, CallbackListener paramCallbackListener) {
    if (TextUtils.isEmpty(paramString2)) {
      if (paramCallbackListener != null)
        paramCallbackListener.onResult(ResultCode.Fail, "用户名或密码为空", ""); 
      return false;
    } 
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("gameId", (ConfigInfo.getInstance()).GAME_ID);
      jSONObject.put("channelId", (ConfigInfo.getInstance()).CHANNEL_ID);
      jSONObject.put("account", paramString1);
      jSONObject.put("password", paramString2);
      jSONObject.put("deviceKey", SharedPreferenceUtil.getImei());
      jSONObject.put("phoneModel", (ClientDeviceInfo.getInstance()).nickname);
      jSONObject.put("platform", Platform.Android);
      jSONObject.put("timeStamp", FileUtils.getSecondTimestamp());
      jSONObject.put("signType", "md5");
    } catch (Exception exception) {}
    return Account.getInstance().register(paramContext, jSONObject.toString(), paramCallbackListener);
  }
  
  public boolean resetPwd(Context paramContext, String paramString1, String paramString2, String paramString3, CallbackListener paramCallbackListener) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("gameId", (ConfigInfo.getInstance()).GAME_ID);
      jSONObject.put("phone", paramString1);
      jSONObject.put("phoneCode", paramString2);
      jSONObject.put("password", paramString3);
      jSONObject.put("timeStamp", FileUtils.getSecondTimestamp());
      jSONObject.put("signType", "md5");
    } catch (Exception exception) {}
    return Account.getInstance().resetPwd(paramContext, jSONObject.toString(), paramCallbackListener);
  }
  
  public boolean sendSMSInAcc(Context paramContext, String paramString, Account.SendCodeType paramSendCodeType, CallbackListener paramCallbackListener) {
    if (TextUtils.isEmpty(paramString)) {
      if (paramCallbackListener != null)
        paramCallbackListener.onResult(ResultCode.Fail, "手机号为空", ""); 
      return false;
    } 
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("gameId", (ConfigInfo.getInstance()).GAME_ID);
      jSONObject.put("phone", paramString);
      jSONObject.put("type", paramSendCodeType.toString());
      jSONObject.put("timeStamp", FileUtils.getSecondTimestamp());
      jSONObject.put("signType", "md5");
    } catch (Exception exception) {}
    return Account.getInstance().sendMsgToPhone(paramContext, jSONObject, paramCallbackListener);
  }
  
  public boolean wxPay(Activity paramActivity, String paramString1, String paramString2, String paramString3, int paramInt, String paramString4, CallbackListener paramCallbackListener) {
    Pay.getInstance().setCurrentPayType(PayType.WXPay);
    return pay(paramActivity, paramString1, paramString2, paramString3, paramInt, PayType.WXPay, paramString4, paramCallbackListener);
  }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\icloud\sdk\YZSDK.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */
