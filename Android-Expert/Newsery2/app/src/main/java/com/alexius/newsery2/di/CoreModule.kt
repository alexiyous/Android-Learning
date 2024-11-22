package com.alexius.newsery2.di

import android.app.Application
import androidx.room.Room
import com.alexius.newsery2.data.local.NewsTypeConverter
import com.alexius.newsery2.data.manager.LocalUserManagerImplementation
import com.alexius.newsery2.data.remote.NewsApi
import com.alexius.newsery2.data.repository.NewsRepositoryImplementation
import com.alexius.newsery2.domain.manager.LocalUserManager
import com.alexius.newsery2.domain.repository.NewsRepository
import com.alexius.newsery2.domain.usecases.appentry.AppEntryUseCases
import com.alexius.newsery2.domain.usecases.appentry.ReadAppEntry
import com.alexius.newsery2.domain.usecases.appentry.SaveAppEntry
import com.alexius.newsery2.domain.usecases.news.DeleteArticle
import com.alexius.newsery2.domain.usecases.news.GetNews
import com.alexius.newsery2.domain.usecases.news.NewsUseCases
import com.alexius.newsery2.domain.usecases.news.SearchNews
import com.alexius.newsery2.domain.usecases.news.SelectArticle
import com.alexius.newsery2.domain.usecases.news.SelectArticles
import com.alexius.newsery2.domain.usecases.news.UpsertArticle
import com.alexius.newsery2.util.Constants.BASE_URL
import com.alexius.newsery2.util.Constants.NEWS_DATABASE_NAME
import com.alexius.newsery2.data.local.NewsDao
import com.alexius.newsery2.data.local.NewsDatabase
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