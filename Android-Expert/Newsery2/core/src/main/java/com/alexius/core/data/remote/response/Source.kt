package com.alexius.core.data.remote.response

import com.google.gson.annotations.SerializedName


data class Source(
    @field:SerializedName("id")
    val id: String,
    @field:SerializedName("name")
    val name: String
)