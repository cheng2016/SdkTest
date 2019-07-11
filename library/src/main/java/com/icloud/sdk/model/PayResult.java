package com.icloud.sdk.model;

import android.text.TextUtils;
import java.util.Iterator;
import java.util.Map;

public class PayResult {
  private String memo;
  
  private String result;
  
  private String resultStatus;
  
  public PayResult(Map<String, String> paramMap) {
    if (paramMap != null) {
      Iterator iterator = paramMap.keySet().iterator();
      while (true) {
        if (iterator.hasNext()) {
          String str = (String)iterator.next();
          if (TextUtils.equals(str, "resultStatus")) {
            this.resultStatus = (String)paramMap.get(str);
            continue;
          } 
          if (TextUtils.equals(str, "result")) {
            this.result = (String)paramMap.get(str);
            continue;
          } 
          if (TextUtils.equals(str, "memo"))
            this.memo = (String)paramMap.get(str); 
          continue;
        } 
        return;
      } 
    } 
  }
  
  public String getMemo() { return this.memo; }
  
  public String getResult() { return this.result; }
  
  public String getResultStatus() { return this.resultStatus; }
  
  public String toString() { return "resultStatus={" + this.resultStatus + "};memo={" + this.memo + "};result={" + this.result + "}"; }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\icloud\sdk\model\PayResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */