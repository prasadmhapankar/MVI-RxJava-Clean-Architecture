package com.prasad.nytimes_mvi.database.converter

import androidx.room.TypeConverter
import com.prasad.nytimes_mvi.model.Multimedia
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class StoryConverter : KoinComponent{
    private val moshi by inject<Moshi>()

    @TypeConverter
    fun fromCurrencyJsonToList(value: String?): List<Multimedia> =
        value?.let {
            val type = Types.newParameterizedType(List::class.java, Multimedia::class.java)
            val adapter = moshi.adapter<List<Multimedia>>(type)
            adapter.fromJson(value)
        } ?: emptyList()


    @TypeConverter
    fun fromCurrencyListToJson(list: List<Multimedia>?): String =
        moshi.adapter(List::class.java).run {
            toJson(list)
        }
}