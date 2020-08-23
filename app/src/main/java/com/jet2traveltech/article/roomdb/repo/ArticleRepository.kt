package com.jet2traveltech.article.roomdb.repo

import androidx.lifecycle.LiveData
import com.jet2traveltech.article.model.Article
import com.jet2traveltech.article.roomdb.dao.ArticleDao

class ArticleRepository(private val articleDao: ArticleDao) {

    val allArticles: LiveData<List<Article>> = articleDao.getAlphabetizedWords()

    suspend fun insert(articles: List<Article>) = articleDao.insert(articles)

    suspend fun deleteAll() = articleDao.deleteAll()

}