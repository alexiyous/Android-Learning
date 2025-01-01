package com.alexius.core.data.remote.huggingface.request

data class BarkRequest(
    val inputs: String,
    val parameters: Parameters = Parameters()
)

data class Parameters(
    val voice_preset: String = "v2/en_speaker_6"
)