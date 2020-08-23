package com.jet2traveltech.article.roomdb.repo

import androidx.lifecycle.LiveData
import com.jet2traveltech.article.model.Article
import com.jet2traveltech.article.roomdb.dao.ArticleDao

/**
 * Repository class to fetch, insert and delete Article(s) in DB.
 */
class ArticleRepository(private val articleDao: ArticleDao) {

    /**
     * LiveData fetched from the articleDao from RoomDB.
     */
    val allArticles: LiveData<List<Article>> = articleDao.getArticles()

    /**
     * Suspended method to insert list of Article(s) in to RoomDB.
     *
     * @param articles List<Article> to be inserted into DB.
     */
    suspend fun insert(articles: List<Article>) = articleDao.insert(articles)

    /**
     * Suspended method to delete all article records from DB.
     */
    suspend fun deleteAll() = articleDao.deleteAll()

}