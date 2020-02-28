package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_partical.view.*

class NoteFragment : Fragment() {

    private val NOTE_ID = "noteId"

    fun newInstance(id: Long): NoteFragment {
        val fragment = NoteFragment()
        val args = Bundle()
        args.putLong(NOTE_ID, id)
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_partical, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val note = NoteRepository.getNoteWithId(arguments?.getLong("noteId", 1) ?: 1)
        if (note != null) {
            view.noteTextInfo.text = note.text
            view.noteImageView.setImageDrawable(activity?.getDrawable(note.drowableRes))
        }
    }

}