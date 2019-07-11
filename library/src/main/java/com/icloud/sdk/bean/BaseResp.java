package com.icloud.sdk.bean;

public class BaseResp<T> extends Object {
  private T data;
  
  private String message;
  
  private String result;
  
  public T getData() { return (T)this.data; }
  
  public String getMessage() { return this.message; }
  
  public String getResult() { return this.result; }
  
  public void setData(T paramT) { this.data = paramT; }
  
  public void setMessage(String paramString) { this.message = paramString; }
  
  public void setResult(String paramString) { this.result = paramString; }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\icloud\sdk\bean\BaseResp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */