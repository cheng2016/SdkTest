package com.yz.action;

import android.app.Activity;
import android.app.Application;
import android.util.Log;
import com.yz.action.apiadapter.IAdapterFactory;
import com.yz.action.utility.IAdapterFactoryGet;

public class ActionSdk {
  private static ActionSdk instance;
  
  private Activity act;
  
  private IAdapterFactory iAdapterFactory = IAdapterFactoryGet.get();
  
  private ActionSdk() {
    if (this.iAdapterFactory == null)
      Log.e("ActionSdk", "get iAdapterFactory error"); 
  }
  
  public static ActionSdk getInstance() {
    if (instance == null)
      instance = new ActionSdk(); 
    return instance;
  }
  
  public Activity getActivity() { return this.act; }
  
  public void initApplication(Application paramApplication) { this.iAdapterFactory.adtActivity().initApplication(paramApplication); }
  
  public void onCreate(Activity paramActivity) {
    this.act = paramActivity;
    this.iAdapterFactory.adtActivity().onCreate(paramActivity);
  }
  
  public void onEvent(String paramString1, String paramString2) { this.iAdapterFactory.adtFunction().onEvent(paramString1, paramString2); }
  
  public void onPause(Activity paramActivity) { this.iAdapterFactory.adtActivity().onPause(paramActivity); }
  
  public void onResume(Activity paramActivity) { this.iAdapterFactory.adtActivity().onResume(paramActivity); }
  
  public void setPurchase(String paramString) { this.iAdapterFactory.adtFunction().setPurchase(paramString); }
  
  public void setRegister(String paramString) { this.iAdapterFactory.adtFunction().register(paramString); }
  
  public void setUpdateLevel(int paramInt) { this.iAdapterFactory.adtFunction().setUpdateLevel(paramInt); }
  
  public void setUserUniqueID(String paramString) { this.iAdapterFactory.adtFunction().setUserUniqueID(paramString); }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\yz\action\ActionSdk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */