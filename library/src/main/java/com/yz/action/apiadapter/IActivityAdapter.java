package com.yz.action.apiadapter;

import android.app.Activity;
import android.app.Application;

public abstract class IActivityAdapter {
  public abstract void initApplication(Application paramApplication);
  
  public abstract void onCreate(Activity paramActivity);
  
  public abstract void onPause(Activity paramActivity);
  
  public abstract void onResume(Activity paramActivity);
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\yz\action\apiadapter\IActivityAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */