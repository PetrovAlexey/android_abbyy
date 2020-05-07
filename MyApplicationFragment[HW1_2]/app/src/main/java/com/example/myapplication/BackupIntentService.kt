package com.example.myapplication

import android.app.IntentService
import android.content.Intent
import android.os.Environment
import java.io.File
import java.io.PrintWriter
import android.widget.Toast
import android.content.Context
import android.widget.EditText

import android.util.Log
import android.view.View
import java.io.FileOutputStream
import java.io.IOException


class BackupIntentService : IntentService("Backup") {
    private val FILE_NAME: String = "Backup"

    override fun onHandleIntent(intent: Intent?) {
        val data = intent?.getStringExtra("Notes")
        Log.d("Backup", "onHandleIntent start: ");
        if (data != null) {
            saveText(data)
        }
    }

    private fun saveText(data: String) {
        var fos: FileOutputStream? = null
        try {
            fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE)
            fos!!.write(data.toByteArray())
            Toast.makeText(this, "Backup saved", Toast.LENGTH_SHORT).show()
        } catch (ex: IOException) {

            Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
        } finally {
            try {
                fos?.close()
            } catch (ex: IOException) {

                Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
            }

        }
    }

}