package com.icloud.sdk.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.icloud.sdk.R;
import com.icloud.sdk.YZSDK;
import com.icloud.sdk.adapter.base.Account;
import com.icloud.sdk.listener.CallbackListener;
import com.icloud.sdk.listener.LoginCallBack;
import com.icloud.sdk.listener.ResultCode;
import com.icloud.sdk.model.PhpInfo;
import com.icloud.sdk.utils.TimeThread;
import com.icloud.sdk.utils.WeiXinUtil;
import com.icloud.sdk.utils.ToastUtil;
import java.util.List;

public class LoginPop extends Dialog implements View.OnClickListener {
  private LinearLayout btn_gstLogin;
  
  private Button btn_login;
  
  private TextView btn_otherLogin;
  
  private Button btn_phone_code;
  
  private LinearLayout btn_wxLogin;
  
  private CheckBox cb_agree;
  
  private Button close;
  
  Context context;
  
  private EditText et_phone_code;
  
  private ImageView iv_Logo;
  
  private View layout_phoneCode;
  
  private CallbackListener loginListener;
  
  private EditText login_account;
  
  private TextView tv_agree;
  
  public LoginPop(Context paramContext, CallbackListener paramCallbackListener) {
    super(paramContext, R.style.base_pop);
    this.context = paramContext;
    setCanceledOnTouchOutside(false);
    setContentView(R.layout.pop_login);
    initview();
    setListener();
    this.loginListener = paramCallbackListener;
  }
  
  private boolean checkAgree() {
    if (!this.cb_agree.isChecked())
      ToastUtil.showShort(context,"请同意用户协议");
    return this.cb_agree.isChecked();
  }
  
  private void gstLogin() { YZSDK.instance().login(this.context, null, null, Account.LoginType.guest, new LoginCallBack(this.context) {
          public void onNext(ResultCode param1ResultCode, String param1String1, String param1String2) {
            if (LoginPop.this.loginListener != null)
              LoginPop.this.loginListener.onResult(param1ResultCode, param1String1, param1String2); 
            if (param1ResultCode == ResultCode.SUCCESS) {
              LoginPop.this.cancel();
              return;
            } 
            if (param1ResultCode == ResultCode.Fail) {
              FailPop.getInstance(LoginPop.this.context, param1String1);
              return;
            } 
          }
        }); }
  
  private boolean initJudge(String paramString) {
    List list = (PhpInfo.getInstance()).login_type;
    if (list.size() > 0)
      for (byte b = 0; b < list.size(); b++) {
        if (paramString.equals(list.get(b)))
          return true; 
      }  
    return false;
  }
  
  private void initview() {
    int i;
    int j = 0;
    this.iv_Logo = (ImageView)findViewById(R.id.logo);
    this.login_account = (EditText)findViewById(R.id.et_login_account);
    this.et_phone_code = (EditText)findViewById(R.id.et_phone_code);
    this.btn_phone_code = (Button)findViewById(R.id.btn_phone_code);
    this.btn_login = (Button)findViewById(R.id.btn_login);
    this.btn_wxLogin = (LinearLayout)findViewById(R.id.btn_wxLogin);
    this.btn_gstLogin = (LinearLayout)findViewById(R.id.btn_gstLogin);
    this.btn_otherLogin = (TextView)findViewById(R.id.btn_otherLogin);
    this.layout_phoneCode = findViewById(R.id.layout_phoneCode);
    this.tv_agree = (TextView)findViewById(R.id.tv_agree);
    this.cb_agree = (CheckBox)findViewById(R.id.cb_agree);
    this.close = (Button)findViewById(R.id.close);
    this.tv_agree.setMovementMethod(LinkMovementMethod.getInstance());
    this.layout_phoneCode.setVisibility(View.GONE);
    ImageView imageView = this.iv_Logo;
    if ((PhpInfo.getInstance()).show_logo) {
      i = R.drawable.logo_001;
    } else {
      i = R.drawable.logo_002;
    } 
    imageView.setBackgroundResource(i);
    LinearLayout linearLayout = this.btn_wxLogin;
    if (initJudge(LoginType.wechat.toString())) {
      i = 0;
    } else {
      i = 8;
    } 
    linearLayout.setVisibility(i);
    linearLayout = this.btn_gstLogin;
    if (initJudge(LoginType.guest.toString())) {
      i = 0;
    } else {
      i = 8;
    } 
    linearLayout.setVisibility(i);
    TextView textView = this.btn_otherLogin;
    if (initJudge(LoginType.reg.toString())) {
      i = j;
    } else {
      i = 8;
    } 
    textView.setVisibility(i);
  }
  
