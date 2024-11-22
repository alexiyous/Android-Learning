package com.alexius.newsery2.domain.usecases.appentry

import com.alexius.newsery2.domain.manager.LocalUserManager

class SaveAppEntry(
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke() {
        localUserManager.saveAppEntry()
    }
}