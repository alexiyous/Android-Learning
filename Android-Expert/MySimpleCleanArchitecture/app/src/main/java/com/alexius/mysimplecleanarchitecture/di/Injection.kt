package com.alexius.mysimplecleanarchitecture.di

import com.alexius.mysimplecleanarchitecture.data.IMessageDataSource
import com.alexius.mysimplecleanarchitecture.data.MessageDataSource
import com.alexius.mysimplecleanarchitecture.data.MessageRepository
import com.alexius.mysimplecleanarchitecture.domain.IMessageRepository
import com.alexius.mysimplecleanarchitecture.domain.MessageInteractor
import com.alexius.mysimplecleanarchitecture.domain.MessageUseCase

object Injection {
    fun provideUseCase(): MessageUseCase {
        val messageRepository = provideRepository()
        return MessageInteractor(messageRepository)
    }

    private fun provideRepository(): IMessageRepository {
        val messageDataSource = provideDataSource()
        return MessageRepository(messageDataSource)
    }

    private fun provideDataSource(): IMessageDataSource {
        return MessageDataSource()
    }
}