package com.ayhanunal.movies.configuration

import android.app.Activity
import android.content.res.Configuration
import android.content.res.Resources
import java.util.*

object LocaleSettings {

    var APP_LOCALE_LANGUAGE: String = Locale.getDefault().language

    fun setLocale(activity: Activity, languageCode: Languages) {
        APP_LOCALE_LANGUAGE = languageCode.localeLang

        val locale = Locale(APP_LOCALE_LANGUAGE)
        Locale.setDefault(locale)
        val resources: Resources = activity.resources
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

}