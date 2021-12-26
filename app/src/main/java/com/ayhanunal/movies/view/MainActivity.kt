package com.ayhanunal.movies.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.ayhanunal.movies.R
import android.widget.ImageButton
import android.widget.Toast
import com.ayhanunal.movies.configuration.LocaleSettings
import com.ayhanunal.movies.configuration.Languages
import com.ayhanunal.movies.listeners.OnLocaleChangeListener
import com.ayhanunal.movies.util.CustomSharedPreferences
import com.ayhanunal.movies.util.checkInternetConnection
import com.ayhanunal.movies.viewmodel.SettingsViewModel


class MainActivity : AppCompatActivity(), OnLocaleChangeListener {

    private lateinit var navigationController: NavController

    private lateinit var customPreferences: CustomSharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (!checkInternetConnection(this)){
            Toast.makeText(this, resources.getString(R.string.check_your_internet_connection_text), Toast.LENGTH_LONG).show()
            finishAndRemoveTask()
        }

        navigationController = Navigation.findNavController(this, R.id.fragment)
        //NavigationUI.setupActionBarWithNavController(this, navigationController)

        customPreferences = CustomSharedPreferences(application)
        LocaleSettings.APP_LOCALE_LANGUAGE = customPreferences.getSavedLocale()
        val languagesForLocaleLang = Languages.values().filter {
            it.localeLang == LocaleSettings.APP_LOCALE_LANGUAGE
        }
        LocaleSettings.setLocale(this, languagesForLocaleLang[0], null)

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navigationController, null)
    }

    private fun refreshCurrentFragment(){
        val id = navigationController.currentDestination?.id
        navigationController.popBackStack(id!!,true)
        navigationController.navigate(id)
    }

    override fun onLocaleChanged(languages: Languages) {
        LocaleSettings.setLocale(this, languages, customPreferences)
        refreshCurrentFragment()
    }

}