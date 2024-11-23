package com.alexius.newsery2

import android.app.Application
import com.alexius.core.di.databaseModule
import com.alexius.core.di.networkModule
import com.alexius.core.di.repositoryModule
import com.alexius.core.di.useCaseModule
import com.alexius.newsery2.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NewsApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@NewsApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule,
                )
            )
        }
    }
}