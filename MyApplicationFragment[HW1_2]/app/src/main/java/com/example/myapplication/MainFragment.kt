package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.db.NoteRepository
import kotlinx.coroutines.*
import android.text.method.ScrollingMovementMethod
import android.widget.TextView


class MainFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    private val REQUEST_CODE = 0
    private val STACK_NAME = "note"

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
        val result = GlobalScope.async (Dispatchers.IO) {
            //delay(1000)
            App.noteRepository.getNotes() }
        GlobalScope.launch (Dispatchers.Main) {
            recyclerView.adapter = NoteAdapter( result.await(), context!! ) { id ->
            (requireActivity() as MainActivity).openNote(id)
            }
        }

        val btn = view.findViewById<ImageView>(R.id.AddButton)
        btn.setOnClickListener {
            val intent = Intent(activity, CameraActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (REQUEST_CODE == requestCode) {
            if (resultCode == Activity.RESULT_OK) {
                val result = data!!.getLongExtra(CameraActivity.INDEX_RESULT_KEY, 0)

                /*supportFragmentManager.popBackStackImmediate(STACK_NAME,
                FragmentManager.POP_BACK_STACK_INCLUSIVE)*/
                val fragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
                fragmentTransaction.addToBackStack(STACK_NAME)

                if (resources.getBoolean(R.bool.is_phone)) {
                    val fragment = NoteFragment.newInstance(result)
                    fragmentTransaction.replace(R.id.dynamicFragmentActivityContainer, fragment, "note")
                    fragmentTransaction.commitAllowingStateLoss();
                } else {
                    val fragment = NoteFragment.newInstance(result)
                    fragmentTransaction.replace(R.id.dynamicFragmentActivityContainerNote, fragment, "note")
                    fragmentTransaction.commitAllowingStateLoss();
                }
            } else {

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    companion object {
        fun newInstance() = MainFragment()
    }
}