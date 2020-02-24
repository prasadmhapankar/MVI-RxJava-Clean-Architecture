package com.prasad.nytimes_mvi.app

import android.app.Application
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.prasad.nytimes_mvi.database.AppDatabase
import com.prasad.nytimes_mvi.interactor.StoryListInteractor
import com.prasad.nytimes_mvi.network.StoriesApi
import com.prasad.nytimes_mvi.repository.StoryRepository
import com.prasad.nytimes_mvi.repository.StoryRepositoryImpl
import com.prasad.nytimes_mvi.ui.common.StoryAdapter
import com.prasad.nytimes_mvi.ui.science.ScienceViewModel
import com.prasad.nytimes_mvi.utils.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


val databaseModule = module {
    single {
        createDb(androidApplication())
    }
    single {
        getStoriesDao(get())
    }
}

val apiModule = module {
    single {
        Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }
    single {
        createRetrofit(get(), get())
    }

    single<StoriesApi> {
        createStoriesService(get())
    }
    single<StoryRepository> {
        StoryRepositoryImpl(get(), get())
    }
    single {
        StoryListInteractor(get())
    }
//    single {
//        StoryDetailInteractor(get())
//    }
}

val viewModelModule = module {

    viewModel {
        ScienceViewModel(get())
    }
//    viewModel {
//        StoryDetailViewModel(get())
//    }
}

val appModule = module {
    factory {
        StoryAdapter()
    }
//    factory {
//        StoryPropertyAdapter()
//    }
}

private fun createDb(context: Application) =
    Room.databaseBuilder(context, AppDatabase::class.java, Constants.databaseName).build()

private fun getStoriesDao(database: AppDatabase) =
    database.storyDao()

private fun createRetrofit(moshi: Moshi, okHttpClient: OkHttpClient) =
    Retrofit.Builder()
        .baseUrl(Constants.baseUrl)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

private fun createStoriesService(retrofit: Retrofit) =
    retrofit.create(StoriesApi::class.java)