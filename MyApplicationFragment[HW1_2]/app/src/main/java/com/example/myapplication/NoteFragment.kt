package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.db.NoteRepository
import kotlinx.android.synthetic.main.fragment_partial.view.*
import java.lang.Exception
import java.lang.IllegalArgumentException

class NoteFragment : Fragment() {


    companion object {
        private val NOTE_ID = "noteId"

        fun newInstance(id: Long): NoteFragment {
            val fragment = NoteFragment()
            val args = Bundle()
            if (id != null) {
                args.putLong(NOTE_ID, id)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_partial, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val note = App.noteRepository.getNoteWithId(arguments?.getLong("noteId", 1) ?: throw IllegalArgumentException())
        if (note != null) {
            view.noteTextInfo.text = note.text
            view.noteImageView.setImageDrawable(activity?.getDrawable(note.drowableRes))
        }
    }

}