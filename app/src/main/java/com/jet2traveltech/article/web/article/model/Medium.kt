package com.jet2traveltech.article.web.article.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Medium {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("blogId")
    @Expose
    var blogId: String? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: String? = null

    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

}