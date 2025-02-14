package com.example.todo.ui

import android.app.Application
import com.example.todo.database.models.MyDatabase

class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        MyDatabase.init(this)
    }
}