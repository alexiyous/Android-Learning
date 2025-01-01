package com.alexius.core.domain.usecases.huggingface

import com.alexius.core.data.remote.huggingface.HuggingFaceAPI
import com.alexius.core.data.remote.huggingface.request.TextToSpeechRequest
import okhttp3.ResponseBody

class GenerateSound(private val huggingFaceAPI: HuggingFaceAPI) {

    suspend operator fun invoke(id: String, apiKey: String, request: TextToSpeechRequest): ResponseBody {
        return huggingFaceAPI.generateSpeech(id, apiKey, request)
    }
}