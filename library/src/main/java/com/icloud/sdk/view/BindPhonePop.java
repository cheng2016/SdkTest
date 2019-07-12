package com.icloud.sdk.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.icloud.sdk.R;
import com.icloud.sdk.YZSDK;
import com.icloud.sdk.adapter.base.Account;
import com.icloud.sdk.listener.CallbackListener;
import com.icloud.sdk.listener.ResultCode;
import com.icloud.sdk.utils.TimeThread;

public class BindPhonePop extends Dialog implements View.OnClickListener {
  private Button btn_phone_code;
  
  private Button btn_submit;
  
  private Button close;
  
  Context context;
  
  private EditText et_phone;
  
  private EditText et_phone_code;
  
  private CallbackListener listener;
  
  private TextView title;
  
  private TextView tv_close;
  
  private TextView tv_noBind;
  
  public BindPhonePop(Context paramContext, CallbackListener paramCallbackListener) {
    super(paramContext, R.style.base_pop);
    this.context = paramContext;
    this.listener = paramCallbackListener;
    setCanceledOnTouchOutside(false);
    setContentView(R.layout.pop_bindphone);
    initview();
    setListener();
    if (paramCallbackListener != null) {
      this.title.setText("为防止账号丢失，建议您绑定手机");
      this.tv_close.setVisibility(4);
      this.tv_noBind.setVisibility(0);
      return;
    } 
    this.title.setText("绑定手机");
    this.tv_close.setVisibility(0);
    this.tv_noBind.setVisibility(8);
  }
  
  private void bindPhone() {
    String str1 = this.et_phone.getText().toString().trim();
    String str2 = this.et_phone_code.getText().toString().trim();
    if (TextUtils.isEmpty(str1)) {
      Toast.makeText(this.context, "请输入手机号", 0).show();
      return;
    } 
    if (TextUtils.isEmpty(str2)) {
      Toast.makeText(this.context, "请输入验证码", 0).show();
      return;
    } 
    YZSDK.instance().bindPhone(this.context, str1, str2, new CallbackListener() {
          public void onResult(ResultCode param1ResultCode, String param1String1, String param1String2) {
            Toast.makeText(BindPhonePop.this.context, param1String1, 0).show();
            if (param1ResultCode == ResultCode.SUCCESS)
              BindPhonePop.this.cancel(); 
          }
        });
  }
  
  private void initview() {
    this.et_phone = (EditText)findViewById(R.id.et_phone);
    this.et_phone_code = (EditText)findViewById(R.id.et_phone_code);
    this.btn_phone_code = (Button)findViewById(R.id.btn_phone_code);
    this.btn_submit = (Button)findViewById(R.id.btn_submit);
    this.close = (Button)findViewById(R.id.close);
    this.tv_close = (TextView)findViewById(R.id.tv_close);
    this.title = (TextView)findViewById(R.id.title);
    this.tv_noBind = (TextView)findViewById(R.id.btn_noBind);
    this.tv_noBind.getPaint().setFlags(8);
    this.tv_noBind.getPaint().setAntiAlias(true);
  }
  
  private void sendSmsCode() {
    String str = this.et_phone.getText().toString().trim();
    if (TextUtils.isEmpty(str)) {
      Toast.makeText(this.context, "请输入手机号", 0).show();
      return;
    } 
    YZSDK.instance().sendSMSInAcc(this.context, str, Account.SendCodeType.binding, new CallbackListener() {
          public void onResult(ResultCode param1ResultCode, String param1String1, String param1String2) {
            Toast.makeText(BindPhonePop.this.context, param1String1, 0).show();
            if (param1ResultCode == ResultCode.SUCCESS)
              et_phone.post(new Runnable() {
                        @Override
                        public void run() {
                            new TimeThread(btn_phone_code, 60 * 1000, 1000).start();
                        }
                    });
          }
        });
  }
  
  private void setListener() {
    this.btn_phone_code.setOnClickListener(this);
    this.btn_submit.setOnClickListener(this);
    this.close.setOnClickListener(this);
    this.tv_close.setOnClickListener(this);
    this.tv_noBind.setOnClickListener(this);
  }
  
  public void cancel() {
    super.cancel();
    if (this.listener != null)
      this.listener.onResult(ResultCode.SUCCESS, "", ""); 
  }
  
  public void onClick(View paramView) {
    if (paramView.getId() == R.id.btn_phone_code)
      sendSmsCode(); 
    if (paramView.getId() == R.id.btn_submit)
      bindPhone(); 
    if (paramView.getId() == R.id.close || paramView.getId() == R.id.tv_close || paramView.getId() == R.id.btn_noBind)
      cancel(); 
  }
}
