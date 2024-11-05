package com.alexius.loginwithdagger.di

import android.content.Context
import com.alexius.loginwithdagger.HomeActivity
import com.alexius.loginwithdagger.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [StorageModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(activity: HomeActivity)
}