package com.icloud.sdk.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.icloud.sdk.YZSDK;
import com.icloud.sdk.adapter.base.Account;
import com.icloud.sdk.listener.CallbackListener;
import com.icloud.sdk.listener.ResultCode;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import java.io.ByteArrayOutputStream;
import org.json.JSONObject;

public class WeiXinUtil {
  public static String APP_ID = "";
  
  public static final int THUMB_SIZE = 150;
  
  static WeiXinUtil instance;
  
  public String TAG = getClass().getSimpleName();
  
  CallbackListener callbackListener;
  
  Context ctx;
  
  CallbackListener loginListener;
  
  IWXAPI msgApi;
  
  CallbackListener payListener;
  
  StringBuffer sb = new StringBuffer();
  
  public static byte[] bmpToByteArray(Bitmap paramBitmap, boolean paramBoolean) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    paramBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
    if (paramBoolean)
      paramBitmap.recycle(); 
    byte[] arrayOfByte = byteArrayOutputStream.toByteArray();
    try {
      byteArrayOutputStream.close();
      return arrayOfByte;
    } catch (Exception e) {
      e.printStackTrace();
      return arrayOfByte;
    } 
  }
  
  private String buildTransaction(String paramString) { return (paramString == null) ? String.valueOf(System.currentTimeMillis()) : (paramString + System.currentTimeMillis()); }
  
  public static WeiXinUtil instance() {
    if (instance == null)
      instance = new WeiXinUtil(); 
    return instance;
  }
  
  public static void setAppId(String paramString) { APP_ID = paramString; }
  
  public boolean isWXAppInstalled(Context paramContext) {
    regist(paramContext);
    return (this.msgApi != null && this.msgApi.isWXAppInstalled());
  }
  
  protected void login(BaseResp paramBaseResp) {
    if (paramBaseResp.errCode == 0) {
      String str = ((SendAuth.Resp)paramBaseResp).code;
      YZSDK.instance().login(this.ctx, str, null, Account.LoginType.wechat, this.loginListener);
    } 
  }
  
  public boolean login(Context paramContext, CallbackListener paramCallbackListener) {
    if (paramContext == null || (paramContext instanceof Activity && ((Activity)paramContext).isFinishing())) {
      LogUtil.e("login", "Context is null or isFinishing ");
      if (paramCallbackListener != null)
        paramCallbackListener.onResult(ResultCode.Fail, "Context 为空", ""); 
      return false;
    } 
    this.ctx = paramContext;
    regist(paramContext);
    if (!isWXAppInstalled(paramContext)) {
      if (paramCallbackListener != null)
        paramCallbackListener.onResult(ResultCode.Fail, "WeChat Not Install", ""); 
      return false;
    } 
    SendAuth.Req req = new SendAuth.Req();
    req.scope = "snsapi_userinfo";
    req.state = "wechat_sdk_demo_test";
    this.msgApi.sendReq(req);
    this.loginListener = paramCallbackListener;
    return true;
  }
  
  public void onResp(BaseResp paramBaseResp) {
    Log.e("WeiXinUtil", paramBaseResp.errCode + "");
    if (paramBaseResp.getType() == 2 && this.callbackListener != null) {
      switch (paramBaseResp.errCode) {
        default:
          this.callbackListener.onResult(ResultCode.Fail, "失败", paramBaseResp.errStr);
          this.callbackListener = null;
          return;
        case 0:
          this.callbackListener.onResult(ResultCode.SUCCESS, "success", paramBaseResp.errStr);
          this.callbackListener = null;
          return;
        case -2:
          break;
      } 
      this.callbackListener.onResult(ResultCode.CANCEL, "取消", paramBaseResp.errStr);
    } else {
      if (paramBaseResp.getType() == 5 && this.payListener != null) {
        switch (paramBaseResp.errCode) {
          default:
            this.payListener.onResult(ResultCode.Fail, paramBaseResp.errStr, "");
            return;
          case 0:
            this.payListener.onResult(ResultCode.SUCCESS, paramBaseResp.errStr, "");
            return;
          case -2:
            break;
        } 
        this.payListener.onResult(ResultCode.CANCEL, paramBaseResp.errStr, "");
        return;
      } 
      if (this.loginListener != null) {
        switch (paramBaseResp.errCode) {
          default:
            this.loginListener.onResult(ResultCode.Fail, "登录失败", paramBaseResp.errStr);
            this.loginListener = null;
          case 0:
            login(paramBaseResp);
            this.loginListener = null;
          case -2:
            this.loginListener.onResult(ResultCode.CANCEL, "登录取消", paramBaseResp.errStr);
            this.loginListener = null;
          case -4:
            this.loginListener.onResult(ResultCode.Fail, "登录失败", paramBaseResp.errStr);
            this.loginListener = null;
          case -5:
            break;
        } 
        this.loginListener.onResult(ResultCode.Fail, "登录失败", paramBaseResp.errStr);
      } else {
        return;
      } 
      this.loginListener = null;
    } 
    this.callbackListener = null;
  }
  
  public void pay(Activity paramActivity, JSONObject paramJSONObject, CallbackListener paramCallbackListener) {
    Log.i("WeiXinUtil", "pay");
    if (paramActivity == null || (paramActivity instanceof Activity && paramActivity.isFinishing())) {
      LogUtil.e("login", "Context is null or isFinishing ");
      if (this.loginListener != null)
        paramCallbackListener.onResult(ResultCode.Fail, "Context 为空", ""); 
    } 
    this.payListener = paramCallbackListener;
    regist(paramActivity);
    if (!isWXAppInstalled(paramActivity) && this.payListener != null)
      this.payListener.onResult(ResultCode.Fail, "Not Install", ""); 
    PayReq payReq = new PayReq();
    payReq.appId = APP_ID;
    payReq.partnerId = paramJSONObject.optString("partnerid");
    payReq.prepayId = paramJSONObject.optString("prepayid");
    payReq.packageValue = paramJSONObject.optString("package");
    payReq.nonceStr = paramJSONObject.optString("noncestr");
    payReq.timeStamp = paramJSONObject.optString("timestamp");
    payReq.sign = paramJSONObject.optString("sign");
    this.msgApi.sendReq(payReq);
  }
  
  protected void regist(Context paramContext) {
    if (this.msgApi == null) {
      this.msgApi = WXAPIFactory.createWXAPI(paramContext, APP_ID, true);
      this.msgApi.registerApp(APP_ID);
    } 
  }
  
  public void share(Context paramContext, String paramString, CallbackListener paramCallbackListener) {
    try {
      regist(paramContext);
      this.callbackListener = paramCallbackListener;
      JSONObject jSONObject = new JSONObject(paramString);
      String str1 = jSONObject.optString("msg");
      String str2 = jSONObject.optString("title");
      String str3 = jSONObject.optString("urlPath");
      String str4 = jSONObject.optString("iconPath");
      String str5 = jSONObject.optString("imgPath");
      int i = jSONObject.optInt("scene", 1);
      if (!TextUtils.isEmpty(str3)) {
        shareUrl(str3, str2, str1, str4, 150, i);
        return;
      } 
      if (!TextUtils.isEmpty(str5)) {
        shareImg(paramContext, str5, i, 150);
        return;
      } 
      shareText(str1, i);
      return;
    } catch (Exception e) {
      return;
    } 
  }
  
  public void shareImg(Context paramContext, String paramString, int paramInt1, int paramInt2) {
    Log.e(this.TAG, "shareImg");
    WXImageObject wXImageObject = new WXImageObject();
    wXImageObject.setImagePath(paramString);
    WXMediaMessage wXMediaMessage = new WXMediaMessage();
    wXMediaMessage.mediaObject = wXImageObject;
    Bitmap bitmap1;
    Bitmap bitmap2 = (bitmap1 = BitmapFactory.decodeFile(paramString)).createScaledBitmap(bitmap1, paramInt2, paramInt2, true);
    bitmap1.recycle();
    wXMediaMessage.thumbData = Util.bmpToByteArray(bitmap2, true);
    if (wXMediaMessage.thumbData.length / 1024 > 32) {
      Toast.makeText(paramContext, "您分享的图片过大", Toast.LENGTH_SHORT).show();
      return;
    } 
    SendMessageToWX.Req req = new SendMessageToWX.Req();
    req.transaction = buildTransaction("img");
    req.message = wXMediaMessage;
    req.scene = paramInt1;
    this.msgApi.sendReq(req);
    Log.e(this.TAG, "shareImg 执行完");
  }
  
  public void shareText(String paramString, int paramInt) {
    WXTextObject wXTextObject = new WXTextObject();
    wXTextObject.text = paramString;
    WXMediaMessage wXMediaMessage = new WXMediaMessage();
    wXMediaMessage.mediaObject = wXTextObject;
    wXMediaMessage.description = paramString;
    SendMessageToWX.Req req = new SendMessageToWX.Req();
    req.transaction = buildTransaction("text");
    req.message = wXMediaMessage;
    req.scene = paramInt;
    this.msgApi.sendReq(req);
  }
  
  public void shareUrl(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2) {
    WXWebpageObject wXWebpageObject = new WXWebpageObject();
    wXWebpageObject.webpageUrl = paramString1;
    WXMediaMessage wXMediaMessage = new WXMediaMessage(wXWebpageObject);
    wXMediaMessage.title = paramString2;
    wXMediaMessage.description = paramString3;
    Bitmap bitmap1;
    Bitmap bitmap2 = (bitmap1 = BitmapFactory.decodeFile(paramString4)).createScaledBitmap(bitmap1, paramInt1, paramInt1, true);
    bitmap1.recycle();
    wXMediaMessage.thumbData = Util.bmpToByteArray(bitmap2, true);
    SendMessageToWX.Req req = new SendMessageToWX.Req();
    req.transaction = buildTransaction("webpage");
    req.message = wXMediaMessage;
    req.scene = paramInt2;
    this.msgApi.sendReq(req);
  }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\icloud\sd\\utils\WeiXinUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */