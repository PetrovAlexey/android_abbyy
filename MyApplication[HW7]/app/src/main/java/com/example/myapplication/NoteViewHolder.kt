package com.example.myapplication

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_partical.view.*
import java.text.SimpleDateFormat

class NoteViewHolder (itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private var view = itemView
    private var note: Note? = null

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        Log.d("RecycleView", "CLICK!")
        /*var context = itemView.context
        var intent = Intent(context, NoteActivity::class.java)
        intent.putExtra("note", note?.id)
        context.startActivity(intent)*/
    }

    fun bind(note: Note) {
        this.note = note
        view.noteTextView.text = note.text
        //view.textDate.text = "Text"//SimpleDateFormat("yyyy - mm - dd").format(note.date)
        view.noteImageView.setImageDrawable(view.context.getDrawable(note.drowableRes))
    }
}