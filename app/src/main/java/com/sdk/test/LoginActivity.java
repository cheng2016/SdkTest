package com.sdk.test;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.swzg.yzxx.R;
import com.icloud.sdk.YZSDK;
import com.icloud.sdk.listener.CallbackListener;
import com.icloud.sdk.listener.ResultCode;
import com.yz.action.ActionSdk;
import java.util.ArrayList;

public class LoginActivity extends Activity implements View.OnClickListener {
  private Button login_login;
  
  @TargetApi(23)
  private void checkAndRequestPermission() {
    ArrayList arrayList = new ArrayList();
    if (checkSelfPermission("android.permission.READ_PHONE_STATE") != 0)
      arrayList.add("android.permission.READ_PHONE_STATE"); 
    if (arrayList.size() == 0) {
      YZSDK.instance().onCreate(this);
      return;
    } 
    String[] arrayOfString = new String[arrayList.size()];
    arrayList.toArray(arrayOfString);
    requestPermissions(arrayOfString, 1024);
  }
  
  
  private boolean hasAllPermissionsGranted(int[] paramArrayOfInt) {
    int i = paramArrayOfInt.length;
    for (byte b = 0; b < i; b++) {
      if (paramArrayOfInt[b] == -1)
        return false; 
    } 
    return true;
  }
  
  private void initview() { this.login_login = (Button)findViewById(R.id.login_login); }
  
  private void login() { YZSDK.instance().login(this, new CallbackListener() {
          public void onResult(ResultCode param1ResultCode, String param1String1, String param1String2) {
            if (param1ResultCode == ResultCode.SUCCESS) {
              Intent intent = new Intent(LoginActivity.this, GameActivity.class);
              intent.putExtra("sdkinfo", param1String2);
              LoginActivity.this.startActivity(intent);
            } 
          }
        }); }
  
  private void setListener() { this.login_login.setOnClickListener(this); }
  
  public void onClick(View paramView) {
    if (paramView.getId() == R.id.login_login)
      login(); 
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_main);
    YZSDK.instance().init(this, new CallbackListener() {
          public void onResult(ResultCode param1ResultCode, String param1String1, String param1String2) {
            if (param1ResultCode != ResultCode.SUCCESS) {
              Toast.makeText(LoginActivity.this, param1String1, 0).show();
              return;
            } 
            LoginActivity.this.login();
          }
        });
    if (Build.VERSION.SDK_INT >= 23) {
      checkAndRequestPermission();
    } else {
      YZSDK.instance().onCreate(this);
    } 
    initview();
    setListener();
  }
  
  protected void onPause() {
    super.onPause();
    YZSDK.instance().onPause(this);
  }
  
  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfInt) {
    super.onRequestPermissionsResult(paramInt, paramArrayOfString, paramArrayOfInt);
    if (paramInt == 1024 && hasAllPermissionsGranted(paramArrayOfInt)) {
      ActionSdk.getInstance().onCreate(this);
      return;
    } 
    Toast.makeText(this, "应用缺少必要的权限！请点击\"权限\"，打开所需要的权限。", 1).show();
    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
    intent.setData(Uri.parse("package:" + getPackageName()));
    startActivity(intent);
    finish();
  }
 
  
  protected void onResume() {
    super.onResume();
    YZSDK.instance().onResume(this);
  }
}
