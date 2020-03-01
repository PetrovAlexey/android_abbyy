package com.example.myapplication

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {

    /*override fun createFragment(): Fragment {
        return MainFragment.newInstance()
    }*/
    private val layoutResId: Int
        @LayoutRes
        get() = R.layout.activity_fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layoutResId)

        val fm = supportFragmentManager
        var fragment = fm.findFragmentById(R.id.dynamicFragmentActivityContainer)

        // ensures fragments already created will not be created
        if (fragment == null) {
            fragment = MainFragment.newInstance()
            // create and commit a fragment transaction
            fm.beginTransaction()
                .add(R.id.dynamicFragmentActivityContainer, fragment)
                .commit()
        }
    }
    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        this.title = getString(R.string.app_title)
        /*
        val recyclerView = findViewById<RecyclerView>(R.id.RecyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = NoteAdapter(NoteRepository.listNotes())
        */

    }*/
    private val STACK_NAME = "note"
    fun openNote(id: Long) {
        supportFragmentManager.popBackStackImmediate(STACK_NAME,
            FragmentManager.POP_BACK_STACK_INCLUSIVE)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack(STACK_NAME)

        val fragment = NoteFragment.newInstance(id)
        fragmentTransaction.replace(R.id.dynamicFragmentActivityContainer, fragment, "note")
        fragmentTransaction.commit()
    }

}
