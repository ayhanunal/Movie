package com.ayhanunal.movies.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.ayhanunal.movies.R
import android.app.Activity
import android.content.res.Configuration
import android.content.res.Resources
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var navigationController: NavController

    companion object{
        var APP_LOCALE_LANGUAGE: String = Locale.getDefault().language
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        navigationController = Navigation.findNavController(this, R.id.fragment)
        //NavigationUI.setupActionBarWithNavController(this, navigationController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navigationController, null)
    }
    
}