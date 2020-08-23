package com.jet2traveltech.article.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article_table")
class Article {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @ColumnInfo(name = "post_id")
    var post_id: String? = null

    @ColumnInfo(name = "post_createdAt")
    var post_createdAt: String? = null

    @ColumnInfo(name = "post_content")
    var post_content: String? = null

    @ColumnInfo(name = "post_comments")
    var post_comments: Long? = null

    @ColumnInfo(name = "post_likes")
    var post_likes: Long? = null


    @ColumnInfo(name = "media_id")
    var media_id: String? = null

    @ColumnInfo(name = "media_blogId")
    var media_blogId: String? = null

    @ColumnInfo(name = "media_createdAt")
    var media_createdAt: String? = null

    @ColumnInfo(name = "media_image")
    var media_image: String? = null

    @ColumnInfo(name = "media_title")
    var media_title: String? = null

    @ColumnInfo(name = "media_url")
    var media_url: String? = null


    @ColumnInfo(name = "user_id")
    var user_id: String? = null

    @ColumnInfo(name = "user_blogId")
    var user_blogId: String? = null

    @ColumnInfo(name = "user_createdAt")
    var user_createdAt: String? = null

    @ColumnInfo(name = "user_name")
    var user_name: String? = null

    @ColumnInfo(name = "user_avatar")
    var user_avatar: String? = null

    @ColumnInfo(name = "user_lastname")
    var user_lastname: String? = null

    @ColumnInfo(name = "user_city")
    var user_city: String? = null

    @ColumnInfo(name = "user_designation")
    var user_designation: String? = null

    @ColumnInfo(name = "user_about")
    var user_about: String? = null

}