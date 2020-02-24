package com.prasad.nytimes_mvi.ui.business

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BusinessViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is business Fragment"
    }
    val text: LiveData<String> = _text
}