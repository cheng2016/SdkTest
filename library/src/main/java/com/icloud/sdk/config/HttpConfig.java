package com.icloud.sdk.config;

public class HttpConfig {
  public static String BINDPHONE_URL;
  
  public static String CHANGEPWD_URL;
  
  public static String FORGETPWD_URL;
  
  public static String LOGIN_URL;
  
  public static String NOTICE_URL;
  
  public static String ORDER_URL;
  
  public static String REGIST_URL;
  
  public static String SENDMSGTOPHONE_URL;
  
  public static String SERVER_INIT;
  
  public static void init(String paramString) {
    SERVER_INIT = paramString + "/appstart";
    SENDMSGTOPHONE_URL = paramString + "/phone/code";
    REGIST_URL = paramString + "/register";
    LOGIN_URL = paramString + "/login";
    FORGETPWD_URL = paramString + "/forgetpwd";
    CHANGEPWD_URL = paramString + "/resetpwd";
    BINDPHONE_URL = paramString + "/setphone";
    NOTICE_URL = paramString + "/api/notice";
    ORDER_URL = paramString + "/order/create";
  }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\icloud\sdk\config\HttpConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */