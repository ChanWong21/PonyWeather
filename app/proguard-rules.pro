# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Program Files\Android\sdk/tools/proguard/proguard-android.txt
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

# proguard.cfg
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

-keepclasseswithmembernames class * {
  native <methods>;
}

-keepclasseswithmembers class * {
  public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
  public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
  public void *(android.view.View);
}

-keepclassmembers enum * {
  public static **[] values();
  public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

# custom
-dontshrink
-dontoptimize

-keepattributes *Annotation*,Exceptions,InnerClasses,Signature,SourceFile,LineNumberTable

-keep public class * extends android.app.Fragment
-keep public class * extends android.support.**

# android-support
-dontwarn android.support.**
-keep class android.support.** { *; }

# app
-keep class me.wcy.weather.model.** { *; }
-keep class me.wcy.music.api.KeyStore { *; }

# butterknife
-dontwarn butterknife.internal.**
-keep class **$$ViewInjector { *; }
-keepnames class * { @butterknife.InjectView *; }

# amap
-keep class com.amap.api.location.** { *; }
-keep class com.amap.api.fence.** { *; }
-keep class com.autonavi.aps.amapapi.model.** { *; }

# rxjava
-dontwarn rx.**
-keep class rx.** { *; }

# retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }

# okio
-dontwarn okio.**
-keep class okio.** { *; }

# bmob
-dontwarn cn.bmob.v3.**
-keep class cn.bmob.v3.** { *; }
