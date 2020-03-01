package com.prasad.nytimes_mvi.mvibase

import android.os.Bundle
import io.reactivex.Observable

interface MviView<I : MviIntent, in S : MviViewState> {

    /**
     * It expects Android's arch-comp `ViewModel` instance to be provided by us.
     */
    val viewModel: MviViewModel<I, in S>

    /**
     * Unique [Observable] used by the [MviViewModel]
     * to listen to the [MviView].
     * All the [MviView]'s [MviIntent]s must go through this [Observable].
     * Expose all the possible intents from the user`s side
     */
    fun intents(): Observable<I> = Observable.empty()

    /**
     * Entry point for the [MviView] to render itself based on a [MviViewState].
     * Reflects [MviViewState] to the screen
     */
    fun render(state: S)

    /**
     * Don't forget to call `super.onMviCreated()` after `viewModel` has been initialzed. We can call it inside
     * `onViewCreated()` of `Fragment` or inside `onCreate()` for an `Activity`. By doing this,
     * our `View` gets subscribed automatically to the `state` and `navigation` events.
     */
    fun onMviViewCreated(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            viewModel.processIntents(intents())
        }
    }

}