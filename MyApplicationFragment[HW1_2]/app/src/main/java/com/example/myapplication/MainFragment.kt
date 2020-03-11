package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.db.NoteRepository

class MainFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_notes, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.RecyclerView)
        recyclerView.setHasFixedSize(true)
        //recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = NoteAdapter(App.noteRepository.getNotes()) { id ->
            (requireActivity() as MainActivity).openNote(id)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    companion object {
        fun newInstance() = MainFragment()
    }
}