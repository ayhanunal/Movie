package com.ayhanunal.movies.configuration

import android.app.Activity
import android.content.res.Configuration
import android.content.res.Resources
import com.ayhanunal.movies.util.CustomSharedPreferences
import java.util.*

object LocaleSettings {

    var APP_LOCALE_LANGUAGE: String = ""

    fun setLocale(activity: Activity, languages: Languages, sharedPreferences: CustomSharedPreferences?) {
        APP_LOCALE_LANGUAGE = languages.localeLang

        sharedPreferences.let {
            it?.saveCurrentLocale(languages)
        }

        val locale = Locale(languages.localeLang)
        Locale.setDefault(locale)
        val resources: Resources = activity.resources
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}