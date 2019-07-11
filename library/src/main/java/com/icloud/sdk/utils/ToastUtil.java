package com.icloud.sdk.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
  public static boolean isShow = true;
  
  private static Toast toast;
  
  public static void hideToast() {
    if (toast != null)
      toast.cancel(); 
  }
  
  public static void show(Context paramContext, int paramInt1, int paramInt2) {
    if (toast == null) {
      toast = Toast.makeText(paramContext, paramInt1, paramInt2);
    } else {
      toast.setText(paramInt1);
      toast.setDuration(paramInt2);
    } 
    if (isShow)
      toast.show(); 
  }
  
  public static void show(Context paramContext, CharSequence paramCharSequence, int paramInt) {
    if (toast == null) {
      toast = Toast.makeText(paramContext, paramCharSequence, paramInt);
    } else {
      toast.setText(paramCharSequence);
      toast.setDuration(paramInt);
    } 
    if (isShow)
      toast.show(); 
  }
  
  public static void showLong(Context paramContext, int paramInt) {
    if (toast == null) {
      toast = Toast.makeText(paramContext, paramInt, 1);
    } else {
      toast.setText(paramInt);
    } 
    if (isShow)
      toast.show(); 
  }
  
  public static void showLong(Context paramContext, CharSequence paramCharSequence) {
    if (toast == null) {
      toast = Toast.makeText(paramContext, paramCharSequence, 1);
    } else {
      toast.setText(paramCharSequence);
    } 
    if (isShow)
      toast.show(); 
  }
  
  public static void showShort(Context paramContext, int paramInt) {
    if (toast == null) {
      toast = Toast.makeText(paramContext, paramInt, 0);
    } else {
      toast.setText(paramInt);
    } 
    if (isShow)
      toast.show(); 
  }
  
  public static void showShort(Context paramContext, CharSequence paramCharSequence) {
    if (toast == null) {
      toast = Toast.makeText(paramContext, paramCharSequence, 0);
    } else {
      toast.setText(paramCharSequence);
    } 
    if (isShow)
      toast.show(); 
  }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\icloud\sd\\utils\ToastUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */