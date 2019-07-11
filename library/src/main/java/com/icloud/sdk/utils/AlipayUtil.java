package com.icloud.sdk.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.alipay.sdk.app.PayTask;
import com.icloud.sdk.listener.CallbackListener;
import com.icloud.sdk.listener.ResultCode;
import com.icloud.sdk.model.PayResult;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import org.json.JSONObject;

public class AlipayUtil {
  private static final int SDK_PAY_FLAG = 1;
  
  private static AlipayUtil instance;
  
  private Handler mHandler = new Handler() {
      public void handleMessage(Message param1Message) {
        switch (param1Message.what) {
          default:
            return;
          case 1:
            break;
        } 
        if (AlipayUtil.this.payListener != null) {
          PayResult payResult = new PayResult((Map)param1Message.obj);
          payResult.getResult();
          if (TextUtils.equals(payResult.getResultStatus(), "9000")) {
            AlipayUtil.this.payListener.onResult(ResultCode.SUCCESS, "", "");
            return;
          } 
          AlipayUtil.this.payListener.onResult(ResultCode.Fail, "", "");
          return;
        } 
      }
    };
  

  private String getSignType() { return "sign_type=\"RSA\""; }
  
  public static AlipayUtil instance() {
    if (instance == null)
      instance = new AlipayUtil(); 
    return instance;
  }

  CallbackListener payListener;
  public void pay(final Activity ctx, JSONObject jsonObject,CallbackListener listener) {
    this.payListener = listener;
    String orderInfo = jsonObject.optString("orderInfo");
    String sign = jsonObject.optString("sign");
    try {
      // 仅需对sign 做URL编码
      sign = URLEncoder.encode(sign, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    // 完整的符合支付宝参数规范的订单信息
    final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
            + getSignType();

    Runnable payRunnable = new Runnable() {
      @Override
      public void run() {
        // 构造PayTask 对象
        PayTask alipay = new PayTask(ctx);
        // 调用支付接口，获取支付结果
        Map<String, String> result = alipay.payV2(payInfo, true);

        Message msg = new Message();
        msg.what = SDK_PAY_FLAG;
        msg.obj = result;
        mHandler.sendMessage(msg);
      }
    };
    // 必须异步调用
    Thread payThread = new Thread(payRunnable);
    payThread.start();
  }
}