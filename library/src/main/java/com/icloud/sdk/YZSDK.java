package com.icloud.sdk;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.icloud.sdk.adapter.base.Account;
import com.icloud.sdk.adapter.base.Pay;
import com.icloud.sdk.adapter.base.Sdk;
import com.icloud.sdk.config.HttpConfig;
import com.icloud.sdk.config.PayType;
import com.icloud.sdk.config.Platform;
import com.icloud.sdk.listener.CallbackListener;
import com.icloud.sdk.listener.ResultCode;
import com.icloud.sdk.model.ClientDeviceInfo;
import com.icloud.sdk.model.ConfigInfo;
import com.icloud.sdk.model.User;
import com.icloud.sdk.utils.FileUtils;
import com.icloud.sdk.utils.LogUtil;
import com.icloud.sdk.utils.SharedPreferenceUtil;
import com.icloud.sdk.utils.Util;
import com.icloud.sdk.view.AutoLoginPop;
import com.icloud.sdk.view.LoginPop;
import com.icloud.sdk.view.WebPaySelectPop;
import com.tencent.mm.opensdk.utils.Log;

import org.json.JSONObject;


public class YZSDK
        extends BaseYZSDK {
    private static YZSDK _instance;

    public static YZSDK instance() {
        if (_instance == null) {
            _instance = new YZSDK();
        }
        return _instance;
    }


    public void initApp(Application app, CallbackListener sdkInitListener) {
        super.initApp(app, sdkInitListener);
        HttpConfig.init((ConfigInfo.getInstance()).SERVER_URL);
        sdkInitListener.onResult(ResultCode.SUCCESS, "", "");
    }


    public void init(Activity act, CallbackListener sdkInitListener) {
        super.init(act, sdkInitListener);
        serverInit(act, sdkInitListener);
    }

    private void serverInit(Context context, CallbackListener sdkInitListener) {
        if (!Util.isNetworkAvailable(context)) {
            if (sdkInitListener != null)
                sdkInitListener.onResult(ResultCode.Fail, "网络连接失败", "");
            return;
        }
        JSONObject json = new JSONObject();
        try {
            json.put("gameId", (ConfigInfo.getInstance()).GAME_ID);
            json.put("channelId", (ConfigInfo.getInstance()).CHANNEL_ID);
            json.put("deviceKey", SharedPreferenceUtil.getImei());
            json.put("phoneModel", (ClientDeviceInfo.getInstance()).nickname);
            json.put("platform", Platform.Android);
            json.put("timeStamp", FileUtils.getSecondTimestamp());
            json.put("signType", "md5");
        } catch (Exception exception) {
        }

        Sdk.getInstance().serverInit(context, json.toString(), sdkInitListener);
    }


    public void login(Context ctx, CallbackListener list) {
        if (TextUtils.isEmpty(SharedPreferenceUtil.getAccessToken())) {
            LoginPop loginPop = new LoginPop(ctx, list);
            loginPop.show();
        } else {
            AutoLoginPop autoLoginPop = new AutoLoginPop(ctx, list);
            autoLoginPop.show();
        }
    }


    public void exitSDK(Context ctx, CallbackListener exitListener) {
        if (exitListener != null) {
            exitListener.onResult(ResultCode.SUCCESS, "", "");
        }
    }


    public boolean sendSMSInAcc(Context ctx, String phone, Account.SendCodeType type, CallbackListener list) {
        if (TextUtils.isEmpty(phone)) {
            if (list != null)
                list.onResult(ResultCode.Fail, "手机号为空", "");
            return false;
        }
        JSONObject json = new JSONObject();
        try {
            json.put("gameId", (ConfigInfo.getInstance()).GAME_ID);
            json.put("phone", phone);
            json.put("type", type.toString());
            json.put("timeStamp", FileUtils.getSecondTimestamp());
            json.put("signType", "md5");
        } catch (Exception exception) {
        }

        return Account.getInstance().sendMsgToPhone(ctx, json, list);
    }


    public boolean regist(Context ctx, String account, String password, CallbackListener listener) {
        if (TextUtils.isEmpty(password)) {
            if (listener != null)
                listener.onResult(ResultCode.Fail, "用户名或密码为空", "");
            return false;
        }

        JSONObject json = new JSONObject();
        try {
            json.put("gameId", (ConfigInfo.getInstance()).GAME_ID);
            json.put("channelId", (ConfigInfo.getInstance()).CHANNEL_ID);
            json.put("account", account);
            json.put("password", password);
            json.put("deviceKey", SharedPreferenceUtil.getImei());
            json.put("phoneModel", (ClientDeviceInfo.getInstance()).nickname);
            json.put("platform", Platform.Android);
            json.put("timeStamp", FileUtils.getSecondTimestamp());
            json.put("signType", "md5");
        } catch (Exception exception) {
        }

        return Account.getInstance().register(ctx, json.toString(), listener);
    }


    public boolean fastLogin(Context ctx, CallbackListener listener) {
        JSONObject json = new JSONObject();

        try {
            json.put("accessToken", SharedPreferenceUtil.getAccessToken());
            json.put("loginType", Account.LoginType.quick);
        } catch (Exception exception) {
        }

        return comLogin(ctx, json.toString(), listener, false);
    }


    public boolean login(Context ctx, String account, String password, Account.LoginType loginType, CallbackListener listener) {
        JSONObject json = new JSONObject();
        try {
            if (!TextUtils.isEmpty(account)) {
                json.put("account", account);
            }
            if (!TextUtils.isEmpty(password)) {
                json.put("password", password);
            }
            json.put("loginType", loginType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comLogin(ctx, json.toString(), listener, false);
    }


    public boolean checkLogin(Context ctx, String account, String password, Account.LoginType loginType, CallbackListener listener) {
        JSONObject json = new JSONObject();
        try {
            if (!TextUtils.isEmpty(account)) {
                json.put("account", account);
            }
            if (!TextUtils.isEmpty(password)) {
                json.put("password", password);
            }
            json.put("loginType", loginType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comLogin(ctx, json.toString(), listener, true);
    }

    private boolean comLogin(Context ctx, String jsonStr, CallbackListener listener, boolean isonlycheck) {
        if (TextUtils.isEmpty(jsonStr)) {
            jsonStr = "{}";
        }

        if (ctx == null || (ctx instanceof Activity && ((Activity) ctx).isFinishing())) {
            LogUtil.e("login", "Context is null or isFinishing ");
            if (listener != null)
                listener.onResult(ResultCode.Fail, "Context 为空", "");
            return false;
        }
        if (!Util.isNetworkAvailable(ctx)) {
            if (listener != null)
                listener.onResult(ResultCode.Fail, "网络连接失败", "");
            return false;
        }
        try {
            JSONObject json = new JSONObject(jsonStr);

            json.put("gameId", (ConfigInfo.getInstance()).GAME_ID);
            json.put("channelId", (ConfigInfo.getInstance()).CHANNEL_ID);
            json.put("zoneId", 0);
            json.put("zoneName", Util.getAppName(ctx));
            json.put("deviceKey", SharedPreferenceUtil.getImei());
            json.put("phoneModel", (ClientDeviceInfo.getInstance()).nickname);
            json.put("platform", Platform.Android);

            json.put("timeStamp", FileUtils.getSecondTimestamp());
            json.put("signType", "md5");

            jsonStr = json.toString();
        } catch (Exception e) {
            Log.e("comLogin", e.toString());
        }

        if (isonlycheck) {
            return Account.getInstance().loginCheck(ctx, jsonStr, listener);
        }
        return Account.getInstance().login(ctx, jsonStr, listener);
    }


    public boolean resetPwd(Context ctx, String phone, String phoneCode, String password, CallbackListener listener) {
        JSONObject json = new JSONObject();
        try {
            json.put("gameId", (ConfigInfo.getInstance()).GAME_ID);
            json.put("phone", phone);
            json.put("phoneCode", phoneCode);
            json.put("password", password);
            json.put("timeStamp", FileUtils.getSecondTimestamp());
            json.put("signType", "md5");
        } catch (Exception exception) {
        }


        return Account.getInstance().resetPwd(ctx, json.toString(), listener);
    }


    public boolean changePwd(Context ctx, String account, String oldPsd, String newPsd, CallbackListener listener) {
        JSONObject json = new JSONObject();
        try {
            json.put("gameId", (ConfigInfo.getInstance()).GAME_ID);
            json.put("account", account);
            json.put("oldPassword", oldPsd);
            json.put("newPassword", newPsd);
            json.put("timeStamp", FileUtils.getSecondTimestamp());
            json.put("signType", "md5");
        } catch (Exception exception) {
        }


        return Account.getInstance().changePwd(ctx, json.toString(), listener);
    }

    public boolean autoPay(Activity ctx, String productId, String productName, String productInfo, int productTotalFee, String attach, CallbackListener payListener) {
        WebPaySelectPop webPaySelectPop = new WebPaySelectPop(ctx, productId, productName, productInfo, productTotalFee, attach, payListener);
        webPaySelectPop.show();
        return true;
    }


    public boolean aliPay(Activity ctx, String productId, String productName, String productInfo, int productTotalFee, String attach, CallbackListener payListener) {
        Pay.getInstance().setCurrentPayType(PayType.AliPay);
        return pay(ctx, productId, productName, productInfo, productTotalFee, PayType.AliPay, attach, payListener);
    }


    public boolean wxPay(Activity ctx, String productId, String productName, String productInfo, int productTotalFee, String attach, CallbackListener payListener) {
        Pay.getInstance().setCurrentPayType(PayType.WXPay);
        return pay(ctx, productId, productName, productInfo, productTotalFee, PayType.WXPay, attach, payListener);
    }


    private boolean pay(Activity ctx, String productId, String productName, String productInfo, int productFee, String tradeMethod, String attach, CallbackListener payListener) {
        if (ctx == null || (ctx instanceof Activity && ctx.isFinishing())) {
            LogUtil.e("getOrder", "Context is null or isFinishing ");
            return false;
        }
        JSONObject json = new JSONObject();
        try {
            json.put("gameId", (ConfigInfo.getInstance()).GAME_ID);
            json.put("playerId", User.getInstance().getPlayerId());
            json.put("channelId", (ConfigInfo.getInstance()).CHANNEL_ID);
            json.put("zoneId", 0);
            json.put("zoneName", Util.getAppName(ctx));
            json.put("productId", productId);
            json.put("productName", productName);
            json.put("productCount", 1);
            json.put("productInfo", productInfo);
            json.put("productFee", productFee);
            json.put("tradeMethod", tradeMethod);
            if (!TextUtils.isEmpty(attach)) json.put("attach", attach);
            json.put("timeStamp", FileUtils.getSecondTimestamp());
            json.put("signType", "md5");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Pay.getInstance().pay(ctx, json.toString(), payListener);
    }

    public boolean bindPhone(Context context, String phone, String phoneCode, CallbackListener listener) {
        JSONObject json = new JSONObject();
        try {
            json.put("gameId", (ConfigInfo.getInstance()).GAME_ID);
            json.put("playerId", User.getInstance().getPlayerId());
            json.put("accessToken", User.getInstance().getAccessToken());
            json.put("phone", phone);
            json.put("phoneCode", phoneCode);
            json.put("timeStamp", FileUtils.getSecondTimestamp());
            json.put("signType", "md5");
        } catch (Exception exception) {
        }

        return Account.getInstance().bindPhone(context, json.toString(), listener);
    }

    public boolean notice(Context context, CallbackListener listener) {
        JSONObject json = new JSONObject();
        try {
            json.put("gameId", (ConfigInfo.getInstance()).GAME_ID);
            json.put("channelId", (ConfigInfo.getInstance()).CHANNEL_ID);
            json.put("zoneId", 0);
            json.put("timeStamp", FileUtils.getSecondTimestamp());
            json.put("signType", "md5");
        } catch (Exception exception) {
        }

        return Account.getInstance().notice(context, json, listener);
    }


    public void logout(Context context) {
        Account.getInstance().logout(context);
    }
}


