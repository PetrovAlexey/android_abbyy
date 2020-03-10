package com.example.myapplication.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.annotation.NonNull
import java.util.concurrent.locks.ReentrantLock


class DatabaseHolder(context: Context) {

    public val appSqliteOpenHelper: AppSqliteOpenHelper

    private var sqLiteDatabase: SQLiteDatabase? = null

    private var databaseOpenCloseBalance: Int = 0

    private val reentrantLock = ReentrantLock()

    init {
        appSqliteOpenHelper = AppSqliteOpenHelper(context)
    }

    fun open(): SQLiteDatabase? {
        try {
            reentrantLock.lock()
            if (databaseOpenCloseBalance == 0) {
                sqLiteDatabase = appSqliteOpenHelper.writableDatabase
            }

            ++databaseOpenCloseBalance

            return sqLiteDatabase
        } finally {
            reentrantLock.unlock()
        }
    }

    fun close() {
        try {
            reentrantLock.lock()
            --databaseOpenCloseBalance

            if (databaseOpenCloseBalance == 0) {
                sqLiteDatabase!!.close()
                sqLiteDatabase = null
            }
        } finally {
            reentrantLock.unlock()
        }
    }
}