package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.db.Note
import kotlinx.android.synthetic.main.element.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException


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
                            val sendIntent: Intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, note.text)
                                type = "text/plain"
                            }

                            startActivity(mCtx, sendIntent, null)
                        }
                        R.id.delete -> {
                            val builder = AlertDialog.Builder(mCtx)
                            builder.setTitle("Delete article")
                            builder.setMessage("Are you want to delete this article?")

                            builder.setPositiveButton("Ok"){dialog, which ->
                                val result = GlobalScope.async (Dispatchers.IO) {
                                    App.noteRepository.deleteNote(note.id)
                                }
                                GlobalScope.launch(Dispatchers.Main) {
                                    if (result.await()) {
                                        Toast.makeText(mCtx,"Article deleted.", Toast.LENGTH_SHORT).show()
                                        notifyItemRemoved(position)
                                    } else {
                                        Toast.makeText(mCtx,"Error.", Toast.LENGTH_SHORT).show()
                                    }
                                }
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