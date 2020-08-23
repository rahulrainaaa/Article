package com.jet2traveltech.article.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.jet2traveltech.article.model.Article
import com.jet2traveltech.article.roomdb.database.ArticleDatabase
import com.jet2traveltech.article.roomdb.repo.ArticleRepository
import com.jet2traveltech.article.web.article.ArticleWebRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ArticleRepository

    val allArticles: LiveData<List<Article>>

    init {
        val wordsDao = ArticleDatabase.getDatabase(application).articleDao()
        repository = ArticleRepository(wordsDao)
        allArticles = repository.allWords
    }

    fun fetchArticles(page: Int, limit: Int) = viewModelScope.launch(Dispatchers.IO) {
        if (page == 0) repository.deleteAll()
        val articles = ArticleWebRepository().getArticles(1, 300)
        repository.insert(articles)
    }

}