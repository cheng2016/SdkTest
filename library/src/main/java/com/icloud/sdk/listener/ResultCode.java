package com.icloud.sdk.listener;

public static enum ResultCode {
  CANCEL, Fail, SUCCESS;
  
  static  {
    CANCEL = new ResultCode("CANCEL", true);
    Fail = new ResultCode("Fail", 2);
    $VALUES = new ResultCode[] { SUCCESS, CANCEL, Fail };
  }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\icloud\sdk\listener\ResultCode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */