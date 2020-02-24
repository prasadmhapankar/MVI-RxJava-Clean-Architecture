package com.prasad.nytimes_mvi.mvibase

import io.reactivex.Observable

interface MviView<I : MviIntent, in S : MviViewState> {

    /**
     * Unique [Observable] used by the [MviViewModel]
     * to listen to the [MviView].
     * All the [MviView]'s [MviIntent]s must go through this [Observable].
     * Expose all the possible intents from the user`s side
     */
    fun intents(): Observable<I>

    /**
     * Entry point for the [MviView] to render itself based on a [MviViewState].
     * Reflects [MviViewState] to the screen
     */
    fun render(state: S)
}