package com.alexius.newsery2.presentation.detail

import com.alexius.core.domain.model.ArticleModel

sealed class DetailsEvent() {

    data class UpsertDeleteArticle(val article: ArticleModel): DetailsEvent()

    object RemoveSideEffect: DetailsEvent()

    data class IsArticleInDatabase(val article: ArticleModel): DetailsEvent()
}