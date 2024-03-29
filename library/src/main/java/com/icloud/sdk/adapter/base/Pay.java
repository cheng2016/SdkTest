package com.icloud.sdk.adapter.base;

import android.app.Activity;
import android.widget.Toast;
import com.icloud.sdk.config.HttpConfig;
import com.icloud.sdk.config.PayType;
import com.icloud.sdk.listener.CallbackListener;
import com.icloud.sdk.listener.ResultCode;
import com.icloud.sdk.utils.AlipayUtil;
import com.icloud.sdk.utils.OkHttpUtil;
import com.icloud.sdk.utils.Util;
import com.icloud.sdk.utils.WeiXinUtil;
import okhttp3.Call;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class Pay {
  private static String currentPayType = "";
  
  private static Pay instance;
  
  public static Pay getInstance() {
    if (instance == null)
      instance = new Pay(); 
    return instance;
  }
  
    protected void interPay(Activity context,String jsonStr, CallbackListener payListener) {
        JSONObject json=null;
        try {
             json = new JSONObject(jsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (currentPayType.equals(com.icloud.sdk.config.PayType.AliPay)){
             AlipayUtil.instance().pay(context,json,payListener);
        }else if (currentPayType.equals(com.icloud.sdk.config.PayType.WXPay)){
           if (Util.isWXAppInstalled(context)){
               WeiXinUtil.instance().pay(context,json,payListener);
           }else {
               Toast.makeText(context, "请安装微信", Toast.LENGTH_SHORT).show();
           }
        }
    }
  
  public boolean pay(final Activity ctx, String paramString, final CallbackListener payListener) {
    OkHttpUtil.post(ctx, HttpConfig.ORDER_URL, paramString, new OkHttpUtil.SimpleResponseHandler() {
          public void onFailure(Exception param1Exception) {
            if (payListener != null)
              payListener.onResult(ResultCode.Fail, param1Exception.toString(), ""); 
          }
          
          public void onSuccess(Call param1Call, Response param1Response) {
            try {
              JSONObject jSONObject = new JSONObject(new String(param1Response.body().string()));
              String str1 = jSONObject.optString("result", "");
              String str2 = jSONObject.optString("message", "");
              if (payListener != null) {
                if ("success".equals(str1)) {
                  String str = (new JSONObject(jSONObject.getString("data"))).getString("extraInfo");
                  Pay.this.interPay(ctx, str, payListener);
                  return;
                } 
                payListener.onResult(ResultCode.Fail, str2, str2);
                return;
              } 
            } catch (Exception e) {
              if (payListener != null)
                payListener.onResult(ResultCode.Fail, "服务器参数异常", e.toString()); 
            } 
          }
        });
    return true;
  }
  
  public void setCurrentPayType(String paramString) { currentPayType = paramString; }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\icloud\sdk\adapter\base\Pay.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */
