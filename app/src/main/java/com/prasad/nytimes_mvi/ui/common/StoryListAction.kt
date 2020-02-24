package com.prasad.nytimes_mvi.ui.common

import com.prasad.nytimes_mvi.model.Story
import com.prasad.nytimes_mvi.mvibase.MviAction

sealed class StoryListAction : MviAction {
    data class LoadStoriesAction(val isRefreshing: Boolean = false,val type: Type = Type.All) : StoryListAction()
    data class UpdateStoryListAction(val stories: List<Story>) : StoryListAction()
}