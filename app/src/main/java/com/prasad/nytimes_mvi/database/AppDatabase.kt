package com.prasad.nytimes_mvi.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.prasad.nytimes_mvi.database.converter.StoryConverter
import com.prasad.nytimes_mvi.database.dao.StoryDao
import com.prasad.nytimes_mvi.model.Story

@Database(entities = [Story::class], version = 3)
@TypeConverters(StoryConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun storyDao(): StoryDao
}