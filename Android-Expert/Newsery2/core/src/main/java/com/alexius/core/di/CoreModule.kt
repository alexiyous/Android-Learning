package com.alexius.core.di

import android.app.Application
import androidx.room.Room
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
import com.alexius.core.data.local.NewsDao
import com.alexius.core.data.local.NewsDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.dsl.single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val databaseModule = module{
    single<LocalUserManager> {
        LocalUserManagerImplementation(androidContext())
    }

    factory {
        get<NewsDatabase>().newsDao
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            NewsDatabase::class.java,
            NEWS_DATABASE_NAME
        ).addTypeConverter(NewsTypeConverter())
        .fallbackToDestructiveMigration()
        .build()
    }
}

val networkModule = module{
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }
}

val repositoryModule = module{
    single<NewsRepository> {
        NewsRepositoryImplementation(
            get(),
            get()
        )
    }
}

val useCaseModule = module{
    single {
        AppEntryUseCases(
            ReadAppEntry(get()),
            SaveAppEntry(get())
        )
    }

    single {
        NewsUseCases(
            GetNews(get()),
            SearchNews(get()),
            UpsertArticle(get()),
            DeleteArticle(get()),
            SelectArticles(get()),
            SelectArticle(get())
        )
    }
}






