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
  
    public void notice(Context context, String noticeData, final ResultCode code, final String msg, final String descr) {
        Log.i(TAG,"notice ");
        NoticePop noticePop = new NoticePop(context, noticeData, new NoticePop.OnCancellCallBack() {
            @Override
            public void login() {
                onNext(code, msg, descr);
            }

            @Override
            public void exit() {
                onNext(ResultCode.CANCEL, "停服中 ", "停服中");
            }
        });
        noticePop.show();
    }
  
  public abstract void onNext(ResultCode paramResultCode, String paramString1, String paramString2);
  
    @Override
    public void onResult(final ResultCode code, final String msg, final String descr) {
        Log.i(TAG,"onResult ");
        if(code == ResultCode.SUCCESS){
            isNotice(new CallbackListener() {
                @Override
                public void onResult(ResultCode resultCode, String message, String noticeData) {
                    if (resultCode == ResultCode.SUCCESS) {
                        notice(context, noticeData, code, msg, descr);
                    } else {
                        onNext(code, msg, descr);
                    }
                }
            });
        }else {
            onNext(code, msg, descr);
        }
    }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\icloud\sdk\listener\LoginCallBack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */
