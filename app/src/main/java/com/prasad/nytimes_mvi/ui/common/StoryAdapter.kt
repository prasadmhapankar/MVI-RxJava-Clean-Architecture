package com.prasad.nytimes_mvi.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prasad.nytimes_mvi.databinding.ItemStoryBinding
import com.prasad.nytimes_mvi.model.Story
import io.reactivex.BackpressureStrategy
import io.reactivex.subjects.PublishSubject

class StoryAdapter : ListAdapter<Story, StoryViewHolder>(StoryDiffCallback()){

    private val onClickSubject = PublishSubject.create<Story>()

    val storyClickObservable: LiveData<Story>
        get() = LiveDataReactiveStreams.fromPublisher(onClickSubject.toFlowable(BackpressureStrategy.BUFFER))


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder =
        StoryViewHolder(ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        getItem(position).let { story ->
            holder.itemBinding.llContent.setOnClickListener {
                onClickSubject.onNext(story)
            }
            holder.bind(story)
        }
    }
}

class StoryViewHolder(val itemBinding: ItemStoryBinding) : RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(item: Story?) {
        itemBinding.story = item
        itemBinding.executePendingBindings()
    }
}

class StoryDiffCallback : DiffUtil.ItemCallback<Story>() {
    override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean = oldItem.title == newItem.title

    override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean = oldItem == newItem
}