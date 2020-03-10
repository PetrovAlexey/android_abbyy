package com.example.myapplication

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.db.Note
import kotlinx.android.synthetic.main.element.view.*
import java.text.SimpleDateFormat

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
        /*intent.putExtra("note", note?.id)
        context.startActivity(intent)
        val notefr = NoteFragment().newInstance(note?.id)
        (getActivity(context) as MainActivity)//.showDetailFragment(note?.id)
        }

        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.demoDetailContainer, DetailFragment.newInstance(name), DetailFragment.TAG)
            .addToBackStack(null)
            .commit();*/
    }

    fun bind(note: Note) {
        this.note = note
        view.textInfo.text = note.text
        view.textDate.text = SimpleDateFormat("yyyy - mm - dd").format(note.date)
        view.imageView.setImageDrawable(view.context.getDrawable(note.drowableRes))
    }
}