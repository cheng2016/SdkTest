package com.icloud.sdk.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.icloud.sdk.R;
import com.icloud.sdk.YZSDK;
import com.icloud.sdk.listener.CallbackListener;
import com.icloud.sdk.listener.ResultCode;
import com.icloud.sdk.utils.ToastUtil;

public class ChangePsdPop extends Dialog implements View.OnClickListener {
  private Button btn_submit;
  
  private Button close;
  
  Context context;
  
  private EditText et_account;
  
  private EditText et_newpsd;
  
  private EditText et_newpsdConfirm;
  
  private EditText et_psd;
  
  public ChangePsdPop(Context paramContext) {
    super(paramContext, R.style.base_pop);
    this.context = paramContext;
    setCanceledOnTouchOutside(false);
    setContentView(R.layout.pop_changepsd);
    initview();
    setListener();
  }
  
  private void changePwd() {
    String str1 = this.et_account.getText().toString().trim();
    if (TextUtils.isEmpty(str1)) {
      ToastUtil.show(this.context, "账号不能为空", 100);
      return;
    } 
    String str2 = this.et_psd.getText().toString().trim();
    if (TextUtils.isEmpty(str2)) {
      ToastUtil.show(this.context, "原密码不能为空", 100);
      return;
    } 
    String str3 = this.et_newpsd.getText().toString().trim();
    if (TextUtils.isEmpty(str3)) {
      ToastUtil.show(this.context, "新密码不能为空", 100);
      return;
    } 
    String str4 = this.et_newpsdConfirm.getText().toString().trim();
    if (TextUtils.isEmpty(str4)) {
      ToastUtil.show(this.context, "确认密码不能为空", 100);
      return;
    } 
    if (!str3.equals(str4)) {
      ToastUtil.show(this.context, "两次输入的新密码不一致", 100);
      return;
    } 
    if (str3.equals(str2)) {
      ToastUtil.show(this.context, "新密码不能与原密码相同", 100);
      return;
    } 
    YZSDK.instance().changePwd(this.context, str1, str2, str3, new CallbackListener() {
          public void onResult(ResultCode param1ResultCode, String param1String1, String param1String2) {
            Toast.makeText(ChangePsdPop.this.context, param1String1, 0).show();
            if (param1ResultCode == ResultCode.SUCCESS)
              ChangePsdPop.this.cancel(); 
          }
        });
  }
  
  private void initview() {
    this.et_account = (EditText)findViewById(R.id.et_account);
    this.et_psd = (EditText)findViewById(R.id.et_psd);
    this.et_newpsd = (EditText)findViewById(R.id.et_newpsd);
    this.et_newpsdConfirm = (EditText)findViewById(R.id.et_newpsdConfirm);
    this.btn_submit = (Button)findViewById(R.id.btn_submit);
    this.close = (Button)findViewById(R.id.close);
  }
  
  private void setListener() {
    this.btn_submit.setOnClickListener(this);
    this.close.setOnClickListener(this);
  }
  
  public void onClick(View paramView) {
    if (paramView.getId() == R.id.btn_submit)
      changePwd(); 
    if (paramView.getId() == R.id.close)
      cancel(); 
  }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\icloud\sdk\view\ChangePsdPop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */