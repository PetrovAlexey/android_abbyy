package com.example.myapplication.db

import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import com.example.myapplication.R
import java.util.*
import android.content.ContentValues

class NoteRepository(private val helper: SQLiteOpenHelper) {

    fun createNote(note: Note) :Long{
        // why not hepler. use ?
            val db = helper.writableDatabase
            val contentValues = ContentValues()
            //contentValues.put(NoteContract.NoteData.ID, note.id)
            contentValues.put(NoteContract.NoteData.DATE, note.date.time)
            contentValues.put(NoteContract.NoteData.TEXT, note.text)
            contentValues.put(NoteContract.NoteData.RES_ID, note.drowableRes)
            //db.insert(NoteContract.TABLE_NAME, null, contentValues)
            val t = db.insert(NoteContract.TABLE_NAME, null, contentValues)
            db.close()
        return t
    }

    fun deleteNote(id: Long) :Boolean {
        var result = false
        try{
            val db = helper.writableDatabase
            result  = db.delete(NoteContract.TABLE_NAME, NoteContract.NoteData.ID + "=" + id, null) > 0
        } finally {
            helper.close()
        }
        return result
    }

    fun getNotes(): List<Note> {
            val notes = mutableListOf<Note>()
            var cursor: Cursor? = null
            try {
                val db = helper.readableDatabase
                //cursorAsync = SimpleCursorAdapter(this, , Cursor c, String[] from, int[] to, int flags)
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

//    private val NOTES: MutableMap<Long, Note> = hashMapOf(
//        1L to Note(
//            UUID.fromString("1"),
//            Date(1576174880000),
//            "First note",
//            R.drawable.image.toString()
//        ),
//        2L to Note(
//            "2",
//            Date(1576174880000),
//            "Second note",
//            R.drawable.image.toString()
//        ),
//        3L to Note(
//            "3",
//            Date(1576174880000),
//            "Third note",
//            R.drawable.image.toString()
//        ),
//        4L to Note(
//            "4",
//            Date(1576174880000),
//            "Fourth note",
//            R.drawable.image.toString()
//        ),
//        5L to Note(
//            "5",
//            Date(1576174880000),
//            "Fifth note",
//            R.drawable.image.toString()
//        )
//    )
//
//    fun listNotes(): List<Note> = NOTES.values.toList()
//
//    fun getNoteWithId(id: Long): Note? = NOTES[id]
}