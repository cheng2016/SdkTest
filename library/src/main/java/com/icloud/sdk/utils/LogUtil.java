package com.icloud.sdk.utils;

import android.content.Context;
import android.util.Log;
import com.icloud.sdk.model.ClientDeviceInfo;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class LogUtil {
  private static boolean DEBUG = false;
  
  private static final String LOG_STRIP = " ";
  
  private static final String LOG_SUFFIX = ".log";
  
  private static final SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
  
  private static String logPath;
  
  private static final int maxSequence = 8000;
  
  private static int num;
  
  private static int sequence;
  
  private static byte[] sync = new byte[0];
  
  static  {
    logPath = new String();
    DEBUG = false;
  }
  
  private static void LogFile(String paramString) {
    synchronized (sync) {
      if (!FileUtils.isSDCardMounted() || !DEBUG)
        return; 
      try {
        num = sequence / 8000;
        FileOutputStream fileOutputStream = new FileOutputStream(logPath + File.separator + "log" + num + ".log", true);
        fileOutputStream.write((dformat.format(Long.valueOf(Calendar.getInstance().getTimeInMillis())) + " " + paramString).getBytes());
        fileOutputStream.write(10);
        fileOutputStream.close();
      } catch (IOException e) {}
      return;
    } 
  }
  
  private static String catInfo(Object... paramVarArgs) {
    byte b = 0;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(String.format("[ThreadID=%04d]", new Object[] { Long.valueOf(Thread.currentThread().getId()) }));
    stringBuilder.append(" ");
    int i = paramVarArgs.length;
    while (b < i) {
      stringBuilder.append(String.valueOf(paramVarArgs[b])).append(" ");
      b++;
    } 
    return stringBuilder.toString();
  }
  
  public static void crash(Context paramContext, Throwable paramThrowable) {
    e("Exception", paramThrowable.getMessage());
    StringBuffer stringBuffer = new StringBuffer();
    for (Map.Entry entry : ClientDeviceInfo.collectDeviceInfo(paramContext).entrySet()) {
      String str1 = (String)entry.getKey();
      String str2 = (String)entry.getValue();
      stringBuffer.append(str1 + "=" + str2 + "\n");
    } 
    stringBuffer.append(genStackTrace(paramThrowable));
    try {
      if (!FileUtils.isSDCardMounted())
        return; 
      long l = System.currentTimeMillis();
      String str = dformat.format(new Date());
      str = "error-" + str + "-" + l + ".log";
      FileOutputStream fileOutputStream = new FileOutputStream(logPath + File.separator + str);
      fileOutputStream.write(stringBuffer.toString().getBytes());
      fileOutputStream.close();
      return;
    } catch (Exception e) {
      Log.e("crash", "an error occured while writing file...", e);
      return;
    } 
  }
  
  public static void d(Object paramObject, String paramString) {
    if (!DEBUG)
      return; 
    paramString = catInfo(new Object[] { paramString });
    Log.d(paramObject.getClass().getName(), paramString);
  }
  
  public static void d(String paramString, Exception paramException) {
    if (!DEBUG)
      return; 
    Log.d(paramString, genStackTrace(paramException));
  }
  
  public static void d(String paramString1, String paramString2) {
    if (!DEBUG)
      return; 
    Log.d(paramString1, catInfo(new Object[] { paramString2 }));
  }
  
  public static void e(Object paramObject, String paramString) {
    if (!DEBUG)
      return; 
    paramString = catInfo(new Object[] { paramString });
    Log.e(paramObject.getClass().getName(), paramString);
    LogFile(paramObject.getClass().getName() + " " + paramString);
  }
  
  public static void e(String paramString, Exception paramException) {
    if (!DEBUG)
      return; 
    String str = genStackTrace(paramException);
    Log.e(paramString, str);
    LogFile(paramString + " " + str);
  }
  
  public static void e(String paramString1, String paramString2) {
    if (!DEBUG)
      return; 
    paramString2 = catInfo(new Object[] { paramString2 });
    Log.e(paramString1, paramString2);
    LogFile(paramString1 + " " + paramString2);
  }
  
  public static String genStackTrace(Throwable paramThrowable) {
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    paramThrowable.printStackTrace(printWriter);
    for (paramThrowable = paramThrowable.getCause(); paramThrowable != null; paramThrowable = paramThrowable.getCause())
      paramThrowable.printStackTrace(printWriter); 
    printWriter.close();
    return stringWriter.toString();
  }
  
  public static void i(Object paramObject) {
    if (paramObject != null && DEBUG) {
      paramObject = catInfo(new Object[] { paramObject });
      Log.i("sysout", paramObject.toString());
      LogFile("sysout" + paramObject);
      return;
    } 
  }
  
  public static void i(Object paramObject, String paramString) {
    if (!DEBUG)
      return; 
    paramString = catInfo(new Object[] { paramString });
    Log.i(paramObject.getClass().getName(), paramString);
    LogFile(paramObject.getClass().getName() + " " + paramString);
  }
  
  public static void i(String paramString, Exception paramException) {
    if (!DEBUG)
      return; 
    String str = genStackTrace(paramException);
    Log.i(paramString, str);
    LogFile(paramString + " " + str);
  }
  
  public static void i(String paramString1, String paramString2) {
    if (!DEBUG)
      return; 
    paramString2 = catInfo(new Object[] { paramString2 });
    Log.i(paramString1, paramString2);
    LogFile(paramString1 + " " + paramString2);
  }
  
  public static void init(Context paramContext, boolean paramBoolean) {
    sequence = 0;
    num = 0;
    logPath = FileUtils.getLogPath(paramContext);
    DEBUG = paramBoolean;
  }
  
  public static void v(Object paramObject, String paramString) {
    if (!DEBUG)
      return; 
    paramString = catInfo(new Object[] { paramString });
    Log.v(paramObject.getClass().getName(), paramString);
  }
  
  public static void v(String paramString, Exception paramException) {
    if (!DEBUG)
      return; 
    Log.v(paramString, genStackTrace(paramException));
  }
  
  public static void v(String paramString1, String paramString2) {
    if (!DEBUG)
      return; 
    Log.v(paramString1, catInfo(new Object[] { paramString2 }));
  }
  
  public static void w(Object paramObject, String paramString) {
    if (!DEBUG)
      return; 
    paramString = catInfo(new Object[] { paramString });
    Log.w(paramObject.getClass().getName(), paramString);
    LogFile(paramObject.getClass().getName() + " " + paramString);
  }
  
  public static void w(String paramString, Exception paramException) {
    if (!DEBUG)
      return; 
    String str = genStackTrace(paramException);
    Log.w(paramString, str);
    LogFile(paramString + " " + str);
  }
  
  public static void w(String paramString1, String paramString2) {
    if (!DEBUG)
      return; 
    paramString2 = catInfo(new Object[] { paramString2 });
    Log.w(paramString1, paramString2);
    LogFile(paramString1 + " " + paramString2);
  }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\icloud\sd\\utils\LogUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */
