package com.example.myapplication

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager

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

    }

    private val STACK_NAME = "note"

    fun openNote(id: Long) {
        supportFragmentManager.popBackStackImmediate(STACK_NAME,
            FragmentManager.POP_BACK_STACK_INCLUSIVE)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack(STACK_NAME)

        if (resources.getBoolean(R.bool.is_phone)) {
            val fragment = NoteFragment.newInstance(id)
            fragmentTransaction.replace(R.id.dynamicFragmentActivityContainer, fragment, "note")
            fragmentTransaction.commitAllowingStateLoss()
        } else {
            val fragment = NoteFragment.newInstance(id)
            fragmentTransaction.replace(R.id.dynamicFragmentActivityContainerNote, fragment, "note")
            fragmentTransaction.commitAllowingStateLoss()
        }
    }

}
