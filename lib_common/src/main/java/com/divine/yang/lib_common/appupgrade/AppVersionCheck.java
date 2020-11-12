package com.divine.yang.lib_common.appupgrade;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.util.List;

import androidx.appcompat.app.AlertDialog;

/**
 * author: Divine
 * <p>
 * date: 2018/12/20
 */
public class AppVersionCheck {
    private static AppVersionCheck mAppVersionCheck;
    private Context mContext;

    private AppVersionCheck(Context mContext) {
        this.mContext = mContext;
    }

    public static AppVersionCheck getInstance(Context mContext) {
        if (null == mAppVersionCheck) {
            mAppVersionCheck = new AppVersionCheck(mContext);
        }
        return mAppVersionCheck;
    }

    public boolean CheckAppVersion(String lastVersion) {
        boolean isUpgrade = false;
        PackageInfo packageInfo = null;
        try {
            packageInfo = mContext.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String currentVersion = packageInfo.versionName;

        String[] curVersions = currentVersion.split("\\.");
        String[] lastVersions = lastVersion.split("\\.");
        for (int i = 0; i < lastVersions.length; i++) {
            int curTemp = Integer.parseInt(curVersions[i]);
            int lastTemp = Integer.parseInt(lastVersions[i]);
            if (lastTemp > curTemp) {
                //                需要更新
                isUpgrade = true;
                break;
            }
        }
        return isUpgrade;
    }

    public  void forceUpdate(final Context context, final String appName, final String downUrl, final String updateinfo, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(context)
                .setTitle(appName + "更新！")
                .setMessage(updateinfo)
                .setPositiveButton("立即更新", listener)
                .setNegativeButton("暂不更新", listener)
                .setCancelable(false)
                .create()
                .show();
    }

    private void showDownloadSetting() {
        String packageName = "com.android.providers.downloads";
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + packageName));
        if (intentAvailable(intent)) {
            mContext.startActivity(intent);
        }
    }

    private boolean intentAvailable(Intent intent) {
        PackageManager packageManager = mContext.getPackageManager();
        List list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private boolean canDownloadState() {
        try {
            int state = mContext.getPackageManager().getApplicationEnabledSetting("com.android.providers.downloads");

            if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
