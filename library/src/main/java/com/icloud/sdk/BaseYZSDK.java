package com.icloud.sdk;

import android.app.Activity;
import android.app.Application;
import com.icloud.sdk.adapter.base.Sdk;
import com.icloud.sdk.listener.CallbackListener;
import com.icloud.sdk.model.ClientDeviceInfo;
import com.icloud.sdk.model.ConfigInfo;
import com.icloud.sdk.utils.LogUtil;
import com.icloud.sdk.utils.SharedPreferenceUtil;

public abstract class BaseYZSDK {
  public Activity initAct = null;
  
  public void init(Activity paramActivity, CallbackListener paramCallbackListener) {
    LogUtil.v(paramActivity, "init");
    this.initAct = paramActivity;
    onCreate(paramActivity);
    ClientDeviceInfo.getInstance().init(paramActivity.getApplication());
  }
  
  public void initApp(Application paramApplication, CallbackListener paramCallbackListener) {
    ConfigInfo.getInstance().init(paramApplication, paramCallbackListener);
    SharedPreferenceUtil.init(paramApplication);
    ClientDeviceInfo.getInstance().init(paramApplication);
    Sdk.getInstance().initApplication(paramApplication);
  }
  
  public void onCreate(Activity paramActivity) {
    LogUtil.v(paramActivity, "onCreate");
    Sdk.getInstance().onCreate(paramActivity);
  }
  
  public void onPause(Activity paramActivity) {
    LogUtil.v(paramActivity, "onPause");
    Sdk.getInstance().onPause(paramActivity);
  }
  
  public void onResume(Activity paramActivity) {
    LogUtil.v(paramActivity, "onResume");
    Sdk.getInstance().onResume(paramActivity);
  }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\icloud\sdk\BaseYZSDK.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */