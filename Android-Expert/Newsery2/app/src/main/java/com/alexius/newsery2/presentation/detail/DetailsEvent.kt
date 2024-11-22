package com.alexius.newsery2.presentation.detail

import com.alexius.newsery2.domain.model.Article

sealed class DetailsEvent() {

    data class UpsertDeleteArticle(val article: Article): DetailsEvent()

    object RemoveSideEffect: DetailsEvent()

    data class IsArticleInDatabase(val article: Article): DetailsEvent()
}