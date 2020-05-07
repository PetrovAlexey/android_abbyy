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
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView


class MainActivity : AppCompatActivity() {

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
            //val intent = Intent(this, CameraActivity::class.java)
            //startActivity(intent)
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

//                this.openNote(result)
               // resultTextView.setText(result)
            } else {
                //Toast.makeText(this, R.string.result_cancelled, Toast.LENGTH_SHORT).show()
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

    fun showPopUp(view: View) {
        val popupMenu = PopupMenu(this, view)
        val inflater = popupMenu.menuInflater
        inflater.inflate(R.menu.header_menu, popupMenu.menu)
        popupMenu.show()

        val id =
        //val id = this.findViewById<CardView>(R.id.note_list_item).id

        popupMenu.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.header1 -> {
                    //Toast.makeText(this@MainActivity, item.title, Toast.LENGTH_SHORT).show();
                }
                R.id.header2 -> {

                    val builder = AlertDialog.Builder(this@MainActivity)

                    // Set the alert dialog title
                    builder.setTitle("Delete article")

                    // Display a message on alert dialog
                    builder.setMessage("Are you want to delete this article?")

                    // Set a positive button and its click listener on alert dialog
                    builder.setPositiveButton("Ok"){dialog, which ->
                        // Do something when user press the positive button
                        //App.noteRepository.deleteNote()

                        Toast.makeText(applicationContext,"Article deleted.",Toast.LENGTH_SHORT).show()

                    }

                    // Display a neutral button on alert dialog
                    builder.setNeutralButton("Cancel"){_,_ ->
                        //Toast.makeText(applicationContext,"You cancelled the dialog.",Toast.LENGTH_SHORT).show()
                    }

                    // Finally, make the alert dialog using builder
                    val dialog: AlertDialog = builder.create()

                    // Display the alert dialog on app interface
                    dialog.show()
                    //Toast.makeText(this@MainActivity, item.title, Toast.LENGTH_SHORT).show();
                }
            }
            true
        }}


}
