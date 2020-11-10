package com.divine.yang.lib_common;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Author: Divine
 * CreateDate: 2020/10/20
 * Describe:
 */
public class FileUtils {

    private static final String TAG = FileUtils.class.getSimpleName();

    /**
     * 创建根缓存目录
     *
     * @return
     */
    public static String createRootPath(Context context) {
        String cacheRootPath = "";
        if (isSdCardAvailable()) {
            // /sdcard/Android/data/<application package>/cache
            cacheRootPath = context.getExternalCacheDir().getPath();
        } else {
            // /data/data/<application package>/cache
            cacheRootPath = context.getCacheDir().getPath();
        }
        return cacheRootPath;
    }

    public static boolean isSdCardAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 递归创建文件夹
     *
     * @param dirPath
     * @return 创建失败返回""
     */
    public static String createDir(String dirPath) {
        try {
            File file = new File(dirPath);
            if (file.getParentFile().exists()) {
                Log.i(TAG, "----- 创建文件夹" + file.getAbsolutePath());
                file.mkdir();
                return file.getAbsolutePath();
            } else {
                createDir(file.getParentFile().getAbsolutePath());
                Log.i(TAG, "----- 创建文件夹" + file.getAbsolutePath());
                file.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dirPath;
    }

    /**
     * 递归创建文件夹
     *
     * @param file
     * @return 创建失败返回""
     */
    public static String createFile(File file) {
        try {
            if (file.getParentFile().exists()) {
                Log.i(TAG, "----- 创建文件" + file.getAbsolutePath());
                file.createNewFile();
                return file.getAbsolutePath();
            } else {
                createDir(file.getParentFile().getAbsolutePath());
                file.createNewFile();
                Log.i(TAG, "----- 创建文件" + file.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取该应用的根目录
     */
    public static String getAppPath() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return "";
        }
        File sdRoot = Environment.getExternalStorageDirectory();
        File file = new File(sdRoot.getAbsolutePath() + "/ImageListSrc/");
        if (!file.exists())
            file.mkdir();
        return file.getAbsolutePath();
    }

    /**
     * 根据文件绝对路径，获取文件名
     *
     * @param path
     * @return
     */
    public static String getFileName(String path) {
        String temp[] = path.replaceAll("\\\\", "/").split("/");
        String fileName = "";
        if (temp.length > 1) {
            fileName = temp[temp.length - 1];
        }
        return fileName;
    }

    /**
     * 生成一个2020111021548663(yyyyMMdd+8位随机数)的文件名
     * @return
     */
    public static String makeFileName() {
        String name;
        //生成8位随机数
        String SYMBOLS = "0123456789";
        Random RANDOM = new SecureRandom();
        char[] nonceChars = new char[8];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        //生成时间信息
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date(System.currentTimeMillis());
        //组合文件名
        name = sf.format(date) + new String(nonceChars);
        return name;
    }


}
