package com.icloud.sdk.listener;

import android.content.Context;
import android.util.Log;
import com.icloud.sdk.YZSDK;
import com.icloud.sdk.view.NoticePop;

public abstract class LoginCallBack implements CallbackListener {
  private static final String TAG = "LoginCallBack";
  
  Context context;
  
  public LoginCallBack(Context paramContext) {
    Log.i("LoginCallBack", "LoginCallBack 构造方法");
    this.context = paramContext;
  }
  
  private void isNotice(CallbackListener paramCallbackListener) {
    Log.i("LoginCallBack", "isNotice ");
    YZSDK.instance().notice(this.context, paramCallbackListener);
  }
  
  public void notice(Context paramContext, String paramString1, ResultCode paramResultCode, String paramString2, String paramString3) {
    Log.i("LoginCallBack", "notice ");
    (new NoticePop(paramContext, paramString1, new NoticePop.OnCancellCallBack(this, paramResultCode, paramString2, paramString3) {
          public void exit() { LoginCallBack.this.onNext(ResultCode.CANCEL, "停服中 ", "停服中"); }
          
          public void login() { LoginCallBack.this.onNext(code, msg, descr); }
        })).show();
  }
  
  public abstract void onNext(ResultCode paramResultCode, String paramString1, String paramString2);
  
  public void onResult(final ResultCode code, final String msg, final String descr) {
    Log.i("LoginCallBack", "onResult ");
    if (paramResultCode == ResultCode.SUCCESS) {
      isNotice(new CallbackListener() {
            public void onResult(ResultCode param1ResultCode, String param1String1, String param1String2) {
              if (param1ResultCode == ResultCode.SUCCESS) {
                LoginCallBack.this.notice(LoginCallBack.this.context, param1String2, code, msg, descr);
                return;
              } 
              LoginCallBack.this.onNext(code, msg, descr);
            }
          });
      return;
    } 
    onNext(paramResultCode, paramString1, paramString2);
  }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\icloud\sdk\listener\LoginCallBack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */