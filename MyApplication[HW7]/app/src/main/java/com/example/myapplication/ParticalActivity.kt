package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ParticalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partical)
        this.title = getString(R.string.sub_title)
    }
}
