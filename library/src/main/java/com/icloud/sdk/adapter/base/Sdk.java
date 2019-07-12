package com.icloud.sdk.adapter.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import com.icloud.sdk.config.HttpConfig;
import com.icloud.sdk.listener.CallbackListener;
import com.icloud.sdk.listener.ResultCode;
import com.icloud.sdk.model.PhpInfo;
import com.icloud.sdk.utils.OkHttpUtil;
import com.yz.action.ActionSdk;
import okhttp3.Call;
import okhttp3.Response;
import org.json.JSONObject;

public class Sdk {
  private static Sdk instance;
  
  public static Sdk getInstance() {
    if (instance == null)
      instance = new Sdk(); 
    return instance;
  }
  
  public void initApplication(Application paramApplication) { ActionSdk.getInstance().initApplication(paramApplication); }
  
  public void onCreate(Activity paramActivity) { ActionSdk.getInstance().onCreate(paramActivity); }
  
  public void onPause(Activity paramActivity) { ActionSdk.getInstance().onPause(paramActivity); }
  
  public void onResume(Activity paramActivity) { ActionSdk.getInstance().onResume(paramActivity); }
  
  public void serverInit(Context paramContext, String paramString, final CallbackListener list) { OkHttpUtil.postNoLoading(paramContext, HttpConfig.SERVER_INIT, paramString, new OkHttpUtil.SimpleResponseHandler() {
          public void onFailure(Exception param1Exception) {
            if (list != null)
              list.onResult(ResultCode.Fail, "初始化失败", param1Exception.toString()); 
          }
          
          public void onSuccess(Call param1Call, Response param1Response) {
            try {
              JSONObject jSONObject = new JSONObject(new String(param1Response.body().string()));
              String str1 = jSONObject.optString("result", "");
              String str2 = jSONObject.optString("message", "");
              if (list != null) {
                if ("success".equals(str1)) {
                  PhpInfo.getInstance().init(jSONObject.optString("data", ""));
                  list.onResult(ResultCode.SUCCESS, "初始化成功", str2);
                  return;
                } 
                list.onResult(ResultCode.Fail, "初始化成功", str2);
                return;
              } 
            } catch (Exception e) {
              if (list != null)
                list.onResult(ResultCode.Fail, "初始化失败", e.toString()); 
            } 
          }
        }); }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\icloud\sdk\adapter\base\Sdk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */
