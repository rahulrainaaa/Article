package com.jet2traveltech.article.web.article.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class User {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("blogId")
    @Expose
    var blogId: String? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("avatar")
    @Expose
    var avatar: String? = null

    @SerializedName("lastname")
    @Expose
    var lastname: String? = null

    @SerializedName("city")
    @Expose
    var city: String? = null

    @SerializedName("designation")
    @Expose
    var designation: String? = null

    @SerializedName("about")
    @Expose
    var about: String? = null

}