  private void login() {
    if (TextUtils.isEmpty(this.login_account.getText().toString())) {
      ToastUtil.showShort(context,"请输入手机号");
      return;
    } 
    if (this.layout_phoneCode.getVisibility() == View.VISIBLE) {
      loginByPhone();
      return;
    } 
    sendSmsCode();
  }
  
  private void loginByPhone() {
    String str1 = this.login_account.getText().toString().trim();
    String str2 = this.et_phone_code.getText().toString().trim();
    if (TextUtils.isEmpty(str2)) {
      ToastUtil.showShort(context,"请输入验证码");
      return;
    } 
    YZSDK.instance().login(this.context, str1, str2, Account.LoginType.reg, new LoginCallBack(this.context) {
          public void onNext(ResultCode param1ResultCode, String param1String1, String param1String2) {
            if (LoginPop.this.loginListener != null)
              LoginPop.this.loginListener.onResult(param1ResultCode, param1String1, param1String2); 
            if (param1ResultCode == ResultCode.SUCCESS) {
              LoginPop.this.cancel();
              return;
            } 
            if (param1ResultCode == ResultCode.Fail) {
              FailPop.getInstance(LoginPop.this.context, param1String1);
              return;
            } 
          }
        });
  }
  
  private void sendSmsCode() {
    new TimeThread(btn_phone_code, 60 * 1000, 1000).start();
        String phone = login_account.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(context, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        YZSDK.instance().sendSMSInAcc(context, phone, Account.SendCodeType.register, new CallbackListener() {
            @Override
            public void onResult(ResultCode code, String msg, String descr) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                if (code == ResultCode.SUCCESS) {
                    layout_phoneCode.setVisibility(View.VISIBLE);
                    iv_Logo.setVisibility(View.GONE);
                }
            }
        });
  }
  
  private void setListener() {
    this.btn_phone_code.setOnClickListener(this);
    this.btn_login.setOnClickListener(this);
    this.btn_gstLogin.setOnClickListener(this);
    this.btn_wxLogin.setOnClickListener(this);
    this.btn_otherLogin.setOnClickListener(this);
    this.close.setOnClickListener(this);
  }
  
  public Dialog getInstance() { return this; }
  
  public void onClick(View paramView) {
    if (paramView.getId() == R.id.btn_phone_code && checkAgree())
      sendSmsCode(); 
    if (paramView.getId() == R.id.btn_login && checkAgree())
      login(); 
    if (paramView.getId() == R.id.btn_gstLogin && checkAgree())
      gstLogin(); 
    if (paramView.getId() == R.id.btn_wxLogin) {
      if (!checkAgree())
        return; 
      WeiXinUtil.instance().login(this.context, new LoginCallBack(this.context) {
            public void onNext(ResultCode param1ResultCode, String param1String1, String param1String2) {
              if (LoginPop.this.loginListener != null)
                LoginPop.this.loginListener.onResult(param1ResultCode, param1String1, param1String2); 
              if (param1ResultCode == ResultCode.SUCCESS) {
                LoginPop.this.cancel();
                return;
              } 
              if (param1ResultCode == ResultCode.Fail) {
                FailPop.getInstance(LoginPop.this.context, param1String1);
                return;
              } 
            }
          });
    } 
    if (paramView.getId() == R.id.close)
      cancel(); 
    if (paramView.getId() == R.id.btn_otherLogin && checkAgree()) {
      (new AccountLoginPop(this.context, new LoginCallBack(this.context) {
            public void onNext(ResultCode param1ResultCode, String param1String1, String param1String2) {
              if (param1ResultCode == ResultCode.SUCCESS) {
                LoginPop.this.loginListener.onResult(param1ResultCode, param1String1, param1String2);
                LoginPop.this.cancel();
              } 
            }
          })).show();
      return;
    } 
  }
  
  public void show() {
    super.show();
    if (this.loginListener != null);
  }
  
  private enum LoginType {
    guest, quick, reg, wechat;
  }
}


