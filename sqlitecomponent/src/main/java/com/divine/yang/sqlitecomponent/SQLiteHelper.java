package com.divine.yang.sqlitecomponent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;


/**
 * Author: Divine
 * CreateDate: 2020/7/28
 * Describe:
 * 查询和更新指令构成了 SQL 的 DML 部分：
 * <p>
 * SELECT - 从数据库表中获取数据
 * UPDATE - 更新数据库表中的数据
 * DELETE - 从数据库表中删除数据
 * INSERT INTO - 向数据库表中插入数据
 * <p>
 * SQL 中最重要的 DDL 语句:
 * <p>
 * CREATE DATABASE - 创建新数据库
 * ALTER DATABASE - 修改数据库
 * CREATE TABLE - 创建新表
 * ALTER TABLE - 变更（改变）数据库表
 * DROP TABLE - 删除表
 * CREATE INDEX - 创建索引（搜索键）
 * DROP INDEX - 删除索引
 * 调用getReadableDatabase 方法返回的并不总是只读数据库对象，
 * 一般来说该方法和getWriteableDatabase 方法的返回情况相同，
 * 只有在数据库仅开放只读权限或磁盘已满时才会返回一个只读的数据库对象。
 * 而getWriteableDatabase()方法打开的数据库，一旦数据库磁盘空间满了，
 * 就只能读而不能写，如果再写则报错。因此建议使用getReadableDatabase()方法来获打开数据库。
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    private String[] createTableSql;

    public SQLiteHelper(@NonNull Context context, @NonNull String name, @NonNull SQLiteDatabase.CursorFactory factory, @NonNull int version, String[] createTableSql) {
        super(context, name, factory, version);
        this.createTableSql = createTableSql;
    }

    public SQLiteHelper(@NonNull Context context, @NonNull String name, @NonNull SQLiteDatabase.CursorFactory factory, @NonNull int version, DatabaseErrorHandler errorHandler, String[] createTableSql) {
        super(context, name, factory, version, errorHandler);
        this.createTableSql = createTableSql;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public SQLiteHelper(@NonNull Context context, @NonNull String name, @NonNull int version, @NonNull SQLiteDatabase.OpenParams openParams, String[] createTableSql) {
        super(context, name, version, openParams);
        this.createTableSql = createTableSql;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.e("yzl", "sqliteHelper onCreate:" + createTableSql);
        try {
            if (createTableSql != null && createTableSql.length > 0) {
                for (String sql : createTableSql) {
                    sqLiteDatabase.execSQL(sql);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("yzl", "sqliteHelper onUpgrade");

        if (oldVersion < newVersion) {
            //            db.execSQL("drop table if exists ");
        }
    }

    /**
     * @param insertSql      insert into 表名(字段) values(值);
     * @param tableName      表名
     * @param nullColumnHack
     * @param values         contentValues
     */
    public void insertRow(String insertSql, String tableName, String nullColumnHack, ContentValues values) {
        SQLiteDatabase db = getReadableDatabase();

        if (!TextUtils.isEmpty(insertSql)) {
            db.execSQL(insertSql);
        } else {
            db.insert(tableName, nullColumnHack, values);
        }
    }

    public void deleteRow(String insertSql, String tableName, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = getReadableDatabase();

        if (!TextUtils.isEmpty(insertSql)) {
            db.execSQL(insertSql);
        } else {
            db.delete(tableName, whereClause, whereArgs);
        }
    }

    public void updateRow(String updateSql, String tableName, ContentValues values, String whereClause, String[] whereArgs) {
        //    update 表名 set 字段1=值1, 字段2=值2 where 条件;
        SQLiteDatabase db = getReadableDatabase();
        if (!TextUtils.isEmpty(updateSql)) {
            db.execSQL(updateSql);
        } else {
            db.update(tableName, values, whereClause, whereArgs);
        }
    }

    public Cursor queryRows(String querySql, String tableName, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        SQLiteDatabase db = getReadableDatabase();
        if (!TextUtils.isEmpty(querySql)) {
            return db.rawQuery(querySql, selectionArgs);
        } else {
            return db.query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        }
    }
}
