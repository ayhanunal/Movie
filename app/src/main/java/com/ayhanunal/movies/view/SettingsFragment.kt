package com.ayhanunal.movies.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ayhanunal.movies.R
import com.ayhanunal.movies.configuration.Languages
import com.ayhanunal.movies.viewmodel.FavMoviesViewModel
import com.ayhanunal.movies.viewmodel.SettingsViewModel

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var viewModel : SettingsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)

        viewModel.onLocaleChangeListener = activity as MainActivity

        view.findViewById<ImageView>(R.id.app_settings_tr_button).setOnClickListener {
            // choosen lang is tr
            viewModel.checkCurrentLanguageAndSetLanguage(Languages.TURKISH)
        }

        view.findViewById<ImageView>(R.id.app_settings_en_button).setOnClickListener {
            // choosen lang is en
            viewModel.checkCurrentLanguageAndSetLanguage(Languages.ENGLISH)
        }

    }

}