package com.prasad.nytimes_mvi.interactor

import com.prasad.nytimes_mvi.mvibase.MviInteractor
import com.prasad.nytimes_mvi.repository.StoryRepository
import com.prasad.nytimes_mvi.ui.common.StoryListAction
import com.prasad.nytimes_mvi.ui.common.StoryListResult
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class StoryListInteractor(val storyRepository: StoryRepository) : MviInteractor<StoryListAction, StoryListResult>{

    override val actionProcessor = ObservableTransformer<StoryListAction, StoryListResult> { actions ->
        actions.publish { selector ->
            Observable.merge(
                selector.ofType(StoryListAction.LoadStoriesAction::class.java).compose(loadStories)
                    .doOnNext { result ->
                        println("result: $result")
                    },
                selector.ofType(StoryListAction.UpdateStoryListAction::class.java).compose(updateStories)
                    .doOnNext { result ->
                        println("result: $result")
                    }
            )
        }
    }

    private val loadStories =
        ObservableTransformer<StoryListAction.LoadStoriesAction, StoryListResult> { actions ->
            actions.flatMap { action ->
                storyRepository.loadStories()
                    .andThen(
                        Observable.just(StoryListResult.LoadStoriesResult.Success(action.type))
                    )
                    .cast(StoryListResult.LoadStoriesResult::class.java)
                    .onErrorReturn { StoryListResult.LoadStoriesResult.Failure(it) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .startWith(StoryListResult.LoadStoriesResult.InProgress(action.isRefreshing))
            }
        }

    private val updateStories =
        ObservableTransformer<StoryListAction.UpdateStoryListAction, StoryListResult> { actions ->
            actions.flatMap { action ->
                Observable.just(StoryListResult.UpdateStoryListResult.Success(action.stories))
                    .cast(StoryListResult.UpdateStoryListResult::class.java)
                    .onErrorReturn { StoryListResult.UpdateStoryListResult.Failure(it) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }
        }

}