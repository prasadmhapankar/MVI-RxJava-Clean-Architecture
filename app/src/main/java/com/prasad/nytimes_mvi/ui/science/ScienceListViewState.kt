package com.prasad.nytimes_mvi.ui.science

import com.prasad.nytimes_mvi.model.Story
import com.prasad.nytimes_mvi.mvibase.MviViewState
import com.prasad.nytimes_mvi.ui.common.Type

data class ScienceListViewState(
    val isLoading: Boolean,
    val isRefreshing: Boolean,
    val stories: List<Story>,
    val type: Type,
    val error: Throwable?,
    val initial: Boolean
) : MviViewState {
    companion object {
        fun idle(): ScienceListViewState {
            return ScienceListViewState(
                isLoading = false,
                isRefreshing = false,
                stories = emptyList(),
                type = Type.All,
                error = null,
                initial = true
            )
        }
    }
}