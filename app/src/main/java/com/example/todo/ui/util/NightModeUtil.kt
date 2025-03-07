package com.example.todo.ui.util

import androidx.appcompat.app.AppCompatDelegate

fun applyModeChange(isDarkMode: Boolean) {
    val mode = if (isDarkMode) {
        AppCompatDelegate.MODE_NIGHT_YES
    } else {
        AppCompatDelegate.MODE_NIGHT_NO
    }
    AppCompatDelegate.setDefaultNightMode(mode)
}