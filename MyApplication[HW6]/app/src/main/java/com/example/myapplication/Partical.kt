package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Partical : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partical)
        this.title = "Note 1";
    }
}
