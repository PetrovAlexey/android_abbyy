package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.db.Note
import kotlinx.android.synthetic.main.element.view.*


class NoteAdapter (private val notes: List<Note>, private val mCtx: Context, private val clickHandler: (Long) -> Unit): RecyclerView.Adapter<NoteViewHolder>() {

    override fun getItemCount(): Int = notes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.element, parent, false)
        return NoteViewHolder(layoutInflater, clickHandler)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)
        holder.itemView.textViewOptions.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {

                val popup = PopupMenu(mCtx, holder.itemView.textViewOptions)
                popup.inflate(R.menu.header_menu)
                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.share -> {
                            //TODO Implement sharing
                        }
                        R.id.delete -> {
                            val builder = AlertDialog.Builder(mCtx)
                            builder.setTitle("Delete article")
                            builder.setMessage("Are you want to delete this article?")

                            builder.setPositiveButton("Ok"){dialog, which ->
                                App.noteRepository.deleteNote(note.id)
                                Toast.makeText(mCtx,"Article deleted.", Toast.LENGTH_SHORT).show()
                                notifyItemRemoved(position)
                            }

                            builder.setNeutralButton("Cancel"){_,_ ->
                                Toast.makeText(mCtx,"Cancelled.",Toast.LENGTH_SHORT).show()
                            }

                            val dialog: AlertDialog = builder.create()
                            dialog.show()
                        }
                    }
                    false
                }

                popup.show()
            }
        })
    }

}