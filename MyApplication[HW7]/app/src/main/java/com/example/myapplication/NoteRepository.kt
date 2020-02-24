package com.example.myapplication

import java.util.*

object NoteRepository {
    private val NOTES: MutableMap<Long, Note> = hashMapOf(
        1L to Note(1, Date(1576174880000), "First note", R.drawable.image),
        2L to Note(2, Date(1576174880000), "Second note", R.drawable.image),
        3L to Note(3, Date(1576174880000), "Third note", R.drawable.image),
        4L to Note(4, Date(1576174880000), "Fourth note", R.drawable.image),
        5L to Note(5, Date(1576174880000), "Fifth note", R.drawable.image)
    )

    fun listNotes(): List<Note> = NOTES.values.toList()

    fun getNoteWithId(id: Long): Note? = NOTES[id]
}