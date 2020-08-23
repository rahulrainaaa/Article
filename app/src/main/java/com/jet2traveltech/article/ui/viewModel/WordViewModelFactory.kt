package com.jet2traveltech.article.ui.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WordViewModelFactory(private val context: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(viewModelClass: Class<T>): T {

        if (viewModelClass.isAssignableFrom(WordViewModel::class.java)) {
            return WordViewModel(context) as T
        } else throw IllegalArgumentException("Unknown ViewModel class")
    }

}