package com.alexius.core.util

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.alexius.core.data.local.entity.SourceEntity

@ProvidedTypeConverter
class NewsTypeConverter {

    @TypeConverter
    fun sourceToString(source: SourceEntity): String {
        return "${source.id},${source.name}"
    }

    @TypeConverter
    fun stringToSource(source: String): SourceEntity {
        val (id, name) = source.split(",")
        return SourceEntity(id, name)
    }
}