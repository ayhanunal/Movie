package com.ayhanunal.movies.model

data class Credits(
    val cast: List<CastX>,
    val crew: List<CrewX>,
    val id: Int
)