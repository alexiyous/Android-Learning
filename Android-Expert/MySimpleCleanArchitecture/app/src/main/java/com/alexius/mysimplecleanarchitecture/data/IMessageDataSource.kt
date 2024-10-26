package com.alexius.mysimplecleanarchitecture.data

import com.alexius.mysimplecleanarchitecture.domain.MessageEntity

interface IMessageDataSource {
    fun getMessageFromSource(name: String): MessageEntity
}