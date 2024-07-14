package com.example.github.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github.data.response.ItemsItem
import com.example.github.data.response.SearchUserResponse
import com.example.github.data.retrofit.ApiConfig
import com.example.github.util.Event
import retrofit2.Call
import retrofit2.Response

class MainViewModel : ViewModel(){

    private val _usernameList = MutableLiveData<List<ItemsItem>>()
    val usernameList: LiveData<List<ItemsItem>> = _usernameList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    companion object {
        private const val TAG = "MainViewModel"
    }

    init {
        searchUsers(MainActivity.INITIAL_USER)
    }

    fun searchUsers(usernameQuery: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchUsers(usernameQuery)
        client.enqueue(object : retrofit2.Callback<SearchUserResponse> {
            override fun onResponse(
                call: Call<SearchUserResponse>,
                response: Response<SearchUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _usernameList.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}