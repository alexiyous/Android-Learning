package com.alexius.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @field:SerializedName("articles")
    val articles: List<Article>,
    @field:SerializedName("status")
    val status: String,
    @field:SerializedName("totalResults")
    val totalResults: Int
)