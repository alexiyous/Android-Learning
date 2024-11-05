package com.alexius.loginwithdagger

import android.app.Application
import com.alexius.loginwithdagger.di.AppComponent
import com.alexius.loginwithdagger.di.DaggerAppComponent

open class MyApplication : Application(){
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(
            applicationContext
        )
    }
}