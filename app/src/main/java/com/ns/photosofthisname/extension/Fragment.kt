package com.ns.photosofthisname.extension

import androidx.fragment.app.Fragment


fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}