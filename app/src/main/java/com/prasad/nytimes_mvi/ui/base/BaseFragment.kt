package com.prasad.nytimes_mvi.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.prasad.nytimes_mvi.mvibase.MviIntent
import com.prasad.nytimes_mvi.mvibase.MviView
import com.prasad.nytimes_mvi.mvibase.MviViewState

abstract class BaseFragment<VB : ViewDataBinding, I : MviIntent, S : MviViewState>(
    @LayoutRes val layoutRes: Int
) : Fragment(), MviView<I, S> {

    lateinit var binding: VB

    abstract fun initViews()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate<VB>(layoutInflater, layoutRes, view?.rootView as ViewGroup?, false)
        binding.lifecycleOwner = this

        initViews()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onMviViewCreated(savedInstanceState)
       // startStream()
    }

    protected fun toast(message: String) =
        activity?.let {
            Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
        }
}