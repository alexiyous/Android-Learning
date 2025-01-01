package com.alexius.core.util

import android.net.Uri
import kotlin.math.min
import androidx.media3.common.C
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DataSpec
import androidx.media3.datasource.TransferListener

@UnstableApi
class ByteArrayDataSource(private val data: ByteArray) : DataSource {
    private var uri: Uri? = null
    private var opened = false
    private var bytesRemaining: Int = 0

    override fun addTransferListener(transferListener: TransferListener) {
        // Implementation not needed for this simple case
    }

    init {
        uri = Uri.parse("byte://array/${data.hashCode()}")
    }

    override fun open(dataSpec: DataSpec): Long {
        uri = dataSpec.uri
        opened = true
        bytesRemaining = data.size
        return data.size.toLong()
    }

    override fun read(buffer: ByteArray, offset: Int, length: Int): Int {
        if (bytesRemaining == 0) {
            return C.RESULT_END_OF_INPUT
        }

        val bytesToRead = min(length, bytesRemaining)
        System.arraycopy(data, data.size - bytesRemaining, buffer, offset, bytesToRead)
        bytesRemaining -= bytesToRead
        return bytesToRead
    }

    override fun getUri(): Uri? = uri

    override fun close() {
        opened = false
    }
}