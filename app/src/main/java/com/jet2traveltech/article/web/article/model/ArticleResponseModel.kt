package com.jet2traveltech.article.web.article.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ArticleResponseModel {

    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: String? = null

    @SerializedName("content")
    @Expose
    var content: String? = null

    @SerializedName("comments")
    @Expose
    var comments: Long? = null

    @SerializedName("likes")
    @Expose
    var likes: Long? = null

    @SerializedName("media")
    @Expose
    var media: List<Medium>? = null

    @SerializedName("user")
    @Expose
    var user: List<User>? = null

}