package com.prasad.nytimes_mvi.ui.storyDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.prasad.nytimes_mvi.R
import com.prasad.nytimes_mvi.utils.GlideApp
import kotlinx.android.synthetic.main.fragment_story_detail.*

class StoryDetailFragment :  Fragment() {

    lateinit var title: String
    lateinit var image: String
    lateinit var author: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = arguments?.getString("title").toString()
        image = arguments?.getString("image").toString()
        author = arguments?.getString("author").toString()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_story_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_title.text = title
        tv_author.text = author
        GlideApp.with(view.context).load(image)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(iv_story)
    }

}