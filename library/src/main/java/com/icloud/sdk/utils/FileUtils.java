package com.icloud.sdk.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.TextUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class FileUtils {
  public static String PACK_NAME = "icloud";

  public static void createFile(String paramString, byte[] paramArrayOfByte) {
    try {
      FileOutputStream fileOutputStream = new FileOutputStream(paramString);
      fileOutputStream.write(paramArrayOfByte);
      fileOutputStream.close();
      return;
    } catch (Exception paramString) {
      paramString.printStackTrace();
      return;
    }
  }

  public static boolean createFile(String paramString1, String paramString2) throws IOException {
    if (!TextUtils.isEmpty(paramString1) && !TextUtils.isEmpty(paramString1)) {
      File file = new File(paramString1);
      if (file.isDirectory() || file.mkdirs()) {
        File file1 = new File(paramString1 + File.separator + paramString2);
        if (file1.exists() || file1.createNewFile())
          return true;
      }
    }
    return false;
  }

  public static String getCurrTime() {
    String str1 = Calendar.getInstance();
    int i = str1.get(1);
    int j = str1.get(2) + 1;
    int k = str1.get(5);
    if (j >= 10) {
      String str = j + "";
    } else {
      str1 = "0" + j;
    }
    if (k >= 10) {
      String str = k + "";
      return i + str1 + str;
    }
    String str2 = "0" + k;
    return i + str1 + str2;
  }

  public static String getLogPath(Context paramContext) {
    String str = getRootCache(paramContext) + File.separator + "log";
    try {
      createFile(str, "log.txt");
      return str;
    } catch (Exception exception) {
      exception.printStackTrace();
      return str;
    }
  }

  public static String getMetaDataValue(Context paramContext, String paramString) {
    String str = null;
    PackageManager packageManager = paramContext.getPackageManager();
    try {
      ApplicationInfo applicationInfo = packageManager.getApplicationInfo(paramContext.getPackageName(), 128);
      paramContext = str;
      if (applicationInfo != null) {
        paramContext = str;
        if (applicationInfo.metaData != null)
          Object object = applicationInfo.metaData.get(paramString);
      }
    } catch (Exception paramContext) {
      paramContext.printStackTrace();
      paramContext = str;
    }
    str = paramContext;
    if (paramContext == null) {
      LogUtil.e("FileUtils", "错误！请查看metaData里是否包含：" + paramString + "?");
      str = new String();
    }
    return str.toString();
  }

  public static String getNoSign(String paramString, boolean paramBoolean) { // Byte code:
    //   0: aload_0
    //   1: invokestatic parseObject : (Ljava/lang/String;)Lcom/alibaba/fastjson/JSONObject;
    //   4: astore #6
    //   6: new java/lang/StringBuilder
    //   9: dup
    //   10: invokespecial <init> : ()V
    //   13: astore #7
    //   15: new java/util/ArrayList
    //   18: dup
    //   19: aload #6
    //   21: invokeinterface keySet : ()Ljava/util/Set;
    //   26: invokespecial <init> : (Ljava/util/Collection;)V
    //   29: astore #8
    //   31: aload #8
    //   33: invokestatic sort : (Ljava/util/List;)V
    //   36: iconst_1
    //   37: istore_3
    //   38: iconst_0
    //   39: istore_2
    //   40: iload_2
    //   41: aload #8
    //   43: invokeinterface size : ()I
    //   48: if_icmpge -> 185
    //   51: aload #8
    //   53: iload_2
    //   54: invokeinterface get : (I)Ljava/lang/Object;
    //   59: checkcast java/lang/String
    //   62: astore #9
    //   64: aload #6
    //   66: aload #9
    //   68: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   73: astore_0
    //   74: aload_0
    //   75: ifnonnull -> 155
    //   78: ldc ''
    //   80: astore_0
    //   81: iload_1
    //   82: ifne -> 95
    //   85: iload_3
    //   86: istore #4
    //   88: aload_0
    //   89: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   92: ifne -> 145
    //   95: new java/lang/StringBuilder
    //   98: dup
    //   99: invokespecial <init> : ()V
    //   102: astore #10
    //   104: iload_3
    //   105: ifeq -> 178
    //   108: ldc ''
    //   110: astore #5
    //   112: aload #7
    //   114: aload #10
    //   116: aload #5
    //   118: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   121: aload #9
    //   123: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   126: ldc '='
    //   128: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   131: aload_0
    //   132: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   135: invokevirtual toString : ()Ljava/lang/String;
    //   138: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   141: pop
    //   142: iconst_0
    //   143: istore #4
    //   145: iload_2
    //   146: iconst_1
    //   147: iadd
    //   148: istore_2
    //   149: iload #4
    //   151: istore_3
    //   152: goto -> 40
    //   155: aload_0
    //   156: instanceof java/lang/String
    //   159: ifeq -> 170
    //   162: aload_0
    //   163: checkcast java/lang/String
    //   166: astore_0
    //   167: goto -> 81
    //   170: aload_0
    //   171: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   174: astore_0
    //   175: goto -> 81
    //   178: ldc '&'
    //   180: astore #5
    //   182: goto -> 112
    //   185: aload #7
    //   187: invokevirtual toString : ()Ljava/lang/String;
    //   190: areturn }

    public static String getRoot(){
      String str = null;
      if (isSDCardMounted())
        str = Environment.getExternalStorageDirectory().getAbsolutePath();
      return str;
    }

    public static String getRootCache (Context paramContext){
      String str = null;
      if (isSDCardMounted())
        str = getRoot() + File.separator + PACK_NAME + File.separator + paramContext.getPackageName();
      return str;
    }

    public static int getSecondTimestamp () {
      Date date = new Date();
      if (date != null) {
        String str = String.valueOf(date.getTime());
        int i = str.length();
        if (i > 3)
          return Integer.valueOf(str.substring(0, i - 3)).intValue();
      }
      return 0;
    }

    public static boolean isSDCardMounted() {
      return "mounted".equals(Environment.getExternalStorageState());
    }
  }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\icloud\sd\\utils\FileUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */