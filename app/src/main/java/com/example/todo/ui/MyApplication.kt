package com.example.todo.ui

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.todo.database.models.MyDatabase
import com.example.todo.ui.util.Constants
import com.example.todo.ui.util.applyModeChange

class MyApplication : Application(){
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate() {
        super.onCreate()
        MyDatabase.init(this)
        setNightMode()
    }

    private fun setNightMode() {
        sharedPreferences = getSharedPreferences(Constants.SH_NAME,Context.MODE_PRIVATE)
        val isDark = sharedPreferences.getBoolean(Constants.IS_DARK_MODE_KEY,getDeviceModeState())


        applyModeChange(isDark)
    }
    private fun getDeviceModeState():Boolean{
       val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }
}