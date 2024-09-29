package com.alexius.storyvibe.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexius.storyvibe.data.Repository
import com.alexius.storyvibe.data.local.LoginDatastore
import com.alexius.storyvibe.di.Injection
import com.alexius.storyvibe.view.login.LoginViewModel
import com.alexius.storyvibe.view.signup.SignUpViewModel

class ViewModelFactory private constructor(private val repository: Repository, private val loginDatastore: LoginDatastore) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository, loginDatastore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(), Injection.provideDataStore(context))
            }.also { instance = it }
    }
}