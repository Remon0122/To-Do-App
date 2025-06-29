package com.example.todo.ui.home.fragments.HomeViewModel

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todo.ui.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _languageCode = MutableLiveData<String>()
    val languageCode: LiveData<String> = _languageCode

    private val _isDarkMode = MutableLiveData<Boolean>()
    val isDarkMode: LiveData<Boolean> = _isDarkMode

    init {
        val currentMode = sharedPreferences.getBoolean(Constants.IS_DARK_MODE_KEY, false)
        _isDarkMode.value = currentMode

        val savedLang = sharedPreferences.getString(Constants.LANGUAGE_KEY, null)
        val langCode = savedLang ?: AppCompatDelegate.getApplicationLocales()[0]?.language ?: "en"
        _languageCode.value = langCode
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(langCode))
    }

    fun setLanguage(code: String) {
        _languageCode.value = code
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(code))
        sharedPreferences.edit().putString(Constants.LANGUAGE_KEY, code).apply()
    }

    fun setMode(isDark: Boolean) {
        _isDarkMode.value = isDark
        AppCompatDelegate.setDefaultNightMode(
            if (isDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
        sharedPreferences.edit().putBoolean(Constants.IS_DARK_MODE_KEY, isDark).apply()
    }
}