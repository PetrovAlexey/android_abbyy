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
        val data = App.noteRepository.getNotes().toString()
        saveText(data)
    }

    private fun saveText(data: String) {
        try {
            openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use { fos ->
                fos!!.write(data.toByteArray())
                Toast.makeText(this, "Backup saved", Toast.LENGTH_SHORT).show()
            }
        } catch (ex: IOException) {
            Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
        }
    }
}