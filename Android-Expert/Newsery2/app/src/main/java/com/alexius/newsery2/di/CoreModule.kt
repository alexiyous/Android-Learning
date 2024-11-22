package com.alexius.newsery2.di

import android.app.Application
import androidx.room.Room
import com.alexius.newsery.MainViewModel
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
import com.alexius.newsery2.presentation.detail.DetailsViewModel
import com.alexius.newsery2.presentation.home.HomeViewModel
import com.alexius.newsery2.presentation.onboarding.OnBoardingViewModel
import com.alexius.newsery2.presentation.search.SearchNewsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.dsl.single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module{
    viewModel{ DetailsViewModel(get()) }
    viewModel{ HomeViewModel(get()) }
    viewModel{ OnBoardingViewModel(get()) }
    viewModel{ SearchNewsViewModel(get()) }
    viewModel{ MainViewModel(get()) }
}

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






