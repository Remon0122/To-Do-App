package com.example.todo.ui.home.fragments.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import com.example.todo.ui.home.HomeActivity
import com.example.todo.ui.home.fragments.taskslist.TasksFragment
import com.example.todo.ui.util.Constants
import com.example.todo.ui.util.applyModeChange
import com.route.todo.R
import com.route.todo.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var sharedPreferences : SharedPreferences
    private lateinit var homeActivity: HomeActivity
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences(Constants.SH_NAME,Context.MODE_PRIVATE)
    }

    override fun onStart() {
        super.onStart()
        initializeUI()
        setLanguageDropDownMenu()
        setModeDropDownMenu()
    }

    private fun initializeUI() {
        setLanguageDropDownMenuListener()
        setModeDropDownMenuListener()
        setInitialLanguageState()
        setInitialModeState()
    }

    private fun setLanguageDropDownMenu() {
        val languages = resources.getStringArray(R.array.languages).toList()
        val adapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, languages)
        binding.autoCompleteTVLanguages.setAdapter(adapter)
    }

    private fun setModeDropDownMenu() {
        val modes = resources.getStringArray(R.array.modes).toList()
        val adapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, modes)
        binding.autoCompleteTVModes.setAdapter(adapter)
    }

    private fun setLanguageDropDownMenuListener() {
        binding.autoCompleteTVLanguages.setOnItemClickListener { _, _, position, _ ->
            val selectedLanguage = binding.autoCompleteTVLanguages.adapter.getItem(position).toString()
            binding.autoCompleteTVLanguages.setText(selectedLanguage)

            val languageCode = when (selectedLanguage) {
                getString(R.string.english) -> Constants.ENGLISH_CODE
                getString(R.string.arabic) -> Constants.ARABIC_CODE
                getString(R.string.Russian) -> Constants.RUSSIAN_CODE
                else -> Constants.ENGLISH_CODE
            }
            applyLanguageChange(languageCode)
        }
    }

    private fun applyLanguageChange(languageCode: String) {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode))
    }

    private fun setModeDropDownMenuListener() {
        binding.autoCompleteTVModes.setOnItemClickListener { _, _, position, _ ->
            val selectedMode = binding.autoCompleteTVModes.adapter.getItem(position).toString()
            binding.autoCompleteTVModes.setText(selectedMode)
            val isDark = selectedMode == getString(R.string.dark)
            applyModeChange(isDark)
            saveModeToSharedPreferences(isDark)
        }
    }

    private fun saveModeToSharedPreferences(isDark: Boolean) {
        with(sharedPreferences.edit()){
            putBoolean(Constants.Task_Key,isDark)
            apply()
        }
    }

    private fun setInitialModeState() {
        val currentMode = AppCompatDelegate.getDefaultNightMode()
        val modeText = when (currentMode) {
            AppCompatDelegate.MODE_NIGHT_YES -> R.string.dark
            else -> R.string.light
        }
        binding.autoCompleteTVModes.setText(modeText)
    }

    private fun setInitialLanguageState() {
        val currentLanguageCode = AppCompatDelegate.getApplicationLocales()[0]?.language ?: getCurrentDeviceLanguageCode()
        val languageText = when (currentLanguageCode) {
            Constants.ENGLISH_CODE -> R.string.english
            Constants.ARABIC_CODE -> R.string.arabic
            Constants.RUSSIAN_CODE -> R.string.Russian
            else -> R.string.english
        }
        binding.autoCompleteTVLanguages.setText(languageText)
    }

    private fun getCurrentDeviceLanguageCode(): String {
        return resources.configuration.locales[0].language
    }
}

