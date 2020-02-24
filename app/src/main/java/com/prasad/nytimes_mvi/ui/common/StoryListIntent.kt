package com.prasad.nytimes_mvi.ui.common

import com.prasad.nytimes_mvi.mvibase.MviIntent

sealed class StoryListIntent : MviIntent {
    object InitialIntent : StoryListIntent()
    object SwipeToRefresh : StoryListIntent()
    data class ChangeIntent(val type: Type) : StoryListIntent()
}