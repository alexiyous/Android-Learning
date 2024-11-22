package com.alexius.newsery2.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.alexius.newsery2.domain.model.Source

@ProvidedTypeConverter
class NewsTypeConverter {

    @TypeConverter
    fun sourceToString(source: Source): String {
        return "${source.id},${source.name}"
    }

    @TypeConverter
    fun stringToSource(source: String): Source {
        val (id, name) = source.split(",")
        return Source(id, name)
    }
}