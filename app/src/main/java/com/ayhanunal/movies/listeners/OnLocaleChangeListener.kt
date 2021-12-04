package com.ayhanunal.movies.listeners

import com.ayhanunal.movies.configuration.Languages

interface OnLocaleChangeListener {
    fun onLocaleChanged(languages: Languages)
}