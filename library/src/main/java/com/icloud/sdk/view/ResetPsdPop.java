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
import com.icloud.sdk.listener.CallbackListener;
import com.icloud.sdk.listener.ResultCode;

public class ResetPsdPop extends Dialog implements View.OnClickListener {
  private Button btn_submit;
  
  private Button close;
  
  private String code;
  
  private Context context;
  
  private EditText et_psd;
  
  private EditText et_psdconfirm;
  
  private CallbackListener listener;
  
  private String phone;
  
  private TextView tv_close;
  
  public ResetPsdPop(Context paramContext, String paramString1, String paramString2, CallbackListener paramCallbackListener) {
    super(paramContext, R.style.base_pop);
    this.context = paramContext;
    this.listener = paramCallbackListener;
    setCanceledOnTouchOutside(false);
    setContentView(R.layout.pop_resetpsd);
    initview();
    setListener();
    this.phone = paramString1;
    this.code = paramString2;
  }
  
  private void initview() {
    this.et_psd = (EditText)findViewById(R.id.et_psd);
    this.et_psdconfirm = (EditText)findViewById(R.id.et_psdconfirm);
    this.btn_submit = (Button)findViewById(R.id.btn_submit);
    this.close = (Button)findViewById(R.id.close);
    this.tv_close = (TextView)findViewById(R.id.tv_close);
  }
  
  private void setListener() {
    this.btn_submit.setOnClickListener(this);
    this.close.setOnClickListener(this);
    this.tv_close.setOnClickListener(this);
  }
  
  private void submit() {
    String str1 = this.et_psd.getText().toString().trim();
    String str2 = this.et_psdconfirm.getText().toString().trim();
    if (TextUtils.isEmpty(str1) || TextUtils.isEmpty(str2)) {
      Toast.makeText(this.context, "密码不能为空！", 0).show();
      return;
    } 
    if (!str1.equals(str2)) {
      Toast.makeText(this.context, "两次输入密码不一致！", 0).show();
      return;
    } 
    YZSDK.instance().resetPwd(this.context, this.phone, this.code, str1, new CallbackListener() {
          public void onResult(ResultCode param1ResultCode, String param1String1, String param1String2) {
            Toast.makeText(ResetPsdPop.this.context, param1String1, 0).show();
            if (param1ResultCode == ResultCode.SUCCESS) {
              if (ResetPsdPop.this.listener != null)
                ResetPsdPop.this.listener.onResult(ResultCode.SUCCESS, param1String1, param1String2); 
              ResetPsdPop.this.cancel();
            } 
          }
        });
  }
  
  public void onClick(View paramView) {
    if (paramView.getId() == R.id.btn_submit)
      submit(); 
    if (paramView.getId() == R.id.close || paramView.getId() == R.id.tv_close)
      cancel(); 
  }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\icloud\sdk\view\ResetPsdPop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */