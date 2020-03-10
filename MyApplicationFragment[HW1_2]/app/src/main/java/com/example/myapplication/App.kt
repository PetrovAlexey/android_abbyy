package com.example.myapplication

import android.app.Application
import android.content.ContentValues
import android.content.Context
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


    }
}