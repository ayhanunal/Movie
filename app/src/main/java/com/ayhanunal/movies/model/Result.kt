package com.ayhanunal.movies.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Result(
    @ColumnInfo(name = "adult")
    val adult: Boolean?,

    @ColumnInfo(name = "backdrop_path")
    val backdrop_path: String?,

    @ColumnInfo(name = "genre_ids")
    val genre_ids: List<Int>?,

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "original_language")
    val original_language: String?,

    @ColumnInfo(name = "original_title")
    val original_title: String?,

    @ColumnInfo(name = "overview")
    val overview: String?,

    @ColumnInfo(name = "popularity")
    val popularity: Double?,

    @ColumnInfo(name = "poster_path")
    val poster_path: String?,

    @ColumnInfo(name = "release_date")
    val release_date: String?,

    @ColumnInfo(name = "title")
    val title: String?,

    @ColumnInfo(name = "video")
    val video: Boolean?,

    @ColumnInfo(name = "vote_average")
    val vote_average: Double?,

    @ColumnInfo(name = "vote_count")
    val vote_count: Int?
)