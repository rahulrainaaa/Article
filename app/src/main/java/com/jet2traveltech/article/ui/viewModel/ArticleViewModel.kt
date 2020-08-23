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

class ArticleViewModel(application: Application) : AndroidViewModel(application) {

    private val articleRepository: ArticleRepository
     val articleWebRepository: ArticleWebRepository
    val allArticles: LiveData<List<Article>>

    init {
        val wordsDao = ArticleDatabase.getDatabase(application).articleDao()
        articleRepository = ArticleRepository(wordsDao)
        articleWebRepository = ArticleWebRepository()
        allArticles = articleRepository.allArticles
    }

    fun fetchArticles(page: Int, limit: Int) = viewModelScope.launch(Dispatchers.IO) {
        if (page <= 1) articleRepository.deleteAll()
        val articles = articleWebRepository.getArticles(page, limit)
        articleRepository.insert(articles)
    }

    fun isArticleReachedEof() = articleWebRepository.articleEOF.value

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) { articleRepository.deleteAll() }

}