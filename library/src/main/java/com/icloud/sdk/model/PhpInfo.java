package com.icloud.sdk.model;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PhpInfo {
  private static PhpInfo instance;
  
  private String app_id;
  
  public List<String> login_type = new ArrayList();
  
  private String mch_id;
  
  private String partner;
  
  private String seller_id;
  
  public boolean show_logo;
  
  public static PhpInfo getInstance() {
    if (instance == null)
      instance = new PhpInfo(); 
    return instance;
  }
  
  public void init(String paramString) {
    boolean bool = true;
    try {
      JSONObject jSONObject1 = new JSONObject(paramString);
      JSONObject jSONObject2 = jSONObject1.getJSONObject("wx_config");
      JSONObject jSONObject3 = jSONObject1.getJSONObject("alipay_config");
      JSONArray jSONArray = jSONObject1.getJSONArray("login_config");
      this.mch_id = jSONObject2.getString("mch_id");
      this.app_id = jSONObject2.getString("app_id");
      this.partner = jSONObject3.getString("partner");
      this.seller_id = jSONObject3.getString("seller_id");
      for (byte b = 0; b < jSONArray.length(); b++)
        this.login_type.add(jSONArray.getString(b)); 
      if (jSONObject1.getInt("show_logo") != 1)
        bool = false; 
      this.show_logo = bool;
      return;
    } catch (JSONException e) {
      e.printStackTrace();
      return;
    } 
  }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\icloud\sdk\model\PhpInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */
