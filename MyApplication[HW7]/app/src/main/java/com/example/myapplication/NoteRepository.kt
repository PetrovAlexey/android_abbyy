package com.example.myapplication

import java.util.*

object NoteRepository {
    private val NOTES: MutableMap<Long, Note> = hashMapOf(
        1L to Note(1, Date(1576174880000), "Lorem ipsum", R.drawable.image),
        2L to Note(2, Date(1576174880000), "Second note", R.drawable.image),
        3L to Note(3, Date(1576174880000), "Hello kitty", R.drawable.image),
        4L to Note(4, Date(1576174880000), "I love Android", R.drawable.image),
        5L to Note(5, Date(1576174880000), "Wow", R.drawable.image)
    )

    fun listNotes(): List<Note> = NOTES.values.toList()

    fun getNoteWithId(id: Long): Note? = NOTES[id]
}