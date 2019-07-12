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
import com.icloud.sdk.listener.ResultCode;
import com.icloud.sdk.utils.SharedPreferenceUtil;
import com.icloud.sdk.utils.ToastUtil;
import com.yz.action.ActionSdk;
import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;

public class AccountLoginPop extends Dialog implements View.OnClickListener {
  private TextView btn_bindPhone;
  
  private TextView btn_changePsd;
  
  private Button btn_forgetPsd;
  
  private Button btn_login;
  
  private Button btn_register;
  
  private CheckBox cb_agree;
  
  private Button close;
  
  Context context;
  
  private EditText et_login_password;
  
  private EditText et_register_account;
  
  private EditText et_register_psd;
  
  private ImageView iv_login;
  
  private ImageView iv_register;
  
  private LinearLayout layout_login;
  
  private LinearLayout layout_register;
  
  private CallbackListener loginListener;
  
  private EditText login_account;
  
  private TextView tv_agree;
  
  private TextView tv_login;
  
  private TextView tv_register;
  
  public AccountLoginPop(Context paramContext, CallbackListener paramCallbackListener) {
    super(paramContext, R.style.base_pop);
    this.context = paramContext;
    setCanceledOnTouchOutside(false);
    setContentView(R.layout.pop_accountlogin);
    initview();
    setListener();
    this.loginListener = paramCallbackListener;
  }
  
  private void accountLogin() {
    String str1 = this.login_account.getText().toString().trim();
    if (TextUtils.isEmpty(str1)) {
      ToastUtil.showShort(context,"账号不能为空");
      return;
    } 
    String str2 = this.et_login_password.getText().toString().trim();
    if (TextUtils.isEmpty(str2)) {
      ToastUtil.showShort(context,"密码不能为空");
      
      return;
    } 
    YZSDK.instance().login(this.context, str1, str2, Account.LoginType.reg, new CallbackListener() {
          public void onResult(ResultCode param1ResultCode, String param1String1, String param1String2) {
            if (AccountLoginPop.this.loginListener != null)
              AccountLoginPop.this.loginListener.onResult(param1ResultCode, param1String1, param1String2); 
            if (ResultCode.SUCCESS == param1ResultCode) {
              AccountLoginPop.this.cancel();
              return;
            } 
            FailPop.getInstance(AccountLoginPop.this.context, param1String1);
          }
        });
  }
  
  private boolean checkAgree() {
    if (!this.cb_agree.isChecked())
      ToastUtil.showShort(context,"请同意用户协议");
    return this.cb_agree.isChecked();
  }
  
  private String generatAccount(int paramInt1, int paramInt2) {
    Random random = new Random();
    StringBuffer stringBuffer = new StringBuffer();
    for (byte b = 0; b < paramInt1; b++)
      stringBuffer.append("abcdefghijklmnopqrstuvwxyz".charAt(random.nextInt(26))); 
    for (paramInt1 = 0; paramInt1 < paramInt2; paramInt1++)
      stringBuffer.append("0123456789".charAt(random.nextInt(10))); 
    return stringBuffer.toString();
  }
  
  private void initview() {
    this.tv_login = (TextView)findViewById(R.id.tv_login);
    this.iv_login = (ImageView)findViewById(R.id.iv_login);
    this.tv_register = (TextView)findViewById(R.id.tv_register);
    this.iv_register = (ImageView)findViewById(R.id.iv_register);
    this.login_account = (EditText)findViewById(R.id.et_login_account);
    this.et_login_password = (EditText)findViewById(R.id.et_login_password);
    this.btn_forgetPsd = (Button)findViewById(R.id.btn_forgetPsd);
    this.btn_login = (Button)findViewById(R.id.btn_login);
    this.btn_bindPhone = (TextView)findViewById(R.id.btn_bindPhone);
    this.btn_bindPhone.getPaint().setFlags(8);
    this.btn_bindPhone.getPaint().setAntiAlias(true);
    this.btn_changePsd = (TextView)findViewById(R.id.btn_changePsd);
    this.btn_changePsd.getPaint().setFlags(8);
    this.btn_changePsd.getPaint().setAntiAlias(true);
    this.tv_agree = (TextView)findViewById(R.id.tv_agree);
    this.cb_agree = (CheckBox)findViewById(R.id.cb_agree);
    this.close = (Button)findViewById(R.id.close);
    this.layout_login = (LinearLayout)findViewById(R.id.center_login);
    this.layout_register = (LinearLayout)findViewById(R.id.center_register);
    this.et_register_account = (EditText)findViewById(R.id.et_register_account);
    this.et_register_psd = (EditText)findViewById(R.id.et_register_psd);
    this.btn_register = (Button)findViewById(R.id.btn_register);
    setState(State.Login);
    this.et_register_account.setText(generatAccount(3, 4));
    this.et_register_psd.setText(generatAccount(3, 4));
    this.login_account.setText(SharedPreferenceUtil.getAccount());
    this.et_login_password.setText(SharedPreferenceUtil.getPsd());
    this.tv_agree.setMovementMethod(LinkMovementMethod.getInstance());
  }
  
