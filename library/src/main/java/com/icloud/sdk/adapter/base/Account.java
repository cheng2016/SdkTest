package com.icloud.sdk.adapter.base;

import android.content.Context;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.icloud.sdk.bean.BaseResp;
import com.icloud.sdk.config.HttpConfig;
import com.icloud.sdk.listener.CallbackListener;
import com.icloud.sdk.listener.ResultCode;
import com.icloud.sdk.model.User;
import com.icloud.sdk.utils.FileUtils;
import com.icloud.sdk.utils.OkHttpUtil;
import com.icloud.sdk.utils.SharedPreferenceUtil;
import com.icloud.sdk.view.BindPhonePop;
import com.tencent.mm.opensdk.utils.Log;
import java.util.List;
import okhttp3.Call;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class Account {
  static final String TAG = Account.class.getSimpleName();
  
  private static Account instance;
  
  public static Account getInstance() {
    if (instance == null)
      instance = new Account(); 
    return instance;
  }
  
  private void registerSuccess(String paramString) {
    try {
      JSONObject jSONObject = new JSONObject(paramString);
      paramString = jSONObject.optString("account");
      String str = jSONObject.optString("password");
      if (!TextUtils.isEmpty(str) && str.length() >= 6) {
        SharedPreferenceUtil.saveAccount(paramString);
        SharedPreferenceUtil.savePsd(str);
      } 
      return;
    } catch (JSONException e) {
      e.printStackTrace();
      return;
    } 
  }
  
  public boolean bindPhone(Context paramContext, String paramString, final CallbackListener listener) {
    OkHttpUtil.post(paramContext, HttpConfig.BINDPHONE_URL, paramString, new OkHttpUtil.SimpleResponseHandler() {
          public void onFailure(Exception param1Exception) {
            if (listener != null)
              listener.onResult(ResultCode.Fail, "发送失败", param1Exception.toString()); 
          }
          
          public void onSuccess(Call param1Call, Response param1Response) {
            try {
              JSONObject jSONObject = new JSONObject(new String(param1Response.body().string()));
              String str1 = jSONObject.optString("result", "");
              String str2 = jSONObject.optString("message", "");
              if (listener != null) {
                if ("success".equals(str1)) {
                  listener.onResult(ResultCode.SUCCESS, str2, str2);
                  return;
                } 
                listener.onResult(ResultCode.Fail, str2, str2);
                return;
              } 
            } catch (Exception e) {
              if (listener != null)
                listener.onResult(ResultCode.Fail, "服务器参数异常", e.toString());
            } 
          }
        });
    return true;
  }
  
  public boolean changePwd(Context paramContext, String paramString, final CallbackListener listener) {
    OkHttpUtil.post(paramContext, HttpConfig.CHANGEPWD_URL, paramString, new OkHttpUtil.SimpleResponseHandler() {
          public void onFailure(Exception param1Exception) {
            if (listener != null)
              listener.onResult(ResultCode.Fail, "发送失败", param1Exception.toString()); 
          }
          
          public void onSuccess(Call call, Response param1Response) {
            try {
              JSONObject jSONObject = new JSONObject(new String(param1Response.body().string()));
              String str1 = jSONObject.optString("result", "");
              String str2 = jSONObject.optString("message", "");
              if (listener != null) {
                if ("success".equals(str1)) {
                  listener.onResult(ResultCode.SUCCESS, "修改密码成功", str2);
                  return;
                } 
                listener.onResult(ResultCode.Fail, str2, str2);
                return;
              } 
            } catch (Exception e) {
              if (listener != null)
                listener.onResult(ResultCode.Fail, "服务器参数异常", e.toString());
            } 
          }
        });
    return true;
  }
  
  protected String getName() { return "yunzhong"; }
  
  public boolean login(final Context ctx, final String jsonClient, final CallbackListener loginListener) {
    OkHttpUtil.post(paramContext, HttpConfig.LOGIN_URL, paramString, new OkHttpUtil.SimpleResponseHandler() {
          public void onFailure(Exception param1Exception) {
            if (loginListener != null)
              loginListener.onResult(ResultCode.Fail, "加载失败，请重试", param1Exception.toString()); 
          }
          
          public void onSuccess(Call call, Response param1Response) {
            try {
              String str1 = new String(param1Response.body().string());
              JSONObject jSONObject = new JSONObject(str1);
              String str2 = jSONObject.optString("result", "");
              String str3 = jSONObject.optString("message", "");
              if (str2.equals("success")) {
                jSONObject = jSONObject.optJSONObject("data");
                int i = jSONObject.optInt("type");
                str2 = jSONObject.optString("playerId");
                Account.this.registerSuccess(jsonClient);
                Account.this.loginSuccess(str1);
                if (i != 2 && !SharedPreferenceUtil.getCurrentDay().equals(str2 + FileUtils.getCurrTime())) {
                  (new BindPhonePop(ctx, new CallbackListener(this, jSONObject) {
                        public void onResult(ResultCode param2ResultCode, String param2String1, String param2String2) {
                          if (loginListener != null)
                            loginListener.onResult(ResultCode.SUCCESS, "登录成功", finaldata.toString()); 
                        }
                      })).show();
                  SharedPreferenceUtil.setCurrentDay(str2 + FileUtils.getCurrTime());
                  return;
                } 
                if (loginListener != null) {
                  loginListener.onResult(ResultCode.SUCCESS, "登录成功", jSONObject.toString());
                  return;
                } 
              } else if (loginListener != null) {
                loginListener.onResult(ResultCode.Fail, str3, str3);
              } 
            } catch (Exception e) {
              if (loginListener != null) {
                loginListener.onResult(ResultCode.Fail, "服务器参数异常", e.toString());
                return;
              } 
            } 
          }
        });
    return true;
  }
  
  public boolean loginCheck(Context paramContext, String paramString, final CallbackListener loginListener) {
    OkHttpUtil.post(paramContext, HttpConfig.LOGIN_URL, paramString, new OkHttpUtil.SimpleResponseHandler() {
          public void onFailure(Exception param1Exception) {
            if (loginListener != null)
              loginListener.onResult(ResultCode.Fail, "加载失败，请重试", param1Exception.toString()); 
          }
          
          public void onSuccess(Call param1Call, Response param1Response) {
            try {
              JSONObject jSONObject = new JSONObject(new String(param1Response.body().string()));
              String str1 = jSONObject.optString("result", "");
              String str2 = jSONObject.optString("message", "");
              if (str1.equals("success")) {
                if (loginListener != null) {
                  loginListener.onResult(ResultCode.SUCCESS, str2, str2);
                  return;
                } 
              } else if (loginListener != null) {
                loginListener.onResult(ResultCode.Fail, str2, str2);
                return;
              } 
            } catch (Exception e) {
              if (loginListener != null)
                loginListener.onResult(ResultCode.Fail, "服务器参数异常", e.toString());
            } 
          }
        });
    return true;
  }
  
  protected void loginSuccess(String paramString) {
    String str7 = "";
    String str5 = "";
    String str4 = "";
    String str6 = "";
    String str3 = str4;
    String str2 = str7;
    String str1 = str5;
    try {
      JSONObject jSONObject = (new JSONObject(paramString)).getJSONObject("data");
      str3 = str4;
      str2 = str7;
      str1 = str5;
      paramString = jSONObject.optString("nickname");
      str3 = str4;
      str2 = paramString;
      str1 = str5;
      str5 = jSONObject.optString("playerId");
      str3 = str4;
      str2 = paramString;
      str1 = str5;
      str4 = jSONObject.optString("accessToken");
      str3 = str4;
      str2 = paramString;
      str1 = str5;
      str7 = jSONObject.optString("deviceKey");
      str3 = str7;
      str1 = str5;
      str2 = paramString;
      paramString = str3;
      str3 = str4;
    } catch (JSONException paramString) {
      paramString.printStackTrace();
      paramString = str6;
    } 
    User.getInstance().upUserInfo(str2, str1, str3, paramString);
  }
  
  public void logout(Context paramContext) { User.getInstance().logout(); }
  
  public boolean notice(Context paramContext, JSONObject paramJSONObject, final CallbackListener listener) {
    OkHttpUtil.postNoLoading(paramContext, HttpConfig.NOTICE_URL, paramJSONObject.toString(), new OkHttpUtil.SimpleResponseHandler() {
          public void onFailure(Exception param1Exception) {
            if (listener != null)
              listener.onResult(ResultCode.Fail, "发送失败", param1Exception.toString()); 
          }
          
          public void onSuccess(Call param1Call, Response param1Response) {
            try {
              String str1 = new String(param1Response.body().string());
              BaseResp baseResp = (BaseResp)(new Gson()).fromJson(str1, BaseResp.class);
              String str2 = baseResp.getMessage();
              if (listener != null) {
                if ("success".equals(baseResp.getResult())) {
                  if (((List)baseResp.getData()).size() > 0) {
                    listener.onResult(ResultCode.SUCCESS, str2, (new Gson()).toJson(baseResp.getData()));
                    return;
                  } 
                  listener.onResult(ResultCode.CANCEL, str2, str2);
                  return;
                } 
                listener.onResult(ResultCode.Fail, str2, str2);
              } 
            } catch (Exception e) {
              e.printStackTrace();
              if (listener != null) {
                listener.onResult(ResultCode.Fail, "服务器参数异常", e.toString());
                return;
              } 
            } 
          }
        });
    return true;
  }
  
  public boolean register(Context paramContext, String paramString, final CallbackListener list) {
    OkHttpUtil.post(paramContext, HttpConfig.REGIST_URL, paramString, new OkHttpUtil.SimpleResponseHandler() {
          public void onFailure(Exception param1Exception) {
            if (list != null)
              list.onResult(ResultCode.Fail, "注册失败", param1Exception.toString()); 
          }
          
          public void onSuccess(Call param1Call, Response param1Response) {
            try {
              String str1 = new String(param1Response.body().string());
              JSONObject jSONObject = new JSONObject(str1);
              String str2 = jSONObject.optString("result", "");
              String str3 = jSONObject.optString("message", "");
              if (list != null) {
                if (str2.equals("success")) {
                  list.onResult(ResultCode.SUCCESS, "注册成功", str1);
                  return;
                } 
                list.onResult(ResultCode.Fail, str3, str3);
                return;
              } 
            } catch (Exception e) {
              if (list != null)
                list.onResult(ResultCode.Fail, "服务器参数异常", e.toString());
            } 
          }
        });
    return true;
  }
  
  public boolean resetPwd(Context paramContext, String paramString, final CallbackListener list) {
    OkHttpUtil.post(paramContext, HttpConfig.FORGETPWD_URL, paramString, new OkHttpUtil.SimpleResponseHandler() {
          public void onFailure(Exception param1Exception) {
            if (list != null)
              list.onResult(ResultCode.Fail, "发送失败", param1Exception.toString()); 
          }
          
          public void onSuccess(Call param1Call, Response param1Response) {
            try {
              JSONObject jSONObject = new JSONObject(new String(param1Response.body().string()));
              String str1 = jSONObject.optString("result", "");
              String str2 = jSONObject.optString("message", "");
              if (list != null) {
                if ("success".equals(str1)) {
                  list.onResult(ResultCode.SUCCESS, "重置密码成功", str2);
                  return;
                } 
                list.onResult(ResultCode.Fail, str2, str2);
                return;
              } 
            } catch (Exception e) {
              if (list != null)
                list.onResult(ResultCode.Fail, "服务器参数异常", e.toString());
            } 
          }
        });
    return true;
  }
  
  public boolean sendMsgToPhone(Context paramContext, JSONObject paramJSONObject, final CallbackListener list) {
    OkHttpUtil.post(paramContext, HttpConfig.SENDMSGTOPHONE_URL, paramJSONObject.toString(), new OkHttpUtil.SimpleResponseHandler() {
          public void onFailure(Exception param1Exception) {
            Log.e("sendMsgToPhone onFailer", "exception：" + param1Exception);
            if (list != null)
              list.onResult(ResultCode.Fail, "发送失败", param1Exception.toString()); 
          }
          
          public void onSuccess(Call param1Call, Response param1Response) {
            try {
              JSONObject jSONObject = new JSONObject(param1Response.body().string());
              String str1 = jSONObject.optString("result", "");
              String str2 = jSONObject.optString("message", "");
              if (list != null) {
                if ("success".equals(str1)) {
                  list.onResult(ResultCode.SUCCESS, "发送成功", str2);
                  return;
                } 
                list.onResult(ResultCode.Fail, str2, str2);
                return;
              } 
            } catch (Exception e) {
              Log.e("sendMsgToPhone", "exception：" + e);
              if (list != null)
                list.onResult(ResultCode.Fail, "服务器参数异常", e.toString());
            } 
          }
        });
    return true;
  }
  
  public void switchAccount(Context paramContext, CallbackListener paramCallbackListener) {
    logout(paramContext);
    if (paramCallbackListener != null)
      paramCallbackListener.onResult(ResultCode.SUCCESS, "success", ""); 
  }
  
  public enum LoginType {
    guest, quick, reg, wechat;
    
    static  {
      guest = new LoginType("guest", 2);
      quick = new LoginType("quick", 3);
      $VALUES = new LoginType[] { reg, wechat, guest, quick };
    }
  }
  
  public enum SendCodeType {
    register, binding, forget;
    
    static  {
      forget = new SendCodeType("forget", true);
      binding = new SendCodeType("binding", 2);
      $VALUES = new SendCodeType[] { register, forget, binding };
    }
  }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\icloud\sdk\adapter\base\Account.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */