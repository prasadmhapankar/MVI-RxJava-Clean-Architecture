package com.prasad.nytimes_mvi.ui.science

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.prasad.nytimes_mvi.interactor.StoryListInteractor
import com.prasad.nytimes_mvi.ui.base.BaseViewModel
import com.prasad.nytimes_mvi.ui.common.StoryListAction
import com.prasad.nytimes_mvi.ui.common.StoryListIntent
import com.prasad.nytimes_mvi.ui.common.StoryListResult
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class ScienceViewModel(storyListInteractor: StoryListInteractor
) : BaseViewModel<StoryListIntent, StoryListAction, StoryListResult, ScienceListViewState>() {

    override val reducer = BiFunction { previousState: ScienceListViewState, result: StoryListResult ->
        when (result) {

            is StoryListResult.LoadStoriesResult ->
                when (result) {
                    is StoryListResult.LoadStoriesResult.Success -> {
                        previousState.copy(
                            isLoading = false,
                            isRefreshing = false,
                            type = result.type,
                            error = null,
                            initial = false
                        )
                    }
                    is StoryListResult.LoadStoriesResult.Failure -> {
                        previousState.copy(isLoading = false, error = result.error, initial = false)
                    }
                    is StoryListResult.LoadStoriesResult.InProgress -> {
                        if (result.isRefreshing) {
                            previousState.copy(isLoading = false, isRefreshing = true)
                        } else previousState.copy(isLoading = true, isRefreshing = false, initial = false)
                    }
                }

            is StoryListResult.UpdateStoryListResult ->
                when (result) {
                    is StoryListResult.UpdateStoryListResult .Success -> {
                        previousState.copy(
                            initial = false,
                            stories = result.stories
                        )
                    }
                    is StoryListResult.UpdateStoryListResult.Failure -> {
                        previousState.copy(
                            initial = false,
                            error = result.error)
                    }
                }
        }
    }

    override fun actionFromIntent(intent: StoryListIntent): StoryListAction =
        when (intent) {
            is StoryListIntent.InitialIntent -> StoryListAction.LoadStoriesAction(false)
            is StoryListIntent.SwipeToRefresh -> StoryListAction.LoadStoriesAction(true)
            is StoryListIntent.ChangeIntent -> StoryListAction.LoadStoriesAction(type = intent.type)
        }

    override val statesLiveData :  LiveData<ScienceListViewState> =
        LiveDataReactiveStreams.fromPublisher(
            intentsSubject
                .doOnNext { intent ->
                    println("intent: $intent")
                }
                .map(this::actionFromIntent)
                //observe app state - database
                .mergeWith(storyListInteractor.storyRepository.getStories().map {
                    StoryListAction.UpdateStoryListAction(
                        it
                    )
                })
                .doOnNext { action ->
                    println("action: $action")
                }
                .compose(storyListInteractor.actionProcessor)
                // Cache each state and pass it to the reducer to create a new state from
                // the previous cached one and the latest Result emitted from the action processor.
                // The Scan operator is used here for the caching.
                .scan(ScienceListViewState.idle(), reducer)
                // When a reducer just emits previousState, there's no reason to call render. In fact,
                // redrawing the UI in cases like this can cause jank (e.g. messing up snackbar animations
                // by showing the same snackbar twice in rapid succession).
                .distinctUntilChanged()
                // Emit the last one event of the stream on subscription
                // Useful when a View rebinds to the ViewModel after rotation.
                .replay(1)
                // Create the stream on creation without waiting for anyone to subscribe
                // This allows the stream to stay alive even when the UI disconnects and
                // match the stream's lifecycle to the ViewModel's one.
                .autoConnect(0)
                .toFlowable(BackpressureStrategy.BUFFER))

    override fun processIntents(intents: Observable<StoryListIntent>) =
        intents
            .doOnNext { intent ->
                println("intent: $intent")
            }
            .subscribe(intentsSubject)


}