package com.alexius.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alexius.core.data.local.entity.ArticleEntity
import com.alexius.core.util.NewsTypeConverter

@Database(entities = [ArticleEntity::class], version = 4)
@TypeConverters(NewsTypeConverter::class)
abstract class NewsDatabase: RoomDatabase() {

    abstract val newsDao: NewsDao
}