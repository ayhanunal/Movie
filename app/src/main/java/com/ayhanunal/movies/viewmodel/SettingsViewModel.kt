package com.ayhanunal.movies.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import com.ayhanunal.movies.configuration.Languages
import com.ayhanunal.movies.configuration.LocaleSettings
import com.ayhanunal.movies.listeners.OnLocaleChangeListener
import com.ayhanunal.movies.view.SettingsFragment

class SettingsViewModel(application: Application) : BaseViewModel(application) {

    var onLocaleChangeListener: OnLocaleChangeListener? = null

    fun checkCurrentLanguageAndSetLanguage(languages: Languages){
        if (LocaleSettings.APP_LOCALE_LANGUAGE != languages.localeLang){
            onLocaleChangeListener?.onLocaleChanged(languages)
        }
    }

}