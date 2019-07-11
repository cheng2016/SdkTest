package com.icloud.sdk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.icloud.sdk.utils.LogUtil;
import com.icloud.sdk.utils.WeiXinUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class BaseWXEntryActivity extends Activity implements IWXAPIEventHandler {
  private IWXAPI api;
  
  public void onCreate(Bundle paramBundle) {
    getWindow().setFlags(1024, 1024);
    requestWindowFeature(1);
    super.onCreate(paramBundle);
    this.api = WXAPIFactory.createWXAPI(this, WeiXinUtil.APP_ID);
    try {
      if (!this.api.handleIntent(getIntent(), this))
        finish(); 
      return;
    } catch (Exception e) {
      e.printStackTrace();
      return;
    } 
  }
  
  protected void onNewIntent(Intent paramIntent) {
    super.onNewIntent(paramIntent);
    setIntent(paramIntent);
    this.api.handleIntent(paramIntent, this);
  }
  
  public void onReq(BaseReq paramBaseReq) { LogUtil.d("ContentValues", "onPayFinish, errCode = " + paramBaseReq.toString()); }
  
  public void onResp(BaseResp paramBaseResp) {
    LogUtil.d("ContentValues", "onPayFinish, errCode = " + paramBaseResp.errCode);
    WeiXinUtil.instance().onResp(paramBaseResp);
    finish();
  }
}