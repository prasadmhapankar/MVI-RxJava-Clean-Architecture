package com.prasad.nytimes_mvi.utils

import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prasad.nytimes_mvi.R
import com.prasad.nytimes_mvi.model.Multimedia

@BindingAdapter("show")
fun View.setShow(show: Boolean) {
    if (parent is ViewGroup)
        TransitionManager.beginDelayedTransition(parent as ViewGroup)
    visibility = if (show) View.VISIBLE else View.GONE
}

@BindingAdapter("invisible")
fun View.setInvisible(invisible: Boolean) {
    if (parent is ViewGroup)
        TransitionManager.beginDelayedTransition(parent as ViewGroup)
    visibility = if (invisible) View.INVISIBLE else View.VISIBLE
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("list")
fun <E> RecyclerView.setList(list: List<E>?) {
    println("list adapter ==>> $list")
    list?.let {
        (adapter as ListAdapter<E, *>?)?.submitList(it)
    }
}

@BindingAdapter("svg")
fun ImageView.setSvgResource(list: List<Multimedia>) {
    list.let {
        it.forEach {
            if (it.format == "superJumbo"){
                it.url.let {
                    GlideApp.with(context).load(it)
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .into(this)
                }
            }
        }
    }
}

@BindingAdapter("src")
fun ImageView.setImageRes(@DrawableRes drawableResource: Int) {
    setImageResource(drawableResource)
}