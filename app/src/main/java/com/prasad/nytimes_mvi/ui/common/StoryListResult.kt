package com.prasad.nytimes_mvi.ui.common

import com.prasad.nytimes_mvi.model.Story
import com.prasad.nytimes_mvi.mvibase.MviResult

sealed class StoryListResult : MviResult{
    sealed class LoadStoriesResult : StoryListResult() {
        data class Success(val type : Type) : LoadStoriesResult()
        data class Failure(val error: Throwable) : LoadStoriesResult()
        data class InProgress(val isRefreshing: Boolean) : LoadStoriesResult()
    }

    sealed class UpdateStoryListResult : StoryListResult() {
        data class Success(val stories: List<Story>) : UpdateStoryListResult()
        data class Failure(val error: Throwable) : UpdateStoryListResult()
    }
}