package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.db.NoteRepository
import kotlinx.android.synthetic.main.fragment_partial.view.*
import kotlinx.coroutines.*
import java.lang.Exception
import java.lang.IllegalArgumentException
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import java.util.*

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

        val result = GlobalScope.async (Dispatchers.IO) {
            //delay(1000)
            App.noteRepository.getNote(arguments?.getLong(NOTE_ID, 1) ?: throw IllegalArgumentException())
        }
        GlobalScope.launch(Dispatchers.Main) {
            val note = result.await()
            if (note != null) {
                view.noteTextInfo.text = note.text
                Picasso
                    .get()
                    .load("file://"+note.drowableRes)
                    .error(R.drawable.image)
                    .fit()
                    .centerInside()
                    .into(view.noteImageView);

                //view.noteImageView.setImageDrawable(activity?.getDrawable(note.drowableRes))
            }
        }

    }

}