package com.example.myapplication.db

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import java.util.*

class NoteRepository(private val helper: SQLiteOpenHelper) {

    fun createNote(date: Date, text: String, drowableRes: String) :Long {
        helper.use { helper ->
            val db = helper.writableDatabase
            val contentValues = ContentValues()
            contentValues.put(NoteContract.NoteData.DATE, date.time)
            contentValues.put(NoteContract.NoteData.TEXT, text)
            contentValues.put(NoteContract.NoteData.RES_ID, drowableRes)
            val t = db.insert(NoteContract.TABLE_NAME, null, contentValues)
            db.close()
            return t
        }
    }

    fun deleteNote(id: Long) :Boolean {
        helper.use { helper ->
            val db = helper.writableDatabase
            return db.delete(NoteContract.TABLE_NAME, NoteContract.NoteData.ID + "=" + id, null) > 0
        }
    }

    fun getNotes(): List<Note> {
            val notes = mutableListOf<Note>()
            var cursor: Cursor? = null
            try {
                val db = helper.readableDatabase
                cursor = db.query(
                    NoteContract.TABLE_NAME,
                    arrayOf(NoteContract.NoteData.ID, NoteContract.NoteData.DATE, NoteContract.NoteData.TEXT, NoteContract.NoteData.RES_ID),
                    null,
                    null,
                    null,
                    null,
                    null
                    )

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(cursor.getColumnIndex(NoteContract.NoteData.ID))
                    val date = Date(cursor.getLong(cursor.getColumnIndex(NoteContract.NoteData.DATE)))
                    val text = cursor.getString(cursor.getColumnIndex(NoteContract.NoteData.TEXT))
                    val imageId = cursor.getString(cursor.getColumnIndex(NoteContract.NoteData.RES_ID))
                    notes.add(Note(id, date, text, imageId))
                }
            } finally {
                cursor?.close()
                helper.close()
            }
            return notes
    }

    fun editNote(date: Date, text: String, drowableRes: String, id: Long): Int {
        helper.use { helper ->
            val db = helper.writableDatabase
            val contentValues = ContentValues()
            contentValues.put(NoteContract.NoteData.DATE, date.time)
            contentValues.put(NoteContract.NoteData.TEXT, text)
            contentValues.put(NoteContract.NoteData.RES_ID, drowableRes)
            val t = db.update(NoteContract.TABLE_NAME, contentValues, "_id="+id, null)
            db.close()
            return t
        }
    }

    fun getNote(id: Long): Note? {
        var cursor: Cursor? = null
        try{
            val db = helper.readableDatabase
            cursor = db.query(
                NoteContract.TABLE_NAME,
                arrayOf(NoteContract.NoteData.ID, NoteContract.NoteData.DATE, NoteContract.NoteData.TEXT, NoteContract.NoteData.RES_ID),
                "${NoteContract.NoteData.ID} = ?",
                arrayOf(id.toString()),
                null,
                null,
                null
            )
            while (cursor.moveToFirst()) {
                val _id = cursor.getLong(cursor.getColumnIndex(NoteContract.NoteData.ID))
                val date = Date(cursor.getLong(cursor.getColumnIndex(NoteContract.NoteData.DATE)))
                val text = cursor.getString(cursor.getColumnIndex(NoteContract.NoteData.TEXT))
                val imageId = cursor.getString(cursor.getColumnIndex(NoteContract.NoteData.RES_ID))
                return Note(_id, date, text, imageId)
            }
        } finally {
            cursor?.close()
            helper.close()
        }
        return null
    }
}