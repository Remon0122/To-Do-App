package com.example.todo.ui

import android.app.Application
import com.example.todo.database.models.MyDataBase

class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        MyDataBase.init(this)
    }
}