package com.icloud.sdk.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.icloud.sdk.view.LoadingBar;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpUtil {
  private static final String TAG = "OkHttpUtil";
  
  private static OkHttpClient client;
  
  private static LoadingBar loadingBar;

  private static boolean isSetLoading = false;
  
  static  {
    HttpLoggingInterceptor httpLoggingInterceptor = (new HttpLoggingInterceptor()).setLevel(HttpLoggingInterceptor.Level.BODY);
    client = (new OkHttpClient.Builder()).writeTimeout(30000L, TimeUnit.MILLISECONDS).readTimeout(20000L, TimeUnit.MILLISECONDS).connectTimeout(15000L, TimeUnit.MILLISECONDS).addNetworkInterceptor(httpLoggingInterceptor).build();
  }
  
  public static void get(String paramString, SimpleResponseHandler paramSimpleResponseHandler) {
    Request request = (new Request.Builder()).get().url(paramString).build();
    client.newCall(request).enqueue(paramSimpleResponseHandler);
  }
  
  public static OkHttpClient getClient() { return client; }
  
  private static ExecutorService getDefaultThreadPool() { return Executors.newCachedThreadPool(); }
  
  public static void post(Context paramContext, String paramString1, String paramString2, SimpleResponseHandler paramSimpleResponseHandler) {
    isSetLoading = true;
    loadingBar = new LoadingBar(paramContext);
    Logger.d("post", "url:" + paramString1 + "\njsonStr:" + paramString2);
    MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
    Request request = (new Request.Builder()).url(paramString1).post(RequestBody.create(mediaType, Util.generatingSign(paramString2).toString())).build();
    Call call = client.newCall(request);
    getDefaultThreadPool().execute(new ResponseRunnable(call, paramSimpleResponseHandler));
  }
  
  public static void postNoLoading(Context paramContext, String paramString1, String paramString2, SimpleResponseHandler paramSimpleResponseHandler) {
    isSetLoading = false;
    Logger.d("post", "url:" + paramString1 + "\njsonStr:" + paramString2);
    MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
    Request request = (new Request.Builder()).url(paramString1).post(RequestBody.create(mediaType, Util.generatingSign(paramString2).toString())).build();
    Call call = client.newCall(request);
    getDefaultThreadPool().execute(new ResponseRunnable(call, paramSimpleResponseHandler));
  }
  
  private static class ResponseRunnable implements Runnable {
    private Call call;
    
    private OkHttpUtil.SimpleResponseHandler callback;
    
    public ResponseRunnable(Call param1Call, OkHttpUtil.SimpleResponseHandler param1SimpleResponseHandler) {
      this.call = param1Call;
      this.callback = param1SimpleResponseHandler;
      param1SimpleResponseHandler.sendStartMessage();
    }
    
    public void run() {
      try {
        Response response = this.call.execute();
        this.callback.onResponse(this.call, response);
        return;
      } catch (IOException iOException) {
        this.callback.onFailure(this.call, iOException);
        return;
      } finally {
        this.callback.sendFinishMessage();
      } 
    }
  }
  
  private static class ResultHandler extends Handler {
    OkHttpUtil.SimpleResponseHandler responseHandler;
    
    ResultHandler(OkHttpUtil.SimpleResponseHandler param1SimpleResponseHandler, Looper param1Looper) {
      super(param1Looper);
      this.responseHandler = param1SimpleResponseHandler;
    }
    
    public void handleMessage(Message param1Message) {
      super.handleMessage(param1Message);
      this.responseHandler.handleMessage(param1Message);
    }
  }
  
  public static abstract class SimpleResponseHandler implements Callback {
    private Handler handler = new OkHttpUtil.ResultHandler(this, Looper.myLooper());
    
    public void handleMessage(Message message) {
      Log.d(TAG, "SimpleResponseHandler   handleMessage current Thread: " + Thread.currentThread().getName() +", message.what() == " + message.what);
      switch (message.what) {
        case -1:
          onStart();
          break;
        case 0:
          Object[] objects = (Object[]) message.obj;
          onSuccess((Call) objects[0], (Response) objects[1]);
          break;
        case 1:
          onFailure((Exception) message.obj);
          break;
        case 2:
          onFinish();
          break;
        default:
          break;
      }

    }
    
    Message obtainMessage(int param1Int, Object param1Object) { return Message.obtain(this.handler, param1Int, param1Object); }
    
    public abstract void onFailure(Exception param1Exception);
    
    public void onFailure(Call param1Call, IOException param1IOException) {
      Logger.i("OkHttpUtil", "SimpleResponseHandler   onFailure current Thread: " + Thread.currentThread().getName() + " , ThreadId : " + Thread.currentThread().getId());
      sendFailuerMessage(param1IOException);
    }
    
    public void onFinish() {
      Logger.d("OkHttpUtil", "SimpleResponseHandler    onFinish");
      if (isSetLoading || (loadingBar != null && loadingBar.isShowing()))
        loadingBar.cancel(); 
    }
    
    public void onResponse(Call param1Call, Response param1Response) throws IOException {
      Logger.i("OkHttpUtil", "SimpleResponseHandler   onResponse current Thread: " + Thread.currentThread().getName() + " , ThreadId : " + Thread.currentThread().getId());
      if (param1Response.code() < 200 || param1Response.code() >= 300) {
        sendFailuerMessage(new IOException(param1Response.message()));
        return;
      } 
      sendSuccessMessage(param1Response.code(), param1Call, param1Response);
    }
    
    public void onStart() {
      Logger.d("OkHttpUtil", "SimpleResponseHandler    onStart");
      if (isSetLoading)
        loadingBar.show(); 
    }

    public abstract void onSuccess(Call call, Response response);
    
    void sendFailuerMessage(Throwable param1Throwable) { this.handler.sendMessage(obtainMessage(1, param1Throwable)); }
    
    void sendFinishMessage() { this.handler.sendMessage(obtainMessage(2, null)); }
    
    void sendStartMessage() { this.handler.sendMessage(obtainMessage(-1, null)); }
    
    void sendSuccessMessage(int param1Int, Call param1Call, Response param1Response) { this.handler.sendMessage(obtainMessage(0, new Object[] { param1Call, param1Response })); }
  }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\icloud\sd\\utils\OkHttpUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */