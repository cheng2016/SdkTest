package com.sdk.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.swzg.yzxx.R;
import com.icloud.sdk.YZSDK;
import com.icloud.sdk.listener.CallbackListener;
import com.icloud.sdk.listener.ResultCode;
import com.yz.action.ActionSdk;
import org.json.JSONException;
import org.json.JSONObject;


public class GameActivity extends Activity implements View.OnClickListener {
  private Button btn_logout;
  
  private Button btn_pay;
  
  private TextView textview;
  
  public void aliPay(View paramView) {
    YZSDK.instance().aliPay(this, "1", "测试商品", "测试商品", 1, "", new CallbackListener() {
          public void onResult(ResultCode param1ResultCode, String param1String1, String param1String2) { Log.i("aliPay", "code ： " + param1ResultCode.name()); }
        });
    setPurchase();
  }
  
  public void autoPay(View paramView) {
    YZSDK.instance().autoPay(this, "1", "测试商品", "测试商品", 1, "", new CallbackListener() {
          public void onResult(ResultCode param1ResultCode, String param1String1, String param1String2) { Log.i("autoPay", "code ： " + param1ResultCode.name()); }
        });
    setPurchase();
  }
  
  
  public void onClick(View paramView) {
    switch (paramView.getId()) {
      default:
        return;
      case 2131296264:
        YZSDK.instance().logout(this);
        startActivity(new Intent(this, LoginActivity.class));
        Toast.makeText(this, "登出成功", 0).show();
        finish();
        return;
      case 2131296265:
        break;
    } 
    pay();
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_account);
    btn_logout = (Button) findViewById(R.id.btn_logout);
    btn_pay = (Button) findViewById(R.id.btn_pay);
    textview = (TextView) findViewById(R.id.textview);
    this.btn_logout.setOnClickListener(this);
    this.btn_pay.setOnClickListener(this);
    String str = getIntent().getStringExtra("sdkinfo");
    this.textview.setText(str);
  }
  
  public void pay() {
    YZSDK.instance().wxPay(this, "1", "测试商品", "测试商品", 1, "", new CallbackListener() {
          public void onResult(ResultCode param1ResultCode, String param1String1, String param1String2) { Log.i("wxPay", "code ： " + param1ResultCode.name()); }
        });
    setPurchase();
  }
  
  
  public void setPurchase() {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("content_type", "zhifibao");
      jSONObject.put("content_name", "dafjdk");
      jSONObject.put("content_id", "fajkd");
      jSONObject.put("content_num", 1);
      jSONObject.put("payment_channel", "zhifubao");
      jSONObject.put("currency", "fahdj");
      jSONObject.put("is_succes", true);
      jSONObject.put("currency_amount", 90);
    } catch (JSONException jSONException) {
      jSONException.printStackTrace();
    } 
    ActionSdk.getInstance().setPurchase(jSONObject.toString());
  }
}
