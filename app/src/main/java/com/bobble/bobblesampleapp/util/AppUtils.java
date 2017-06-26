package com.bobble.bobblesampleapp.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.bobble.bobblesampleapp.preferences.BobblePrefs;
import com.facebook.device.yearclass.YearClass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

/**
 * Created by varun on 26/06/17.
 */

public final class AppUtils {

    private AppUtils() {
        // This utility class is not publicly instantiable.
    }

    public static String getCurrentTime() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmssSSS")
                .format(new Date());
        return timeStamp;
    }

    public static void updateAppVersion(Context context) {
        int value = getAppVersion(context);
        BobblePrefs bobblePrefs = new BobblePrefs(context);
        bobblePrefs.appVersion().put(value);
        bobblePrefs.appOpenedCountVersionSpecific().put(0);
    }

    public static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public static String getDeviceId(Context context) {
        String androidDeviceId = getAndroidDeviceId(context);
        if (androidDeviceId == null)
            androidDeviceId = UUID.randomUUID().toString();
        return androidDeviceId;

    }

    private static String getAndroidDeviceId(Context context) {
        final String INVALID_ANDROID_ID = "9774d56d682e549c";
        final String androidId = android.provider.Settings.Secure.getString(
                context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
        if (androidId == null
                || androidId.toLowerCase().equals(INVALID_ANDROID_ID)) {
            return null;
        }
        return androidId;
    }

    public static String getManufacturer(Context context) {
        String mfg = "unknown";
        try {
            final Class<?> buildClass = Build.class;
            mfg = (String) buildClass.getField("MANUFACTURER").get(null); //$NON-NLS-1$
            mfg = mfg + " " + buildClass.getField("MODEL").get(null);
            mfg = mfg + " " + buildClass.getField("PRODUCT").get(null);
        } catch (final Exception ignore) {

        }

        HashMap<String, String> params = new HashMap<String, String>();
        try {
            params.put("ModelInfo", mfg);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            params.put("RootStatus", String.valueOf(RootUtil.isDeviceRooted()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            params.put("YearClass", String.valueOf(YearClass.get(context)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return params.toString();
    }

    static String timeZone() {
        TimeZone tz = TimeZone.getDefault();
        return tz.getID();
    }

    public static void setMobileDpiCategory(Context context, DisplayMetrics displaymetrics) {
        int density = displaymetrics.densityDpi;
        BobblePrefs pref = new BobblePrefs(context);
        if (density == DisplayMetrics.DENSITY_LOW) {
            pref.mobileDpiCategory().put(DisplayMetrics.DENSITY_HIGH);
        } else if (density == DisplayMetrics.DENSITY_MEDIUM) {
            pref.mobileDpiCategory().put(DisplayMetrics.DENSITY_HIGH);
        } else if (density == DisplayMetrics.DENSITY_HIGH) {
            pref.mobileDpiCategory().put(DisplayMetrics.DENSITY_HIGH);
        } else if (density == DisplayMetrics.DENSITY_XHIGH) {
            pref.mobileDpiCategory().put(DisplayMetrics.DENSITY_XHIGH);
        } else if (density == DisplayMetrics.DENSITY_XXHIGH) {
            pref.mobileDpiCategory().put(DisplayMetrics.DENSITY_XHIGH);
        } else {
            // in case something wrong happens
            pref.mobileDpiCategory().put(DisplayMetrics.DENSITY_XHIGH);
        }

        if (pref.mobileDpiCategory().get() == DisplayMetrics.DENSITY_XHIGH) {
            pref.mobileDpiString().put("xhdpi");
        } else {
            pref.mobileDpiString().put("hdpi");
        }
    }

    public static boolean isAppInstalled(Context context, String uri) {
        PackageManager pm = context.getPackageManager();
        boolean appInstalled = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            appInstalled = true;
        } catch (PackageManager.NameNotFoundException e) {
            appInstalled = false;
        }
        return appInstalled;
    }

    public static String getUserIdentity(Context context) {
        AccountManager manager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        Account[] list = manager.getAccounts();
        String emailId = null;
        for (Account account : list) {
            if (account.type.equalsIgnoreCase("com.google")) {
                emailId = account.name;
                break;
            }
        }
        if (emailId != null) {
            Log.d(Utils.TAG, "emailIdOfUser : " + emailId);
            return emailId;
        }
        return "";
    }

    public static String getCurrentActivityNameOfBobbleApp(Context context) {
        // For SDK > 20 Android returns default or empty value for Activity which is not owned by us
        ActivityManager manager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> task = manager.getRunningTasks(1);
        if (task.size() > 0) {
            ComponentName componentInfo = task.get(0).topActivity;
            return componentInfo.getClassName();
        }
        return null;
    }

    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        try {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
                List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
                for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                    if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        for (String activeProcess : processInfo.pkgList) {
                            if (activeProcess.equals(context.getPackageName())) {
                                isInBackground = false;
                            }
                        }
                    }
                }
            } else {
                List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
                ComponentName componentInfo = taskInfo.get(0).topActivity;
                if (componentInfo.getPackageName().equals(context.getPackageName())) {
                    isInBackground = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
        return isInBackground;
    }

    static String getAppName(Context context, String packageName) {
        try {
            ApplicationInfo app = context.getPackageManager().getApplicationInfo(packageName, 0);
            return context.getPackageManager().getApplicationLabel(app).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /*
     * Few devices are not able to play video because there are some vendor specific bug , that's why putting this devices in exception
     */
    public static boolean canPlayVideo(Context context) {
        String deviceInfo = getManufacturer(context);
        if (deviceInfo.contains("GT-S7562")) {
            return false;
        } else if (deviceInfo.contains("A117")) {
            return false;
        } else if (deviceInfo.contains("A210")) {
            return false;
        } else if (deviceInfo.contains("A250")) {
            return false;
        } else if (deviceInfo.contains("A240")) {
            return false;
        } else if (deviceInfo.contains("P650")) {
            return false;
        } else if (deviceInfo.contains("Y51L")) {
            return false;
        } else if (deviceInfo.contains("GT-I8262")) {
            return false;
        } else if (deviceInfo.contains("GT-I9300")) {
            return false;
        } else if (deviceInfo.contains("GT-I9300")) {
            return false;
        } else if (deviceInfo.toLowerCase().contains("samsung")) {
            return false;
        }
        return true;
    }

    public static void toggleKeyBoard(Context context) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideKeyBoard(Context context, View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
