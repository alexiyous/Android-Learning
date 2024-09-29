package com.alexius.storyvibe.view.login

import androidx.lifecycle.ViewModel
import com.alexius.storyvibe.data.Repository
import com.alexius.storyvibe.data.local.LoginDatastore

class LoginViewModel(private val repository: Repository, private val loginDatastore: LoginDatastore) : ViewModel() {
    fun login(email: String, password: String) = repository.login(email, password, loginDatastore)

}