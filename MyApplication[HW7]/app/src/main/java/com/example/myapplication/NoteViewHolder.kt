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
        var context = view.context
        var intent = Intent(context, NoteActivity::class.java)
        intent.putExtra("note", note?.id)
        context.startActivity(intent)
    }

    fun bind(note: Note) {
        this.note = note
        view.textInfo.text = note.text
        view.textDate.text = SimpleDateFormat("yyyy - mm - dd").format(note.date)
        view.imageView.setImageDrawable(view.context.getDrawable(note.drowableRes))
    }
}