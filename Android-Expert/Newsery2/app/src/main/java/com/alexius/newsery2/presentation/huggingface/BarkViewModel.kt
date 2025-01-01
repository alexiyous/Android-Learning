package com.alexius.newsery2.presentation.huggingface

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexius.core.data.remote.huggingface.request.TextToSpeechRequest
import com.alexius.core.domain.usecases.huggingface.GenerateSound
import com.alexius.core.util.Constants.HUGGINGFACE_API_KEY
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BarkViewModel(private val huggingUsecase: GenerateSound): ViewModel() {
    private val _uiState = MutableStateFlow<UIState>(UIState.Idle)
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    fun generateSpeech(text: String) {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            try {
                val request = TextToSpeechRequest(
                    text = text,
                    model_id = "eleven_multilingual_v2"
                )

                val response = huggingUsecase(
                    id = "JBFqnCBsd6RMkjVDRZzb",
                    apiKey = HUGGINGFACE_API_KEY,
                    request = request
                )

                val audioData = response.bytes()
                _uiState.value = UIState.Success(audioData)
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