package com.jet2traveltech.article.web.article

import com.jet2traveltech.article.web.article.model.ArticleResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleWebService {

    @GET("blogs")
    suspend fun getArticles(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<ArticleResponseModel>

}