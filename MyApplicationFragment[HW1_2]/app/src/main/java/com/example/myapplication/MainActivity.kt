package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.FragmentManager
import com.example.myapplication.db.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private val layoutResId: Int
        @LayoutRes
        get() = R.layout.activity_fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)

        val fm = supportFragmentManager
        var fragment = fm.findFragmentById(R.id.dynamicFragmentActivityContainer)

        if (fragment == null) {
            fragment = MainFragment.newInstance()
            fm.beginTransaction()
                .add(R.id.dynamicFragmentActivityContainer, fragment)
                .commit()
        }

        if (App.noteRepository.getNotes().isEmpty()) {
            for (i in 0 until 5) {
//                val values = ContentValues().apply {
//                    put(NoteContract.NoteData.ID, UUID.randomUUID().toString())
//                    put(NoteContract.NoteData.TEXT, "Number ${i}")
//                    put(NoteContract.NoteData.DATE, Date().time)
//                    put(NoteContract.NoteData.RES_ID, R.drawable.image)
//                }
                App.noteRepository.createNote(Note(i.toLong(), Date(), "$i Number", R.drawable.image.toString()))
            }
        }

        val btn = findViewById<ImageView>(R.id.AddButton)
        btn.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE")
        super.onSaveInstanceState(outState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (REQUEST_CODE == requestCode) {
            if (resultCode == Activity.RESULT_OK) {
                val result = data!!.getLongExtra(CameraActivity.INDEX_RESULT_KEY, 0)

                    /*supportFragmentManager.popBackStackImmediate(STACK_NAME,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE)*/
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
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

    private val REQUEST_CODE = 0
    private val STACK_NAME = "note"

    fun openNote(id: Long) {
        supportFragmentManager.popBackStackImmediate(STACK_NAME,
            FragmentManager.POP_BACK_STACK_INCLUSIVE)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack(STACK_NAME)

        if (resources.getBoolean(R.bool.is_phone)) {
            val fragment = NoteFragment.newInstance(id)
            fragmentTransaction.replace(R.id.dynamicFragmentActivityContainer, fragment, "note")
            fragmentTransaction.commit()
        } else {
            val fragment = NoteFragment.newInstance(id)
            fragmentTransaction.replace(R.id.dynamicFragmentActivityContainerNote, fragment, "note")
            fragmentTransaction.commit()
        }
    }

}
