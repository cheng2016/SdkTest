package com.icloud.sdk.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import com.icloud.sdk.R;
import com.icloud.sdk.YZSDK;
import com.icloud.sdk.listener.CallbackListener;
import com.icloud.sdk.listener.ResultCode;

public class WebPaySelectPop extends Dialog implements View.OnClickListener {
  String attach;
  
  Activity context;
  
  CallbackListener payListener;
  
  String productId;
  
  String productInfo;
  
  String productName;
  
  int productTotalFee;
  
  public WebPaySelectPop(Activity paramActivity, String paramString1, String paramString2, String paramString3, int paramInt, String paramString4, CallbackListener paramCallbackListener) {
    super(paramActivity, R.style.base_pop);
    this.context = paramActivity;
    this.payListener = paramCallbackListener;
    this.productId = paramString1;
    this.productName = paramString2;
    this.productInfo = paramString3;
    this.productTotalFee = paramInt;
    this.attach = paramString4;
    setCanceledOnTouchOutside(true);
    setContentView(R.layout.pay_pop_select);
    View view1 = findViewById(R.id.btn_ali_pay);
    View view2 = findViewById(R.id.btn_gamecard_pay);
    view1.setOnClickListener(this);
    view2.setOnClickListener(this);
    setOnCancelListener(new DialogInterface.OnCancelListener() {
          public void onCancel(DialogInterface param1DialogInterface) {
            if (WebPaySelectPop.this.payListener != null) {
              WebPaySelectPop.this.payListener.onResult(ResultCode.CANCEL, "", "");
              Log.i("WebPaySelectPop", "code: " + ResultCode.CANCEL.name());
              WebPaySelectPop.this.payListener = null;
            } 
          }
        });
  }
  
  public void onClick(View paramView) {
    if (paramView.getId() == R.id.btn_ali_pay) {
      YZSDK.instance().aliPay(this.context, this.productId, this.productName, this.productInfo, this.productTotalFee, this.attach, this.payListener);
    } else if (paramView.getId() == R.id.btn_gamecard_pay) {
      YZSDK.instance().wxPay(this.context, this.productId, this.productName, this.productInfo, this.productTotalFee, this.attach, this.payListener);
    } 
    dismiss();
  }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\icloud\sdk\view\WebPaySelectPop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */