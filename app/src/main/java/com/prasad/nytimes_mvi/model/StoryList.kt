package com.prasad.nytimes_mvi.model

import androidx.room.Entity
import com.squareup.moshi.Json

@Entity
data class StoryList(
    @field:Json(name = "results")
    val results : List<Story>,
    @field:Json(name = "num_results")
    val num_results : Int,
    @field:Json(name = "status")
    val status : String

)