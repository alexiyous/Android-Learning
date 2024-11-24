package com.alexius.core.data.local.entity

import androidx.room.ColumnInfo


data class SourceEntity(
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "name")
    val name: String
)
