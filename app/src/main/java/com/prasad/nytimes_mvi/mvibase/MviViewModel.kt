package com.prasad.nytimes_mvi.mvibase

import androidx.lifecycle.LiveData
import io.reactivex.Observable

interface MviViewModel<I : MviIntent, S : MviViewState> {

    val statesLiveData: LiveData<S>

    fun processIntents(intents: Observable<I>)
}