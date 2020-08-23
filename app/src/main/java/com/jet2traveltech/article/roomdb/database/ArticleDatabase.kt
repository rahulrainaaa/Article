package com.jet2traveltech.article.roomdb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jet2traveltech.article.model.Article
import com.jet2traveltech.article.roomdb.dao.ArticleDao

/**
 * RoomDB class to create and manage the single DB object for entire application lifecycle.
 */
@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class ArticleDatabase : RoomDatabase() {

    /**
     * Method to fetch ArticleDao object to access Article(s) from DB.
     */
    abstract fun articleDao(): ArticleDao

    companion object {

        /**
         * Volatile companion object to save single instance of DB state for re-usability.
         */
        @Volatile
        private var INSTANCE: ArticleDatabase? = null

        /**
         * Companion Method to create DB object for first time.
         * Then return the same instance when called upon within that application lifespan.
         *
         * @param context Context object to create DB object.
         * @return ArticleDatabase DB object to access DAO service(s).
         */
        fun getDatabase(context: Context): ArticleDatabase {
            val tempInstance = INSTANCE

            // Return DB object is already was created.
            if (tempInstance != null) {
                return tempInstance
            }

            // Synchronize block for thread safe DB object creation.
            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, ArticleDatabase::class.java, "article_db").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}