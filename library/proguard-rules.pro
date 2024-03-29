# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-dontskipnonpubliclibraryclasses # 不忽略非公共的库类
-optimizationpasses 5 # 指定代码的压缩级别
-dontusemixedcaseclassnames # 是否使用大小写混合
-dontpreverify # 混淆时是否做预校验 -verbose # 混淆时是否记录日志
-keepattributes *Annotation* # 保持注解
-ignorewarning # 忽略警告
-dontoptimize # 优化不优化输入的类文件
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/* # 混淆时所采用的算法
#保持哪些类不被混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
#生成日志数据，gradle build时在本项目根目录输出
-dump class_files.txt #apk包内所有class的内部结构
-printseeds seeds.txt #未混淆的类和成员
-printusage unused.txt #打印未被使用的代码
-printmapping mapping.txt #混淆前后的映射


-keep public class * extends android.support.** #如果有引用v4或者v7包，需添加

#-libraryjars libs/**.jar #混淆第三方jar包，其中xxx为jar包名
#-keep class com.xxx.**{*;} #不混淆某个包内的所有文件
#-dontwarn com.xxx** #忽略某个包的警告

-keepattributes Signature #不混淆泛型
-keepnames class * implements java.io.Serializable #不混淆Serializable

-keepclassmembers class **.R$* { #不混淆资源类
    public static <fields>;
}
-keepclasseswithmembernames class * {# 保持 native 方法不被混淆
    native <methods>;
 }

-keepclasseswithmembers class * {# 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
 }
-keepclasseswithmembers class * {# 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
 }
-keepclassmembers class * extends android.app.Activity {# 保持自定义控件类不被混淆
    public void *(android.view.View);
 }
-keepclassmembers enum * {# 保持枚举 enum 类不被混淆
    public static **[] values(); public static ** valueOf(java.lang.String);
 }
 -keep class * implements android.os.Parcelable {# 保持 Parcelable 不被混淆
     public static final android.os.Parcelable$Creator *;
 }
 
 # FastJson -dontwarn com.alibaba.fastjson.**
 -keep class com.alibaba.fastjson.** { *; }

 # OkHttp3 -dontwarn com.squareup.okhttp3.**
 -keep class com.squareup.okhttp3.** { *;}
 -dontwarn okio.**
 # Okio -dontwarn com.squareup.**
 -dontwarn okio.**
 -keep public class org.codehaus.* { *; }
 -keep public class java.nio.* { *; }


# 微信支付 -dontwarn com.tencent.mm.**
-dontwarn com.tencent.wxop.stat.**
-keep class com.tencent.mm.** {*;}
-keep class com.tencent.wxop.stat.**{*;}

# 支付宝钱包 -dontwarn com.alipay.**
-dontwarn HttpUtils.HttpFetcher
-dontwarn com.ta.utdid2.**
-dontwarn com.ut.device.**
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-keep class com.alipay.mobilesecuritysdk.*
-keep class com.ut.*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.** { *;}
#这句非常重要，主要是滤掉 com.demo.demo.bean包下的所有.class文件不进行混淆编译,com.demo.demo是你的包名
-keep class com.icloud.sdk.bean.** {*;}

#自定义模块
-keep class com.yz.action.**{*;}
-keep class com.icloud.sdk.listener.**{*;}
-keep class com.icloud.sdk.YZSDK{*;}
-keep class com.icloud.sdk.BaseYZSDK{*;}
-keep class com.icloud.sdk.adapter.base.Account{*;} 
 
