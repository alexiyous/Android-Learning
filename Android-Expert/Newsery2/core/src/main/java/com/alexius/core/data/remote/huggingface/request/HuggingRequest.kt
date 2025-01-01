package com.alexius.core.data.remote.huggingface.request

data class TextToSpeechRequest(
    val text: String,
    val model_id: String = "eleven_multilingual_v2",
    val voice_settings: VoiceSettings = VoiceSettings()
)

data class VoiceSettings(
    val stability: Float = 0.5f,
    val similarity_boost: Float = 0.5f
)