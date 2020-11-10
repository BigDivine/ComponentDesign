package com.divine.yang.lib_common;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Author: Divine
 * CreateDate: 2020/11/10
 * Describe:
 */
public class SystemUtils {
    private static final String TAG = SystemUtils.class.getSimpleName();

    public static String getApplicationId(Context appContext) throws IllegalArgumentException {
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = appContext.getPackageManager().getApplicationInfo(appContext.getPackageName(), PackageManager.GET_META_DATA);
            if (applicationInfo == null) {
                throw new IllegalArgumentException(" get application info = null, has no meta data! ");
            }
            Log.d(TAG, appContext.getPackageName() + " " + applicationInfo.metaData.getString("APP_ID"));
            return applicationInfo.metaData.getString("APP_ID");
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalArgumentException(" get application info error! ", e);
        }
    }
}
