package com.prasad.nytimes_mvi.repository

import com.prasad.nytimes_mvi.database.dao.StoryDao
import com.prasad.nytimes_mvi.model.Story
import com.prasad.nytimes_mvi.network.StoriesApi
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface StoryRepository {
    fun loadStories(): Completable

    fun getStories(): Observable<List<Story>>

    fun getStory(storyTitle: String?): Single<Story>
}

class StoryRepositoryImpl(private val storyService: StoriesApi, private val storyDao: StoryDao) :
    StoryRepository {
    override fun loadStories(): Completable =
        storyService.getTopScienceStories()
            .doOnSuccess { stories ->
                println("Dispatching ${stories.results.size} stories from API...")
                storeStoriesInDb(stories.results)
            }.ignoreElement()

    override fun getStories(): Observable<List<Story>> =
        storyDao.getAll()
            .doOnNext { println("Dispatching ${it.size} from DB...") }
            .toObservable()

    override fun getStory(storyTitle: String?): Single<Story> =
        storyDao.getStory(storyTitle)
            .doOnSuccess { println("Dispatching ${it.title} from DB...") }

    private fun storeStoriesInDb(stories: List<Story>?) {
        println("Saving ${stories?.size} stories from API to DB...")
        stories?.let { storyDao.insertStories(it) }
    }

}

