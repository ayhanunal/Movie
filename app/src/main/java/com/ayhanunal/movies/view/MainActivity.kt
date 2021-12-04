package com.ayhanunal.movies.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.ayhanunal.movies.R
import android.widget.ImageButton
import com.ayhanunal.movies.configuration.LocaleSettings
import com.ayhanunal.movies.configuration.Languages


class MainActivity : AppCompatActivity() {

    private lateinit var navigationController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        navigationController = Navigation.findNavController(this, R.id.fragment)
        //NavigationUI.setupActionBarWithNavController(this, navigationController)

        findViewById<ImageButton>(R.id.change_locale).setOnClickListener {
            LocaleSettings.setLocale(this, Languages.ENGLISH)
            refreshCurrentFragment()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navigationController, null)
    }

    private fun refreshCurrentFragment(){
        val id = navigationController.currentDestination?.id
        navigationController.popBackStack(id!!,true)
        navigationController.navigate(id)
    }

}