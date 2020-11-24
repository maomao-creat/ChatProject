# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\ninos\AndroidSDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-optimizationpasses 5


# 混淆时不会产生形形色色的类名
#-dontusemixedcaseclassnames


# 指定不去忽略非公共的库类
-dontskipnonpubliclibraryclasses


# 不预校验
# -dontpreverify


# 预校验
-dontoptimize




# 这1句是屏蔽警告
-ignorewarnings
-verbose


# 优化
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*


# 这1句是导入第三方的类库，防止混淆时候读取包内容出错
#-libraryjars libs/youjar.jar


# 去掉警告
-dontwarn
-dontskipnonpubliclibraryclassmembers


# 不进行混淆保持原样
#-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
#-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService
-keep class com.hantek.idso1070.** { *; }






# 过滤R文件的混淆：
-keep class **.R$* {*;}


# 过滤第三方包的混淆：其中packagename为第三方包的包名
# -keep class packagename.** {*;}
#-keep class com.hisilicon.android.hibdinfo.** {*;}
#-keep class com.huawei.iptv.stb.dlna.mymedia.dto.** {*;}
#-keep class com.huawei.iptv.stb.dlna.mymedia.facade.** {*;}
#-keep class com.huawei.mymediaprifacade.** {*;}
#-keep class com.hisilicon.android.mediaplayer.** {*;}
#-keep class com.nostra13.universalimageloader.** {*;}
#-keep class com.huawei.android.airsharing.** {*;}


# 所有方法不进行混淆
-keep public abstract interface com.huawei.android.airsharing.listener{
public protected <methods>;
}


-keep public abstract interface com.huawei.android.airsharing.api{
public protected <methods>;
}


# 对该方法不进行混淆
# -keep public class com.asqw.android{
# public void Start(java.lang.String);
# }


# 保护指定的类和类的成员的名称，如果所有指定的类成员出席(在压缩步骤之后)
#-keepclasseswithmembernames class * {
#    native <methods>;
#}


# 保护指定的类和类的成员，但条件是所有指定的类和类成员是要存在
#-keepclasseswithmembernames class * {
#    public <init>(android.content.Context, android.util.AttributeSet);
#}


-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}


# 保护指定类的成员，如果此类受到保护他们会保护的更好
-keepclassmembers class * extends android.app.Activity {
public void *(android.view.View);
}




-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}


# 保护指定的类文件和类的成员
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}


#-libraryjars libs/android-support-v4.jar
#-dontwarn android.support.v4.**{*;}


# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}

-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}
-keep class com.mob.**{*;}
-keep class m.framework.**{*;}
-dontwarn cn.sharesdk.**
-dontwarn com.sina.**
-dontwarn com.mob.**
-dontwarn **.R$*


#不混淆第三方包中的指定内容
#-keep class android-support-v4.**{*;}
#-keep public class * extends android.support.v4.**
#-keep class android.view.**{*;}