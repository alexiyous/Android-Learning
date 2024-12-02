package com.alexius.core.util

import com.alexius.core.data.local.entity.ArticleEntity
import com.alexius.core.data.local.entity.SourceEntity
import com.alexius.core.domain.model.ArticleModel
import com.alexius.core.domain.model.SourceModel

object DataMapper {

    fun mapEntityToDomain(input: ArticleEntity): ArticleModel {
        val sourceModel = SourceModel(
            id = input.source.id,
            name = input.source.name
        )

        return ArticleModel(
            author = input.author,
            content = input.content,
            description = input.description,
            publishedAt = input.publishedAt,
            source = sourceModel,
            title = input.title,
            url = input.url,
            urlToImage = input.urlToImage,
            isBookmarked = input.isBookmarked
        )
    }

    fun mapDomainToEntity(input: ArticleModel): ArticleEntity {
        val sourceEntity = SourceEntity(
            id = input.source.id,
            name = input.source.name
        )

        return ArticleEntity(
            author = input.author,
            content = input.content,
            description = input.description,
            publishedAt = input.publishedAt,
            source = sourceEntity,
            title = input.title,
            url = input.url,
            urlToImage = input.urlToImage,
            isBookmarked = input.isBookmarked
        )
    }

}