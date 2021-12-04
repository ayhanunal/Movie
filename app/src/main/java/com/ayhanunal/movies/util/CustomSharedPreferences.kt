package com.ayhanunal.movies.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit
import com.ayhanunal.movies.configuration.Languages
import java.util.*

class CustomSharedPreferences {

    companion object {

        private const val LOCALE_SETTINGS_LANG = "locale_setting_language_sp"
        private var sharedPreferences: SharedPreferences? = null

        @Volatile private var instance: CustomSharedPreferences? = null

        private val lock = Any()
        operator fun invoke(context: Context) : CustomSharedPreferences = instance ?: synchronized(lock){
            instance ?: makeCustomSharedPreferences(context).also {
                instance = it
            }
        }

        private fun makeCustomSharedPreferences(context: Context) : CustomSharedPreferences{
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPreferences()
        }
    }

    fun saveCurrentLocale(lang: Languages){
        sharedPreferences?.edit(commit = true){
            putString(LOCALE_SETTINGS_LANG, lang.localeLang).apply()
        }
    }

    fun getSavedLocale(): String {
        return sharedPreferences?.getString(LOCALE_SETTINGS_LANG, Locale.getDefault().language)!!
    }

}