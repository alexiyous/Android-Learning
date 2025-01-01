package com.alexius.newsery2.presentation.huggingface

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexius.core.data.remote.huggingface.HuggingFaceAPI
import com.alexius.core.data.remote.huggingface.request.BarkRequest
import com.alexius.core.util.Constants.HUGGINGFACE_API_KEY
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BarkViewModel(private val huggingApi: HuggingFaceAPI): ViewModel() {
    private val _uiState = MutableStateFlow<UIState>(UIState.Idle)
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    fun generateSpeech(text: String) {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            try {
                val response = huggingApi.generateSpeech(
                    "Bearer $HUGGINGFACE_API_KEY",
                    BarkRequest(inputs = text)
                )

                if (response.isSuccessful) {
                    response.body()?.let { audioData ->
                        _uiState.value = UIState.Success(audioData)
                    } ?: run {
                        _uiState.value = UIState.Error("Empty response")
                    }
                } else {
                    _uiState.value = UIState.Error("API Error: ${response.code()}")
                }
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e.message ?: "Unknown error")
            }
        }
    }

    sealed class UIState {
        object Idle : UIState()
        object Loading : UIState()
        data class Success(val audioData: ByteArray) : UIState()
        data class Error(val message: String) : UIState()
    }
}