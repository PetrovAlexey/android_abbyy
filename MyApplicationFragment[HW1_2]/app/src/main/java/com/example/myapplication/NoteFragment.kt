package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.text.method.KeyListener
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.db.Note
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_partial.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch



class NoteFragment : Fragment() {
    companion object {
        private const val NOTE_ID = "noteId"

        fun newInstance(id: Long): NoteFragment {
            val fragment = NoteFragment()
            val args = Bundle()
            args.putLong(NOTE_ID, id)
            fragment.arguments = args
            return fragment
        }
    }

    lateinit var note: Note

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_partial, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editText = view.noteTextInfo as EditText
        val editButton = view.editButton as Button

        val result = GlobalScope.async (Dispatchers.IO) {
            App.noteRepository.getNote(arguments?.getLong(NOTE_ID, 1) ?: throw IllegalArgumentException())
        }
        GlobalScope.launch(Dispatchers.Main) {
            note = result.await()!!
            editText.setText(note.text)
            editText.tag = editText.keyListener;
            editText.keyListener = null;
            editText.movementMethod = ScrollingMovementMethod()
            Picasso
                .get()
                .load("file://"+note.drowableRes)
                .error(R.drawable.image)
                .fit()
                .centerInside()
                .into(view.noteImageView);
        }

        editButton.setOnClickListener {
            if (editButton.text == "Save") {
                val update = GlobalScope.async (Dispatchers.IO) {
                    App.noteRepository.editNote(
                        note.date,
                        editText.text.toString(),
                        note.drowableRes,
                        note.id)
                }
                GlobalScope.launch(Dispatchers.Main) {
                    if (update.await() > 0) {
                        // Hide keyboard
                        val imm =  activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(view.windowToken, 0)

                        editButton.text = "Edit"
                        editText.tag = editText.keyListener;
                        editText.keyListener = null;
                    } else {
                        Toast.makeText(activity,"Error.", Toast.LENGTH_SHORT).show()
                    }

                }
            } else {
                editText.requestFocus()

                // toggle keyboard
                val imm =  activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)

                editButton.text = "Save"
                editText.keyListener = editText.tag as KeyListener
            }
        }

    }

}