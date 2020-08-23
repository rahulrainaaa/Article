package com.jet2traveltech.article.web.client

import com.jet2traveltech.article.web.article.ArticleWebService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientBuilder {

    private const val BASE_URL = "https://5e99a9b1bc561b0016af3540.mockapi.io/jet2/api/v1/"

    private fun getRetrofitClient() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val articleWebService: ArticleWebService =
        getRetrofitClient().create(ArticleWebService::class.java)
}