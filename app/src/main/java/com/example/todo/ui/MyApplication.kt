package com.example.todo.ui

import android.app.Application
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.example.todo.ui.util.Constants
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApplication : Application() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate() {
        super.onCreate()

        sharedPreferences = getSharedPreferences(Constants.SH_NAME, MODE_PRIVATE)

        // Apply saved language and mode
        applySavedLanguage()
        applySavedDarkMode()
    }

    private fun applySavedLanguage() {
        val langCode = sharedPreferences.getString(Constants.LANGUAGE_KEY, Constants.ENGLISH_CODE) ?: Constants.ENGLISH_CODE
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(langCode))
    }

    private fun applySavedDarkMode() {
        val isDark = sharedPreferences.getBoolean(Constants.IS_DARK_MODE_KEY, getSystemDefaultDarkMode())
        AppCompatDelegate.setDefaultNightMode(
            if (isDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    private fun getSystemDefaultDarkMode(): Boolean {
        val nightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return nightMode == Configuration.UI_MODE_NIGHT_YES
    }
}
