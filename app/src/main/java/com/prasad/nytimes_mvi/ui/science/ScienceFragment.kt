package com.prasad.nytimes_mvi.ui.science

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.jakewharton.rxbinding2.support.v4.widget.refreshes
import com.prasad.nytimes_mvi.R
import com.prasad.nytimes_mvi.databinding.FragmentScienceBinding
import com.prasad.nytimes_mvi.ui.base.BaseFragment
import com.prasad.nytimes_mvi.ui.common.StoryAdapter
import com.prasad.nytimes_mvi.ui.common.StoryListIntent
import com.prasad.nytimes_mvi.utils.navigate
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScienceFragment :
    BaseFragment<FragmentScienceBinding, StoryListIntent, ScienceListViewState>(R.layout.fragment_science){

    private val initialIntentPublisher = PublishSubject.create<StoryListIntent.InitialIntent>()

    private val swipeToRefreshIntent by lazy {
        binding.swiperefresh.refreshes()
            .map {
                StoryListIntent.SwipeToRefresh
            }
    }

    private val adapter by inject<StoryAdapter>()

    private val viewModel: ScienceViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.statesLiveData.observe(this, Observer { state ->
            println("state: $state")
            render(state)
        })


    }

    override fun initViews() {
        setupListView()
    }

    override fun startStream() =
        // Pass the UI's intents to the ViewModel
        viewModel.processIntents(intents())

    override fun intents(): Observable<StoryListIntent> = Observable.merge(
        initialIntent(),
        swipeToRefreshIntent
    )

    override fun render(state: ScienceListViewState) {
        binding.model = state

        if (state.initial) {
            initialIntentPublisher.onNext(StoryListIntent.InitialIntent)
        }

        if (state.error != null) {
            showErrorMessage(state.error)
        }
    }

    private fun setupListView() {
        binding.rvScience.layoutManager = LinearLayoutManager(activity)
        (binding.rvScience.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        binding.rvScience.adapter = adapter

        adapter.storyClickObservable.observe(this, Observer { story ->
            println("story => $story")
            var imageUrl : String? = null
            story.multimedia.let {
                it?.forEach { it ->
                    if (it.format == "superJumbo") {
                        it.url.let {
                            imageUrl = it
                            return@forEach
                        }
                    }
                }
            }

            val bundle = bundleOf(
                "title" to story.title,
                "image" to imageUrl,
                "author" to story.byline
            )

           navigate(R.id.action_navigation_science_to_storyDetailFragment, bundle)
        })
    }

    private fun initialIntent(): Observable<StoryListIntent.InitialIntent> =
        initialIntentPublisher

    private fun showErrorMessage(exception: Throwable) =
        toast(exception.localizedMessage)

}