package com.alexius.bookmark

import com.alexius.bookmark.bookmark.BookmarkViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val bookmarkModule = module {
    viewModel { BookmarkViewModel(get()) }
}