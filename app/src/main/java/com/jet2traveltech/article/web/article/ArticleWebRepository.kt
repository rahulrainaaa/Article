package com.jet2traveltech.article.web.article

import com.jet2traveltech.article.model.Article
import com.jet2traveltech.article.web.client.RetrofitClientBuilder

class ArticleWebRepository {

    suspend fun getArticles(page: Int, limit: Int): List<Article> {
        val response = RetrofitClientBuilder.articleWebService.getArticles(page, limit)

        val articleList = ArrayList<Article>()

        for (model in response) {

            val article = Article()
            article.post_id = model.id
            article.post_createdAt = model.id
            article.post_content = model.content
            article.post_comments = model.comments
            article.post_likes = model.likes

            if (model?.media?.size ?: 0 > 0) {

                val media = model.media?.get(0)
                article.media_id = media?.id
                article.media_blogId = media?.blogId
                article.media_createdAt = media?.createdAt
                article.media_image = media?.image
                article.media_title = media?.title
                article.media_url = media?.url
            }

            if (model?.user?.size ?: 0 > 0) {
                val user = model?.user?.get(0)
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

        return articleList

    }
}