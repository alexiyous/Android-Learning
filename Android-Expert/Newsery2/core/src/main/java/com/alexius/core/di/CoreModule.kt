package com.alexius.core.di

import androidx.room.Room
import com.alexius.core.BuildConfig
import com.alexius.core.data.local.NewsDatabase
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
import com.alexius.core.util.NewsTypeConverter
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module{
    single<LocalUserManager> {
        LocalUserManagerImplementation(androidContext())
    }

    factory {
        get<NewsDatabase>().newsDao
    }

    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("newsery".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            NewsDatabase::class.java,
            NEWS_DATABASE_NAME
        ).addTypeConverter(NewsTypeConverter())
        .fallbackToDestructiveMigration()
        .openHelperFactory(factory)
        .build()
    }
}

val networkModule = module{
    single {
        val hostname = "newsapi.org"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/Q/Dhu9FBOaTRCRL38yOpIxCg3wvkc5pnQhC7NVqnZoo=")
            .add(hostname, "sha256/kIdp6NNEd8wsugYyyIYFsi1ylMCED3hZbSR8ZFsa/A4=")
            .add(hostname, "sha256/mEflZT5enoR1FuXLgYYGqnVEoZvmf9c2bVBpiOjYQ0c=")
            .build()

        val okHttpClientBuilder = OkHttpClient.Builder()
            .certificatePinner(certificatePinner)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            okHttpClientBuilder.addInterceptor(logging)
        }

        val okHttpClient = okHttpClientBuilder.build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
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






