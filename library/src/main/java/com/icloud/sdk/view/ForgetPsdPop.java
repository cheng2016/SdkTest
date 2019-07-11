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

public class ForgetPsdPop extends Dialog implements View.OnClickListener {
  private Button btn_submit;
  
  private Button close;
  
  private Context context;
  
  private EditText et_phone;
  
  private EditText et_phone_code;
  
  private boolean isFirst;
  
  private View layout_phoneCode;
  
  private TextView tv_close;
  
  public ForgetPsdPop(Context paramContext) {
    super(paramContext, R.style.base_pop);
    this.context = paramContext;
    setCanceledOnTouchOutside(false);
    setContentView(R.layout.pop_forgetpsd);
    initview();
    setListener();
  }
  
  private void initview() {
    this.et_phone = (EditText)findViewById(R.id.et_account);
    this.et_phone_code = (EditText)findViewById(R.id.et_phone_code);
    this.btn_submit = (Button)findViewById(R.id.btn_submit);
    this.close = (Button)findViewById(R.id.close);
    this.tv_close = (TextView)findViewById(R.id.tv_close);
    this.layout_phoneCode = findViewById(R.id.layout_phoneCode);
    this.isFirst = true;
    this.layout_phoneCode.setVisibility(8);
  }
  
  private void sendSmsCode() {
    String str = this.et_phone.getText().toString().trim();
    if (TextUtils.isEmpty(str)) {
      Toast.makeText(this.context, "请输入手机号", 0).show();
      return;
    } 
    YZSDK.instance().sendSMSInAcc(this.context, str, Account.SendCodeType.forget, new CallbackListener() {
          public void onResult(ResultCode param1ResultCode, String param1String1, String param1String2) {
            Toast.makeText(ForgetPsdPop.this.context, param1String1, 0).show();
            if (param1ResultCode == ResultCode.SUCCESS) {
              ForgetPsdPop.access$102(ForgetPsdPop.this, false);
              ForgetPsdPop.this.layout_phoneCode.setVisibility(0);
              return;
            } 
            ForgetPsdPop.this.cancel();
          }
        });
  }
  
  private void setListener() {
    this.btn_submit.setOnClickListener(this);
    this.close.setOnClickListener(this);
    this.tv_close.setOnClickListener(this);
  }
  
  public void onClick(View paramView) {
    if (paramView.getId() == R.id.btn_submit)
      if (this.isFirst) {
        sendSmsCode();
      } else {
        String str1 = this.et_phone.getText().toString().trim();
        String str2 = this.et_phone_code.getText().toString().trim();
        if (TextUtils.isEmpty(str1) || TextUtils.isEmpty(str2))
          Toast.makeText(this.context, "手机号或者验证码不能空", 0).show(); 
        (new ResetPsdPop(this.context, str1, str2, null)).show();
        cancel();
      }  
    if (paramView.getId() == R.id.close || paramView.getId() == R.id.tv_close)
      cancel(); 
  }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\icloud\sdk\view\ForgetPsdPop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */