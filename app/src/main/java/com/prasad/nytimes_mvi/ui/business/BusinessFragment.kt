package com.prasad.nytimes_mvi.ui.business

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.prasad.nytimes_mvi.R

class BusinessFragment : Fragment() {

    private lateinit var businessViewModel: BusinessViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        businessViewModel =
            ViewModelProviders.of(this).get(BusinessViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_business, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        businessViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}