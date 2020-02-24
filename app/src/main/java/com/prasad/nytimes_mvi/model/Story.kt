package com.prasad.nytimes_mvi.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class Story(
    @PrimaryKey
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "byline")
    val byline: String,
    @field:Json(name = "multimedia")
    val multimedia: List<Multimedia>?
){
    constructor():this("",
        "",null)
}

data class Multimedia(
    @field:Json(name = "url") val url: String,
    @field:Json(name = "format") val format: String,
    @field:Json(name = "type") val type: String,
    @field:Json(name = "height") val height: Int,
    @field:Json(name = "width") val width: Int
)