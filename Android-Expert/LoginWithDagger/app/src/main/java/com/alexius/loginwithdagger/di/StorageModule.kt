package com.alexius.loginwithdagger.di

import android.content.Context
import com.alexius.loginwithdagger.SessionManager
import dagger.Module
import dagger.Provides

@Module
class StorageModule {
    @Provides
    fun provideSessionManager(context: Context): SessionManager = SessionManager(context)
}