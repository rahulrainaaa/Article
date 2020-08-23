package com.jet2traveltech.article.ui.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jet2traveltech.article.model.Article
import com.jet2traveltech.article.roomdb.database.ArticleDatabase
import com.jet2traveltech.article.roomdb.repo.ArticleRepository
import com.jet2traveltech.article.web.article.ArticleWebRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel for ArticleFragment class to hold the handle the Data.
 * Call initialize for the first time to initialize the required repositories.
 * 1. Fetches all Article(s) from DB Repository in LiveData to publish over UI.
 * 2. Fetched Article(s) from Web and caches them into DB for UI.
 * 3. allArticles will get updated everytime when changes happen in DB.
 */
class ArticleViewModel : ViewModel() {


    private lateinit var articleRepository: ArticleRepository       // Repository to fetch Articles from DB.
    lateinit var articleWebRepository: ArticleWebRepository         // Repository to fetch Articles from Web.
    lateinit var allArticles: LiveData<List<Article>>               // LiveData hold all articles from web.
    var currentRecyclerPosition = 0                                 // Object to save RecyclerView state during orientation change.
    private var flagInitialized = false                             // Flag to indicate the object initialized or not.

    /**
     * Method to be called only once in ViewModel lifecycle to avoid multiple instance creation of used repositories.
     * Includes initializations of required object(s) which required applicationContext reference.
     *
     * @param application Application object reference.
     * @return ArticleViewModel this ViewModel object reference.
     */
    fun initialize(application: Application): ArticleViewModel {

        // Check if already initialized then return.
        if (flagInitialized) return this
        // Create ArticleRepository to fetch Article(s) from DB.
        val articleDao = ArticleDatabase.getDatabase(application).articleDao()
        articleRepository = ArticleRepository(articleDao)
        // Create ArticleWebRepository to fetch Article from REST API.
        articleWebRepository = ArticleWebRepository()
        allArticles = articleRepository.allArticles
        flagInitialized = true
        return this
    }

    /**
     * Method to fetch Article(s) from ArticleWebRepository with given page and limit.
     * Using ViewModelScope to call web API on IO thread.
     *
     * @param page denotes the page number.
     * @param limit denotes the limit of data in given page.
     */
    fun fetchArticles(page: Int, limit: Int) = viewModelScope.launch(Dispatchers.IO) {

        // If First page then remove all cached Article(s) from DB.
        if (page == 1) articleRepository.deleteAll()
        // Fetch Articles from web repository and insert in DB.
        val articles = articleWebRepository.getArticles(page, limit)
        articleRepository.insert(articles)
    }

    /**
     * Getter method to check EOF of records in Article Web Repository.
     */
    fun isArticleReachedEof() = articleWebRepository.articleEOF.value
}