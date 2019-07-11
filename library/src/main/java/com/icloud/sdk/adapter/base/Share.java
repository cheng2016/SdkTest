package com.icloud.sdk.adapter.base;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.icloud.sdk.listener.CallbackListener;
import com.icloud.sdk.utils.LogUtil;
import java.io.File;
import org.json.JSONObject;

public class Share {
  private static Share instance;
  
  public static Share getInstance() {
    if (instance == null)
      instance = new Share(); 
    return instance;
  }
  
  public void share(Context paramContext, String paramString, CallbackListener paramCallbackListener) {
    try {
      JSONObject jSONObject = new JSONObject(paramString);
      String str = jSONObject.optString("msg");
      share(paramContext, jSONObject.optString("title"), str, jSONObject.optString("imgPath"));
      return;
    } catch (Exception paramContext) {
      return;
    } 
  }
  
  protected void share(Context paramContext, String paramString1, String paramString2, String paramString3) {
    if (TextUtils.isEmpty(paramString1) || TextUtils.isEmpty(paramString2)) {
      LogUtil.e("share", "tip or msg is null");
      return;
    } 
    Intent intent = new Intent("android.intent.action.SEND");
    if (TextUtils.isEmpty(paramString3)) {
      intent.setType("text/plain");
    } else {
      File file = new File(paramString3);
      if (file != null && file.exists() && file.isFile()) {
        intent.setType("image/*");
        intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file));
      } 
    } 
    intent.putExtra("android.intent.extra.SUBJECT", paramString1);
    intent.putExtra("android.intent.extra.TEXT", paramString2);
    paramContext.startActivity(intent.setFlags(268435456).createChooser(intent, "share"));
  }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\icloud\sdk\adapter\base\Share.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */