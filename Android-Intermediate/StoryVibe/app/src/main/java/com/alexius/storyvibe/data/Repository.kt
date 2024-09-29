package com.alexius.storyvibe.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import com.alexius.storyvibe.data.local.LoginDatastore
import com.alexius.storyvibe.data.remote.response.ErrorResponse
import com.alexius.storyvibe.data.remote.response.LoginResponse
import com.alexius.storyvibe.data.remote.response.RegisterResponse
import com.alexius.storyvibe.data.remote.retrofit.ApiService
import com.alexius.storyvibe.di.Injection
import com.google.gson.Gson
import retrofit2.HttpException

class Repository private constructor(
    private val apiService: ApiService
) {

    fun registerUser(name: String, email: String, password: String): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(name, email, password)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val errorResponse = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(errorResponse, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            Log.d("Repository", "RegisterUser: $errorMessage")
            emit(Result.Error(errorMessage?:"Error"))
        }
    }

    fun login(email: String, password: String, datastore: LoginDatastore): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(email, password)
            val token = response.loginResult?.token
            datastore.saveLoginToken(token?: "")
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val errorResponse = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(errorResponse, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            Log.d("Repository", "Login: ${e.response()}")
            emit(Result.Error(errorMessage?:"Error"))
        }
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService)
            }.also { instance = it }
    }
}