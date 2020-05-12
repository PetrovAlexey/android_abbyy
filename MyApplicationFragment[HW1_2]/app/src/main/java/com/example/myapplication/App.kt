package com.example.myapplication

import android.app.Application
import android.content.Intent
import com.example.myapplication.db.AppSqliteOpenHelper
import com.example.myapplication.db.NoteRepository

class App: Application() {
    companion object {
        lateinit var noteRepository: NoteRepository
    }

    override fun onCreate() {
        super.onCreate()
        val helper = AppSqliteOpenHelper(this)
        noteRepository = NoteRepository(helper)

        val intentMyIntentService = Intent(this, BackupIntentService::class.java)
        startService(intentMyIntentService)
    }

}