package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.title = "Notes"
        val cardView = findViewById<View>(R.id.note_list_item)
        cardView.setOnClickListener {
            val intent = Intent(Partical@this, Partical::class.java)
            startActivity(intent)
        }
    }
}
