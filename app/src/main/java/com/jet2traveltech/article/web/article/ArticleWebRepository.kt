package com.jet2traveltech.article.web.article

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.jet2traveltech.article.model.Article
import com.jet2traveltech.article.web.client.RetrofitClientBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArticleWebRepository {

    private var fetchLock = MutableLiveData(false)
    val articleEOF = MutableLiveData(false)

    suspend fun getArticles(page: Int, limit: Int): List<Article> {

        if (page <= 1 && fetchLock.value == false)
            setArticleEOF(false)

        Log.d("bbbbbbbbbbbbbbbbbbb", "Hitting: Page = $page, Limit = $limit")
        Log.d("bbbbbbbbbbbbbbbbbbb", "Initially lock = ${fetchLock.value}, EOF = ${articleEOF.value}")

        if (fetchLock.value == true || articleEOF.value == true)
            return emptyList()

        setFetchLock(true)

        Log.d("bbbbbbbbbbbbbbbbbbb", "Fetching: Page = $page, Limit = $limit")
        val response = RetrofitClientBuilder.articleWebService.getArticles(page, limit)

        Log.d("bbbbbbbbbbbbbbbbbbb", Gson().toJson(response))
        Log.d("bbbbbbbbbbbbbbbbbbb", "Size = ${response.size}")
        val articleList = ArrayList<Article>()

        for (model in response) {

            val article = Article()
            article.post_id = model.id
            article.post_createdAt = model.id
            article.post_content = model.content
            article.post_comments = model.comments
            article.post_likes = model.likes

            if (model.media?.size ?: 0 > 0) {

                val media = model.media?.get(0)
                article.media_id = media?.id
                article.media_blogId = media?.blogId
                article.media_createdAt = media?.createdAt
                article.media_image = media?.image
                article.media_title = media?.title
                article.media_url = media?.url
            }

            if (model.user?.size ?: 0 > 0) {
                val user = model.user?.get(0)
                article.user_id = user?.id
                article.user_blogId = user?.blogId
                article.user_createdAt = user?.createdAt
                article.user_name = user?.name
                article.user_avatar = user?.avatar
                article.user_lastname = user?.lastname
                article.user_city = user?.city
                article.user_designation = user?.designation
                article.user_about = user?.about
            }

            articleList.add(article)
        }

        if (articleList.isEmpty())
            setArticleEOF(true)

        setFetchLock(false)
        return articleList
    }

    private suspend fun setArticleEOF(status: Boolean) = withContext(Dispatchers.Main) {
        articleEOF.value = status
    }

    private suspend fun setFetchLock(status: Boolean) = withContext(Dispatchers.Main) {
        fetchLock.value = status
    }
}