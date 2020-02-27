package com.example.myapplication

import android.media.Image
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_partical.view.*

class NoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partical)

        val noteId = intent.getSerializableExtra("note") as Long?
        title = getString(R.string.app_title)

        val imageView  = findViewById<View>(R.id.noteImageView)

        val textView  = findViewById<View>(R.id.noteTextInfo)


        val note = NoteRepository.getNoteWithId(noteId ?: 1)
        if (note != null) {
            textView.noteTextInfo.text = note.text
            imageView.noteImageView.setImageDrawable(getDrawable(note.drowableRes))
        }
    }
}