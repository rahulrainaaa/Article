package com.jet2traveltech.article.web.article

import com.jet2traveltech.article.web.article.model.ArticleResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Web Service class to fetch required page of article(s) from web using retrofit.
 */
interface ArticleWebService {

    @GET("blogs")
    suspend fun getArticles(@Query("page") page: Int, @Query("limit") limit: Int): List<ArticleResponseModel>

}