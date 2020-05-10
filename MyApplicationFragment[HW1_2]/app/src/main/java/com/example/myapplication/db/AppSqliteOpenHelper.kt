package com.example.myapplication.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class AppSqliteOpenHelper(val context: Context) : SQLiteOpenHelper(context, DB_NAME, null, VERSION), AutoCloseable {

    companion object {
        const val DB_NAME = "Notes.db"
        const val VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        NoteContract.createTable(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}