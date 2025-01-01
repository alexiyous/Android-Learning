package com.alexius.newsery2.presentation.huggingface

import android.media.MediaPlayer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexius.newsery2.presentation.huggingface.BarkViewModel.UIState
import org.koin.androidx.compose.koinViewModel
import java.io.File

@Composable
fun ElevenLabsScreen(
    viewModel: BarkViewModel = koinViewModel()
) {
    var text by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.release()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Enter text to convert to speech") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            textStyle = TextStyle(fontSize = 16.sp)
        )

        Button(
            onClick = { viewModel.generateSpeech(text) },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            enabled = text.isNotBlank() && uiState !is UIState.Loading
        ) {
            Text("Generate Speech")
        }

        when (val state = uiState) {
            is UIState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            is UIState.Success -> {
                LaunchedEffect(state) {
                    mediaPlayer?.release()
                    mediaPlayer = MediaPlayer().apply {
                        val file = File.createTempFile("audio", ".mp3", context.cacheDir)
                        file.writeBytes(state.audioData)
                        setDataSource(file.path)
                        prepare()
                        start()
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = { mediaPlayer?.start() }
                    ) {
                        Icon(Icons.Default.PlayArrow, "Play")
                    }
                    IconButton(
                        onClick = { mediaPlayer?.pause() }
                    ) {
                        Icon(Icons.Default.Pause, "Pause")
                    }
                    IconButton(
                        onClick = {
                            mediaPlayer?.stop()
                            mediaPlayer?.prepare()
                        }
                    ) {
                        Icon(Icons.Default.Stop, "Stop")
                    }
                }
            }
            is UIState.Error -> {
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            else -> Unit
        }
    }
}
