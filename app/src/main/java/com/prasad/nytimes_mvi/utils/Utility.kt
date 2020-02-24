package com.prasad.nytimes_mvi.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun Fragment.navigate(actionId: Int, bundle: Bundle) {
    findNavController().navigate(actionId, bundle)
}