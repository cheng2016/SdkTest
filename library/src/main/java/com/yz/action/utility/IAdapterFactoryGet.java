package com.yz.action.utility;

import com.yz.action.apiadapter.IAdapterFactory;

public class IAdapterFactoryGet {
  private static final String pkg = "undefined";
  
  public static IAdapterFactory get() {
    try {
      return (IAdapterFactory)Class.forName("com.yz.action.apiadapter.undefined.AdapterFactory").newInstance();
    } catch (Exception exception) {
      exception.printStackTrace();
      return null;
    } 
  }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\yz\actio\\utility\IAdapterFactoryGet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */