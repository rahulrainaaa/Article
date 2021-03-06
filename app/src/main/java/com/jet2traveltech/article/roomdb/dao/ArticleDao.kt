package com.jet2traveltech.article.roomdb.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jet2traveltech.article.model.Article

/**
 * DAO Module to fetch and other required DB operation(s) on Article(s).
 */
@Dao
interface ArticleDao {

    @Query("SELECT * from article_table")
    fun getArticles(): LiveData<List<Article>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(articles: List<Article>)

    @Query("DELETE FROM article_table")
    suspend fun deleteAll()
}