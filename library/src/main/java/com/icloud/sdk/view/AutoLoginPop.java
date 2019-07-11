package com.icloud.sdk.view;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.icloud.sdk.R;
import com.icloud.sdk.YZSDK;
import com.icloud.sdk.listener.CallbackListener;
import com.icloud.sdk.listener.LoginCallBack;
import com.icloud.sdk.listener.ResultCode;
import com.icloud.sdk.utils.SharedPreferenceUtil;

public class AutoLoginPop extends Dialog implements View.OnClickListener {
  private ObjectAnimator animator;
  
  private TextView btn_bindPhone;
  
  private Button btn_login;
  
  Context context;
  
  private boolean flag;
  
  Handler handler = new Handler();
  
  private CallbackListener loginListener;
  
  private Runnable thread = new Runnable() {
      public void run() {
        AutoLoginPop.this.btn_login.setText("登录中...");
        AutoLoginPop.access$102(AutoLoginPop.this, true);
        AutoLoginPop.this.login();
        try {
          Thread.sleep(800L);
          return;
        } catch (InterruptedException interruptedException) {
          interruptedException.printStackTrace();
          return;
        } 
      }
    };
  
  private TextView tv_account;
  
  public AutoLoginPop(Context paramContext, CallbackListener paramCallbackListener) {
    super(paramContext, R.style.base_pop);
    this.context = paramContext;
    setCanceledOnTouchOutside(false);
    setContentView(R.layout.auto_loading);
    initview();
    setListener();
    this.loginListener = paramCallbackListener;
  }
  
  private void initview() {
    this.tv_account = (TextView)findViewById(R.id.tv_account);
    this.btn_bindPhone = (TextView)findViewById(R.id.btn_bindPhone);
    this.btn_login = (Button)findViewById(R.id.btn_login);
    this.animator = ObjectAnimator.ofFloat((ImageView)findViewById(R.id.progressBar), "rotation", new float[] { 0.0F, 360.0F });
    this.animator.setDuration(2000L);
    this.animator.setRepeatCount(-1);
    this.animator.start();
    this.tv_account.setText(SharedPreferenceUtil.getNikeName());
    this.btn_login.setText("切换账号");
    this.flag = false;
    this.handler.postDelayed(this.thread, 2500L);
    if (SharedPreferenceUtil.getUserType() != 2) {
      this.btn_bindPhone.setVisibility(0);
      this.btn_bindPhone.getPaint().setFlags(8);
      this.btn_bindPhone.getPaint().setAntiAlias(true);
      return;
    } 
    this.btn_bindPhone.setVisibility(8);
  }
  
  private void login() { YZSDK.instance().fastLogin(this.context, new LoginCallBack(this.context) {
          public void onNext(ResultCode param1ResultCode, String param1String1, String param1String2) {
            if (AutoLoginPop.this.loginListener != null)
              AutoLoginPop.this.loginListener.onResult(param1ResultCode, param1String1, param1String2); 
            AutoLoginPop.this.cancel();
            if (param1ResultCode == ResultCode.Fail) {
              (new LoginPop(AutoLoginPop.this.context, AutoLoginPop.this.loginListener)).show();
              FailPop.getInstance(AutoLoginPop.this.context, param1String1);
            } 
          }
        }); }
  
  private void setListener() {
    this.btn_login.setOnClickListener(this);
    this.btn_bindPhone.setOnClickListener(this);
  }
  
  public void cancel() {
    super.cancel();
    this.animator = null;
    this.handler.removeCallbacks(this.thread);
  }
  
  public void onClick(View paramView) {
    if (paramView.getId() == R.id.btn_login && !this.flag) {
      (new LoginPop(this.context, this.loginListener)).show();
      cancel();
      SharedPreferenceUtil.setAccessToken("");
    } 
    if (paramView.getId() == R.id.btn_bindPhone && !this.flag) {
      (new BindPhonePop(this.context, new CallbackListener(this) {
            public void onResult(ResultCode param1ResultCode, String param1String1, String param1String2) { AutoLoginPop.this.login(); }
          })).show();
      this.handler.removeCallbacks(this.thread);
    } 
  }
  
  public void show() {
    super.show();
    if (this.loginListener != null);
  }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\icloud\sdk\view\AutoLoginPop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */