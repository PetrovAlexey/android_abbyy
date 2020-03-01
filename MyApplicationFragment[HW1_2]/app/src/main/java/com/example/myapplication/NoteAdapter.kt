package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter (private val notes: List<Note>, private val clickHandler: (Long) -> Unit): RecyclerView.Adapter<NoteViewHolder>() {

    override fun getItemCount(): Int = notes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.activity_main, parent, false)
        return NoteViewHolder(layoutInflater, clickHandler)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)
    }
}