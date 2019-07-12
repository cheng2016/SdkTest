package com.icloud.sdk.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.icloud.sdk.model.ConfigInfo;
import com.tencent.mm.opensdk.utils.Log;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.json.JSONObject;

public class Util {
  private static String ALIPAY_PACK;
  
  private static String WX_PACK = "com.tencent.mm";
  
  static  {
    ALIPAY_PACK = "com.eg.android.AlipayGphone";
  }
  
  public static byte[] bmpToByteArray(Bitmap paramBitmap, boolean paramBoolean) {
    int j;
    int i;
    if (paramBitmap.getHeight() > paramBitmap.getWidth()) {
      i = paramBitmap.getWidth();
      j = paramBitmap.getWidth();
    } else {
      i = paramBitmap.getHeight();
      j = paramBitmap.getHeight();
    } 
    Bitmap bitmap = Bitmap.createBitmap(i, j, Bitmap.Config.RGB_565);
    Canvas canvas = new Canvas(bitmap);
    while (true) {
      canvas.drawBitmap(paramBitmap, new Rect(0, 0, i, j), new Rect(0, 0,i, j), null);
      if (paramBoolean)
        paramBitmap.recycle();
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
      bitmap.recycle();
      byte[] arrayOfByte = byteArrayOutputStream.toByteArray();
      try {
        byteArrayOutputStream.close();
        return arrayOfByte;
      } catch (Exception e) {
        i = paramBitmap.getHeight();
        j = paramBitmap.getHeight();
      } 
    } 
  }
  
  public static JSONObject generatingSign(String paramString) {
    String str = FileUtils.getNoSign(paramString, false);
    str = "appKey=" + (ConfigInfo.getInstance()).APP_KEY + "&" + str + "&appSecret=" + (ConfigInfo.getInstance()).APP_SECRET;
    LogUtil.d("sign", str);
    try {
      JSONObject jSONObject = new JSONObject(paramString);
      jSONObject.put("sign", MD5Util.MD5Encode(str, "UTF-8"));
      return jSONObject;
    } catch (Exception e) {
      Log.e("generatingSign", e.toString());
      return new JSONObject();
    } 
  }
  
	public static JSONObject generatingSign(JSONObject json){
		String sign = FileUtils.getNoSign(json.toString(),false);
		sign ="appKey="+ ConfigInfo.getInstance().APP_KEY+"&"+sign+"&"+"appSecret="+ConfigInfo.getInstance().APP_SECRET;
		LogUtil.d("sign",sign);
		try {
			json.put("sign", MD5Util.MD5Encode(sign, "UTF-8"));
		}catch (Exception e){
			com.tencent.mm.opensdk.utils.Log.e("generatingSign",e.toString());
		}
		return json;
	}
  
  public static String getAppName(Context paramContext) {
    try {
      PackageManager packageManager = paramContext.getPackageManager();
      return packageManager.getApplicationLabel(packageManager.getApplicationInfo(paramContext.getPackageName(), PackageManager.GET_META_DATA)).toString();
    } catch (android.content.pm.PackageManager.NameNotFoundException e) {
      e.printStackTrace();
      return null;
    } 
  }
  
	public static byte[] getHtmlByteArray(final String url) {
		 URL htmlUrl = null;     
		 InputStream inStream = null;     
		 try {         
			 htmlUrl = new URL(url);         
			 URLConnection connection = htmlUrl.openConnection();         
			 HttpURLConnection httpConnection = (HttpURLConnection)connection;         
			 int responseCode = httpConnection.getResponseCode();         
			 if(responseCode == HttpURLConnection.HTTP_OK){             
				 inStream = httpConnection.getInputStream();         
			  }     
			 } catch (MalformedURLException e) {               
				 e.printStackTrace();     
			 } catch (IOException e) {              
				e.printStackTrace();    
		  } 
		byte[] data = inputStreamToByte(inStream);

		return data;
	}
  
	public static byte[] inputStreamToByte(InputStream is) {
		try{
			ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
			int ch;
			while ((ch = is.read()) != -1) {
				bytestream.write(ch);
			}
			byte imgdata[] = bytestream.toByteArray();
			bytestream.close();
			return imgdata;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
  
  public static boolean isAlipayInstalled(Context paramContext) { return isAvilible(paramContext, ALIPAY_PACK); }
  
  public static boolean isAvilible(Context paramContext, String paramString) {
    List list = paramContext.getPackageManager().getInstalledPackages(0);
    if (list != null)
      for (byte b = 0; b < list.size(); b++) {
        if (((PackageInfo)list.get(b)).packageName.equals(paramString))
          return true; 
      }  
    return false;
  }
  
  public static boolean isNetworkAvailable(Context paramContext) {
    NetworkInfo networkInfo = ((ConnectivityManager)paramContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
    return (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED);
  }
  
  public static boolean isWXAppInstalled(Context paramContext) { return isAvilible(paramContext, WX_PACK); }
}
