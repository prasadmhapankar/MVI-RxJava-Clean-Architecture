package com.prasad.nytimes_mvi.network

import com.prasad.nytimes_mvi.model.StoryList
import io.reactivex.Single
import retrofit2.http.GET

interface StoriesApi {
    @GET("science.json?api-key=xI4AA4gcMj9JyFlyQn2dSAj689PGjKjA")
    fun getTopScienceStories(): Single<StoryList>
}