package com.alexius.bookmark.bookmark

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.alexius.newsery2.presentation.common.ArticlesList
import com.alexius.newsery2.presentation.common.TypewriterText
import com.alexius.core.util.Dimens.MediumPadding1
import com.alexius.core.domain.model.Article

@Composable
fun BookmarkScreen(
    modifier: Modifier = Modifier.Companion,
    state: BookmarkState,
    navigateToDetails: (Article) -> Unit,
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(top = MediumPadding1, start = MediumPadding1, end = MediumPadding1)
    ) {
        TypewriterText(
            text = "Bookmark",
            fontSize = 24.sp,
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Companion.Bold),
            color = if (isSystemInDarkTheme()){
                Color(0xFFE4E6EB)
            } else {
                Color.Black
            }
        )

        Spacer(
            modifier = Modifier.Companion.height(
                MediumPadding1
            )
        )

        ArticlesList(articles = state.articles, onClick = { navigateToDetails(it) })
    }
}