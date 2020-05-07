package com.example.myapplication

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.db.Note
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.element.view.*
import java.text.SimpleDateFormat
import java.util.*

class NoteViewHolder (itemView: View, private val clickHandler: (Long) -> Unit): RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private var view = itemView
    private var note: Note? = null

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (note != null) {
            clickHandler(note!!.id)
        }
    }


    fun bind(note: Note) {
        this.note = note
        view.textInfo.text = note.text
        view.textDate.text = SimpleDateFormat("yyyy - mm - dd").format(note.date)
        Picasso
            .get()
            .load("file://"+note.drowableRes)
            .error(R.drawable.image)
            .fit()
            .centerInside()
            .into(view.imageView)
        //view.imageView.setImageDrawable(view.context.getDrawable(note.drowableRes.))
    }
}