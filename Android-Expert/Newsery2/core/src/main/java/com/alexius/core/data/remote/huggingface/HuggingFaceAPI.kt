package com.alexius.core.data.remote.huggingface

import com.alexius.core.data.remote.huggingface.request.BarkRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface HuggingFaceAPI {
    @POST("models/suno/bark")
    suspend fun generateSpeech(
        @Header("Authorization") token: String,
        @Body request: BarkRequest
    ): Response<ByteArray>
}
