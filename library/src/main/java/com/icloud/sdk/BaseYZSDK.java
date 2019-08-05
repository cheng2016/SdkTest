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


    public void initApp(Application app, CallbackListener sdkInitListener) {
        ConfigInfo.getInstance().init(app, sdkInitListener);
        SharedPreferenceUtil.init(app);
        ClientDeviceInfo.getInstance().init(app);
        Sdk.getInstance().initApplication(app);
    }


    public void init(Activity act, CallbackListener sdkInitListener) {
        LogUtil.v(act, "init");
        this.initAct = act;
        onCreate(act);

        ClientDeviceInfo.getInstance().init(act.getApplication());
    }

    public void onCreate(Activity act) {
        LogUtil.v(act, "onCreate");
        Sdk.getInstance().onCreate(act);
    }


    public void onResume(Activity act) {
        LogUtil.v(act, "onResume");
        Sdk.getInstance().onResume(act);
    }


    public void onPause(Activity act) {
        LogUtil.v(act, "onPause");
        Sdk.getInstance().onPause(act);
    }
}


