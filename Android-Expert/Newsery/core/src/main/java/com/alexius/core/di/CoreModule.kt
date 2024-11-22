package com.alexius.core.di

import android.app.Application
import androidx.room.Room
import com.alexius.core.data.local.NewsDao
import com.alexius.core.data.local.NewsDatabase
import com.alexius.core.data.local.NewsTypeConverter
import com.alexius.core.data.manager.LocalUserManagerImplementation
import com.alexius.core.data.remote.NewsApi
import com.alexius.core.data.repository.NewsRepositoryImplementation
import com.alexius.core.domain.manager.LocalUserManager
import com.alexius.core.domain.repository.NewsRepository
import com.alexius.core.domain.usecases.appentry.AppEntryUseCases
import com.alexius.core.domain.usecases.appentry.ReadAppEntry
import com.alexius.core.domain.usecases.appentry.SaveAppEntry
import com.alexius.core.domain.usecases.news.DeleteArticle
import com.alexius.core.domain.usecases.news.GetNews
import com.alexius.core.domain.usecases.news.NewsUseCases
import com.alexius.core.domain.usecases.news.SearchNews
import com.alexius.core.domain.usecases.news.SelectArticle
import com.alexius.core.domain.usecases.news.SelectArticles
import com.alexius.core.domain.usecases.news.UpsertArticle
import com.alexius.core.util.Constants.BASE_URL
import com.alexius.core.util.Constants.NEWS_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application
    ): LocalUserManager = LocalUserManagerImplementation(application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(localUserManager: LocalUserManager) = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager)
    )

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsApi: NewsApi,
        newsDao: NewsDao
    ): NewsRepository = NewsRepositoryImplementation(newsApi, newsDao)

    @Provides
    @Singleton
    fun provideNewsUseCases(newsRepository: NewsRepository, newsDao: NewsDao): NewsUseCases {
        return NewsUseCases(
            getNews = GetNews(newsRepository),
            searchNews = SearchNews(newsRepository),
            upsertArticle = UpsertArticle(newsRepository),
            deleteArticle = DeleteArticle(newsRepository),
            selectArticles = SelectArticles(newsRepository),
            selectArticle = SelectArticle(newsRepository)
        )
    }

    @Provides
    @Singleton
    fun proviodeNewsDatabase(
        application: Application
    ): NewsDatabase{
        return Room.databaseBuilder(
            context = application,
            klass = NewsDatabase::class.java,
            name = NEWS_DATABASE_NAME
        ).addTypeConverter(NewsTypeConverter())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(newsDatabase: NewsDatabase): NewsDao = newsDatabase.newsDao
}