package com.prasad.nytimes_mvi.database.dao

import androidx.room.*
import com.prasad.nytimes_mvi.model.Story
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStory(story: Story)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStories(stories: List<Story>)

    @Update
    fun updateStory(story: Story)

    @Query("SELECT * FROM story WHERE title = :name")
    fun getStory(name: String?): Single<Story>

    @Query("SELECT * FROM story WHERE title = :name")
    fun getStorySync(name: String): Story?

    @Query("SELECT * FROM story order by title")
    fun getAll(): Flowable<List<Story>>

    @Query("SELECT * FROM story order by title")
    fun getAllSync(): List<Story>
}