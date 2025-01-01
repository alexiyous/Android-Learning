package com.alexius.core.data.remote.huggingface

import com.alexius.core.data.remote.huggingface.request.TextToSpeechRequest
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface HuggingFaceAPI {
    @POST("text-to-speech/{voice_id}")
    suspend fun generateSpeech(
        @Path("voice_id") voiceId: String,
        @Header("xi-api-key") apiKey: String,
        @Body request: TextToSpeechRequest
    ): ResponseBody
}