  private void loginSuccess(String paramString1, String paramString2) {
    setState(State.Login);
    this.login_account.setText(paramString1);
    this.et_login_password.setText(paramString2);
  }
  
  private void register() {
    final String account = this.et_register_account.getText().toString().trim();
    if (TextUtils.isEmpty(account))
      ToastUtil.showShort(context,"账号不能为空");
    final String psd = this.et_register_psd.getText().toString().trim();
    if (TextUtils.isEmpty(psd))
      ToastUtil.showShort(context,"密码不能为空");
    YZSDK.instance().regist(this.context, account, psd, new CallbackListener() {
          public void onResult(ResultCode param1ResultCode, String param1String1, String param1String2) {
            ToastUtil.showShort(context,param1String1);
            if (param1ResultCode == ResultCode.SUCCESS)
              AccountLoginPop.this.loginSuccess(account, psd); 
            JSONObject jSONObject = new JSONObject();
            try {
              jSONObject.put("method", "account");
              jSONObject.put("is_success", true);
            } catch (JSONException e) {
              e.printStackTrace();
            } 
            ActionSdk.getInstance().setRegister(jSONObject.toString());
          }
        });
  }
  
  private void setListener() {
    this.tv_login.setOnClickListener(this);
    this.tv_register.setOnClickListener(this);
    this.btn_login.setOnClickListener(this);
    this.btn_forgetPsd.setOnClickListener(this);
    this.btn_bindPhone.setOnClickListener(this);
    this.btn_changePsd.setOnClickListener(this);
    this.close.setOnClickListener(this);
    this.btn_register.setOnClickListener(this);
  }
  
  private void setState(State paramState) {
    if (paramState == State.Login) {
      this.tv_register.setTextColor(this.context.getResources().getColor(R.color.gray_color));
      this.tv_login.setTextColor(this.context.getResources().getColor(R.color.blue_color));
      this.iv_register.setBackgroundResource(R.drawable.bg_line001);
      this.iv_login.setBackgroundResource(R.drawable.bg_line002);
      layout_login.setVisibility(View.VISIBLE);
      layout_register.setVisibility(View.GONE);
      btn_bindPhone.setVisibility(View.VISIBLE);
      btn_changePsd.setVisibility(View.VISIBLE);
      return;
    } 
    this.tv_login.setTextColor(this.context.getResources().getColor(R.color.gray_color));
    this.tv_register.setTextColor(this.context.getResources().getColor(R.color.blue_color));
    this.iv_register.setBackgroundResource(R.drawable.bg_line002);
    this.iv_login.setBackgroundResource(R.drawable.bg_line001);
    layout_login.setVisibility(View.GONE);
    layout_register.setVisibility(View.VISIBLE);
    btn_bindPhone.setVisibility(View.INVISIBLE);
    btn_changePsd.setVisibility(View.INVISIBLE);
  }
  
  public void onClick(View paramView) {
    if (paramView.getId() == R.id.tv_login)
      setState(State.Login); 
    if (paramView.getId() == R.id.tv_register)
      setState(State.Register); 
    if (paramView.getId() == R.id.btn_login) {
      if (!checkAgree())
        return; 
      accountLogin();
    } 
    if (paramView.getId() == R.id.btn_bindPhone) {
      String str1 = this.login_account.getText().toString().trim();
      String str2 = this.et_login_password.getText().toString().trim();
      if (TextUtils.isEmpty(str1) || TextUtils.isEmpty(str2)) {
        ToastUtil.showShort(context,"请先输入账号密码");
        return;
      } 
      YZSDK.instance().checkLogin(this.context, str1, str2, Account.LoginType.reg, new CallbackListener() {
            public void onResult(ResultCode param1ResultCode, String param1String1, String param1String2) {
              if (param1ResultCode != ResultCode.SUCCESS) {
                ToastUtil.showShort(context,param1String1);
                return;
              } 
              (new BindPhonePop(AccountLoginPop.this.context, null)).show();
            }
          });
    } 
    if (paramView.getId() == R.id.btn_changePsd)
      (new ChangePsdPop(this.context)).show(); 
    if (paramView.getId() == R.id.close)
      cancel(); 
    if (paramView.getId() == R.id.btn_register)
      if (checkAgree()) {
        register();
      } else {
        return;
      }  
    if (paramView.getId() == R.id.btn_forgetPsd) {
      (new ForgetPsdPop(this.context)).show();
      return;
    } 
  }
  
  private enum State {
    Login, Register;
  }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\icloud\sdk\view\AccountLoginPop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */
