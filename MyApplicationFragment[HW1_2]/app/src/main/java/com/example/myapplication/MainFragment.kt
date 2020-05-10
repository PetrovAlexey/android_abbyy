package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


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
        val result = GlobalScope.async (Dispatchers.IO)
        {
            App.noteRepository.getNotes()
        }
        GlobalScope.launch (Dispatchers.Main) {
            recyclerView.adapter = NoteAdapter( result.await(), context!! ) { id ->
            (requireActivity() as MainActivity).openNote(id)
            }
        }

        val btn = view.findViewById<ImageView>(R.id.AddButton)
        btn.setOnClickListener {
            val intent = Intent(activity, CameraActivity::class.java)
            startActivityForResult(intent, Companion.REQUEST_CODE)
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Companion.REQUEST_CODE == requestCode) {
            if (resultCode == Activity.RESULT_OK) {
                val result = data!!.getLongExtra(CameraActivity.INDEX_RESULT_KEY, 0)
                (requireActivity() as MainActivity).openNote(result)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


    companion object {
        fun newInstance() = MainFragment()
        private const val REQUEST_CODE = 0
    }
}