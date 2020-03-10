package com.example.myapplication.db

import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import com.example.myapplication.R
import java.lang.Long.getLong
import java.util.*
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.annotation.NonNull




class NoteRepository(private val helper: SQLiteOpenHelper) {

    fun createNote(note: Note) {
        helper.use { helper ->
            val db = helper.writableDatabase

            val contentValues = ContentValues()
            contentValues.put(NoteContract.NoteData.ID, note.id)
            contentValues.put(NoteContract.NoteData.DATE, note.date.time)
            contentValues.put(NoteContract.NoteData.TEXT, note.text)
            contentValues.put(NoteContract.NoteData.RES_ID, note.drowableRes)
            db.insert(NoteContract.TABLE_NAME, null, contentValues)
            db.close()
        }
    }

    fun getNotes(): List<Note> {
        val notes = mutableListOf<Note>()
        var cursor: Cursor? = null
        try {
            val db = helper.readableDatabase
            cursor = db.query(
                NoteContract.TABLE_NAME,
                arrayOf(NoteContract.NoteData.DATE, NoteContract.NoteData.TEXT, NoteContract.NoteData.RES_ID),
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
                val imageId = cursor.getInt(cursor.getColumnIndex(NoteContract.NoteData.RES_ID))
                notes.add(Note(id, date, text, imageId))
            }
        } finally {
            cursor?.close()
            helper.close()
        }
        return notes
    }

    fun getNote(id: Long): Note? {
        var cursor: Cursor? = null
        try{
            val db = helper.readableDatabase
            cursor = db.query(
                NoteContract.TABLE_NAME,
                arrayOf(NoteContract.NoteData.DATE, NoteContract.NoteData.TEXT, NoteContract.NoteData.RES_ID),
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
                val imageId = cursor.getInt(cursor.getColumnIndex(NoteContract.NoteData.RES_ID))
                return Note(_id, date, text, imageId)
            }
        } finally {
            cursor?.close()
            helper.close()
        }
        return null
    }

    private val NOTES: MutableMap<Long, Note> = hashMapOf(
        1L to Note(
            1,
            Date(1576174880000),
            "First note",
            R.drawable.image
        ),
        2L to Note(
            2,
            Date(1576174880000),
            "Second note",
            R.drawable.image
        ),
        3L to Note(
            3,
            Date(1576174880000),
            "Third note",
            R.drawable.image
        ),
        4L to Note(
            4,
            Date(1576174880000),
            "Fourth note",
            R.drawable.image
        ),
        5L to Note(
            5,
            Date(1576174880000),
            "Fifth note",
            R.drawable.image
        ),
        6L to Note(
            5,
            Date(1576174880000),
            "Fifth note",
            R.drawable.image
        ),
        7L to Note(
            5,
            Date(1576174880000),
            "Fifth note",
            R.drawable.image
        ),
        8L to Note(
            5,
            Date(1576174880000),
            "Fifth note",
            R.drawable.image
        ),
        9L to Note(
            5,
            Date(1576174880000),
            "Fifth note",
            R.drawable.image
        ),
        10L to Note(
            5,
            Date(1576174880000),
            "Fifth note",
            R.drawable.image
        )
    )

    fun listNotes(): List<Note> = NOTES.values.toList()

    fun getNoteWithId(id: Long): Note? = NOTES[id]
}