package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        this.title = getString(R.string.app_title)

        val recyclerView = findViewById<RecyclerView>(R.id.RecyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = NoteAdapter(NoteRepository.listNotes())

        //val adapter = NoteAdapter(NoteRepository.listNotes())

        //recyclerView.adapter = adapter
       // Log.e("1", NoteRepository.listNotes().toString())
        /*
        recyclerView.adapter = NoteAdapter(NoteRepository.listNotes())*/

        /*val cardView = findViewById<View>(R.id.note_list_item)
        cardView.setOnClickListener {
            val intent = Intent(Partical@this, ParticalActivity::class.java)
            startActivity(intent)
        }*/
    }
}
