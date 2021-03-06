package com.example.myapplication.db

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

object NoteContract {

    const val TABLE_NAME = "notes"
        object NoteData {
            const val ID = BaseColumns._ID
            const val DATE = "date"
            const val TEXT = "text"
            const val RES_ID = "res_id"
        }

        private const val SQL_CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME " +
                    " (" +
                    "${NoteData.ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${NoteData.DATE} INTEGER NOT NULL," +
                    "${NoteData.TEXT} TEXT NOT NULL," +
                    "${NoteData.RES_ID} INTEGER NOT NULL" +
                    " );"

        private const val SQL_MIGRATION =
            "INSERT INTO $TABLE_NAME " +
                    " (" +
                    "${NoteData.ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${NoteData.DATE} INTEGER NOT NULL," +
                    "${NoteData.TEXT} TEXT NOT NULL," +
                    "${NoteData.RES_ID} INTEGER NOT NULL" +
                    " );"

        fun createTable(db: SQLiteDatabase?) {
            db?.execSQL(SQL_CREATE_TABLE)
        }
    }