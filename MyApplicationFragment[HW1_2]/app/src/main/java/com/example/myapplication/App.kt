package com.example.myapplication

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import com.example.myapplication.db.*
import java.util.*

class App: Application() {
    companion object {
        lateinit var noteRepository: NoteRepository
    }

    override fun onCreate() {
        super.onCreate()
        val helper = AppSqliteOpenHelper(this)
        noteRepository = NoteRepository(helper)

        val notes = noteRepository.getNotes()
        val intentMyIntentService = Intent(this, BackupIntentService::class.java)
        startService(
            intentMyIntentService
                .putExtra("Notes", notes.toString())
        )
    }

}