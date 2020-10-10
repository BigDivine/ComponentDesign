package com.divine.yang.sqlitecomponent;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;


/**
 * Author: Divine
 * CreateDate: 2020/7/28
 * Describe: 数据库操作类
 */
public class SQLiteUtil {
    private static SQLiteUtil sqLiteUtil;

    private SQLiteHelper mSQLiteHelper;

    /**
     * 构造函数，对应SQLiteHelper的构造函数
     * SQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version)
     *
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    private SQLiteUtil(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, String[] createTableSql) {
        mSQLiteHelper = new SQLiteHelper(context, name, factory, version, createTableSql);

    }


    public static SQLiteUtil init(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, String[] createTableSql) {
        if (sqLiteUtil == null) {
            synchronized (SQLiteUtil.class) {
                if (sqLiteUtil == null) {
                    sqLiteUtil = new SQLiteUtil(context, name, factory, version, createTableSql);
                }
            }
        }
        return sqLiteUtil;
    }

    /**
     * 构造函数，对应SQLiteHelper的构造函数
     * SQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler)
     *
     * @param context
     * @param name
     * @param factory
     * @param version
     * @param errorHandler
     */
    private SQLiteUtil(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler, String[] createTableSql) {
        mSQLiteHelper = new SQLiteHelper(context, name, factory, version, errorHandler, createTableSql);
    }

    public static SQLiteUtil init(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler, String[] createTableSql) {
        if (sqLiteUtil == null) {
            synchronized (sqLiteUtil) {
                if (sqLiteUtil == null) {
                    sqLiteUtil = new SQLiteUtil(context, name, factory, version, errorHandler, createTableSql);
                }
            }
        }
        return sqLiteUtil;
    }

    /**
     * 构造函数，对应SQLiteHelper的构造函数
     * SQLiteHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams)
     * 使用有版本限制，只有API.version>=28，该函数生效
     *
     * @param context
     * @param name
     * @param version
     * @param openParams
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    private SQLiteUtil(@Nullable Context context, @Nullable String name, int version, SQLiteDatabase.OpenParams openParams, String[] createTableSql) {
        mSQLiteHelper = new SQLiteHelper(context, name, version, openParams, createTableSql);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public static SQLiteUtil init(@Nullable Context context, @Nullable String name, int version, SQLiteDatabase.OpenParams openParams, String[] createTableSql) {
        if (sqLiteUtil == null) {
            synchronized (sqLiteUtil) {
                if (sqLiteUtil == null) {
                    sqLiteUtil = new SQLiteUtil(context, name, version, openParams, createTableSql);
                }
            }
        }
        return sqLiteUtil;
    }

    public SQLiteHelper getSQLiteHelper() {
        return mSQLiteHelper;
    }
}
