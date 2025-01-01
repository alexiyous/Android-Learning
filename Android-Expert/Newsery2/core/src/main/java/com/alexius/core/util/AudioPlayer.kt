package com.alexius.core.util

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import java.io.File

class AudioPlayer(private val context: Context) {
    private var mediaPlayer: MediaPlayer? = null

    fun playAudio(audioData: ByteArray) {
        try {
            val file = File.createTempFile("audio", ".wav", context.cacheDir)
            file.writeBytes(audioData)

            mediaPlayer?.release()
            mediaPlayer = MediaPlayer().apply {
                setDataSource(file.path)
                prepare()
                start()
            }
        } catch (e: Exception) {
            Log.e("AudioPlayer", "Error playing audio: ${e.message}")
        }
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}