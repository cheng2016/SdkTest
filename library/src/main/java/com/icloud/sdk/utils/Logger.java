package com.icloud.sdk.utils;

import android.content.Context;
import android.os.Environment;
import android.os.Process;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Logger {
  public static final int A = 7;
  
  public static final int D = 3;
  
  public static final int E = 6;
  
  private static final ExecutorService EXECUTOR_SERVICE;
  
  public static final int I = 4;
  
  private static final String LINE_SEP;
  
  private static final String LOG_FORMAT = "%s  %d/%d/%s  %c/%s  %s：";
  
  private static final DateFormat LOG_TIME_FORMAT;
  
  private static final char[] T;
  
  private static final String TAG = Logger.class.getSimpleName();
  
  public static final int V = 2;
  
  public static final int W = 5;
  
  private static Level currentLevel;
  
  private static String defaultDir;
  
  private static String defaultTag;
  
  private static boolean isWriter;
  
  private static int myPid;
  
  private static int pkgCode;
  
  public static String pkgName;
  
  static  {
    T = new char[] { 'V', 'D', 'I', 'W', 'E', 'A' };
    LOG_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    LINE_SEP = System.getProperty("line.separator");
    EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();
    isWriter = true;
    currentLevel = Level.VERBOSE;
    defaultTag = "Logger";
  }
  
  public static int checkSelfPermission(Context paramContext, String paramString) {
    if (paramString == null)
      throw new IllegalArgumentException("permission is null"); 
    return paramContext.checkPermission(paramString, Process.myPid(), Process.myUid());
  }
  
  private static boolean createOrExistsDir(File paramFile) { return (paramFile != null && (paramFile.exists() ? paramFile.isDirectory() : paramFile.mkdirs())); }
  
  private static boolean createOrExistsFile(String paramString) {
    boolean bool = false;
    file = new File(paramString);
    if (file.exists())
      return file.isFile(); 
    if (createOrExistsDir(file.getParentFile()))
      try {
        return file.createNewFile();
      } catch (IOException file) {
        file.printStackTrace();
        return false;
      }  
    return bool;
  }
  
  public static final void d(String paramString1, String paramString2) { log(3, paramString1, paramString2); }
  
  public static final void d(String paramString1, String paramString2, Throwable paramThrowable) { log(3, paramString1, paramString2, paramThrowable); }
  
  public static final void e(String paramString1, String paramString2) { log(6, paramString1, paramString2); }
  
  public static final void e(String paramString1, String paramString2, Throwable paramThrowable) { log(6, paramString1, paramString2, paramThrowable); }
  
  public static final void i(String paramString1, String paramString2) { log(4, paramString1, paramString2); }
  
  public static final void i(String paramString1, String paramString2, Throwable paramThrowable) { log(4, paramString1, paramString2, paramThrowable); }
  
  public static void init(Context paramContext) {
    Log.i(TAG, "init");
    pkgName = "com.icloud.sdk";
    myPid = Process.myPid();
    pkgCode = AppUtil.getVersionCode(paramContext);
    if (isSDCardOK())
      defaultDir = FileUtils.getLogPath(paramContext); 
    if (lacksPermissions(paramContext, new String[] { "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE" })) {
      Log.e(TAG, "很抱歉，没有读写权限，无法写入SD卡中");
      isWriter = false;
    } 
  }
  
  private static void input2File(final String input, final String fullPath) {
    if (!createOrExistsFile(paramString2)) {
      Log.e("Logger", "create " + paramString2 + " failed!");
      return;
    } 
    EXECUTOR_SERVICE.execute(new Runnable() {
          public void run() { // Byte code:
            //   0: aconst_null
            //   1: astore_1
            //   2: aconst_null
            //   3: astore #4
            //   5: new java/io/BufferedWriter
            //   8: dup
            //   9: new java/io/FileWriter
            //   12: dup
            //   13: aload_0
            //   14: getfield val$fullPath : Ljava/lang/String;
            //   17: iconst_1
            //   18: invokespecial <init> : (Ljava/lang/String;Z)V
            //   21: invokespecial <init> : (Ljava/io/Writer;)V
            //   24: astore_2
            //   25: aload_2
            //   26: aload_0
            //   27: getfield val$input : Ljava/lang/String;
            //   30: invokevirtual write : (Ljava/lang/String;)V
            //   33: aload_2
            //   34: ifnull -> 41
            //   37: aload_2
            //   38: invokevirtual close : ()V
            //   41: return
            //   42: astore_1
            //   43: aload_1
            //   44: invokevirtual printStackTrace : ()V
            //   47: return
            //   48: astore_3
            //   49: aload #4
            //   51: astore_2
            //   52: aload_2
            //   53: astore_1
            //   54: aload_3
            //   55: invokevirtual printStackTrace : ()V
            //   58: aload_2
            //   59: astore_1
            //   60: ldc 'Logger'
            //   62: new java/lang/StringBuilder
            //   65: dup
            //   66: invokespecial <init> : ()V
            //   69: ldc 'log to '
            //   71: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   74: aload_0
            //   75: getfield val$fullPath : Ljava/lang/String;
            //   78: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   81: ldc ' failed!'
            //   83: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   86: invokevirtual toString : ()Ljava/lang/String;
            //   89: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)I
            //   92: pop
            //   93: aload_2
            //   94: ifnull -> 41
            //   97: aload_2
            //   98: invokevirtual close : ()V
            //   101: return
            //   102: astore_1
            //   103: aload_1
            //   104: invokevirtual printStackTrace : ()V
            //   107: return
            //   108: astore_2
            //   109: aload_1
            //   110: ifnull -> 117
            //   113: aload_1
            //   114: invokevirtual close : ()V
            //   117: aload_2
            //   118: athrow
            //   119: astore_1
            //   120: aload_1
            //   121: invokevirtual printStackTrace : ()V
            //   124: goto -> 117
            //   127: astore_3
            //   128: aload_2
            //   129: astore_1
            //   130: aload_3
            //   131: astore_2
            //   132: goto -> 109
            //   135: astore_3
            //   136: goto -> 52
            // Exception table:
            //   from	to	target	type
            //   5	25	48	java/io/IOException
            //   5	25	108	finally
            //   25	33	135	java/io/IOException
            //   25	33	127	finally
            //   37	41	42	java/io/IOException
            //   54	58	108	finally
            //   60	93	108	finally
            //   97	101	102	java/io/IOException
            //   113	117	119	java/io/IOException }
        });
  }
  
  public static boolean isSDCardOK() { return !!Environment.getExternalStorageState().equals("mounted"); }
  
  private static boolean lacksPermission(Context paramContext, String paramString) { return (checkSelfPermission(paramContext, paramString) == -1); }
  
  public static boolean lacksPermissions(Context paramContext, String... paramVarArgs) {
    boolean bool = false;
    int i = paramVarArgs.length;
    for (byte b = 0;; b++) {
      boolean bool1 = bool;
      if (b < i) {
        if (lacksPermission(paramContext, paramVarArgs[b]))
          return true; 
      } else {
        return bool1;
      } 
    } 
  }
  
  public static final void log(int paramInt, String paramString1, String paramString2) {
    if (currentLevel.value > Level.WARN.value)
      return; 
    if (isWriter)
      write(paramString1, paramString2, paramInt); 
    Log.println(paramInt, TAG, paramString1 + " " + paramString2);
  }
  
  public static final void log(int paramInt, String paramString1, String paramString2, Throwable paramThrowable) {
    if (currentLevel.value > Level.WARN.value)
      return; 
    if (isWriter)
      write(paramString1, paramString2, paramInt, paramThrowable); 
    Log.println(paramInt, TAG, paramString1 + " " + paramString2);
  }
  
  public static void main(String[] paramArrayOfString) {
    String str = LOG_TIME_FORMAT.format(new Date());
    System.out.println("date: " + str.substring(0, 10));
    new Object[6][0] = str;
    new Object[6][1] = Integer.valueOf(123);
    new Object[6][2] = "com.cheng.app";
    new char[6][0] = 'V';
    new char[6][1] = 'D';
    new char[6][2] = 'I';
    new char[6][3] = 'W';
    new char[6][4] = 'E';
    new char[6][5] = 'A';
    str = String.format("%s  %d/%d/%s  %c/%s  %s：", new Object[] { null, null, null, Character.valueOf(new char[6][0]), "logg", "tag" });
    System.out.println(str + "this is a message!");
  }
  
  private static String saveCrashInfo(Throwable paramThrowable) {
    StringBuffer stringBuffer = new StringBuffer();
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    paramThrowable.printStackTrace(printWriter);
    for (paramThrowable = paramThrowable.getCause(); paramThrowable != null; paramThrowable = paramThrowable.getCause())
      paramThrowable.printStackTrace(printWriter); 
    printWriter.close();
    stringBuffer.append(stringWriter.toString());
    return stringBuffer.toString();
  }
  
  public static void setIsWriter(boolean paramBoolean) { isWriter = paramBoolean; }
  
  public static final void v(String paramString1, String paramString2) { log(2, paramString1, paramString2); }
  
  public static final void v(String paramString1, String paramString2, Throwable paramThrowable) { log(2, paramString1, paramString2, paramThrowable); }
  
  public static final void w(String paramString1, String paramString2) { log(5, paramString1, paramString2); }
  
  public static final void w(String paramString1, String paramString2, Throwable paramThrowable) { log(5, paramString1, paramString2, paramThrowable); }
  
  private static final void write(String paramString1, String paramString2, int paramInt) {
    String str1 = LOG_TIME_FORMAT.format(new Date(System.currentTimeMillis()));
    String str2 = str1.substring(0, 10);
    StringBuilder stringBuilder = new StringBuilder((str2 = defaultDir + str2 + ".txt").format("%s  %d/%d/%s  %c/%s  %s：", new Object[] { str1, Integer.valueOf(myPid), Integer.valueOf(pkgCode), pkgName, Character.valueOf(T[paramInt - 2]), defaultTag, paramString1 }));
    stringBuilder.append(paramString2);
    stringBuilder.append(LINE_SEP);
    input2File(stringBuilder.toString(), str2);
  }
  
  private static final void write(String paramString1, String paramString2, int paramInt, Throwable paramThrowable) {
    String str1 = LOG_TIME_FORMAT.format(new Date(System.currentTimeMillis()));
    String str2 = str1.substring(0, 10);
    StringBuilder stringBuilder = new StringBuilder((str2 = defaultDir + str2 + ".txt").format("%s  %d/%d/%s  %c/%s  %s：", new Object[] { str1, Integer.valueOf(myPid), Integer.valueOf(pkgCode), pkgName, Character.valueOf(T[paramInt - 2]), defaultTag, paramString1 }));
    stringBuilder.append(paramString2);
    stringBuilder.append(LINE_SEP);
    stringBuilder.append(saveCrashInfo(paramThrowable));
    input2File(stringBuilder.toString(), str2);
  }
  
  public enum Level {
    ASSERT,
    CLOSE,
    DEBUG,
    ERROR,
    INFO,
    VERBOSE(2),
    WARN(2);
    
    int value;
    
    static  {
      DEBUG = new Level("DEBUG", true, 3);
      INFO = new Level("INFO", 2, 4);
      WARN = new Level("WARN", 3, 5);
      ERROR = new Level("ERROR", 4, 6);
      ASSERT = new Level("ASSERT", 5, 7);
      CLOSE = new Level("CLOSE", 6, 8);
      $VALUES = new Level[] { VERBOSE, DEBUG, INFO, WARN, ERROR, ASSERT, CLOSE };
    }
    
    Level(int param1Int1) { this.value = param1Int1; }
  }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\icloud\sd\\utils\Logger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */